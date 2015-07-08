package uk.ac.ebi.pride.sequence.gui.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 10/02/15
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */



/**
 * This class model a JTable to visualize data from multiple file containing sequences
 * and Isoelectric point (if available). Useful to get info from multiple MS runs files.
 */

public class MultipleFileTableViewer extends JTable{

    //Fields
    public static JTable table = new JTable();


    //Constructor
    public MultipleFileTableViewer () {
        this.setDefaultTableModel();
        table.setVisible(true);
    }

    //methods
    public JTable getTable(){
        return table;
    }

    /**
     * This method retrieve a sequences list from table...
     */
    public static ArrayList<String> getSequenceListOfTable(){
        ArrayList<String> sequence_list = new ArrayList<String>();
        if (table.getValueAt(0,0)!= null){
            for(int i = 0; i < table.getRowCount(); i++){
                String sequence = (String)table.getValueAt(i, 0);
                sequence_list.add(i,sequence);
            }
        }
        return  sequence_list;
    }



    /**
     * This method fill the table with sequences, pI values and charge information...
     */
    public void fillDataOnTable (ArrayList<Integer> fractions, ArrayList<Integer> msRun, ArrayList<String> sequences, ArrayList<Double> array_doubles){

        DefaultTableModel current_model = (DefaultTableModel) table.getModel();

        String sequence;
        Double calcPI = 0.0;
        Double chargeAtPH = 0.0;

        int i=0;
        current_model.getDataVector().removeAllElements();
        if(sequences.size()== array_doubles.size()){
            for (Double expPI:array_doubles){
                sequence = sequences.get(i);
                Object[] tmpData = {sequence, expPI, calcPI, chargeAtPH};
                current_model.addRow(tmpData);
                i++;
            }
        }
        else{
            System.out.println("ERROR: The peptide sequence array do not have the same size of isoelectric point array.");
        }

        table.setModel(current_model);
    }


    /**
     * Setting the default model of table...
     */
    public void setDefaultTableModel(){

        table.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                },
                new String[] {
                        "Fraction","msRun()", "Sequences", "Experimental Isoelectric Point", "Calculated Isoelectric Point", "Charge at pH"
                })
        {
            Class<?>[] columnTypes = new Class<?>[] {
                    Integer.class, Integer.class, String.class, Double.class, Double.class, Double.class
            };
            boolean[] columnEditable = new boolean[] {
                     false, false, false, false, false, false
            };
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                //return columnEditable[columnIndex];
                return false;
            }
        });
        table.setCellSelectionEnabled(true);
        table.setColumnSelectionAllowed(true);
        table.setGridColor(new Color(204, 204, 204));
    }

    /**
     * Method to clear the table.
     */
    public void clearTable(){
        //Invoke defaultTableModel
        this.setDefaultTableModel();
    }


}
