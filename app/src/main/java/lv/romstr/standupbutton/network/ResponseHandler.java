package lv.romstr.standupbutton.network;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Roman on 01.04.16..
 */
public interface ResponseHandler {

    void handleJsonObjectResponse(JSONObject object);
    void handleErrorResponse(VolleyError error);

}
