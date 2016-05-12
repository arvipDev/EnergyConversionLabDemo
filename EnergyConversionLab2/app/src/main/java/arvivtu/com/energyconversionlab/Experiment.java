package arvivtu.com.energyconversionlab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import fragment.Boys;
import fragment.Junkers;
import fragment.Redwood;


public class Experiment extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout experiment_container;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        //---------------------------------------------------------------------------------------------------
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflate = LayoutInflater.from(this);
        View customView = inflate.inflate(R.layout.custom_actionbar, null);

        TextView custom_ab_title = (TextView)customView.findViewById(R.id.custom_ab_title);
        custom_ab_title.setText("Experiment");
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

        Bundle bundle = getIntent().getExtras();
        pos = bundle.getInt("position");
        Log.d("pos", "" + pos);

        experiment_container = (FrameLayout)findViewById(R.id.experiment_container);
        experimentPicker(pos);

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.custom_iv_return_others:
                finish();
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_experiment, menu);
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

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    //-------------------------------------------------------------------------------------------------------

    private void experimentPicker(int position)
    {
        switch(position)
        {
            case 0:
                Fragment fragment = new Junkers();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().
                        replace(R.id.experiment_container, fragment);
                fragmentTransaction.commit();
                break;
            case 1:
                Fragment fragment2 = new Boys();
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction().
                        replace(R.id.experiment_container, fragment2);
                fragmentTransaction2.commit();
                break;
            case 2:
                Fragment fragment3 = new Redwood();
                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction().
                        replace(R.id.experiment_container, fragment3);
                fragmentTransaction3.commit();
                break;
        }
    }

}
