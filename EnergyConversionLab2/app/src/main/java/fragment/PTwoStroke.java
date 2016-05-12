package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import arvivtu.com.energyconversionlab.R;

public class PTwoStroke extends Fragment implements View.OnClickListener {

    private String compression_ratio;
    private float tcc, D, L, d, cylinder_capacity, rho_w, Cv, g, rho_p, Cd, rho_water, realgas_c, Pa, eta_g, Cpg;
    private TextView pts_tv_given_text;

    public PTwoStroke()
    {
        tcc = 10;
        D = 57;
        L = 57;
        d = 25;
        compression_ratio = "7.4:1";
        cylinder_capacity = 150;
        rho_w = 1000f;
        Cv = 47500;
        g = 9.81f;
        rho_p = 750;
        Cd = 0.62f;
        rho_water = 1000;
        Pa = 101325;
        realgas_c = 287;
        eta_g = 0.75f;
        Cpg = 1.005f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        final View rootView = inflater.inflate(R.layout.fragment_petrol_two_stroke, container, false);

        //--------------------------------------------------------------------------------------------------------------
        TextView pts_tv_abbreviations_text = (TextView) rootView.findViewById(R.id.pts_tv_abbreviations_text);
        pts_tv_abbreviations_text.setText(Html.fromHtml("D = Bore diameter in mm, L = Stroke length in mm <br>" +
                "d = Orifice diameter in mm, &rho;<sub><small>w</small></sub> = Water density in kg/m<sup><small>3</small></sup>, " +
                "C<sub><small>v</small></sub> = Calorific value of petrol in KJ/kg <br>" +
                "g = Acceleration due to gravity in m/sec<sup><small>2</small></sup> <br>" +
                "&rho<sub><small>p</small></sub> = Petrol density in Kg/m<sup><small>3</small></sub> <br> " +
                "t = time taken for supplying " + tcc + " cc of fuel in seconds, " +
                "m<sub><small>f</small></sub> = Mass of fuel consumed in Kg/s <br>" +
                "TFC = Total fuel consumed in Kg/Hour <br>" +
                "m<sub><small>a</small></sub> = Mass of air supplied in Kg/s <br>" +
                "&rho<sub><small>air</small></sub> = Density of air in Kg/m<sup><small>3</small></sup> <br>" +
                "BHP = Break horse power in KW <br>" +
                "Q = Heat input in Kw, SFC = Specefic fuel consumption in kg/kWHr <br>" +
                "&eta;<sub><small>vol</small></sub> = Volumetric efficiency <br>" +
                "V<sub><small>th</small></sub> = Swept volume, N = No of strokes  " +
                "&eta;<sub><small>bth</small></sub> = Break thermal efficiency <br>" +
                "C<sub><small>pg</small></sub> = Specific heat of air = KJ/Kg<sup><small><small>o</small></small></sup>C <br>" +
                "T<sub><small>a</small></sub> = Room temp,  &rho<sub><small>air</small></sub>= Density of air in Kg/m" +
                "<sup><small>3</small></sup> <br>" +
                "h<sub><small>a</small></sub> = head of air in mts " +
                "h<sub><small>manometer</small></sub> = manometer reading in mm, "  +
                "V = Voltmeter reading, I = Ammeter reading <br>" +
                "V<sub><small>a</small></sub> = actual volume of air intake, A<sub><small>o</small></sub> = area of orifice <br>" +
                "T<sub><small>a</small></sub> = Inlet air in <sup><sup><small><small>o</small></small></sup></sup>C, " +
                "T<sub><small>g</small></sub> = Exhaust air in <sup><sup><small><small>o</small></small></sup></sup>C "));

        TextView pts_tv_formula_text = (TextView) rootView.findViewById(R.id.pts_tv_formula_text);
        pts_tv_formula_text.setText(Html.fromHtml("m<sub><small>f</small></sub> = (fuel consumed in cc &times; " +
                "10<sup><small>-6</small></sup> &times; &rho;<sub><small>p</small></sub>) &divide; t in Kg/s <br>" +
                "TFC = m<sub><small>f</small></sub> &times; 60 &times; 60 in Kg/Hr <br> " +
                "m<sub><small>a</small></sub> = V<sub><small>a</small></sub> &times; &rho;<sub><small>a</small></sub>" +
                "in kg/min <br>" +
                "V<sub><small>a</small></sub> = 60C<sub><small>d</small></sub>A<sub><small>o</small></sub>" +
                "&radic;(2gh<sub><small>a</small></sub>) in m<sup><small>3</small></sup>/min <br> " +
                "A<sub><small>o</small></sub> = &pi;d<sup><small>2</small></sup> &divide; 4 <br>" +
                "&rho;<sub><small>a</small></sub> = P<sub><small>a</small></sub> &divide; RT<sub><small>a</small></sub>" +
                " in Kg/m<sup><small>3</small></sup> <br> " +
                " &rho<sub><small>air</small></sub> = P<sub><small>a</small></sub> &divide; RT<sub><small>a</small></sub> <br>" +
                "h<sub><small>a</small></sub> = h<sub><small>manometer</small></sub> &times; 1000 &times;" +
                "&rho;<sub><small>water</small></sub> &divide; &rho;<sub><small>air</small></sub> in meters <br> " +
                "BHP = (V &times; I) &divide; 1000 &times; &eta<sub><small>g</small></sub> <br> " +
                "Air - Fuel ratio = m<sub><small>a</small></sub> &divide; m<sub><small>f</small></sub> <br>" +
                "Q = m<sub><small>f</small></sub> &times; CV in kW <br>" +
                "SFC = mA<sub><small>f</small></sub> &times; 3600 &divide; BP in Kg/kWHr <br>" +
                "&eta;<sub><small>vol</small></sub> = (V<sub><small>act</small></sub> &divide; " +
                "V<sub><small>th</small></sub>) &times; 100 <br>" +
                "T<sub><small>th</small></sub> = L &times; A &times; N in m<sup><small>3</small></sup>/min <br>" +
                "A = &pi;D<sup><small>2</small></sup> &divide; 4 in m<sup><small>2</small></sup> <br>" +
                "&eta;<sub><small>bth</small></sub> = (BP &divide; Q) &times; 100 <br>" +
                "Heat carried by exhaust gases = m<sub><small>g</small></sub> &times; C<sub><small>pg</small></sub>(" +
                "T<sub><small>g</small></sub> - T<sub><small>a</small></sub>) <br>" +
                "m<sub><small>g</small></sub> = m<sub><small>a</small></sub> + m<sub><small>f</small></sub> <br>" +
                "Heat Unaccountable = 1 - (Heat equivalent to BP + Heat carried by exhaust gases)"));

        pts_tv_given_text = (TextView)rootView.findViewById(R.id.pts_tv_given_text);
        pts_tv_given_text.setText(Html.fromHtml("D = " + D + " mm, L = " + L + " mm, d = " + d + " mm, Compression ratio = " +
                compression_ratio + ", Cylinder capacity = " + cylinder_capacity + " CC, &rho;<sub><small>w</small></sub> = " +
                rho_w + " Kg/m<sup><small>3</small></sup>, C<sub><small>v</small></sub> = " + Cv + " KJ/kg, g = " + g + "" +
                " m/sec<sup><small>2</small></sup>, &rho;<sub><small>p</small></sub> = "
                + rho_p + " Kg/m<sup><small>3</small></sup>, Volume of fuel supplied = " + tcc + " CC," +
                " C<sub><small>d</small></sub> = " + Cd + ", &rho;<sub><small>water</small></sub> = " + rho_water +
                " Kg/m<sup><small>3</small></sup>, p<sub><small>a</small></sub> = " + Pa + " N/m<sup><small>2</small></sup>, " +
                "R = " + realgas_c + " J/Kg<sup><small>0</small></sup>K, &eta;<sub><small>g</small></sub> = " + eta_g + "" +
                ", C<sub><small>pg</small></sub> = " + Cpg + " J/Kg<sup><small>0</small></sup>K"));



        TextView pts_row1_1_c_1 = (TextView)rootView.findViewById(R.id.pts_row1_c_1);
        pts_row1_1_c_1.setText(Html.fromHtml("Trial<br>No"));

        TextView pts_row1_1_c_2 = (TextView)rootView.findViewById(R.id.pts_row1_c_2);
        pts_row1_1_c_2.setText(Html.fromHtml("RPM"));

        TextView pts_row1_1_c_3 = (TextView)rootView.findViewById(R.id.pts_row1_c_3);
        pts_row1_1_c_3.setText(Html.fromHtml("t<br>sec"));

        TextView pts_row1_1_c_4 = (TextView)rootView.findViewById(R.id.pts_row1_c_4);
        pts_row1_1_c_4.setText(Html.fromHtml("h<sub><small>1</small></sub><br>mm"));

        TextView pts_row1_1_c_5 = (TextView)rootView.findViewById(R.id.pts_row1_c_5);
        pts_row1_1_c_5.setText(Html.fromHtml("h<sub><small>2</small></sub><br>mm"));

        TextView pts_row1_1_c_6 = (TextView)rootView.findViewById(R.id.pts_row1_c_6);
        pts_row1_1_c_6.setText(Html.fromHtml("T<sub><small>a</small></sub><br>" +
                "<sup><small>0</small></sup>C"));

        TextView pts_row1_1_c_7 = (TextView)rootView.findViewById(R.id.pts_row1_c_7);
        pts_row1_1_c_7.setText(Html.fromHtml("T<sub><small>g</small></sub><br>" +
                "<sup><small>0</small></sup>C"));

        TextView pts_row1_1_c_8 = (TextView)rootView.findViewById(R.id.pts_row1_c_8);
        pts_row1_1_c_8.setText(Html.fromHtml("(V)"));

        TextView pts_row1_1_c_9 = (TextView)rootView.findViewById(R.id.pts_row1_c_9);
        pts_row1_1_c_9.setText(Html.fromHtml("  (I)  "));


        return rootView;
    }


    @Override
    public void onClick(View v) {

    }


}
