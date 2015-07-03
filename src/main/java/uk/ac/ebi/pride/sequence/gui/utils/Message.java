package uk.ac.ebi.pride.sequence.gui.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 23/02/15
 * Time: 3:09
 * To change this template use File | Settings | File Templates.
 */


/**
   This class hold some useful messages to show in GUI...
 */

public class Message {

     //Fields

     public static final String SEQUENCE_ERROR_MESSAGE =        "The AminoAcid sequence is not valid. +\n" +
                                                                "Use ONE-LETTER AminoAcid code...";

     public static final String C_FACTOR_ERROR_MESSAGE =        "The C factor parameter is not valid. +\n" +
                                                                "Enter a valid Integer number from 1 to 100...";

     public static final String SIGMA_ERROR_MESSAGE =           "The Sigma parameter is not valid. +\n" +
                                                                "Enter a valid Double number from 0.01 to 1.0...";

     public static final String FRACTION_ID_ERROR_MESSAGE =     "The Fraction ID parameter is not valid. +\n" +
                                                                "Enter a valid Integer number from 1 to 24...";

     public static final String PH_VALUE_ERROR_MESSAGE =        "The pH value is not valid. +\n" +
                                                                "Enter a valid Double number from 0.1 to 14.0";
}
