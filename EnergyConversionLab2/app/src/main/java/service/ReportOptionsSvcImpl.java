package service;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import arvivtu.com.energyconversionlab.R;
import domain.ExistingReportList;
import domain.FileIO;

public class ReportOptionsSvcImpl implements IOption
{

    private RelativeLayout layout_options_home, layout_options_new, layout_options_existing, layout_options_delete,
            layout_options_share;
    private List<String> reportNames = new ArrayList<>();
    private ExistingReportList adapter;

    private String masterFile = "masterFile.txt";
    private Activity activity;
    private FileIO fileIO;
    private Bundle bundle;
    private File created;

    //FileSIO fileSIO;

    public ReportOptionsSvcImpl(Activity activity)
    {
        this.activity = activity;
    }

    public void fileIO()
    {
        fileIO = new FileIO(activity);
        if(fileIO.read(masterFile)!=null)
            reportNames = fileIO.read(masterFile);
    }

    public void optionsDialogLayoutView_visible(RelativeLayout visible)
    {
        visible.setVisibility(View.VISIBLE);
    }

    public void optionsDialogLayoutView_invisible()
    {
        layout_options_home.setVisibility(View.GONE);
        layout_options_new.setVisibility(View.GONE);
        layout_options_existing.setVisibility(View.GONE);
        layout_options_delete.setVisibility(View.GONE);
        layout_options_share.setVisibility(View.GONE);
    }

    public void createFile(String filename)
    {
        Log.d("createFile", "Inside");

        String one = bundle.getString("one");
        String two = bundle.getString("two");
        String three = bundle.getString("three");
        String four = bundle.getString("four");

        List<String> row1 = new ArrayList<>();
        row1.add("Experiment - Junkers gas calorimeter" + "\n");
        row1.add("Readings -");
        row1.add("Volume of gas burnt in liter Vg = " + one);
        row1.add("Water inlet temp T1 = " + two);
        row1.add("Water outlet temp T2 = " + three + "\n");
        row1.add("Result -");
        row1.add(four  + "\n");
        row1.add("-----------------------------------------------------");

        created = fileIO.createFile(filename, row1);

        Log.d("createFile", "Inside");
    }

    public void addToExistingFile(String filename)
    {
        String one = bundle.getString("one");
        String two = bundle.getString("two");
        String three = bundle.getString("three");
        String four = bundle.getString("four");

        List<String> row1 = new ArrayList<>();
        row1.add("Experiment - Junkers gas calorimeter" + "\n");
        row1.add("Readings -");
        row1.add("Volume of gas burnt in liter Vg = " + one);
        row1.add("Water inlet temp T1 = " + two);
        row1.add("Water outlet temp T2 = " + three + "\n");
        row1.add("Result -");
        row1.add(four + "\n");
        row1.add("-----------------------------------------------------");

        fileIO.existingFile(filename, row1);

    }

    public void setBundle(Bundle bundle)
    {
        this.bundle = bundle;
    }

    public void shareFile ( File created )
    {

        if(created != null || created.exists()){
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_SUBJECT, "Energy Conversion Lab Report");
            email.putExtra(Intent.EXTRA_TEXT, "");
            email.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + created.getAbsoluteFile()));
            email.setType("message/rfc822");
            activity.startActivity(Intent.createChooser(email, "Send Text File"));
        }
        else
        {
            Toast.makeText(activity, "File does not exists...",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void createOptionsDialog()
    {
        fileIO();
        LayoutInflater li = LayoutInflater.from(activity);
        final View dialogView = li.inflate(R.layout.options_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        layout_options_home = (RelativeLayout)dialogView.findViewById(R.id.layout_options_home);
        layout_options_new = (RelativeLayout)dialogView.findViewById(R.id.layout_options_new);
        layout_options_existing = (RelativeLayout)dialogView.findViewById(R.id.layout_options_existing);
        layout_options_delete = (RelativeLayout)dialogView.findViewById(R.id.layout_options_delete);
        layout_options_share = (RelativeLayout)dialogView.findViewById(R.id.layout_options_share);
        optionsDialogLayoutView_invisible();
        optionsDialogLayoutView_visible(layout_options_home);

        Button options_result_b_new = (Button)dialogView.findViewById(R.id.options_result_b_new);
        options_result_b_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                optionsDialogLayoutView_invisible();
                optionsDialogLayoutView_visible(layout_options_new);

                final EditText layout_options_new_et = (EditText) dialogView.findViewById(R.id.layout_options_new_et);

                Button layout_options_new_b = (Button) dialogView.findViewById(R.id.layout_options_new_b);
                layout_options_new_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String new_reportName = layout_options_new_et.getText().toString();
                        if (new_reportName.isEmpty() || new_reportName.equals(null)) {
                            Toast.makeText(activity, "Invalid entry - name field is empty",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            if(!reportNames.contains(new_reportName + ".txt"))
                            {
                                reportNames.add(new_reportName + ".txt");
                                fileIO.write(masterFile, reportNames);
                                createFile(new_reportName + ".txt");
                                Toast.makeText(activity, "New report created...",
                                        Toast.LENGTH_LONG).show();

                                optionsDialogLayoutView_invisible();
                                optionsDialogLayoutView_visible(layout_options_home);
                            }
                            else
                                Toast.makeText(activity, "Invalid name - file already exists",
                                        Toast.LENGTH_LONG).show();

                        }
                    }
                });

                Button layout_options_new_b_back = (Button) dialogView.findViewById(R.id.layout_options_new_b_back);
                layout_options_new_b_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsDialogLayoutView_invisible();
                        optionsDialogLayoutView_visible(layout_options_home);
                    }
                });

            }
        });

        Button options_result_b_add = (Button)dialogView.findViewById(R.id.options_result_b_add);
        options_result_b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                optionsDialogLayoutView_invisible();
                optionsDialogLayoutView_visible(layout_options_existing);

                ListView layout_options_existing_list = (ListView) dialogView.findViewById(R.id.layout_options_existing_list);
                adapter = new ExistingReportList(activity, reportNames);
                layout_options_existing_list.setAdapter(adapter);
                layout_options_existing_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        String tempName = reportNames.get(position);
                        addToExistingFile(tempName);
                        Toast.makeText(activity, "Report added to an existing report...",
                                Toast.LENGTH_LONG).show();

                        optionsDialogLayoutView_invisible();
                        optionsDialogLayoutView_visible(layout_options_home);
                    }
                });

                Button layout_options_existing_list_b_back = (Button) dialogView.findViewById(R.id.layout_options_existing_list_b_back);
                layout_options_existing_list_b_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsDialogLayoutView_invisible();
                        optionsDialogLayoutView_visible(layout_options_home);
                    }
                });
            }
        });

        Button options_result_b_delete = (Button)dialogView.findViewById(R.id.options_result_b_delete);
        options_result_b_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                optionsDialogLayoutView_invisible();
                optionsDialogLayoutView_visible(layout_options_delete);

                ListView layout_options_delete_list = (ListView) dialogView.findViewById(R.id.layout_options_delete_list);
                adapter = new ExistingReportList(activity, reportNames);
                layout_options_delete_list.setAdapter(adapter);
                layout_options_delete_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String tempName = reportNames.get(position);
                        reportNames.remove(position);
                        fileIO.write(masterFile, reportNames);
                        if (fileIO.deleteFile(tempName)) {
                            Toast.makeText(activity, "File deleted....",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(activity, "File does not exist... But removed from list",
                                    Toast.LENGTH_LONG).show();
                        }

                        optionsDialogLayoutView_invisible();
                        optionsDialogLayoutView_visible(layout_options_home);
                    }
                });

                Button layout_options_delete_list_b_back = (Button) dialogView.findViewById(R.id.layout_options_delete_list_b_back);
                layout_options_delete_list_b_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsDialogLayoutView_invisible();
                        optionsDialogLayoutView_visible(layout_options_home);
                    }
                });
            }
        });

        Button options_result_b_share = (Button)dialogView.findViewById(R.id.options_result_b_share);
        options_result_b_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsDialogLayoutView_invisible();
                optionsDialogLayoutView_visible(layout_options_share);

                ListView layout_options_share_list = (ListView) dialogView.findViewById(R.id.layout_options_share_list);
                adapter = new ExistingReportList(activity, reportNames);
                layout_options_share_list.setAdapter(adapter);
                layout_options_share_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String tempName = reportNames.get(position);
                        File tempFile = fileIO.existingFile(tempName, null);
                        shareFile(tempFile);

                        optionsDialogLayoutView_invisible();
                        optionsDialogLayoutView_visible(layout_options_home);
                    }
                });

                Button layout_options_share_list_b_back = (Button) dialogView.findViewById(R.id.layout_options_share_list_b_back);
                layout_options_share_list_b_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsDialogLayoutView_invisible();
                        optionsDialogLayoutView_visible(layout_options_home);
                    }
                });
            }
        });

        Button options_result_b_back = (Button)dialogView.findViewById(R.id.options_result_b_back);
        options_result_b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        Log.d("Exiting", "ReportOptionsSvcImpl, createOptionsDialog");
        alertDialog.show();
    }

}
