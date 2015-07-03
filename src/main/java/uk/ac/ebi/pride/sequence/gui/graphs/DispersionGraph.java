package uk.ac.ebi.pride.sequence.gui.graphs;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager;
import com.xeiam.xchart.XChartPanel;
import javafx.scene.chart.XYChart;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 25/02/15
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */

public class DispersionGraph {

    /*
     *The Pane holding the graph to show
     */
    private static XChartPanel CHART_PANEL;

     /*
     *Style of the graph
     */
    private static final StyleManager.ChartType SCATTER_CHART_TYPE = StyleManager.ChartType.Scatter;

    /*
    *Name of X-Axis
    */
    private static final String X_AXIS_TITLE = "Experimental Isoelectric Point";

    /*
    *Name of Y-Axis
    */
    private static final String Y_AXIS_TITLE = "Theoretical Isoelectric Point";

    /*
    *Name of the CHART
    */
    private static final String CHART_TITLE = "Isoelectric Point Dispersion";

    private int width = 800;

    private int height = 600;

    private Chart chart = null;

    /*
     *Storage X-Vector
     */
    public static  ArrayList<Double> x_array;  //x-data

    /*
     *Storage Y-Vector
     */
    public static  ArrayList<Double> y_array;  //y-data

    //constructor
    public DispersionGraph(ArrayList<Double> input_x_array,  ArrayList<Double> input_y_array){
        this.chart = new ChartBuilder().chartType(SCATTER_CHART_TYPE).width(width).height(height).title(CHART_TITLE).xAxisTitle(X_AXIS_TITLE).yAxisTitle(Y_AXIS_TITLE).build();
        x_array = input_x_array;
        y_array = input_y_array;
    }

    //methods
    public XChartPanel getChartPanel(){

        if(!x_array.isEmpty() && !y_array.isEmpty() && x_array.size()==y_array.size()){
            this.chart.addSeries("Sequences", x_array, y_array);
            CHART_PANEL = new XChartPanel(chart);
            CHART_PANEL.setAutoscrolls(true);
            CHART_PANEL.setBackground(Color.WHITE);
        }
        else{
            return null; //any fail action
        }
        return CHART_PANEL;
    }

}
