package lv.romstr.standupbutton.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import lv.romstr.standupbutton.R;
import lv.romstr.standupbutton.model.Auth;

/**
 * Created by Roman on 03.04.16..
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.title_login);
        ActiveAndroid.initialize(this);

        List<Auth> auths = new Select().from(Auth.class).execute();
        if (auths != null && !auths.isEmpty()) {
            ((EditText) findViewById(R.id.etLogin)).setText(auths.get(0).getLogin());
            ((EditText) findViewById(R.id.etServer)).setText(auths.get(0).getServer());
            ((EditText) findViewById(R.id.etPassword)).setText(auths.get(0).getPassword());
        } else {
            ((EditText) findViewById(R.id.etServer)).setText("http://");
        }
    }

    public void saveCredentials(View view) {
        new Delete().from(Auth.class).execute();
        Auth auth = new Auth();
        auth.setLogin(String.valueOf(((EditText) findViewById(R.id.etLogin)).getText()));
        auth.setPassword(String.valueOf(((EditText) findViewById(R.id.etPassword)).getText()));
        auth.setServer(String.valueOf(((EditText) findViewById(R.id.etServer)).getText()));
        auth.save();
        finish();
    }
}
