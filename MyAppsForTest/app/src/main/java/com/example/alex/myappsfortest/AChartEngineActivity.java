package com.example.alex.myappsfortest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class AChartEngineActivity extends AppCompatActivity {

    private GraphicalView mChart;
    private String[] mMonth = new String[] {
            "Jan", "Feb" , "Mar", "Apr", "May", "Jun", "Jul", "Aug" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_engine);

        OpenChart();
    }

    private void OpenChart(){
        int x[]={10, 18, 32, 21, 48, 60, 53, 80};
        XYSeries xySeries = new XYSeries("X Series");
        for(int i=0; i<8; i++){
            xySeries.add(i, x[i]);
        }
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(xySeries);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(Color.GREEN);
        renderer.setPointStyle(PointStyle.DIAMOND);
        renderer.setDisplayChartValues(true);
        renderer.setLineWidth(2);
        renderer.setFillPoints(true);

        XYMultipleSeriesRenderer multipleSeriesRenderer = new XYMultipleSeriesRenderer();
        multipleSeriesRenderer.setChartTitle("X-Y Chart");
        multipleSeriesRenderer.setXTitle("X Values");
        multipleSeriesRenderer.setYTitle("Y values");
        multipleSeriesRenderer.setZoomButtonsVisible(true);
        multipleSeriesRenderer.setXLabels(0);
        multipleSeriesRenderer.setPanEnabled(false);
        multipleSeriesRenderer.setShowGrid(true);
        multipleSeriesRenderer.setClickEnabled(true);

        for(int i=0;i<8;i++){
            multipleSeriesRenderer.addXTextLabel(i, mMonth[i]);
        }

        multipleSeriesRenderer.addSeriesRenderer(renderer);

        LinearLayout chart_container = (LinearLayout)findViewById(R.id.chart_layout);

        mChart = (GraphicalView) ChartFactory.getLineChartView(
                getBaseContext(), dataset, multipleSeriesRenderer);

        mChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeriesSelection series_selection = mChart.getCurrentSeriesAndPoint();

                if(series_selection != null){
                    int series_index = series_selection.getSeriesIndex();
                    String select_seriese = "X Series";
                    if(series_index == 0){
                        select_seriese = "X Series";
                    } else {
                        select_seriese = "Y Series";
                    }
                    String month = mMonth[(int)series_selection.getXValue()];
                    int amount = (int)series_selection.getValue();
                    Toast.makeText(getBaseContext(),
                            select_seriese + "in" + month + ":" + amount, Toast.LENGTH_LONG).show();
                }
            }
        });
        chart_container.addView(mChart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chart_engine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
