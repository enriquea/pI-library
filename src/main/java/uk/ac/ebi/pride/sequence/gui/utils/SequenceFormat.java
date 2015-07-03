package uk.ac.ebi.pride.sequence.gui.utils;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 23/02/15
 * Time: 1:05
 * To change this template use File | Settings | File Templates.
 */

/**
 * Util class to formatter AminoAcid sequence input.
 *
 */

 public class SequenceFormat extends Format {

    private final StringBuffer default_str_bff = null;

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
                               FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(toAppendTo.length());
        String str = obj.toString();

        for (int k=0; k<str.length(); k++) {
            Character nextCh = str.charAt(k);
            if (Character.isLetter(nextCh) && Character.isUpperCase(nextCh)) {
                //nextCh = Character.toUpperCase(nextCh);
                toAppendTo.append(nextCh);
            }
            else{
                return default_str_bff;
            }
        }
        fieldPosition.setEndIndex(toAppendTo.length());
        return toAppendTo;
    }

    public Object parseObject(String text, ParsePosition pos) {
        pos.setIndex(pos.getIndex()+text.length());
        return text;
    }
}
