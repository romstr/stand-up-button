package lv.romstr.standupbutton.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Button;

/**
 * Created by Roman on 03.04.16..
 */
public class MyPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add a button to the header list.
        if (hasHeaders()) {
            Button button = new Button(this);
            button.setText("Some action");
            setListFooter(button);
        }
    }



}
