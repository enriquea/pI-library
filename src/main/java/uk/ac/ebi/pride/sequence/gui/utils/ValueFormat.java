package uk.ac.ebi.pride.sequence.gui.utils;

import java.text.*;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 23/02/15
 * Time: 1:37
 * To change this template use File | Settings | File Templates.
 */

/**
 * Util class to format Double value input.
 */

public class ValueFormat extends Format {

    private final StringBuffer default_str_bff = null;

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        pos.setBeginIndex(toAppendTo.length());
        String str = obj.toString();

        try{
            Double value = Double.valueOf(str);  //is a double number?
            String toStr = Double.toString(value);
            for (int k=0; k < toStr.length(); k++) {
                Character nextCh = toStr.charAt(k);
                if (Character.isDigit(nextCh) || nextCh.equals('.')) {
                    toAppendTo.append(nextCh);
                }
                else{
                    return default_str_bff;
                }
            }

        }catch (NumberFormatException ex){
            ex.printStackTrace();
            return default_str_bff;
        }

        pos.setEndIndex(toAppendTo.length());
        return toAppendTo;
    }

    public Object parseObject(String text, ParsePosition pos) {
        pos.setIndex(pos.getIndex()+text.length());
        return text;
    }
}
