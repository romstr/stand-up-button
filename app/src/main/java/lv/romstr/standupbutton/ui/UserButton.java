package lv.romstr.standupbutton.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lv.romstr.standupbutton.R;
import lv.romstr.standupbutton.model.User;
import lv.romstr.standupbutton.user.UserExtra;

/**
 * Created by Roman on 02.04.16..
 */
public class UserButton extends LinearLayout {

    public UserButton(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.button_attendant, this);
    }

    public UserButton(Context context, User user) {
        this(context);

        ((TextView) findViewById(R.id.tvAttendantFirstName)).setText(UserExtra.getUserFirstName(user));
        ((TextView) findViewById(R.id.tvAttendantInitials)).setText(UserExtra.getLastNameFirstThree(user));
        ((TextView) findViewById(R.id.tvAttendantName)).setText(UserExtra.getUserName(user));

        ImageView imageView =
                ((ImageView) findViewById(R.id.ivAttendantAvatar));
        Drawable drawable = UserExtra.getHashDrawable(user, context);
        drawable.mutate().setColorFilter(UserExtra.getHashColor(user), PorterDuff.Mode.MULTIPLY);
        imageView.setImageDrawable(drawable);
    }

    public UserButton(Context context, User user, int color) {
        this(context, user);

        findViewById(R.id.llUserButtonMain).setBackgroundColor(color);
    }

}
