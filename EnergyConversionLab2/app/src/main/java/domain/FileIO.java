package domain;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileIO{

    private Activity context;

    public FileIO(Activity context)
    {
        this.context = context;
    }

    public  void write(String filename, List<String> reports)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            for(String rep : reports)
            {
                outputStreamWriter.write(rep);
                outputStreamWriter.write(System.getProperty("line.separator"));
            }
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("", "File write failed: " + e.toString());
        }
    }

    public List<String> read(String filename)
    {
        List<String> ret = new ArrayList<>();

        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null )
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String report = "";

                while ( (report = bufferedReader.readLine()) != null )
                {
                    ret.add(report);
                }

                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public File createFile(String filename, List<String> row)
    {
        File energyConversion = new File("/sdcard/EnergyConversionLab/");
        if (!energyConversion.exists()) {
            energyConversion.mkdir();
            System.out.println("Directory created");
        }

        File report = new File(energyConversion, filename);

        try {
            FileWriter fileWriter = new FileWriter(report);
            for(String write : row)
            {
                fileWriter.write(write + "\n");
            }
            fileWriter.flush();
            fileWriter.close();

            return report;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File existingFile(String filename, List<String> row)
    {
        Log.d("inside", "fileIO");
        File externalStorageDir = new File("/sdcard/EnergyConversionLab/" + filename);

        FileWriter fileWritter = null;
        try {
            fileWritter = new FileWriter(externalStorageDir,true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            Log.d("bufferwritter", "fileIO");
            if(row != null)
            {
                for(String write : row) bufferWritter.write(write + "\n");
                bufferWritter.close();
            }
            else bufferWritter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("outside", "fileIO");
        return externalStorageDir;
    }

    public boolean deleteFile(String filename)
    {
        File energyConversion = new File("/sdcard/EnergyConversionLab/" + filename);
        boolean deleted = energyConversion.delete();
        return deleted;
    }

}

//----------------------------------------------------------------------------------------------------------------
/* .CSV file format
        File dir = new File("/sdcard/EnergyConversionLab/");

        if (!dir.exists()) {
            dir.mkdir();
            System.out.println("Directory created");
        }
        //ceate .csv file with header
        File myFile = new File(dir, filename);

        if (!myFile.exists()) {
            try {
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile.getAbsoluteFile(),true);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

                myOutWriter.write("col1,col2,col3"+"\n");

                myOutWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("File created");
        }

        String second = row.get(0) + "," + row.get(1)  + "," + row.get(2)  + "\n";
        myFile = new File(dir,filename);
        if (!myFile.exists()) {
            try {
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile.getAbsoluteFile(),true);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

                //write to CSV
                myOutWriter.write(second);
                myOutWriter.flush();
                System.out.println("write to student csv .....");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
*/