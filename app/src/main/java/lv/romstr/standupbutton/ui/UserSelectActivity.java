package lv.romstr.standupbutton.ui;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.activeandroid.ActiveAndroid;

import java.util.List;

import lv.romstr.standupbutton.R;
import lv.romstr.standupbutton.db.AppDb;
import lv.romstr.standupbutton.model.StandUpTime;
import lv.romstr.standupbutton.model.User;

/**
 * Created by Roman on 22.04.16..
 */
public class UserSelectActivity extends Activity {

    private static final int ICONS_PORTRAIT = 2;
    private static final int ICONS_LANDSCAPE = 4;
    public static final String USER_ID_MESSAGE = "USER_ID_MESSAGE";
    public static final String SHOW_TIMES = "times";
    public static final String AUTO_STAND_UP = "auto";
    public static final int PORTRAIT = 1;
    public static final int LANDSCAPE = 2;


    private Activity currentActivity;
    private TableLayout layout;
    private List<User> userList;
    private int iconsInLine;
    private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentActivity = this;
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_standup);
        fab = (FloatingActionButton) findViewById(R.id.fabRandom);
        fab.setVisibility(View.GONE);
        setIconsInLine();
        layout = (TableLayout) findViewById(R.id.tlStandUp);

        drawIconTile();
    }

    @Override
    protected void onResume() {
        super.onResume();

        drawIconTile();
    }

    private void drawIconTile() {

        userList = AppDb.loadNonAttendantsFromDb();

        setTitle(R.string.title_queue);
        int iconCount = 0;
        TableRow tableRow = null;

        layout.removeAllViewsInLayout();

        for (final User user : userList) {

            if (iconCount % iconsInLine == 0) {
                tableRow = new TableRow(this);
                tableRow.setGravity(((iconCount / iconsInLine) % 2 == 0 ? Gravity.START : Gravity.END));
                layout.addView(tableRow);
            }
            UserButton userButton = new UserButton(this, user);
            userButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addUserToStandUp(user);
                }
            });

            if (tableRow != null) {
                tableRow.addView(userButton);
            }
            iconCount++;
        }
    }

    private void addUserToStandUp(User user) {
        user.setIsOnStandUp(true);
        user.setWasOnStandUp(false);
        user.save();
        finish();
    }

    private int getScreenOrientation() {
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getRotation();
        if (orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180) {
            return PORTRAIT;
        } else {
            return LANDSCAPE;
        }
    }

    private void setIconsInLine() {
        if (getScreenOrientation() == PORTRAIT) {
            iconsInLine = ICONS_PORTRAIT;
        } else {
            iconsInLine = ICONS_LANDSCAPE;
        }
    }

}
