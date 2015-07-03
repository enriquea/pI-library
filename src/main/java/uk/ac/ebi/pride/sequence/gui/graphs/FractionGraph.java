package uk.ac.ebi.pride.sequence.gui.graphs;

import com.xeiam.xchart.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 25/02/15
 * Time: 21:54
 * To change this template use File | Settings | File Templates.
 */

public class FractionGraph {

    //fields

    private static XChartPanel CHART_PANEL;

    private static final StyleManager.ChartType SCATTER_CHART_TYPE = StyleManager.ChartType.Scatter;

    private static final String X_AXIS_TITLE = "Experimental Isoelectric Point";

    private static final String Y_AXIS_TITLE = "Theoretical Isoelectric Point";

    private static final String CHART_TITLE = "Fraction analysis";

    private static int WIDTH = 800;

    private static int HEIGHT = 600;

    private Chart chart = null;

    private ArrayList<Double> x_array;  //x-data

    private ArrayList<Double> y_array;  //y-data

    private ArrayList<Double> error_array;  //error-y-data

    //constructor
    public FractionGraph(ArrayList<Double> input_x_array,  ArrayList<Double> input_y_array, ArrayList<Double> error){

        chart = new ChartBuilder().chartType(SCATTER_CHART_TYPE).width(WIDTH).height(HEIGHT).title(CHART_TITLE).xAxisTitle(X_AXIS_TITLE).yAxisTitle(Y_AXIS_TITLE).build();

        //customize chart
        chart.getStyleManager().setChartTitleVisible(true);
        chart.getStyleManager().setLegendVisible(true);
        chart.getStyleManager().setAxisTitlesVisible(true);

        this.x_array = input_x_array;
        this.y_array = input_y_array;
        this.error_array = error;
    }

    //methods
    public XChartPanel getChartPanel(){

        if(!x_array.isEmpty() && !y_array.isEmpty() && x_array.size()== y_array.size()){
            Series series = chart.addSeries("Fraction", x_array, y_array, error_array);
            series.setMarkerColor(Color.BLUE);
            series.setMarker(SeriesMarker.SQUARE);
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
