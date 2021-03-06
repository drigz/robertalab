package de.fhg.iais.roberta.shared.sensor.ev3;

import java.util.Locale;

import de.fhg.iais.roberta.util.dbc.DbcException;

/**
 * Modes in which the sensor can operate.
 */
public enum MotorTachoMode {
    ROTATION(), DEGREE(), RESET(), DISTANCE();

    private final String[] values;

    private MotorTachoMode(String... values) {
        this.values = values;
    }

    /**
     * get mode from {@link MotorTachoMode} from string parameter. It is possible for one mode to have multiple string mappings.
     * Throws exception if the mode does not exists.
     *
     * @param name of the mode
     * @return mode from the enum {@link MotorTachoMode}
     */
    public static MotorTachoMode get(String s) {
        if ( s == null || s.isEmpty() ) {
            throw new DbcException("Invalid mode: " + s);
        }
        String sUpper = s.trim().toUpperCase(Locale.GERMAN);
        for ( MotorTachoMode mo : MotorTachoMode.values() ) {
            if ( mo.toString().equals(sUpper) ) {
                return mo;
            }
            for ( String value : mo.values ) {
                if ( sUpper.equals(value) ) {
                    return mo;
                }
            }
        }
        throw new DbcException("Invalid mode: " + s);
    }
}