package lv.romstr.standupbutton.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;

import lv.romstr.standupbutton.R;

/**
 * Created by Roman on 21.04.16..
 */
public class SplashActivity extends Activity {

    private static Activity thisActivity;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setTitle(R.string.title_splash);
        thisActivity = this;
    }

    public static void close() {
        if (thisActivity != null) {
            thisActivity.finish();
        }
    }
}
