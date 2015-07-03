package uk.ac.ebi.pride.sequence.gui.utils;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 23/02/15
 * Time: 1:59
 * To change this template use File | Settings | File Templates.
 */

/**
 * Util class to verify valid double or integer value input.
 * The value must be in specified range [min-max]
 */

public class ValueVerifier extends InputVerifier{

    private final String m_errMsg;  //Message error showed when value incoming is not valid
    private final Double min_value;
    private final Double max_value;
    private final Double default_value;


    public ValueVerifier(Double deft, Double min, Double max, String errMsg) {
        m_errMsg = errMsg;
        min_value = min;
        max_value = max;
        default_value = deft;

    }

    public boolean verify(JComponent input) {
        //...Set here any state bar...
        if (!(input instanceof JTextField))
            return true;
        JTextField txt = (JTextField)input;
        String str = txt.getText();
        try{
            Double.valueOf(str);  //is a double number?
            Double value = Double.valueOf(str);
                if (value >= min_value && value <= max_value) {
                   return true;
                }
                else{
                    JOptionPane.showMessageDialog(new JDialog(), m_errMsg);
                    //lose focus
                    //txt.setFocusable(false);
                    //ensure always a valid value (default here)
                    txt.setText(Double.toString(default_value));
                    return false;
                }

        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(new JDialog(), m_errMsg);
            //lose focus
            //txt.setFocusable(false);
            //ensure always a valid value (default here)
            txt.setText(Double.toString(default_value));
            return false;
        }
    }
}
