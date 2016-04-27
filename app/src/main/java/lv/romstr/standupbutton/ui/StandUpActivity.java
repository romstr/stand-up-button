package lv.romstr.standupbutton.ui;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.List;

import lv.romstr.standupbutton.R;
import lv.romstr.standupbutton.db.AppDb;
import lv.romstr.standupbutton.model.StandUpTime;
import lv.romstr.standupbutton.model.User;
import lv.romstr.standupbutton.user.StaticRandom;
import lv.romstr.standupbutton.user.UserToSpeech;

/**
 * Created by Roman on 02.04.16..
 */
public class StandUpActivity extends AppCompatActivity {

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
    private List<StandUpTime> times;
    private int iconsInLine;
    private Boolean showTimes;
    private Boolean autoStandUp;
    private TextToSpeech tts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ON_CREATE", savedInstanceState + "");
        if (savedInstanceState != null) {
            showTimes = savedInstanceState.getBoolean(SHOW_TIMES);
            autoStandUp = savedInstanceState.getBoolean(AUTO_STAND_UP);
            Log.d("ON_CREATE", showTimes + "");

        }

        currentActivity = this;
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_standup);
        setIconsInLine();
        layout = (TableLayout) findViewById(R.id.tlStandUp);

        drawScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();


        drawScreen();
        if (autoStandUp != null && autoStandUp && !userList.isEmpty()) {
            sendOnStandUp(getRandomUser());
        }

//        if (userList.isEmpty()) {
//            if (times == null || times.isEmpty()) {
//                Toast.makeText(this, R.string.no_attendants, Toast.LENGTH_SHORT).show();
//                finish();
//            } else {
//                drawTimes();
//            }
//        } else {
//            drawIconTile();
//        }
    }

    private void drawTimes() {

        showTimes = true;

        layout.removeAllViewsInLayout();
        setTitle(R.string.title_results);

        for (StandUpTime time : times) {

            User user = (User) (new Select().from(User.class).where("userId = ?", time.getUserId()).execute()).get(0);

            UserTimeItem userTimeItem = new UserTimeItem(this, user, time);
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
            tableRow.addView(userTimeItem);

            layout.addView(tableRow);
        }
    }

    private void drawIconTile() {

        showTimes = false;

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
                    sendOnStandUp(user);
                }
            });

            if (tableRow != null) {
                tableRow.addView(userButton);
            }
            iconCount++;
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            changeTimeStatus();
        }
        return super.onKeyDown(keyCode, event);

    }

    private void changeTimeStatus() {
        if (times != null) {
            Log.d("ACTIVEANDROID", "saving...");
            ActiveAndroid.beginTransaction();
            try {
                for (StandUpTime time : times) {
                    time.setDisplayed(true);
                    time.save();
                }
                ActiveAndroid.setTransactionSuccessful();
                Log.d("ACTIVEANDROID", "success");
            } finally {
                ActiveAndroid.endTransaction();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //        MenuItem mi = menu.add(0, 1, 0, "Preferences");

        MenuItem mi1 = menu.add(0, 1, 0, "Show times");
        MenuItem mi2 = menu.add(0, 1, 0, "Resume stand-up");
        MenuItem mi3 = menu.add(0, 1, 0, "Clear times");
        MenuItem mi4 = menu.add(0, 1, 0, "Start random stand-up");

//        mi.setIntent(new Intent(this, MyPreferenceActivity.class));
        mi1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showTimes = true;
                drawScreen();
                return false;
            }
        });

        mi2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showTimes = false;
                drawScreen();
                return false;
            }
        });

        mi3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                changeTimeStatus();
                drawScreen();
                return false;
            }
        });

        mi4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startRandomStandUp(null);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void startRandomStandUp(View view) {
        autoStandUp = true;
        if (userList != null && !userList.isEmpty()) {
            sendOnStandUp(getRandomUser());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("SAVE", showTimes + "");
        outState.putBoolean(SHOW_TIMES, showTimes);
        outState.putBoolean(AUTO_STAND_UP, autoStandUp == null ? false : autoStandUp);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        showTimes = savedInstanceState.getBoolean(SHOW_TIMES);
        autoStandUp = savedInstanceState.getBoolean(AUTO_STAND_UP);
        drawScreen();
        if (autoStandUp && !userList.isEmpty()) {
            sendOnStandUp(getRandomUser());
        }
        Log.d("RESOTRE", showTimes + "");

    }

    private void drawScreen() {
        userList = AppDb.loadAttendantsFromDb();
        times = AppDb.loadFreshTimesFromDb();

        if (userList.isEmpty() && times.isEmpty()) {
            Toast.makeText(this, R.string.no_attendants, Toast.LENGTH_LONG).show();
            finish();
        }

        if (showTimes == null || !showTimes) {
            if (userList.isEmpty()) {
                drawTimes();
            } else {
                drawIconTile();
            }
        } else {
            drawTimes();
        }
    }

    private User getRandomUser() {
        return userList == null ? null : userList.get(StaticRandom.rand().nextInt(userList.size()));
    }

    private void sendOnStandUp(User user) {
        user.setWasOnStandUp(true);
        user.save();

        new UserToSpeech(this).speakUserName(user);


    }

}
