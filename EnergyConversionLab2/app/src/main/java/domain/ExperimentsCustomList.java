package domain;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import arvivtu.com.energyconversionlab.R;

public class ExperimentsCustomList extends ArrayAdapter<ECLab>
{
    private Activity context = null;
    private List<ECLab> profiles = new ArrayList<>();

    public ExperimentsCustomList(Activity context, List<ECLab> profiles)
    {
        super(context, R.layout.experiments_listview, profiles);
        this.context = context;
        this.profiles = profiles;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        final ECLab currentProfile = profiles.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.experiments_listview, null, true);

        TextView experiments_lv_tv = (TextView) rowView.findViewById(R.id.experiments_lv_tv);
        experiments_lv_tv.setText(currentProfile.getDescription());

        ImageView experiments_lv_iv = (ImageView) rowView.findViewById(R.id.experiments_lv_iv);
        experiments_lv_iv.setImageBitmap(currentProfile.getImage());
        experiments_lv_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createResultDialog(currentProfile.getImage());
            }
        });

        return rowView;
    }

    public void createResultDialog(Bitmap img)
    {
        LayoutInflater li = LayoutInflater.from(getContext());
        View dialogView = li.inflate(R.layout.explist_image, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        alertDialogBuilder.setView(dialogView);

        final ImageView explist_dialog_image  = (ImageView)dialogView.findViewById(R.id.explist_dialog_image);
        explist_dialog_image.setImageBitmap(img);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        Button explist_dialog_b_back = (Button) dialogView.findViewById(R.id.explist_dialog_b_back);
        explist_dialog_b_back.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

        alertDialog.show();
    }

}
