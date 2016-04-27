package lv.romstr.standupbutton.user;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.util.LinkedList;
import java.util.List;

import lv.romstr.standupbutton.R;

/**
 * Created by Roman on 02.04.16..
 */
public class ImageList {

    private final List<Drawable> icons;

    public ImageList(Context context) {
        icons = new LinkedList<>();
        icons.add(ContextCompat.getDrawable(context, R.drawable.ic_face_asia));
        icons.add(ContextCompat.getDrawable(context, R.drawable.ic_face_cyclops));
        icons.add(ContextCompat.getDrawable(context, R.drawable.ic_face_glasses));
        icons.add(ContextCompat.getDrawable(context, R.drawable.ic_face_ninja));
        icons.add(ContextCompat.getDrawable(context, R.drawable.ic_face_what));
    }

    public List<Drawable> getIcons() {
        return icons;
    }
}
