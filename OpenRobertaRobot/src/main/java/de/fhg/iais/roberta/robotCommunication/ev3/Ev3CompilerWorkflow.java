package de.fhg.iais.roberta.robotCommunication.ev3;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.fhg.iais.roberta.blockly.generated.BlockSet;
import de.fhg.iais.roberta.components.ev3.Ev3Configuration;
import de.fhg.iais.roberta.jaxb.JaxbHelper;
import de.fhg.iais.roberta.syntax.codegen.ev3.Ast2Ev3JavaVisitor;
import de.fhg.iais.roberta.syntax.codegen.ev3.Ast2Ev3PythonVisitor;
import de.fhg.iais.roberta.transformer.BlocklyProgramAndConfigTransformer;
import de.fhg.iais.roberta.transformer.Jaxb2BlocklyProgramTransformer;
import de.fhg.iais.roberta.transformer.ev3.Jaxb2Ev3ConfigurationTransformer;
import de.fhg.iais.roberta.util.Key;
import de.fhg.iais.roberta.util.dbc.Assert;

public class Ev3CompilerWorkflow {

    private static final Logger LOG = LoggerFactory.getLogger(Ev3CompilerWorkflow.class);
    private final Ev3Communicator brickCommunicator;
    public final String pathToCrosscompilerBaseDir;
    public final String crossCompilerResourcesDir;
    public final String pathToCrossCompilerBuildXMLResource;

    private enum Language {
        JAVA( ".java" ), PYTHON( ".py" );

        private String extension;

        Language(String ext) {
            this.extension = ext;
        }

        String getExtension() {
            return this.extension;
        }

        public static Language fromCommunicationData(Ev3CommunicationData state) {
            if ( state == null ) {
                return JAVA;
            }
            String fwName = state.getFirmwareName();
            return (fwName != null && fwName.equals("ev3dev")) ? PYTHON : JAVA;
        }
    }

    @Inject
    public Ev3CompilerWorkflow(
        Ev3Communicator brickCommunicator,
        @Named("crosscompiler.basedir") String pathToCrosscompilerBaseDir, //
        @Named("robot.crossCompilerResources.dir") String crossCompilerResourcesDir, //
        @Named("crosscompiler.build.xml") String pathToCrossCompilerBuildXMLResource) //
    {
        this.brickCommunicator = brickCommunicator;
        this.pathToCrosscompilerBaseDir = pathToCrosscompilerBaseDir;
        this.crossCompilerResourcesDir = crossCompilerResourcesDir;
        this.pathToCrossCompilerBuildXMLResource = pathToCrossCompilerBuildXMLResource;
    }

    /**
     * - load the program from the database<br>
     * - generate the AST<br>
     * - typecheck the AST, execute sanity checks, check a matching brick configuration<br>
     * - generate Java code<br>
     * - store the code in a token-specific (thus user-specific) directory<br>
     * - compile the code and generate a jar in the token-specific directory (use a ant script, will be replaced later)<br>
     * <b>Note:</b> the jar is prepared for upload, but not uploaded from here. After a handshake with the brick (the brick has to tell, that it is ready) the
     * jar is uploaded to the brick from another thread and then started on the brick
     *
     * @param token the credential the end user (at the terminal) and the brick have both agreed to use
     * @param programName name of the program
     * @param programText source of the program
     * @param configurationText the hardware configuration source that describes characteristic data of the robot
     * @return a message key in case of an error; null otherwise
     */
    public Key execute(String token, String programName, BlocklyProgramAndConfigTransformer data) {
        Language lang = Language.fromCommunicationData(this.brickCommunicator.getState(token));
        String sourceCode = generateProgram(lang, programName, data);

        //Ev3CompilerWorkflow.LOG.info("generated code:\n{}", sourceCode); // only needed for EXTREME debugging
        try {
            storeGeneratedProgram(token, programName, sourceCode, ".nxc");
        } catch ( Exception e ) {
            Ev3CompilerWorkflow.LOG.error("Storing the generated program into directory " + token + " failed", e);
            return Key.COMPILERWORKFLOW_ERROR_PROGRAM_STORE_FAILED;
        }
        switch ( lang ) {
            case JAVA:
                Key messageKey = runBuild(token, programName, "generated.main");
                if ( messageKey == Key.COMPILERWORKFLOW_SUCCESS ) {
                    Ev3CompilerWorkflow.LOG.info("jar for program {} generated successfully", programName);
                } else {
                    Ev3CompilerWorkflow.LOG.info(messageKey.toString());
                }
                return messageKey;
            case PYTHON:
                // maybe copy from /src/ to /target/
                // python -c "import py_compile; py_compile.compile('.../src/...py','.../target/....pyc')"
                return Key.COMPILERWORKFLOW_SUCCESS;
        }
        return null;
    }

    /**
     * - take the program given<br>
     * - generate the AST<br>
     * - typecheck the AST, execute sanity checks, check a matching brick configuration<br>
     * - generate source code in the right language for the robot<br>
     * - and return it
     *
     * @param token the credential the end user (at the terminal) and the brick have both agreed to use
     * @param programName name of the program
     * @param programText source of the program
     * @param configurationText the hardware configuration source that describes characteristic data of the robot
     * @return the generated source code; null in case of an error
     */
    public String generateSourceCode(String token, String programName, String programText, String configurationText) {
        BlocklyProgramAndConfigTransformer data = BlocklyProgramAndConfigTransformer.transform(programText, configurationText);
        if ( data.getErrorMessage() != null ) {
            return null;
        }
        Language lang = Language.fromCommunicationData(this.brickCommunicator.getState(token));
        return generateProgram(lang, programName, data);
    }

    /**
     * return the jaxb transformer for a given XML program text.
     *
     * @param blocklyXml the program XML as String
     * @return jaxb the transformer
     * @throws Exception
     */
    public static Jaxb2BlocklyProgramTransformer<Void> generateProgramTransformer(String blocklyXml) throws Exception {
        BlockSet project = JaxbHelper.xml2BlockSet(blocklyXml);
        Jaxb2BlocklyProgramTransformer<Void> transformer = new Jaxb2BlocklyProgramTransformer<>();
        transformer.transform(project);
        return transformer;
    }

    /**
     * return the brick configuration for given XML configuration text.
     *
     * @param blocklyXml the configuration XML as String
     * @return brick configuration
     * @throws Exception
     */
    public static Ev3Configuration generateConfiguration(String blocklyXml) throws Exception {
        BlockSet project = JaxbHelper.xml2BlockSet(blocklyXml);
        Jaxb2Ev3ConfigurationTransformer transformer = new Jaxb2Ev3ConfigurationTransformer();
        return transformer.transform(project);
    }

    private String generateProgram(Language lang, String programName, BlocklyProgramAndConfigTransformer data) {
        String sourceCode = "";
        switch ( lang ) {
            case JAVA:
                sourceCode = Ast2Ev3JavaVisitor.generate(programName, data.getBrickConfiguration(), data.getProgramTransformer().getTree(), true);
                break;
            case PYTHON:
                sourceCode = Ast2Ev3PythonVisitor.generate(programName, data.getBrickConfiguration(), data.getProgramTransformer().getTree(), true);
                break;
        }
        Ev3CompilerWorkflow.LOG.info("generating {} code", lang.toString().toLowerCase());
        return sourceCode;
    }

    private void storeGeneratedProgram(String token, String programName, String sourceCode, String ext) throws Exception {
        Assert.isTrue(token != null && programName != null && sourceCode != null);
        File sourceFile = new File(this.pathToCrosscompilerBaseDir + token + "/src/" + programName + ext);
        Ev3CompilerWorkflow.LOG.info("stored under: " + sourceFile.getPath());
        FileUtils.writeStringToFile(sourceFile, sourceCode, StandardCharsets.UTF_8.displayName());
    }

    /**
     * 1. Make target folder (if not exists).<br>
     * 2. Clean target folder (everything inside).<br>
     * 3. Compile .java files to .class.<br>
     * 4. Make jar from class files and add META-INF entries.<br>
     *
     * @param token
     * @param mainFile
     * @param mainPackage
     */
    Key runBuild(String token, String mainFile, String mainPackage) {
        final StringBuilder sb = new StringBuilder();
        String scriptName = "../OpenRobertaServer/src/main/resources/nbc";

        try {
            ProcessBuilder procBuilder = new ProcessBuilder(new String[] {
                scriptName,
                this.pathToCrosscompilerBaseDir + token + "/src/" + mainFile + ".nxc",
                "-O=" + this.pathToCrosscompilerBaseDir + token + "/" + mainFile + ".rxc",
                "-I=./src/main/resources/hal.h"

            });
            procBuilder.redirectInput(Redirect.INHERIT);
            procBuilder.redirectOutput(Redirect.INHERIT);
            procBuilder.redirectError(Redirect.INHERIT);
            Process p = procBuilder.start();
            int ecode = p.waitFor();
            System.err.println("Exit code " + ecode);

            if ( ecode != 0 ) {
                return Key.COMPILERWORKFLOW_ERROR_PROGRAM_COMPILE_FAILED;
            }
            return Key.COMPILERWORKFLOW_SUCCESS;
        } catch ( Exception e ) {
            if ( sb.length() > 0 ) {
                Ev3CompilerWorkflow.LOG.error("build exception. Messages from the build script are:\n" + sb.toString(), e);
            } else {
                Ev3CompilerWorkflow.LOG.error("exception when preparing the build", e);
            }
            e.printStackTrace();
            return Key.COMPILERWORKFLOW_ERROR_PROGRAM_COMPILE_FAILED;
        }
    }

}
