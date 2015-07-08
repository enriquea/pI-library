package uk.ac.ebi.pride.sequence.gui.tables;

import uk.ac.ebi.pride.sequence.gui.utils.ElectrophoreticFraction;
import weka.core.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 10/02/15
 * Time: 23:44
 * To change this template use File | Settings | File Templates.
 */


public class StatisticalFractionTableViewer extends JTable{

    private static JTable table = new JTable();

    //constructor
    public StatisticalFractionTableViewer(){
          setDefaultTableModel();
          table.setVisible(true);
    }

    //methods

    public JTable getTable (){
        return table;
    }

    public void clearTable(){
        this.setDefaultTableModel();
    }

    public void fillDataOnTable(ArrayList<Double> teo_pi_values, ArrayList<Double> exp_pi_values, double start_pi, double end_pi, double step_pi){

        DefaultTableModel dm = (DefaultTableModel) table.getModel();
        dm.getDataVector().removeAllElements();
        ElectrophoreticFraction fraction;

        while (start_pi <= end_pi){

            fraction = new ElectrophoreticFraction(teo_pi_values, exp_pi_values, start_pi, (start_pi + step_pi));
            if(fraction.getCountElements() >= 2){

                String fraction_interval = String.valueOf(Utils.roundDouble(start_pi, 3)).concat("-").concat(String.valueOf(Utils.roundDouble(start_pi + step_pi, 3)));

                Object[] tmpData = new Object[]{fraction_interval,
                                                fraction.getCountElements(),
                                                fraction.getPiExpFractionMean(),
                                                fraction.getPiTeoFractionMean(),
                                                fraction.getStandardDev(),
                                                fraction.getOutliersPercent()};
                dm.addRow(tmpData);
            }
           start_pi += step_pi;
        }

        //add model to table
        table.setModel(dm);
    }

    //Default table settings
    private void setDefaultTableModel(){
        table.setModel(new DefaultTableModel(
                new Object[][] {
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
                        "Fraction", "Number of elements", "pI_mean exp", "pI_mean teo", "Standard Dev", "Outliers count"
                }
        ) {
            Class<?>[] columnTypes = new Class<?>[] {
                    Object.class, Integer.class, Double.class, Double.class, Double.class, Double.class
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
                return columnEditable[columnIndex];
            }
        });

        table.setCellSelectionEnabled(true);
        table.setColumnSelectionAllowed(true);
        table.setGridColor(new Color(204, 204, 204));
    }
}
