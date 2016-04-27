package lv.romstr.standupbutton.network;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roman on 01.04.16..
 */
public class RequestHandler {

    private String url;
    private String login;
    private String password;
    private Context context;


    public RequestHandler(Context context, String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
        this.context = context;
    }

    public void getJsonRequest(final ResponseHandler handler) {

        RequestQueue queue = Volley.newRequestQueue(context);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        handler.handleJsonObjectResponse(response);
                        Log.d("VOLLEY", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        handler.handleErrorResponse(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getCreds();
            }
        };

        queue.add(jsObjRequest);
        Log.d("VOLLEY", url);

    }

    private Map<String, String> getCreds() {

        Map<String, String> params = new HashMap<>();
        String credentials = String.format("%s:%s", login, password);
        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
        params.put("Authorization", auth);

        return params;
    }
}
