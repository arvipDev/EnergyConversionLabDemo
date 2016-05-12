package arvivtu.com.energyconversionlab;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.List;

import graph.Graph;
import service.IOption;
import service.RedwoodReportOptionsSvcImpl;

public class RedwoodResult extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout kinamaticVGraph, dynamicVGraph;
    private List<Float> kinematicViscosityList = new ArrayList<>();
    private List<Float> dynamicViscosityList = new ArrayList<>();
    private List<Float> oilDensity = new ArrayList<>();
    private List<Float> temperature = new ArrayList<>();
    private List<String> kin, tem, oil, dyn;
    private Bitmap kinGraph, dynBitmap;
    private GraphicalView kinamaticGraphView, dynamicGraphView;
    private boolean isSaybolt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redwood_result);
        //---------------------------------------------------------------------------------------------------
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflate = LayoutInflater.from(this);
        View customView = inflate.inflate(R.layout.custom_actionbar, null);

        TextView custom_ab_title = (TextView)customView.findViewById(R.id.custom_ab_title);
        custom_ab_title.setText("Result");
        custom_ab_title.setGravity(View.TEXT_ALIGNMENT_CENTER);

        ImageButton custom_iv_sample = (ImageButton)customView.findViewById(R.id.custom_iv_sample);
        custom_iv_sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Refresh Clicked!",
                        Toast.LENGTH_LONG).show();
            }
        });

        ImageButton custom_iv_return_others = (ImageButton)customView.findViewById(R.id.custom_iv_return_others);
        custom_iv_return_others.setOnClickListener(this);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(customView, layoutParams);
        actionBar.setDisplayShowCustomEnabled(true);
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        //----------------------------------------------------------------------------------------------------------

        TextView redwood_d_r1_tv1 = (TextView)findViewById(R.id.redwood_d_r1_tv1);
        redwood_d_r1_tv1.setText(Html.fromHtml("Trial<br>No"));

        TextView redwood_d_r1_tv2 = (TextView)findViewById(R.id.redwood_d_r1_tv2);
        redwood_d_r1_tv2.setText(Html.fromHtml("&gamma;<br>m<sup><small>2</small></sup>/s"));

        TextView redwood_d_r1_tv3 = (TextView)findViewById(R.id.redwood_d_r1_tv3);
        redwood_d_r1_tv3.setText(Html.fromHtml("&rho;<br>Kg/m<sup><small>3</small></sup>"));

        TextView redwood_d_r1_tv4 = (TextView)findViewById(R.id.redwood_d_r1_tv4);
        redwood_d_r1_tv4.setText(Html.fromHtml("&mu;<br>Ns/m<sup><small>2</small></sup>"));

        TextView redwood_d_r2_tv2 = (TextView)findViewById(R.id.redwood_d_r2_tv2);
        TextView redwood_d_r2_tv3 = (TextView)findViewById(R.id.redwood_d_r2_tv3);
        TextView redwood_d_r2_tv4 = (TextView)findViewById(R.id.redwood_d_r2_tv4);
        TextView redwood_d_r3_tv2 = (TextView)findViewById(R.id.redwood_d_r3_tv2);
        TextView redwood_d_r3_tv3 = (TextView)findViewById(R.id.redwood_d_r3_tv3);
        TextView redwood_d_r3_tv4 = (TextView)findViewById(R.id.redwood_d_r3_tv4);
        TextView redwood_d_r4_tv2 = (TextView)findViewById(R.id.redwood_d_r4_tv2);
        TextView redwood_d_r4_tv3 = (TextView)findViewById(R.id.redwood_d_r4_tv3);
        TextView redwood_d_r4_tv4 = (TextView)findViewById(R.id.redwood_d_r4_tv4);
        TextView redwood_d_r5_tv2 = (TextView)findViewById(R.id.redwood_d_r5_tv2);
        TextView redwood_d_r5_tv3 = (TextView)findViewById(R.id.redwood_d_r5_tv3);
        TextView redwood_d_r5_tv4 = (TextView)findViewById(R.id.redwood_d_r5_tv4);
        TextView redwood_d_r6_tv2 = (TextView)findViewById(R.id.redwood_d_r6_tv2);
        TextView redwood_d_r6_tv3 = (TextView)findViewById(R.id.redwood_d_r6_tv3);
        TextView redwood_d_r6_tv4 = (TextView)findViewById(R.id.redwood_d_r6_tv4);
        TextView redwood_d_r7_tv2 = (TextView)findViewById(R.id.redwood_d_r7_tv2);
        TextView redwood_d_r7_tv3 = (TextView)findViewById(R.id.redwood_d_r7_tv3);
        TextView redwood_d_r7_tv4 = (TextView)findViewById(R.id.redwood_d_r7_tv4);

        kinamaticVGraph = (LinearLayout)findViewById(R.id.redwood_graph_kinamatic);
        dynamicVGraph = (LinearLayout)findViewById(R.id.redwood_graph_dynamic);

        Button redwood_b_results_reportoptions = (Button)findViewById(R.id.redwood_b_results_reportoptions);
        redwood_b_results_reportoptions.setOnClickListener(this);

        Button redwood_b_results_close = (Button) findViewById(R.id.redwood_b_results_close);
        redwood_b_results_close.setOnClickListener(this);

        //----------------------------------------------------------------------------------------------------------

        Bundle bundle = getIntent().getExtras();
        isSaybolt = bundle.getBoolean("isSaybolt");
        kin = bundle.getStringArrayList("kinematicViscosityList");
        dyn = bundle.getStringArrayList("dynamicViscosityList");
        oil = bundle.getStringArrayList("oilDensity");
        tem = bundle.getStringArrayList("temperature");

        for(int i = 0; i < kin.size(); i++)
        {
            kinematicViscosityList.add(Float.valueOf(kin.get(i)));
            dynamicViscosityList.add(Float.valueOf(dyn.get(i)));
            oilDensity.add(Float.valueOf(oil.get(i)));
            temperature.add(Float.valueOf(tem.get(i)));

            Log.d("kinematicViscosityList", "" + kinematicViscosityList.get(i));
            Log.d("dynamicViscosityList", "" + dynamicViscosityList.get(i));
            Log.d("oilDensity", "" + oilDensity.get(i));
            Log.d("temperature", "" + temperature.get(i));
        }

        if(dynamicViscosityList.size() < 6)
        {
            for(int i = dynamicViscosityList.size(); i < 6; i++)
            {
                dynamicViscosityList.add(0f);
                kinematicViscosityList.add(0f);
                oilDensity.add(0f);
            }
        }

        Log.d("kin size", ""+kinematicViscosityList.size());
        Log.d("kin size", ""+temperature.size());

        redwood_d_r2_tv2.setText(""+kinematicViscosityList.get(0));
        redwood_d_r2_tv3.setText(""+oilDensity.get(0));
        redwood_d_r2_tv4.setText(""+dynamicViscosityList.get(0));
        redwood_d_r3_tv2.setText("" + kinematicViscosityList.get(1));
        redwood_d_r3_tv3.setText("" + oilDensity.get(1));
        redwood_d_r3_tv4.setText("" + dynamicViscosityList.get(1));
        redwood_d_r4_tv2.setText(""+kinematicViscosityList.get(2));
        redwood_d_r4_tv3.setText(""+oilDensity.get(2));
        redwood_d_r4_tv4.setText(""+dynamicViscosityList.get(2));
        redwood_d_r5_tv2.setText(""+kinematicViscosityList.get(3));
        redwood_d_r5_tv3.setText(""+oilDensity.get(3));
        redwood_d_r5_tv4.setText(""+dynamicViscosityList.get(3));
        redwood_d_r6_tv2.setText(""+kinematicViscosityList.get(4));
        redwood_d_r6_tv3.setText(""+oilDensity.get(4));
        redwood_d_r6_tv4.setText(""+dynamicViscosityList.get(4));
        redwood_d_r7_tv2.setText(""+kinematicViscosityList.get(5));
        redwood_d_r7_tv3.setText(""+oilDensity.get(5));
        redwood_d_r7_tv4.setText(""+dynamicViscosityList.get(5));

        if(dynamicViscosityList.size() > 1)
        {
            setGraphParams();

            /*
            kinamaticVGraph.setDrawingCacheEnabled(true);
            kinamaticVGraph.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            kinamaticVGraph.layout(0, 0, kinamaticVGraph.getMeasuredWidth(), kinamaticVGraph.getMeasuredHeight());

            kinamaticVGraph.buildDrawingCache(true);
            Log.d("is enabled", "" + kinamaticVGraph.isDrawingCacheEnabled());
            Bitmap b = Bitmap.createScaledBitmap(kinamaticVGraph.getDrawingCache(), kinamaticVGraph.getWidth(), kinamaticVGraph.getHeight(), true);
            kinamaticVGraph.setDrawingCacheEnabled(false);
            */

        }
        else Toast.makeText(this, "Can not create graph with a single reading",
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.custom_iv_return_others:
                finish();
                break;

            case R.id.redwood_b_results_reportoptions:
                if(temperature.size() > 1)
                {
                    kinGraph = kinamaticGraphView.toBitmap();
                    dynBitmap = dynamicGraphView.toBitmap();
                    Log.d("graoh bitmap", "" + kinGraph);
                    IOption resultOptions = new RedwoodReportOptionsSvcImpl(this, kinGraph, dynBitmap);
                    Bundle bundle = getIntent().getExtras();
                    resultOptions.setBundle(bundle);
                    resultOptions.createOptionsDialog();
                }
                else
                {
                    IOption redOptions = new RedwoodReportOptionsSvcImpl(this, null, null);
                    Bundle bundle = getIntent().getExtras();
                    redOptions.setBundle(bundle);
                    redOptions.createOptionsDialog();
                }
                break;

            case R.id.redwood_b_results_close:
                finish();
                break;
        }
    }

    public void setGraphParams()
    {
        Graph graph = new Graph();
        graph.setDynamicViscosity(dynamicViscosityList);
        graph.setKinamaticViscosity(kinematicViscosityList);
        graph.setTemperature(temperature);

        if(temperature.size() > 1)
        {
            kinamaticGraphView = graph.getKinamaticViscosityGraph(this);
            kinamaticVGraph.addView(kinamaticGraphView);

            dynamicGraphView = graph.getDynamicViscosityGraph(this);
            dynamicVGraph.addView(dynamicGraphView);

        }
        else
            Toast.makeText(this, "Need at least two readings to plot the graph",
                    Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_redwood_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
