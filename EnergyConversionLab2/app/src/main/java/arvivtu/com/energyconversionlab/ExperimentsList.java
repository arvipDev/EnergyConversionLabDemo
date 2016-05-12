package arvivtu.com.energyconversionlab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import domain.ECLab;
import domain.ExperimentsCustomList;

public class ExperimentsList extends AppCompatActivity {

    private ListView experiments_lv;
    private List<ECLab> experiments = new ArrayList<>();
    private ExperimentsCustomList adapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experimentslist);

        //-----------------------------------------------------------------------------------------------------------------
        //Custom action bar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        LayoutInflater inflate = LayoutInflater.from(this);
        View customView = inflate.inflate(R.layout.custom_actionbar, null);

        TextView custom_ab_title = (TextView)customView.findViewById(R.id.custom_ab_title);
        custom_ab_title.setText("Energy Conversion Lab");
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
        custom_iv_return_others.setVisibility(View.GONE);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(customView, layoutParams);
        actionBar.setDisplayShowCustomEnabled(true);
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        //-------------------------------------------------------------------------------------------------------------------

        populateExp();
        experiments_lv = (ListView) findViewById(R.id.experiments_lv);
        adapter = new ExperimentsCustomList(this, experiments);
        experiments_lv.setAdapter(adapter);
        experiments_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);
                Intent experimentsActivity = new Intent("android.intent.action.EXPERIMENT");
                Bundle extras = new Bundle();
                extras.putInt("position", position);
                experimentsActivity.putExtras(extras);
                startActivity(experimentsActivity);
            }
        });

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //-------------------------------------------------
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_experiments, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    //--------------------------------------------------------------------------------------------------------
    public List<ECLab> populateExp()
    {

        ECLab exp1 = new ECLab();
        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.junkers);
        exp1.setImage(Bitmap.createBitmap(bmp1));
        exp1.setDescription("Junkers Gas Calorimeter");

        ECLab exp2 = new ECLab();
        Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.boys);
        exp2.setImage(Bitmap.createBitmap(bmp2));
        exp2.setDescription("Boys Gas Calorimeter");

        ECLab exp3 = new ECLab();
        Bitmap bmp3 = BitmapFactory.decodeResource(getResources(), R.drawable.redwood);
        exp3.setImage(Bitmap.createBitmap(bmp3));
        exp3.setDescription("Redwood Viscometer");

        ECLab exp4 = new ECLab();
        Bitmap bmp4 = BitmapFactory.decodeResource(getResources(), R.drawable.saybolt);
        exp4.setImage(Bitmap.createBitmap(bmp4));
        exp4.setDescription("Saybolt Viscometer");

        /*
        ECLab exp5 = new ECLab();
        Bitmap bmp5 = BitmapFactory.decodeResource(getResources(), R.drawable.twostroke);
        exp5.setImage(Bitmap.createBitmap(bmp5));
        exp5.setDescription("Performance test of 2 stroke, single cylinder petrol engine");
        */

        try {
            experiments.add(exp1);
            experiments.add(exp2);
            experiments.add(exp3);
            experiments.add(exp4);
            //experiments.add(exp5);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Cant add", "Dint add");
        }
        return experiments;
    }

}
