package graph;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

public class Graph
{
    private List<Float> kinamaticViscosity = new ArrayList<>();
    private List<Float> dynamicViscosity = new ArrayList<>();
    private List<Float>temperature = new ArrayList<>();

    public void setKinamaticViscosity(List<Float> kinamaticViscosity)
    {
        this.kinamaticViscosity = kinamaticViscosity;
    }

    public void setDynamicViscosity(List<Float> dynamicViscosity)
    {
        this.dynamicViscosity = dynamicViscosity;
    }

    public void setTemperature(List<Float> temperature)
    {
        this.temperature = temperature;
    }

    public void display()
    {
        for(int i = 0; i < temperature.size(); i++) Log.d("time",""+ temperature.get(i));
        for(int i = 0; i < dynamicViscosity.size(); i++) Log.d("dynamicViscosity",""+ dynamicViscosity.get(i));
        for(int i = 0; i < kinamaticViscosity.size(); i++) Log.d("kinamaticViscosity",""+ kinamaticViscosity.get(i));
    }

    public GraphicalView getKinamaticViscosityGraph(Context context)
    {
        TimeSeries series = new TimeSeries("Kinamatic Viscosity Curve");
        display();
        for(int i = 0; i < temperature.size(); i++)
        {
            series.add(temperature.get(i), kinamaticViscosity.get(i));
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        Log.d("dataset", "" + dataset.getSeriesCount());

        XYMultipleSeriesRenderer seriesRenderer = new XYMultipleSeriesRenderer();
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setChartValuesTextSize(10);
        renderer.setLineWidth(2);
        renderer.setColor(Color.WHITE);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(false);

        seriesRenderer.addSeriesRenderer(renderer);

        Log.d("renderer", "" + renderer.getPointStyle());
        seriesRenderer.setXTitle("Temperature");
        seriesRenderer.setAxisTitleTextSize(25);
        seriesRenderer.setYTitle("Viscosity");
        seriesRenderer.setApplyBackgroundColor(true);
        seriesRenderer.setBackgroundColor(Color.BLACK);
        seriesRenderer.setLabelsTextSize(25);
        seriesRenderer.setZoomButtonsVisible(true);
        seriesRenderer.setLabelsColor(Color.GREEN);
        seriesRenderer.setShowGrid(true);
        seriesRenderer.setGridColor(Color.BLUE);
        seriesRenderer.setXLabelsPadding(5);
        seriesRenderer.setChartTitle("Viscosity VS Temperature");
        seriesRenderer.setChartTitleTextSize(25);
        seriesRenderer.setPointSize(10);

        return ChartFactory.getLineChartView(context, dataset, seriesRenderer);
    }

    public GraphicalView getDynamicViscosityGraph(Context context)
    {
        TimeSeries series = new TimeSeries("Dynamic Viscosity Curve");
        display();
        for(int i = 0; i < temperature.size(); i++)
        {
            series.add(temperature.get(i), dynamicViscosity.get(i));
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        Log.d("dataset", "" + dataset.getSeriesCount());

        XYMultipleSeriesRenderer seriesRenderer = new XYMultipleSeriesRenderer();
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setChartValuesTextSize(10);
        renderer.setLineWidth(2);
        renderer.setColor(Color.YELLOW);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(false);

        seriesRenderer.addSeriesRenderer(renderer);

        Log.d("renderer", "" + renderer.getPointStyle());
        seriesRenderer.setXTitle("Temperature");
        seriesRenderer.setAxisTitleTextSize(25);
        seriesRenderer.setYTitle("Viscosity");
        seriesRenderer.setApplyBackgroundColor(true);
        seriesRenderer.setBackgroundColor(Color.BLACK);
        seriesRenderer.setLabelsTextSize(25);
        seriesRenderer.setLabelsColor(Color.GREEN);
        seriesRenderer.setShowGrid(true);
        seriesRenderer.setZoomButtonsVisible(true);
        seriesRenderer.setGridColor(Color.BLUE);
        seriesRenderer.setXLabelsPadding(5);
        seriesRenderer.setChartTitle("Viscosity VS Temperature");
        seriesRenderer.setChartTitleTextSize(25);
        seriesRenderer.setPointSize(10);

        return ChartFactory.getLineChartView(context, dataset, seriesRenderer);
    }

}
