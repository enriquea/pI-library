package uk.ac.ebi.pride.sequence.gui.graphs;

import com.xeiam.xchart.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 25/02/15
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */



public class DistributionGraph {

    //fields
    private static XChartPanel CHART_PANEL;  //Panel for holding Chart instance

    private static final StyleManager.ChartType BAR_CHART_TYPE = StyleManager.ChartType.Bar;

    private static final String CHART_TITLE = "Isoelectric Point Distribution";

    private static final String X_AXIS_TITLE = "Isoelectric Point";

    private static final String Y_AXIS_TITLE = "Number of Elements";

    private static final int MIN_X_AXIS_VALUE = 0;

    private static final int MAX_X_AXIS_VALUE = 14;

    private static int WIDTH = 800;

    private static int HEIGHT = 600;

    private static int bin = 100; //fit bar's shape

    private static Chart chart = null;

    private static ArrayList<ArrayList<Double>> set_arrays;  //x-data


    //constructor
    public DistributionGraph(ArrayList<ArrayList<Double>> series){

        //fitting theoretical and experimental axis range

        //double min_experimental_pi = Statistical.getMinValue(pI_experimental_set);
        //double max_experimental_pi = Statistical.getMaxValue(pI_experimental_set);
        //double min_theoretical_pi = Statistical.getMinValue(pI_theoretical_set);
        //double max_theoretical_pi = Statistical.getMaxValue(pI_theoretical_set);

        //building chart with basic information
        chart = new ChartBuilder().chartType(BAR_CHART_TYPE).width(WIDTH).height(HEIGHT).title(CHART_TITLE).xAxisTitle(X_AXIS_TITLE).yAxisTitle(Y_AXIS_TITLE).build();
        chart.getStyleManager().setLegendPosition(StyleManager.LegendPosition.OutsideE);
        chart.getStyleManager().setBarWidthPercentage(.96);
        chart.getStyleManager().setBarsOverlapped(true);

        //Initialize data series field
        set_arrays = series;
    }

    private static void buildChart(){
        Iterator it = set_arrays.iterator();
        while(it.hasNext()){
             ArrayList<Double> data = (ArrayList<Double>) it.next();
             Histogram histogram = new Histogram(data, bin, MIN_X_AXIS_VALUE, MAX_X_AXIS_VALUE);
             chart.addSeries(data.toString(), histogram.getxAxisData(), histogram.getyAxisData());
        }
    }


    public XChartPanel getChartPanel(){
        buildChart();
        if(!chart.getSeriesMap().isEmpty()){
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
