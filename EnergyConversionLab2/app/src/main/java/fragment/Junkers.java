package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import arvivtu.com.energyconversionlab.R;

public class Junkers extends Fragment implements View.OnClickListener {

    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private float waterDensity = 1000;
    private float gasDensity = 0.22f;
    private float speceficHeat_water = 1;
    private float waterCollected_volume = 1;

    private TextView junkers_tv_given_one, junkers_tv_given_two, junkers_tv_given_three, junkers_tv_given_four;
    private EditText junker_tl_table_row2_c_three, junker_tl_table_row2_c_two, junker_tl_table_row2_c_one;

    private String wd1 = "Density of water, &rho;<sub><small>w</small></sub> = ";
    private String wd2 = " kg/m<sup><small>3</small></sup>";
    private String wd = wd1 + waterDensity + wd2;

    private String shw1 = "Specific heat of water, C<sub><small>pw</small></sub> = ";
    private String shw2 = " K Cal/Kg K";
    private String shw = shw1 + speceficHeat_water + shw2;

    private String gd1 = "Density of gas, &rho;<sub><small>g</small></sub> = ";
    private String gd2 = " kg/m<sup><small>3</small></sup>";
    private String gd =  gd1 + gasDensity + gd2;

    private String wcv1 = "Volume of water collected V<sub><small>w</small></sub> = ";
    private String wcv2 = " litre";
    private String wcv =  wcv2 + waterCollected_volume + wcv1;

    private Float result;

    public Junkers() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        final View rootView = inflater.inflate(R.layout.fragment_junkers, container, false);

        //--------------------------------------------------------------------------------------------------------------
        //populating text in textview.

        TextView junkers_tv_formula_text = (TextView)rootView.findViewById(R.id.junkers_tv_formula_text);
        junkers_tv_formula_text.setText(Html.fromHtml("CV<sub><small>gas</small></sub> = " +
                "( V<sub><small>w</small></sub> * &rho;<sub><small>w</small></sub> * C<sub><small>pw</small></sub>" +
                " * &Delta;T ) &divide; <br> ( V<sub><small>g</small></sub> * &rho;<sub><small>g</small></sub> )"));

        junkers_tv_given_one = (TextView)rootView.findViewById(R.id.junkers_tv_given_one);
        junkers_tv_given_two = (TextView)rootView.findViewById(R.id.junkers_tv_given_two);
        junkers_tv_given_three = (TextView)rootView.findViewById(R.id.junkers_tv_given_three);
        junkers_tv_given_four = (TextView)rootView.findViewById(R.id.junkers_tv_given_four);

        populateGiven(waterDensity, gasDensity, speceficHeat_water, waterCollected_volume);

        TextView junkers_tv_given_five = (TextView)rootView.findViewById(R.id.junkers_tv_given_five);
        junkers_tv_given_five.setText(Html.fromHtml("Change in temperature, &Delta;T = ( T<sub><small>2</small></sub>" +
                " - T<sub><small>1</small></sub> )"));

        TextView junker_tl_table_row_c_one = (TextView)rootView.findViewById(R.id.junker_tl_table_row_c_one);
        junker_tl_table_row_c_one.setText(Html.fromHtml("Volume of gas burnt<br>in liter V<sub><small>g</small></sub>"));

        TextView junker_tl_table_row_c_two = (TextView)rootView.findViewById(R.id.junker_tl_table_row_c_two);
        junker_tl_table_row_c_two.setText(Html.fromHtml("Water inlet<br>temp T<sub><small>1</small></sub>" +
                "<sup><small><small>o</small></small></sup>C"));

        TextView junker_tl_table_row_c_three = (TextView)rootView.findViewById(R.id.junker_tl_table_row_c_three);
        junker_tl_table_row_c_three.setText(Html.fromHtml("Water outlet<br>temp T<sub><small>2</small></sub>" +
                "<sup><small><small>o</small></small></sup>C"));

        //-----------------------------------------------------------------------------------------------------------------------

        Button junkers_b_editgiven = (Button)rootView.findViewById(R.id.junkers_b_editgiven);
        junkers_b_editgiven.setOnClickListener(this);

        Button junkers_b_results = (Button)rootView.findViewById(R.id.junkers_b_results);
        junkers_b_results.setOnClickListener(this);

        junker_tl_table_row2_c_three = (EditText)rootView.findViewById(R.id.junker_tl_table_row2_c_three);
        junker_tl_table_row2_c_two  = (EditText)rootView.findViewById(R.id.junker_tl_table_row2_c_two);
        junker_tl_table_row2_c_one  = (EditText)rootView.findViewById(R.id.junker_tl_table_row2_c_one);

        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.junkers_b_editgiven:
                createDialog();
                populateGiven(waterDensity, gasDensity, speceficHeat_water, waterCollected_volume);
                break;

            case R.id.junkers_b_results:
                if(!junker_tl_table_row2_c_one.getText().toString().isEmpty() &&
                        !junker_tl_table_row2_c_two.getText().toString().isEmpty() &&
                        !junker_tl_table_row2_c_three.getText().toString().isEmpty() )
                {
                    Float one = Float.parseFloat(junker_tl_table_row2_c_one.getText().toString());
                    Float two = Float.parseFloat(junker_tl_table_row2_c_two.getText().toString());
                    Float three = Float.parseFloat(junker_tl_table_row2_c_three.getText().toString());
                    result = calculation(one, two, three);
                    createResultDialog();
                }
                else
                {
                    Toast.makeText(getActivity(), "Invalid entry in the table",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    public void populateGiven(Float one, Float two, Float three, Float four)
    {
        junkers_tv_given_one.setText(Html.fromHtml(wd1 + one + wd2));
        junkers_tv_given_two.setText(Html.fromHtml(gd1 + two + gd2));
        junkers_tv_given_three.setText(Html.fromHtml(shw1 + three + shw2));
        junkers_tv_given_four.setText(Html.fromHtml(wcv1 + four + wcv2));
    }

    public Float calculation(Float one, Float two, Float three)
    {
        if(one != 0.0f && two != 0.0f && three != 0.0f)
        {
            Float temp1 = (waterDensity * waterCollected_volume * speceficHeat_water * (three - two)) / (one * gasDensity);
            return temp1;
        }
        return null;
    }

    public void createDialog()
    {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View dialogView = li.inflate(R.layout.junkers_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        alertDialogBuilder.setView(dialogView);

        final EditText junkers_dialog_et_one = (EditText) dialogView
                .findViewById(R.id.junkers_dialog_et_one);

        final EditText junkers_dialog_et_two = (EditText) dialogView
                .findViewById(R.id.junkers_dialog_et_two);

        final EditText junkers_dialog_et_three = (EditText) dialogView
                .findViewById(R.id.junkers_dialog_et_three);

        final EditText junkers_dialog_et_four = (EditText) dialogView
                .findViewById(R.id.junkers_dialog_et_four);

        junkers_dialog_et_one.setHint(Html.fromHtml("&rho;<sub><small>w</small></sub>"));
        junkers_dialog_et_two.setHint(Html.fromHtml("&rho;<sub><small>g</small></sub>"));
        junkers_dialog_et_three.setHint(Html.fromHtml("C<sub><small>pw</small></sub>"));
        junkers_dialog_et_four.setHint(Html.fromHtml("V<sub><small>w</small></sub>"));

        final AlertDialog alertDialog = alertDialogBuilder.create();

        Button junkers_dialog_b_back = (Button) dialogView.findViewById(R.id.junkers_dialog_b_back);
        junkers_dialog_b_back.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.cancel();
                    }
                });

        Button junkers_dialog_b_save = (Button) dialogView.findViewById(R.id.junkers_dialog_b_save);
        junkers_dialog_b_save.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(!junkers_dialog_et_one.getText().toString().isEmpty() )
                            waterDensity = Float.valueOf(junkers_dialog_et_one.getText().toString());
                        if(!junkers_dialog_et_two.getText().toString().isEmpty() )
                            gasDensity = Float.valueOf(junkers_dialog_et_two.getText().toString());
                        if(!junkers_dialog_et_three.getText().toString().isEmpty() )
                            speceficHeat_water = Float.valueOf(junkers_dialog_et_three.getText().toString());
                        if(!junkers_dialog_et_four.getText().toString().isEmpty() )
                            waterCollected_volume = Float.valueOf(junkers_dialog_et_four.getText().toString());

                        Log.d("wd", "" + waterDensity);
                        Log.d("gasDensity", "" + gasDensity);
                        Log.d("speceficHeat_water", "" + speceficHeat_water);
                        Log.d("waterCollected_volume", "" + waterCollected_volume);
                        populateGiven(waterDensity, gasDensity, speceficHeat_water, waterCollected_volume);
                        Log.d("wd", "" + wd);
                        Log.d("gasDensity", "" + gd);
                        Log.d("speceficHeat_water", "" + shw);
                        Log.d("waterCollected_volume", "" + wcv);
                        alertDialog.cancel();
                    }
                });

        // show it
        alertDialog.show();
    }

    public void createResultDialog()
    {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View dialogView = li.inflate(R.layout.junkers_result, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        alertDialogBuilder.setView(dialogView);

        final TextView junkers_result_tv  = (TextView)dialogView.findViewById(R.id.junkers_result_tv);
        junkers_result_tv.setText(Html.fromHtml("The calorific value of given gaseous fuel is " + result + " K Cal/Kg"));

        final AlertDialog alertDialog = alertDialogBuilder.create();

        Button junkers_result_b = (Button) dialogView.findViewById(R.id.junkers_result_b);
        junkers_result_b.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.cancel();
                    }
                });

        alertDialog.show();
    }
}
