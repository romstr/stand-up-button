package lv.romstr.standupbutton.user;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import java.util.logging.LogRecord;

import lv.romstr.standupbutton.model.User;
import lv.romstr.standupbutton.ui.SplashActivity;
import lv.romstr.standupbutton.ui.StandUpActivity;
import lv.romstr.standupbutton.ui.TimerActivity;

/**
 * Created by Roman on 09.04.16..
 */



public class UserToSpeech implements TextToSpeech.OnInitListener {

    private static final Long SMALL_DELAY = 100L;

    private static TextToSpeech tts;
    private User user;
    private Context context;

    public UserToSpeech(Context context) {
        this.context = context;
    }

    public void speakUserName(User user) {
        this.user = user;
        if (tts == null) {
            startSplashActivity();
            tts = new TextToSpeech(context, this);
        } else {
            startTimerActivity(user);
            speak(user);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void speak(User user) {
        String userName;
        if (user.getTtsName() != null && !user.getTtsName().equals("")) {
            userName = user.getTtsName();
        } else {
            userName = user.getFirstname() + " " + user.getLastname();
        }
        tts.speak(userName + " speaking", TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public static void destroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
            Log.e("TTS", "Destroyed");
        }
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.ENGLISH);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
                showError();
            } else {
                Log.e("TTS", "Initialization successful");
                speak(user);
                waitForSpeechStart(user);
            }

        } else {
            Log.e("TTS", "Initialization failed!");
            showError();
        }
    }

    private void showError() {
        Toast.makeText(context, "Unable to speak", Toast.LENGTH_LONG).show();
        SplashActivity.close();
    }



    private void startTimerActivity(User user) {
        Intent intent = new Intent(context, TimerActivity.class);
        intent.putExtra(StandUpActivity.USER_ID_MESSAGE, user.getId());
        context.startActivity(intent);
    }

    private void startSplashActivity() {
        Intent intent = new Intent(context, SplashActivity.class);
        context.startActivity(intent);
    }

    private void waitForSpeechStart(final User user) {
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                if (!tts.isSpeaking()) {
                    handler.postDelayed(this, SMALL_DELAY);
                } else {
                    SplashActivity.close();
                    startTimerActivity(user);
                }
            }
        };

        handler.postDelayed(r, SMALL_DELAY);
    }
}
