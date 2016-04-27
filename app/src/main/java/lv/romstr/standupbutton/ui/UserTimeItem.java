package lv.romstr.standupbutton.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import lv.romstr.standupbutton.R;
import lv.romstr.standupbutton.model.StandUpTime;
import lv.romstr.standupbutton.model.User;
import lv.romstr.standupbutton.user.UserExtra;

/**
 * Created by Roman on 02.04.16..
 */
public class UserTimeItem extends LinearLayout {

    public UserTimeItem(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item_times, this);
    }

    public UserTimeItem(Context context, User user, StandUpTime standUpTime) {
        this(context);

        ((LinearLayout) findViewById(R.id.llTimeItemPlaceHolder)).addView(new UserButton(context, user));
        ((TextView) findViewById(R.id.tvSavedTime)).setText(standUpTime.getPrettyTime());
        ((TextView) findViewById(R.id.tvSavedDate)).setText(standUpTime.getPrettyDate());
    }
}
