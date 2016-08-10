package testing;

import java.util.Comparator;
import java.util.List;

public class ListOperationsV2 {
    public static <T> boolean isSorted(List<T> values, Comparator<T> comparator) {
        for (int i = 1; i < values.size(); i++)
            if (isGreater(values.get(i - 1), values.get(i), comparator))
                return false;
        return true;
    }
    private static <T> boolean isGreater(T o1, T o2, Comparator<T> comparator) {
        return comparator.compare(o1, o2) > 0;
    }
}
