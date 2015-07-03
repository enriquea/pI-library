package uk.ac.ebi.pride.sequence.gui.utils;



import uk.ac.ebi.pride.sequence.utils.SequenceUtils;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 23/02/15
 * Time: 1:05
 * To change this template use File | Settings | File Templates.
 */


/**
 * Util class to verify whether AminoAcid sequence input is valid.
 *
 */

 public  class SequenceVerifier extends InputVerifier {

    private final String m_errMsg;  //Message error showed when sequence incoming is not valid

    public SequenceVerifier(String errMsg) {
        m_errMsg = errMsg;
    }

    public boolean verify(JComponent input) {
        //Set here any state bar...;
        if (!(input instanceof JTextField))
            return true;
        JTextField txt = (JTextField)input;
        String str = txt.getText();
        if (!SequenceUtils.validateSequence(str)) {
            JOptionPane.showMessageDialog(new JDialog(), m_errMsg);
            return false;
        }
        return true;
    }
}
