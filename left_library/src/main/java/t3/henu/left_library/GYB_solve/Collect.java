package t3.henu.left_library.GYB_solve;

import java.util.ArrayList;
import java.util.List;



public class Collect {
    public static List<All_View> all_view_list = new ArrayList<All_View>();

    public static void addView(All_View all_view) {
        all_view_list.add(all_view);
    }

    public static void removeView(All_View all_view) {
        all_view_list.remove(all_view);
    }

    public static void removeAll() {
        all_view_list.clear();
    }
}
