package bodhitree.tree.lib;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapUtils {
    public static Map<String, String> create (String[][] keyValues) {
        return Stream.of(keyValues)
            .collect(
                Collectors.toMap(data -> data[0], data -> data[1])
            );
    }
}
