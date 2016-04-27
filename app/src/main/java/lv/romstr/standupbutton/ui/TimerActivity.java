package lv.romstr.standupbutton.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import lv.romstr.standupbutton.R;
import lv.romstr.standupbutton.model.StandUpTime;
import lv.romstr.standupbutton.model.User;

/**
 * Created by Roman on 02.04.16..
 */
public class TimerActivity extends AppCompatActivity {

    private static final Long SPEECH_TIME = 60 * 1000L; //60s
    private static final Long COUNT_DOWN_STEP = 1000L; //1s
    private static final Long HALF_TIME = SPEECH_TIME / 2;
    private static final Long WARNING_TIME = 5 * 1000L; //5s

    private static Long elapsedTime;

    private TextView timerView;
    private CountDownTimer timer;
    private static CountDownTimer alertTimer;
    private Context context;
    private LinearLayout layoutMain;
    private User user;
    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_timer);

        setTitle(R.string.title_timer);
        context = this;

        layoutMain = (LinearLayout) findViewById(R.id.llTimerActivityMain);

        setOrientation(layoutMain);

        Long id = -1L;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong(StandUpActivity.USER_ID_MESSAGE);
        }

        user = User.load(User.class, id);

        if (user != null) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llTimerUserButton);
            UserButton userButton = new UserButton(this, user, Color.TRANSPARENT);
            linearLayout.addView(userButton);
        }

        if (elapsedTime == null) {
            elapsedTime = 0L;
        }

        timerView = (TextView) findViewById(R.id.tvTimer);
        timerView.setText(getTime(SPEECH_TIME - elapsedTime));

        if (timer == null) {
            timer = new CountDownTimer(SPEECH_TIME - elapsedTime, COUNT_DOWN_STEP) {
                @Override
                public void onTick(long millisUntilFinished) {
                    elapsedTime += COUNT_DOWN_STEP;
                    timerView.setText(getTime(millisUntilFinished));
                    if (millisUntilFinished <= WARNING_TIME) {
                        layoutMain.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBad));
                    } else if (millisUntilFinished <= HALF_TIME) {
                        layoutMain.setBackgroundColor(ContextCompat.getColor(context, R.color.colorMedium));
                    }
                }

                @Override
                public void onFinish() {
                    alertOnFinish();
                }
            }.start();
        }

    }

    public void startUserSelectActivity(View view) {
        Intent intent = new Intent(this, UserSelectActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        timer.cancel();
//        Log.d("TIMER", "Timer cancelled!");

    }

    private String getTime(Long millis) {
        return new SimpleDateFormat("mm:ss", Locale.getDefault()).format(new Date(millis));
    }

    public void closeMe(View view) {

        stopTimers();

        StandUpTime time = new StandUpTime();
        time.setUserId(user.getUserId());
        time.setSpentTime(elapsedTime);
        time.setStandUpDate(new Date());
        time.setDisplayed(false);
        time.save();

        elapsedTime = null;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        finish();
    }

    public void removeMe(View view) {

        stopTimers();

        user.setWasOnStandUp(false);
        user.setIsOnStandUp(false);
        user.save();

        elapsedTime = null;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        finish();
    }

    public void postponeMe(View view) {

        stopTimers();

        user.setWasOnStandUp(false);
        user.save();

        elapsedTime = null;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        finish();
    }

    private void stopTimers() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (alertTimer != null) {
            alertTimer.cancel();
            alertTimer = null;
        }
    }


    private void setOrientation(LinearLayout layout) {
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getRotation();

        if (orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180) {
            layout.setOrientation(LinearLayout.VERTICAL);
        } else {
            layout.setOrientation(LinearLayout.HORIZONTAL);
        }
    }

    private void alertOnFinish() {
        timer = null;

        timerView.setText(getTime(0L));
        final int[] ticks = {0};
        if (alertTimer == null) {
            alertTimer = new CountDownTimer(WARNING_TIME, COUNT_DOWN_STEP / 2) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (elapsedTime != null) {
                        elapsedTime += COUNT_DOWN_STEP / 2;
                    }
                    int color = ticks[0]++ % 2 == 0 ? R.color.colorBad : R.color.colorMedium;
                    layoutMain.setBackgroundColor(ContextCompat.getColor(context, color));
                }

                @Override
                public void onFinish() {
                    closeMe(timerView);
                }
            }.start();
        }
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
            mediaPlayer.setVolume(1, 1);
            mediaPlayer.start();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Toast.makeText(this, R.string.no_back_warning, Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }




}
