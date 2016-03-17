package de.fhg.iais.roberta.connection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.json.JSONObject;

import de.fhg.iais.roberta.util.ORAtokenGenerator;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class NXTUSBBTConnector extends Observable implements Runnable, Connector {

    private String serverIp = "localhost";
    private String serverPort = "1999";
    private final String serverAddress;

    private static Logger log = Logger.getLogger("Connector");

    private NXTCommunicator nxtcomm;
    private ServerCommunicator servcomm;

    private JSONObject brickData = null;
    private State state = State.DISCOVER; // First state when program starts
    private String token = "";
    private boolean connected = false;
    private boolean userDisconnect = false;

    private String currentNXT = null;
    private int currentProtocol = 0;

    public NXTUSBBTConnector(ResourceBundle serverProps) {
        if ( serverProps != null ) {
            this.serverIp = serverProps.getString("serverIp");
            this.serverPort = serverProps.getString("serverPort");
        }
        this.serverAddress = this.serverIp + ":" + this.serverPort;
        log.config("Server address " + this.serverAddress);
    }

    @Override
    public boolean findRobot() {
        try {
            NXTInfo[] nxts = NXTCommunicator.discover();
            this.nxtcomm = new NXTCommunicator(nxts[0], NXTCommFactory.USB);
            this.nxtcomm.connect();
            boolean b = this.nxtcomm.isProgramRunning();
            log.info("findRobot program is running: " + b);
            this.nxtcomm.disconnect();
            return !b;
        } catch ( NullPointerException | IOException | NXTCommException e ) {
            log.info("findRobot " + e.getMessage());
            return false;
        }
    }

    private void setupServerCommunicator() {
        this.servcomm = new ServerCommunicator(this.serverAddress);
    }

    @Override
    public void run() {
        log.config("Starting NXT Connector Thread.");
        setupServerCommunicator();
        while ( true ) {
            switch ( this.state ) {
                case DISCOVER:
                    NXTInfo[] nxts = NXTCommunicator.discover();
                    if ( (nxts.length > 0) && !this.connected ) {
                        try {
                            // TODO let user choose which one to connect
                            this.nxtcomm = new NXTCommunicator(nxts[0], NXTCommFactory.USB);
                            this.nxtcomm.connect();
                            this.currentNXT = nxts[0].name;
                            this.currentProtocol = nxts[0].protocol;
                            if ( !this.nxtcomm.isProgramRunning() ) {
                                this.state = State.WAIT_FOR_CONNECT;
                                notifyConnectionStateChanged(this.state);
                                break;
                            } else {
                                this.nxtcomm.disconnect();
                            }
                        } catch ( NXTCommException | IOException e ) {
                            log.info("DISCOVER " + e.getMessage());
                        } finally {
                            try {
                                Thread.sleep(1000);
                            } catch ( InterruptedException ee ) {
                                // ok
                            }
                        }
                    }
                    break;
                case WAIT_EXECUTION:
                    this.state = State.WAIT_EXECUTION;
                    notifyConnectionStateChanged(this.state);
                    try {
                        this.nxtcomm.connect();
                        if ( !this.nxtcomm.isProgramRunning() ) {
                            this.state = State.WAIT_FOR_CMD;
                            notifyConnectionStateChanged(this.state);
                            break;
                        } else {
                            this.nxtcomm.disconnect();
                        }
                    } catch ( NXTCommException | IOException e ) {
                        log.info("WAIT_EXECUTION " + e.getMessage());
                    } finally {
                        try {
                            Thread.sleep(1000);
                        } catch ( InterruptedException ee ) {
                            // ok
                        }
                    }
                    break;
                case WAIT_FOR_CONNECT:
                    try {
                        if ( !this.nxtcomm.isProgramRunning() ) {
                            this.state = State.WAIT_FOR_CONNECT;
                            notifyConnectionStateChanged(this.state);
                            try {
                                Thread.sleep(1000);
                            } catch ( InterruptedException e ) {
                                // ok
                            }
                        } else {
                            reset(null);
                            break;
                        }
                    } catch ( RuntimeException | IOException e ) {
                        log.info("WAIT_FOR_CONNECT " + e.getMessage());
                        reset(null);
                    }
                    break;
                case CONNECT:
                    this.token = ORAtokenGenerator.generateToken();
                    this.state = State.WAIT_FOR_SERVER;
                    notifyConnectionStateChanged(this.state);
                    try {
                        this.brickData = this.nxtcomm.getDeviceInfo();
                        this.brickData.put(KEY_TOKEN, this.token);
                        this.brickData.put(KEY_CMD, CMD_REGISTER);
                    } catch ( IOException e ) {
                        log.info("CONNECT " + e.getMessage());
                        reset(State.ERROR_BRICK);
                        break;
                    }
                    try {
                        JSONObject serverResponse = this.servcomm.pushRequest(this.brickData);
                        String command = serverResponse.getString("cmd");
                        switch ( command ) {
                            case CMD_REPEAT:
                                if ( !this.connected ) {
                                    this.nxtcomm.playAscending();
                                    this.connected = true;
                                }
                                this.state = State.WAIT_FOR_CMD;
                                notifyConnectionStateChanged(this.state);
                                break;
                            case CMD_ABORT:
                                notifyConnectionStateChanged(State.TOKEN_TIMEOUT);
                                this.state = State.DISCOVER;
                                notifyConnectionStateChanged(this.state);
                                break;
                            default:
                                throw new RuntimeException("Unexpected command " + command + "from server");
                        }
                    } catch ( IOException | RuntimeException e ) {
                        log.info("CONNECT " + e.getMessage());
                        reset(State.ERROR_HTTP);
                    }
                    break;
                case WAIT_FOR_CMD:
                    if ( !this.nxtcomm.isStillConnected(this.currentNXT, this.currentProtocol) ) {
                        throw new RuntimeException("NXT connection lost");
                    }
                    try {
                        this.brickData = this.nxtcomm.getDeviceInfo(); // TODO check connection
                        this.brickData.put(KEY_TOKEN, this.token);
                        this.brickData.put(KEY_CMD, CMD_PUSH);
                    } catch ( IOException e ) {
                        log.info("WAIT_FOR_CMD " + e.getMessage());
                        reset(State.ERROR_BRICK);
                        break;
                    }
                    try {
                        switch ( this.servcomm.pushRequest(this.brickData).getString(KEY_CMD) ) {
                            case CMD_REPEAT:
                                break;
                            case CMD_DOWNLOAD:
                                log.info("Download user program");
                                try {
                                    byte[] binaryfile = this.servcomm.downloadProgram(this.brickData);
                                    String filename = this.servcomm.getFilename();
                                    File file = new File("TestNBCprogram.rxe");
                                    this.nxtcomm.uploadAndRunFile(Files.readAllBytes(file.toPath()), file.getName());
                                    this.nxtcomm.disconnect();
                                    this.state = State.WAIT_EXECUTION;
                                } catch ( IOException | InterruptedException e ) {
                                    log.info("Download and run failed: " + e.getMessage());
                                    this.state = State.WAIT_FOR_CMD;
                                }
                                break;
                            case CMD_CONFIGURATION:
                                break;
                            case CMD_UPDATE: // log and go to abort
                                log.info("Firmware updated not necessary and not supported!");
                            case CMD_ABORT: // go to default
                            default:
                                throw new RuntimeException("Unexpected response from server");
                        }
                    } catch ( RuntimeException | IOException e ) {
                        log.info("WAIT_FOR_CMD " + e.getMessage());
                        reset(State.ERROR_HTTP);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void reset(State additionalerrormessage) {
        if ( this.connected ) {
            this.nxtcomm.playDescending();
        }
        if ( (!this.userDisconnect) && (additionalerrormessage != null) ) {
            notifyConnectionStateChanged(additionalerrormessage);
        }
        this.nxtcomm.disconnect();
        this.connected = false;
        this.userDisconnect = false;
        this.state = State.DISCOVER;
        notifyConnectionStateChanged(this.state);
    }

    @Override
    public void connect() {
        this.state = State.CONNECT;
    }

    @Override
    public void disconnect() {
        this.userDisconnect = true;
        this.servcomm.abort();
    }

    @Override
    public void close() {
        disconnect();
        this.servcomm.shutdown();
    }

    @Override
    public void notifyConnectionStateChanged(State state) {
        setChanged();
        notifyObservers(state);
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public String getBrickName() {
        String brickname = this.brickData.getString("brickname");
        if ( brickname != null ) {
            return brickname;
        } else {
            return "";
        }
    }

    @Override
    public void update() {
        this.state = State.UPDATE;
    }

    @Override
    public void updateCustomServerAddress(String customServerAddress) {
        this.servcomm.updateCustomServerAddress(customServerAddress);
        log.info("Now using custom address " + customServerAddress);
    }

    @Override
    public void resetToDefaultServerAddress() {
        this.servcomm.updateCustomServerAddress(this.serverAddress);
        log.info("Now using default address " + this.serverAddress);
    }

}
