package lv.romstr.standupbutton.user;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lv.romstr.standupbutton.model.User;

/**
 * Created by Roman on 02.04.16..
 */
public class UserExtra {

    private static ImageList imageList;
    private static ColorList colorList;

    public static String getUserName(User user) {
        return user.getLastname() + " " + user.getFirstname();
    }

    public static String getUserNameAndDate(User user) {
        return getUserName(user) + new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
    }

    public static String getUserFirstName(User user) {
        return user.getFirstname().toUpperCase();
    }

    public static String getLastNameFirstThree(User user) {
        return user.getLastname().toUpperCase().substring(0,3);
    }

    public static String getUserInitials(User user) {
        return user.getLastname().substring(0, 1) + user.getFirstname().substring(0, 1);
    }

    public static Drawable getHashDrawable(User user, Context context) {
        if (imageList == null) {
            imageList = new ImageList(context);
        }
        int pos = getUserNameAndDate(user).hashCode() % imageList.getIcons().size();
        return imageList.getIcons().get(Math.abs(pos)).getConstantState().newDrawable();
    }

    public static int getHashColor(User user) {
        if (colorList == null) {
            colorList = new ColorList();
        }
        int pos = getUserNameAndDate(user).hashCode() % colorList.getColors().size();
        return colorList.getColors().get(Math.abs(pos));
    }
}
