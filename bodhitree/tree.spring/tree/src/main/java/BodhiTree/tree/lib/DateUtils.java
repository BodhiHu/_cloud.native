package bodhitree.tree.lib;

import java.util.Date;

public class DateUtils {
    public static boolean isPast (Date date, int offsetMilliSeconds) {
        return isPast(new Date(date.getTime() + offsetMilliSeconds));
    }
    public static boolean isPast (Date date) {
        return date.before(new Date());
    }
    public static boolean isFuture (Date date, int offsetMilliSeconds) {
        return isFuture(new Date(date.getTime() + offsetMilliSeconds));
    }
    public static boolean isFuture (Date date) {
        return date.after(new Date());
    }
}
