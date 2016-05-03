package lv.romstr.standupbutton.network;

import android.telecom.Call;
import android.widget.ArrayAdapter;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Comparator;

import lv.romstr.standupbutton.db.AppDb;
import lv.romstr.standupbutton.model.CustomField;
import lv.romstr.standupbutton.model.User;
import lv.romstr.standupbutton.model.UserList;

/**
 * Created by Roman on 01.04.16..
 */
public class UserListResponseHandler implements ResponseHandler {

    private ArrayAdapter<User> adapter;
    private CallBackActivity activity;

    public UserListResponseHandler(ArrayAdapter<User> adapter, CallBackActivity activity) {
        this.adapter = adapter;
        this.activity = activity;
    }


    @Override
    public void handleJsonObjectResponse(JSONObject object) {
        UserList userList = new Gson().fromJson(object.toString(), UserList.class);

        for (User user : userList.getUsers()) {
            user.setFromRedmine(true);
            String ttsName = null;
            for (CustomField field : user.getCustomFields()) {
                if (field.getName().equals("ttsname")) {
                    ttsName = field.getValue();
                }
            }
            user.setTtsName(ttsName);
        }

        AppDb.updateUsersInDb(userList.getUsers());
        activity.onSuccess();
    }

    @Override
    public void handleErrorResponse(VolleyError error) {
        activity.onFailure(prettyVolleyError(error));
    }

    private String prettyVolleyError(VolleyError error) {
        String ve = error.toString();
        return error.getLocalizedMessage() == null ? ve.substring(ve.lastIndexOf(".") + 1) : error.getLocalizedMessage();
    }
}
