package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class Boys extends Fragment implements View.OnClickListener {
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private TextView boys_tv_abbreviations_text, boys_tv_formula_text, boys_tv_given_one;
    private TextView boys_tl_table_row_c_one, boys_tl_table_row_c_two, boys_tl_table_row_c_three, boys_tl_table_row_c_four,
            boys_tl_table_row_c_five, boys_tl_table_row_c_six;

    private Button boys_b_results;

    private Float result;

    private EditText boys_tl_table_row2_c_one, boys_tl_table_row2_c_two, boys_tl_table_row2_c_three,
            boys_tl_table_row2_c_four, boys_tl_table_row2_c_five, boys_tl_table_row2_c_six;

    private Float water_flow_rate, gasWeight_initial, gasWeight_final, time, temp_initial, temp_final;

    public Boys() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        final View rootView = inflater.inflate(R.layout.fragment_boys, container, false);

        //-----------------------------------------------------------------------------------------
        boys_tv_abbreviations_text = (TextView)rootView.findViewById(R.id.boys_tv_abbreviations_text);
        boys_tv_abbreviations_text.setText(Html.fromHtml("CV<sub><small>gas</small></sub> = Calorific value of gas in K Cal/Kg<br>" +
                "W<sub><small>w</small></sub> = Water flow rate in Kg/sec<br>" +
                "C<sub><small>pw</small></sub> = Specefic heat of water K Cal/Kg<sup><small><small>o</small></small></sup>C<br>" +
                "W<sub><small>f</small></sub> = Gas flow rate in Kg/sec<br>" +
                "W<sub><small>1</small></sub> = Initial gas weight, W<sub><small>2</small></sub> = final gas weight in Kg<br>" +
                "T<sub><small>1</small></sub> = Inlet water temp, T<sub><small>2</small></sub> = Outlet water temp<br>" +
                "t = time for gas to reach from W<sub><small>1</small></sub> to W<sub><small>2</small></sub><br>" +
                "&Delta;T = change in temperature of cooling water in <sup><small><small>o</small></small></sup>C"));

        boys_tv_formula_text = (TextView)rootView.findViewById(R.id.boys_tv_formula_text);
        boys_tv_formula_text.setText(Html.fromHtml("CV<sub><small>gas</small></sub> = " +
                "( W<sub><small>w</small></sub> * C<sub><small>pw</small></sub>" +
                " * &Delta;T ) &divide; <br> ( W<sub><small>f</small></sub> )"));

        boys_tv_given_one = (TextView)rootView.findViewById(R.id.boys_tv_given_one);
        boys_tv_given_one.setText(Html.fromHtml("C<sub><small>pw</small></sub> = " +
                " 1 K Cal/Kg<sup><small><small>o</small></small></sup>C"));

        boys_tl_table_row_c_one = (TextView)rootView.findViewById(R.id.boys_tl_table_row_c_one);
        boys_tl_table_row_c_one.setText(Html.fromHtml("W<sub><small>w</small></sub><br>" +
                "(LPM)"));

        boys_tl_table_row_c_two = (TextView)rootView.findViewById(R.id.boys_tl_table_row_c_two);
        boys_tl_table_row_c_two.setText(Html.fromHtml("W<sub><small>1</small></sub><br>" +
                "(Kg)"));

        boys_tl_table_row_c_three = (TextView)rootView.findViewById(R.id.boys_tl_table_row_c_three);
        boys_tl_table_row_c_three.setText(Html.fromHtml("W<sub><small>2</small></sub><br>" +
                "(Kg)"));

        boys_tl_table_row_c_four = (TextView)rootView.findViewById(R.id.boys_tl_table_row_c_four);
        boys_tl_table_row_c_four.setText(Html.fromHtml("t<br>" +
                "(sec)"));

        boys_tl_table_row_c_five = (TextView)rootView.findViewById(R.id.boys_tl_table_row_c_five);
        boys_tl_table_row_c_five.setText(Html.fromHtml("T<sub><small>1</small></sub><br>" +
                "(<sup><small><small>o</small></small></sup>C)"));

        boys_tl_table_row_c_six = (TextView)rootView.findViewById(R.id.boys_tl_table_row_c_six);
        boys_tl_table_row_c_six.setText(Html.fromHtml("T<sub><small>1</small></sub><br>" +
                "(<sup><small><small>o</small></small></sup>C)"));

        boys_b_results = (Button)rootView.findViewById(R.id.boys_b_results);
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
                    temp_initial = Float.parseFloat(boys_tl_table_row2_c_five.getText().toString());
                    temp_final = Float.parseFloat(boys_tl_table_row2_c_six.getText().toString());
                    result = calculation();
                    createResultDialog();
                }
                else
                    Toast.makeText(getActivity(), "Invalid entry in the table",
                        Toast.LENGTH_LONG).show();
                break;
        }
    }

    public Float calculation()
    {
        if(water_flow_rate != 0.0f && gasWeight_initial != 0.0f && gasWeight_final != 0.0f && time != 0.0f
                && temp_initial != 0.0f && temp_final != 0.0f)
        {
            Float temp1 = ((water_flow_rate/60) * (temp_final - temp_initial)) / ((gasWeight_final - gasWeight_initial)/time);
            return temp1;
        }
        return null;
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
