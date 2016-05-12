package service;

import android.os.Bundle;
import android.widget.RelativeLayout;

public interface IOption
{
    void fileIO();
    void optionsDialogLayoutView_visible(RelativeLayout visible);
    void optionsDialogLayoutView_invisible();
    void createFile(String filename);
    void addToExistingFile(String filename);
    void setBundle(Bundle bundle);
    void createOptionsDialog();
}
