package lv.romstr.standupbutton.db;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import lv.romstr.standupbutton.model.StandUpTime;
import lv.romstr.standupbutton.model.User;

/**
 * Created by Roman on 02.04.16..
 */
public class AppDb {

    public static List<User> loadUsersFromDb() {
        Log.d("ACTIVEANDROID", "load");
        List<User> users;
        ActiveAndroid.beginTransaction();
        try {
            users = new Select().from(User.class).orderBy("Attendance DESC, LastName ASC, FirstName ASC").execute();
            for (User user : users) {
                user.setWasOnStandUp(false);
                user.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }

        Log.d("ACTIVEANDROID", users.size() + " records loaded");
        return users;
    }

    public static List<StandUpTime> loadFreshTimesFromDb() {
        Log.d("ACTIVEANDROID", "load");
        List<StandUpTime> times = new Select().from(StandUpTime.class).where("Displayed = ?", 0).orderBy("SpentTime ASC").execute();
        Log.d("ACTIVEANDROID", times.size() + " records loaded");
        return times;
    }

    public static List<User> loadAttendantsFromDb() {
        Log.d("ACTIVEANDROID", "load");
        List<User> users = new Select().from(User.class).where("(Attendance = ? OR Attendance is null) AND Attended = ? ", 1, 0).execute();
        Log.d("ACTIVEANDROID", users.size() + " records loaded");
        return users;
    }

    public static List<User> loadNonAttendantsFromDb() {
        Log.d("ACTIVEANDROID", "load");
        List<User> users = new Select().from(User.class).where("Attendance = ? OR Attendance is null", 0).execute();
        Log.d("ACTIVEANDROID", users.size() + " records loaded");
        return users;
    }

    public static void updateUsersInDb(List<User> users) {
        ActiveAndroid.beginTransaction();
        try {
            for (User user : loadUsersFromDb()) {
                user.setFromRedmine(false);
                user.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }

        ActiveAndroid.beginTransaction();
        try {
            for (User user : users) {
                List<User> tmpList = new Select().from(User.class).where("UserId = ?", user.getUserId()).execute();
                if (tmpList == null || tmpList.isEmpty()) {
                    user.setIsOnStandUp(true);
                    user.save();
                } else {
                    User tmpUser = tmpList.get(0);
                    tmpUser.setFirstname(user.getFirstname());
                    tmpUser.setLastname(user.getLastname());
                    tmpUser.setTtsName(user.getTtsName());
                    tmpUser.setFromRedmine(user.getFromRedmine());
                    tmpUser.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }


    public static void clearDb() {
        Log.d("ACTIVEANDROID", "clear");
        new Delete().from(User.class).execute();
    }

    public static void saveUsersToDb(List<User> users) {
        Log.d("ACTIVEANDROID", "saving...");
        ActiveAndroid.beginTransaction();
        try {
            for (User user : users) {
                user.save();
            }
            ActiveAndroid.setTransactionSuccessful();
            Log.d("ACTIVEANDROID", "success");
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public static void updateUserInfo() {

    }

}
