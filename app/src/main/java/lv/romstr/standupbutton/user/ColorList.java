package lv.romstr.standupbutton.user;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Roman on 02.04.16..
 */
public class ColorList {

    private final List<Integer> colors;

    public ColorList() {
        colors = new LinkedList<>();
        colors.add(0xF4433600);
        colors.add(0xE91E6300);
        colors.add(0x9C27B000);
        colors.add(0x673AB700);
        colors.add(0x3F51B500);
        colors.add(0x82771700);
        colors.add(0x4CAF5000);
        colors.add(0xCDDC3900);
        colors.add(0xFFEB3B00);
        colors.add(0xFF980000);
        colors.add(0xFFC10700);
        colors.add(0xFF572200);
        colors.add(0x9E9E9E00);
        colors.add(0x79554800);
        colors.add(0x8BC34A00);
        colors.add(0x21212100);
        colors.add(0x2196F300);
    }

    public List<Integer> getColors() {
        return colors;
    }
}
