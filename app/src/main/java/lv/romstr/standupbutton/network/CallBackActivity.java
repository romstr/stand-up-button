package lv.romstr.standupbutton.network;

/**
 * Created by Roman on 02.04.16..
 */
public interface CallBackActivity {

    void onSuccess();
    void onFailure(String errorText);

}
