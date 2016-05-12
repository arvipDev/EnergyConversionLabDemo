package domain;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import arvivtu.com.energyconversionlab.R;

public class ExistingReportList extends ArrayAdapter<String>
{
    private Activity context = null;
    private List<String> reports = new ArrayList<>();

    public ExistingReportList(Activity context, List<String> reports)
    {
        super(context, R.layout.reports_listview, reports);
        this.context = context;
        this.reports = reports;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        final String currentReport = reports.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.reports_listview, null, true);

        TextView existing_reports_list_text = (TextView) rowView.findViewById(R.id.existing_reports_list_text);
        existing_reports_list_text.setText(currentReport);

        return rowView;
    }

}
