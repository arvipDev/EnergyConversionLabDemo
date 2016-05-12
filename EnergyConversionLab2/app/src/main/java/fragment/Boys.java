package fragment;

import android.annotation.SuppressLint;
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

import arvivtu.com.energyconversionlab.R;
import service.BoysReportOptionsSvcImpl;
import service.IOption;

public class Boys extends Fragment implements View.OnClickListener {

    private Float result;
    private EditText boys_tl_table_row2_c_one, boys_tl_table_row2_c_two, boys_tl_table_row2_c_three,
            boys_tl_table_row2_c_four, boys_tl_table_row2_c_five, boys_tl_table_row2_c_six;
    private Float water_flow_rate;
    private Float gasWeight_initial;
    private Float gasWeight_final;
    private Float time;

    //-----------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        final View rootView = inflater.inflate(R.layout.fragment_boys, container, false);

        //-----------------------------------------------------------------------------------------
        TextView boys_tv_abbreviations_text = (TextView) rootView.findViewById(R.id.boys_tv_abbreviations_text);
        boys_tv_abbreviations_text.setText(Html.fromHtml("CV<sub><small>gas</small></sub> = Calorific value of gas in K Cal/Kg<br>" +
                "W<sub><small>w</small></sub> = Water flow rate in Kg/sec<br>" +
                "C<sub><small>pw</small></sub> = Specefic heat of water K Cal/Kg<sup><small><small>o</small></small></sup>C<br>" +
                "W<sub><small>f</small></sub> = Gas flow rate in Kg/sec<br>" +
                "W<sub><small>1</small></sub> = Initial gas weight, W<sub><small>2</small></sub> = final gas weight in Kg<br>" +
                "T<sub><small>1</small></sub> = Inlet water temp, T<sub><small>2</small></sub> = Outlet water temp<br>" +
                "t = time for gas to reach from W<sub><small>1</small></sub> to W<sub><small>2</small></sub><br>" +
                "&Delta;T = change in temperature of cooling water in <sup><small><small>o</small></small></sup>C"));

        TextView boys_tv_formula_text = (TextView) rootView.findViewById(R.id.boys_tv_formula_text);
        boys_tv_formula_text.setText(Html.fromHtml("CV<sub><small>gas</small></sub> = " +
                "( W<sub><small>w</small></sub> * C<sub><small>pw</small></sub>" +
                " * &Delta;T ) &divide; <br> ( W<sub><small>f</small></sub> )"));

        TextView boys_tv_given_one = (TextView) rootView.findViewById(R.id.boys_tv_given_one);
        boys_tv_given_one.setText(Html.fromHtml("C<sub><small>pw</small></sub> = " +
                " 1 K Cal/Kg<sup><small><small>o</small></small></sup>C"));

        TextView boys_tl_table_row_c_one = (TextView) rootView.findViewById(R.id.boys_tl_table_row_c_one);
        boys_tl_table_row_c_one.setText(Html.fromHtml("W<sub><small>w</small></sub><br>" +
                "(LPM)"));

        TextView boys_tl_table_row_c_two = (TextView) rootView.findViewById(R.id.boys_tl_table_row_c_two);
        boys_tl_table_row_c_two.setText(Html.fromHtml("W<sub><small>1</small></sub><br>" +
                "(Kg)"));

        TextView boys_tl_table_row_c_three = (TextView) rootView.findViewById(R.id.boys_tl_table_row_c_three);
        boys_tl_table_row_c_three.setText(Html.fromHtml("W<sub><small>2</small></sub><br>" +
                "(Kg)"));

        TextView boys_tl_table_row_c_four = (TextView) rootView.findViewById(R.id.boys_tl_table_row_c_four);
        boys_tl_table_row_c_four.setText(Html.fromHtml("t<br>" +
                "(sec)"));

        TextView boys_tl_table_row_c_five = (TextView) rootView.findViewById(R.id.boys_tl_table_row_c_five);
        boys_tl_table_row_c_five.setText(Html.fromHtml("T<sub><small>1</small></sub><br>" +
                "(<sup><small><small>o</small></small></sup>C)"));

        TextView boys_tl_table_row_c_six = (TextView) rootView.findViewById(R.id.boys_tl_table_row_c_six);
        boys_tl_table_row_c_six.setText(Html.fromHtml("T<sub><small>2</small></sub><br>" +
                "(<sup><small><small>o</small></small></sup>C)"));

        Button boys_b_results = (Button) rootView.findViewById(R.id.boys_b_results);
        boys_b_results.setOnClickListener(this);

        boys_tl_table_row2_c_one = (EditText)rootView.findViewById(R.id.boys_tl_table_row2_c_one);
        boys_tl_table_row2_c_two = (EditText)rootView.findViewById(R.id.boys_tl_table_row2_c_two);
        boys_tl_table_row2_c_three = (EditText)rootView.findViewById(R.id.boys_tl_table_row2_c_three);
        boys_tl_table_row2_c_four = (EditText)rootView.findViewById(R.id.boys_tl_table_row2_c_four);
        boys_tl_table_row2_c_five = (EditText)rootView.findViewById(R.id.boys_tl_table_row2_c_five);
        boys_tl_table_row2_c_six = (EditText)rootView.findViewById(R.id.boys_tl_table_row2_c_six);

        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.boys_b_results:
                if(!boys_tl_table_row2_c_one.getText().toString().isEmpty() &&
                        !boys_tl_table_row2_c_two.getText().toString().isEmpty() &&
                        !boys_tl_table_row2_c_three.getText().toString().isEmpty() &&
                        !boys_tl_table_row2_c_four.getText().toString().isEmpty() &&
                        !boys_tl_table_row2_c_five.getText().toString().isEmpty() &&
                        !boys_tl_table_row2_c_six.getText().toString().isEmpty() )
                {
                    water_flow_rate = Float.parseFloat(boys_tl_table_row2_c_one.getText().toString());
                    gasWeight_initial = Float.parseFloat(boys_tl_table_row2_c_two.getText().toString());
                    gasWeight_final = Float.parseFloat(boys_tl_table_row2_c_three.getText().toString());
                    time = Float.parseFloat(boys_tl_table_row2_c_four.getText().toString());
                    Float temp_initial = Float.parseFloat(boys_tl_table_row2_c_five.getText().toString());
                    Float temp_final = Float.parseFloat(boys_tl_table_row2_c_six.getText().toString());

                    if(Float.valueOf(water_flow_rate.toString()) != 0f &&
                            Float.valueOf(gasWeight_initial.toString()) != 0f &&
                            Float.valueOf(gasWeight_final.toString()) != 0f &&
                            Float.valueOf(time.toString()) != 0f &&
                            Float.valueOf(temp_initial.toString()) != 0f &&
                            Float.valueOf(temp_final.toString()) != 0f )
                    {
                        result = calculation(water_flow_rate, gasWeight_initial, gasWeight_final, time, temp_initial, temp_final);
                        createResultDialog();
                    }
                    else
                        Toast.makeText(getActivity(), "Invalid entry, you entered 0 as one of the values" ,
                                Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getActivity(), "Invalid entry in the table",
                        Toast.LENGTH_LONG).show();
                break;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // All required calculations here

    public Float calculation(Float water_flow_rate, Float gasWeight_initial, Float gasWeight_final, Float time,
                             Float temp_initial, Float temp_final)
    {
        if(water_flow_rate != 0.0f && gasWeight_initial != 0.0f && gasWeight_final != 0.0f && time != 0.0f
                && temp_initial != 0.0f && temp_final != 0.0f)
        {
            return ((calculateFlowRate(water_flow_rate)) * (calculateDeltaT( temp_initial, temp_final))) /
                    (calculateGasFlowRate (gasWeight_initial, gasWeight_final, time));
        }
        return null;
    }

    public Float calculateDeltaT (Float temp_init, Float temp_final)
    {
        return (temp_final - temp_init);
    }

    public Float calculateFlowRate (Float water_flow_rate)
    {
        return (water_flow_rate/60);
    }

    public Float calculateGasFlowRate (Float initial_gas, Float final_gas, Float time)
    {
        return ((final_gas - initial_gas)/time);
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Creating the result dialog here -

    public void createResultDialog()
    {
        LayoutInflater li = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View dialogView = li.inflate(R.layout.junkers_result, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        alertDialogBuilder.setView(dialogView);

        final TextView boys_result_tv  = (TextView)dialogView.findViewById(R.id.junkers_result_tv);
        boys_result_tv.setText(Html.fromHtml("The calorific value of given gaseous fuel is " + result + " K Cal/Kg"));

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
        Button junkers_result_b_options = (Button)dialogView.findViewById(R.id.junkers_result_b_options);
        junkers_result_b_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle  bundle = new Bundle();
                bundle.putString("one", water_flow_rate.toString());
                bundle.putString("two", gasWeight_initial.toString());
                bundle.putString("three", gasWeight_final.toString());
                bundle.putString("four", time.toString());
                bundle.putString("five", time.toString());
                bundle.putString("six", time.toString());
                bundle.putString("seven", boys_result_tv.getText().toString());

                IOption reportOptions = new BoysReportOptionsSvcImpl(getActivity());
                reportOptions.setBundle(bundle);
                reportOptions.createOptionsDialog();
            }
        });

        alertDialog.show();
    }

}
