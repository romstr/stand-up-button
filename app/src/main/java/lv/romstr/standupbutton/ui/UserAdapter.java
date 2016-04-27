package lv.romstr.standupbutton.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lv.romstr.standupbutton.R;
import lv.romstr.standupbutton.user.ColorList;
import lv.romstr.standupbutton.user.ImageList;
import lv.romstr.standupbutton.model.User;
import lv.romstr.standupbutton.user.UserExtra;

/**
 * Created by Roman on 19.02.16..
 */
public class UserAdapter extends ArrayAdapter<User> {

    private LayoutInflater inflater;
    private Context context;

    public UserAdapter(Context context, ArrayList<User> users) {
        super(context, R.layout.list_item_user, users);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_item_user, parent, false);
        }

        ImageView imageView =
                ((ImageView) convertView.findViewById(R.id.ivUserAvatar));
        Drawable drawable = UserExtra.getHashDrawable(getItem(position), context);
        drawable.setColorFilter(UserExtra.getHashColor(getItem(position)), PorterDuff.Mode.MULTIPLY);
        imageView.setImageDrawable(drawable);

        ((TextView) convertView.findViewById(R.id.tvUserName)).setText(UserExtra.getUserName(getItem(position)));
        ((TextView) convertView.findViewById(R.id.tvUserInitials)).setText(UserExtra.getUserInitials(getItem(position)));

        if (!getItem(position).getFromRedmine()) {
            convertView.setBackgroundColor(Color.argb(15, 255, 0, 0));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        final Switch showUserSwitch = (Switch) convertView.findViewById(R.id.swUserActive);
        showUserSwitch.setChecked(getItem(position).getIsOnStandUp());

        showUserSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserSwitch.setChecked(showUserSwitch.isChecked());
                getItem(position).setIsOnStandUp(showUserSwitch.isChecked());
                getItem(position).save();
            }
        });
        return convertView;
    }
}
