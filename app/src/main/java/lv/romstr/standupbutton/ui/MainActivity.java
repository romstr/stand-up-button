package lv.romstr.standupbutton.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import lv.romstr.standupbutton.R;
import lv.romstr.standupbutton.db.AppDb;
import lv.romstr.standupbutton.model.Auth;
import lv.romstr.standupbutton.model.User;
import lv.romstr.standupbutton.network.CallBackActivity;
import lv.romstr.standupbutton.network.RequestHandler;
import lv.romstr.standupbutton.network.UserListResponseHandler;
import lv.romstr.standupbutton.ui.UserAdapter;
import lv.romstr.standupbutton.user.UserToSpeech;

public class MainActivity extends AppCompatActivity implements CallBackActivity {

    private static final String ENDPOINT = "/users.json";

    private ListView userListView;
    private ArrayList<User> userList;
    private UserAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Auth auth;

    private UserListResponseHandler responseHandler;
    private RequestHandler requestHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_main);

        setTitle(R.string.title_users);

        setAuths();

        userListView = (ListView) findViewById(R.id.lvUserList);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srlUserList);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUserList();
            }
        });

        setUserList();
    }

    private void updateUserList() {
        responseHandler = new UserListResponseHandler(adapter, this);
        requestHandler = new RequestHandler(this, auth.getServer() + ENDPOINT, auth.getLogin(), auth.getPassword());
        requestHandler.getJsonRequest(responseHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppDb.saveUsersToDb(userList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserList();
        setAuths();
        UserToSpeech.destroy();
    }

    @Override
    public void onSuccess() {

        setUserList();
        swipeRefreshLayout.setRefreshing(false);
//        AppDb.clearDb();
    }

    @Override
    public void onFailure(String errorText) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, getResources().getString(R.string.general_error) + errorText, Toast.LENGTH_LONG).show();
        showLoginActivity();
    }

    public void startStandUpActivity(View view) {
        AppDb.saveUsersToDb(userList);
        Intent intent = new Intent(this, StandUpActivity.class);
        startActivity(intent);
    }

    private void setUserList() {
        userList = (ArrayList<User>) AppDb.loadUsersFromDb();
        adapter = new UserAdapter(this, userList);
        userListView.setAdapter(adapter);

        if (userList.size() == 0) {
            updateUserList();
        }
    }

    private void showLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuItem mi = menu.add(0, 1, 0, "Preferences");
        MenuItem mi1 = menu.add(0, 1, 0, "Login");
//        mi.setIntent(new Intent(this, MyPreferenceActivity.class));
        mi1.setIntent(new Intent(this, LoginActivity.class));
        return super.onCreateOptionsMenu(menu);

    }

    private void setAuths() {
        List<Auth> auths = new Select().from(Auth.class).execute();
        if (auths == null || auths.isEmpty()) {
            showLoginActivity();
        } else {
            auth = auths.get(0);
        }
    }
}
