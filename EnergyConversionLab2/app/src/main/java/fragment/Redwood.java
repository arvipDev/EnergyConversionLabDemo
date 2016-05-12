package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import arvivtu.com.energyconversionlab.R;

public class Redwood extends Fragment implements View.OnClickListener {

    private EditText trial1_c2, trial1_c3, trial1_c4, trial1_c5;
    private EditText trial2_c2, trial2_c3, trial2_c4, trial2_c5;
    private EditText trial3_c2, trial3_c3, trial3_c4, trial3_c5;
    private EditText trial4_c2, trial4_c3, trial4_c4, trial4_c5;
    private EditText trial5_c2, trial5_c3, trial5_c4, trial5_c5;
    private EditText trial6_c2, trial6_c3, trial6_c4, trial6_c5;

    private List<Float> temperature = new ArrayList<>();
    private List<Float> time = new ArrayList<>();
    private List<Float> flask_weight = new ArrayList<>();
    private List<Float> flask_oil_weight = new ArrayList<>();

    private List<Float> kinematicViscosityList = new ArrayList<>();
    private List<Float> dynamicViscosityList = new ArrayList<>();
    private List<Float> oilDensity = new ArrayList<>();

    private Float ml = 50f;
    private Float A1 = 0.264f;
    private Float A2 = 0.247f;
    private Float B1 = 190f;
    private Float B2 = 65f;
    private int T1 = 40;
    private int T2 = 85;
    private int T3 = 85;
    private int T4 = 2000;

    private TextView redwood_tv_given_one, redwood_tv_formula_text, redwood_tv_abbreviations_text ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        final View rootView = inflater.inflate(R.layout.fragment_redwood, container, false);

        //--------------------------------------------------------------------------------------------------------------
        //populating text in textview.
        redwood_tv_abbreviations_text = (TextView) rootView.findViewById(R.id.redwood_tv_abbreviations_text);
        redwood_tv_abbreviations_text.setText(Html.fromHtml("&gamma; = Kinematic viscosity in m<sup><small>2</small></sup>/s<br>" +
                "&mu; = Dynamic(Absolute) viscosity in Ns/m<sup><small>2</small></sup><br>" +
                "&rho; = Density of given oil in Kg/m<sup><small>3</small></sup><br>" +
                "W<sub><small>1</small></sub> = Weight of measuring jar in grams<br>" +
                "W<sub><small>2</small></sub> = Weight of measuring jar and the fluid in grams<br>" +
                "t = Time for collecting 50 ml of oil in sec<br>" +
                "A and B are instrument constants<br>" +
                "T = temperature in <sup><small><small>o</small></small></sup>C<br>"));

        redwood_tv_formula_text = (TextView) rootView.findViewById(R.id.redwood_tv_formula_text);
        redwood_tv_formula_text.setText(Html.fromHtml("&gamma; = (At - (B &divide; t))*10<sup><small>-6</small></sup> (" +
                "m<sup><small>2</small></sup>/s)<br>" +
                "&rho; = ((W<sub><small>2</small></sub> - W<sub><small>1</small></sub>)&divide; 50 ml )*" +
                "10<sup><small>3</small></sup> (Kg/m<sup><small>3</small></sup>)<br>" +
                "&mu; = &gamma; * &rho; in Ns/m<sup><small>2</small></sup>"));

        redwood_tv_given_one = (TextView) rootView.findViewById(R.id.redwood_tv_given_one);
        populateGiven();

        TextView redwood_tl_table_row_c_one = (TextView) rootView.findViewById(R.id.redwood_tl_table_row_c_one);
        redwood_tl_table_row_c_one.setText(Html.fromHtml("Trial<br>No"));

        TextView redwood_tl_table_row_c_two = (TextView) rootView.findViewById(R.id.redwood_tl_table_row_c_two);
        redwood_tl_table_row_c_two.setText(Html.fromHtml("T<br> (<sup><small><small>o</small></small></sup>C)"));

        TextView redwood_tl_table_row_c_three = (TextView) rootView.findViewById(R.id.redwood_tl_table_row_c_three);
        redwood_tl_table_row_c_three.setText(Html.fromHtml("t<br>(sec)"));

        TextView redwood_tl_table_row_c_four = (TextView) rootView.findViewById(R.id.redwood_tl_table_row_c_four);
        redwood_tl_table_row_c_four.setText(Html.fromHtml("W<sub><small>1</small></sub><br>(gms)"));

        TextView redwood_tl_table_row_c_five = (TextView) rootView.findViewById(R.id.redwood_tl_table_row_c_five);
        redwood_tl_table_row_c_five.setText(Html.fromHtml("W<sub><small>2</small></sub><br>(gms)"));

        Button redwood_b_results = (Button) rootView.findViewById(R.id.redwood_b_results);
        redwood_b_results.setOnClickListener(this);

        trial1_c2 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row2_c_two);
        trial1_c3 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row2_c_three);
        trial1_c4 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row2_c_four);
        trial1_c5 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row2_c_five);

        trial2_c2 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row2_2_c_two);
        trial2_c3 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row2_2_c_three);
        trial2_c4 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row2_2_c_four);
        trial2_c5 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row2_2_c_five);

        trial3_c2 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row3_c_two);
        trial3_c3 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row3_c_three);
        trial3_c4 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row3_c_four);
        trial3_c5 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row3_c_five);

        trial4_c2 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row4_c_two);
        trial4_c3 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row4_c_three);
        trial4_c4 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row4_c_four);
        trial4_c5 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row4_c_five);

        trial5_c2 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row5_c_two);
        trial5_c3 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row5_c_three);
        trial5_c4 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row5_c_four);
        trial5_c5 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row5_c_five);

        trial6_c2 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row6_c_two);
        trial6_c3 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row6_c_three);
        trial6_c4 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row6_c_four);
        trial6_c5 = (EditText)rootView.findViewById(R.id.reedwood_tl_table_row6_c_five);

        Button redwood_b_editgiven = (Button) rootView.findViewById(R.id.redwood_b_editgiven);
        redwood_b_editgiven.setOnClickListener(this);

        //----------------------------------------------------------------------------------------------------------------------

        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.redwood_b_editgiven:
                createDialog();
                populateGiven();
                break;

            case R.id.redwood_b_results:

                clearTableList();

                checkTab_populateList(trial1_c2, trial1_c3, trial1_c4, trial1_c5);
                checkTab_populateList(trial2_c2, trial2_c3, trial2_c4, trial2_c5);
                checkTab_populateList(trial3_c2, trial3_c3, trial3_c4, trial3_c5);
                checkTab_populateList(trial4_c2, trial4_c3, trial4_c4, trial4_c5);
                checkTab_populateList(trial5_c2, trial5_c3, trial5_c4, trial5_c5);
                checkTab_populateList(trial6_c2, trial6_c3, trial6_c4, trial6_c5);

                Boolean error = false;

                for (int i = 0; i < time.size(); i++){
                    if(time.get(i) < T1) {
                        Toast.makeText(getActivity(), "Invalid time entered at trial " + i,
                                Toast.LENGTH_LONG).show();
                        error = true;
                        break;
                    }
                }

                if(!error)
                {
                    calculateResults();
                    if(dynamicViscosityList.size() == 0)
                        Toast.makeText(getActivity(), "Empty table, tabulate readings to generate result",
                                Toast.LENGTH_LONG).show();
                    else
                    {

                        ArrayList<String> kinString = new ArrayList<>();
                        ArrayList<String> dynString = new ArrayList<>();
                        ArrayList<String> oilString = new ArrayList<>();
                        ArrayList<String> temString = new ArrayList<>();
                        //------------------------------------------------------
                        ArrayList<String> timeString = new ArrayList<>();
                        ArrayList<String> flask_weightString = new ArrayList<>();
                        ArrayList<String> flask_oil_weightString = new ArrayList<>();

                        for(int i = 0; i < kinematicViscosityList.size(); i++)
                        {

                            kinString.add(kinematicViscosityList.get(i).toString());
                            dynString.add(dynamicViscosityList.get(i).toString());
                            oilString.add(oilDensity.get(i).toString());
                            temString.add(temperature.get(i).toString());
                            //------------------------------------------------------
                            timeString.add(time.get(i).toString());
                            flask_weightString.add(flask_oil_weight.get(i).toString());
                            flask_oil_weightString.add(flask_oil_weight.get(i).toString());

                        }

                        Intent redwoodResult = new Intent("android.intent.action.REDWOODRESULT");
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("kinematicViscosityList", kinString);
                        bundle.putStringArrayList("dynamicViscosityList", dynString);
                        bundle.putStringArrayList("oilDensity", oilString);
                        bundle.putStringArrayList("temperature", temString);
                        //----------------------------------------------------------
                        bundle.putStringArrayList("time", timeString);
                        bundle.putStringArrayList("flask_oil_weight", flask_weightString);
                        bundle.putStringArrayList("flask_oil_weight", flask_oil_weightString);

                        redwoodResult.putExtras(bundle);
                        startActivity(redwoodResult);

                    }
                }
        }
    }

    public void clearTableList()
    {
        temperature.clear();
        time.clear();
        flask_oil_weight.clear();
        flask_weight.clear();
        kinematicViscosityList.clear();
        dynamicViscosityList.clear();
        oilDensity.clear();
    }

    public Boolean checkTab_populateList(TextView x1, TextView x2, TextView x3, TextView x4)
    {
        if(!x1.getText().toString().isEmpty() &&
                !x2.getText().toString().isEmpty() &&
                !x3.getText().toString().isEmpty() &&
                !x4.getText().toString().isEmpty())
        {
            temperature.add(Float.parseFloat(x1.getText().toString()));
            time.add(Float.parseFloat(x2.getText().toString()));
            flask_weight.add(Float.parseFloat(x3.getText().toString()));
            flask_oil_weight.add(Float.parseFloat(x4.getText().toString()));

            return true;
        }
        else
            return false;

    }

    public void calculateResults()
    {
        for(int i = 0; i < temperature.size(); i++)
        {
            Float t = time.get(i);
            Float w1 = flask_weight.get(i);
            Float w2 = flask_oil_weight.get(i);
            if(t >= T1 && t <= T2)kinematicViscosityList.add(calculatekinematicViscosity(t, A1, B1));
            else if(t > T3 && t <= T4)kinematicViscosityList.add(calculatekinematicViscosity(t, A2, B2));
            else
            {
                Toast.makeText(getActivity(), "Invalid time",
                        Toast.LENGTH_LONG).show();
                return;
            }
            oilDensity.add(calculateDensity(w1, w2));
            dynamicViscosityList.add(calculateDynamicViscosity(oilDensity.get(i), kinematicViscosityList.get(i)));
        }
    }

    public Float calculateDynamicViscosity(Float density, Float kv)
    {
        return (density * kv);
    }

    public Float calculatekinematicViscosity(Float time, Float A, Float B)
    {
        return ((A*time) - (B/time)) ;
    }

    public Float calculateDensity(Float w1, Float w2)
    {
        return (((w2 - w1)/ml) * 1000);
    }

    private void populateGiven()
    {
        redwood_tv_given_one.setText(Html.fromHtml("A<sub><small>1</small></sub> = " + A1 + ", B<sub><small>1</small></sub> = " + B1 + " if t = " + T1 + " - " + T2 + " sec<br>" +
                "A<sub><small>2</small></sub>= " + A2 + ", B<sub><small>2</small></sub> = " + B2 + " if t = " + T3 + " - " + T4 + " sec<br>" +
                "Amount of oil collected = " + ml + " ml"));

        redwood_tv_formula_text.setText(Html.fromHtml("&gamma; = At - (B &divide; t))*10<sup><small>-6</small></sup> (" +
                "m<sup><small>2</small></sup>/s)<br>" +
                "&rho; = ((W<sub><small>2</small></sub> - W<sub><small>1</small></sub>)&divide; " + ml + " )*" +
                "10<sup><small>3</small></sup> (Kg/m<sup><small>3</small></sup>)<br>" +
                "&mu; = &gamma; * &rho; in Ns/m<sup><small>2</small></sup>"));

        redwood_tv_abbreviations_text.setText(Html.fromHtml("&gamma; = Kinematic viscosity in m<sup><small>2</small></sup>/s<br>" +
                "&mu; = Dynamic(Absolute) viscosity in Ns/m<sup><small>2</small></sup><br>" +
                "&rho; = Density of given oil in Kg/m<sup><small>3</small></sup><br>" +
                "W<sub><small>1</small></sub> = Weight of measuring jar in grams<br>" +
                "W<sub><small>2</small></sub> = Weight of measuring jar and the fluid in grams<br>" +
                "t = Time for collecting " + ml + " ml of oil in sec<br>" +
                "A and B are instrument constants<br>" +
                "T = temperature in <sup><small><small>o</small></small></sup>C<br>"));
    }

    public void createDialog()
    {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View dialogView = li.inflate(R.layout.redwood_editconstants, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        alertDialogBuilder.setView(dialogView);

        final EditText redwood_dialog_et_one = (EditText) dialogView
                .findViewById(R.id.redwood_dialog_et_one);

        final EditText redwood_dialog_et_two = (EditText) dialogView
                .findViewById(R.id.redwood_dialog_et_two);

        final EditText redwood_dialog_et_two2 = (EditText) dialogView
                .findViewById(R.id.redwood_dialog_et_two2);

        final EditText redwood_dialog_et_two3 = (EditText) dialogView
                .findViewById(R.id.redwood_dialog_et_two3);

        final EditText redwood_dialog_et_three = (EditText) dialogView
                .findViewById(R.id.redwood_dialog_et_three);

        final EditText redwood_dialog_et_four = (EditText) dialogView
                .findViewById(R.id.redwood_dialog_et_four);

        final EditText redwood_dialog_et_four5 = (EditText) dialogView
                .findViewById(R.id.redwood_dialog_et_four5);

        final EditText redwood_dialog_et_four6 = (EditText) dialogView
                .findViewById(R.id.redwood_dialog_et_four6);

        final EditText redwood_dialog_et_edittext = (EditText) dialogView
                .findViewById(R.id.redwood_dialog_et_edittext);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        Button redwood_dialog_b_back = (Button) dialogView.findViewById(R.id.redwood_dialog_b_back);
        redwood_dialog_b_back.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.cancel();
                    }
                });

        Button redwood_dialog_b_save = (Button) dialogView.findViewById(R.id.redwood_dialog_b_save);
        redwood_dialog_b_save.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(!redwood_dialog_et_one.getText().toString().isEmpty() && Float.valueOf(redwood_dialog_et_one.getText().
                                toString()) != 0f )
                            A1 = Float.valueOf(redwood_dialog_et_one.getText().toString());
                        if(!redwood_dialog_et_two.getText().toString().isEmpty() && Float.valueOf(redwood_dialog_et_two.getText().
                                toString()) != 0f )
                            B2 = Float.valueOf(redwood_dialog_et_two.getText().toString());
                        if(!redwood_dialog_et_two2.getText().toString().isEmpty() && Float.valueOf(redwood_dialog_et_two2.getText().
                                toString()) != 0f )
                            T1 = Integer.valueOf(redwood_dialog_et_two2.getText().toString());
                        if(!redwood_dialog_et_two3.getText().toString().isEmpty() && Float.valueOf(redwood_dialog_et_two3.getText().
                                toString()) != 0f)
                            T2 = Integer.valueOf(redwood_dialog_et_two3.getText().toString());
                        if(!redwood_dialog_et_three.getText().toString().isEmpty() && Float.valueOf(redwood_dialog_et_three.getText().
                                toString()) != 0f)
                            A2 = Float.valueOf(redwood_dialog_et_three.getText().toString());
                        if(!redwood_dialog_et_four.getText().toString().isEmpty() && Float.valueOf(redwood_dialog_et_four.getText().
                                toString()) != 0f )
                            B2 = Float.valueOf(redwood_dialog_et_four.getText().toString());
                        if(!redwood_dialog_et_four5.getText().toString().isEmpty()&& Float.valueOf(redwood_dialog_et_four5.getText().
                                toString()) != 0f )
                            T3 = Integer.valueOf(redwood_dialog_et_four5.getText().toString());
                        if(!redwood_dialog_et_four6.getText().toString().isEmpty() && Float.valueOf(redwood_dialog_et_four6.getText().
                                toString()) != 0f)
                            T4 = Integer.valueOf(redwood_dialog_et_four6.getText().toString());
                        if(!redwood_dialog_et_edittext.getText().toString().isEmpty() && Float.valueOf(redwood_dialog_et_edittext.getText().
                                toString()) != 0f )
                            ml = Float.valueOf(redwood_dialog_et_edittext.getText().toString());
                        populateGiven();
                        alertDialog.cancel();
                    }
                });

        alertDialog.show();
    }
}
