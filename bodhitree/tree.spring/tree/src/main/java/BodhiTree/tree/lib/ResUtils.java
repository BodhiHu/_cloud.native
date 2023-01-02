package bodhitree.tree.lib;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ResUtils {

    public static File loadFile (String filePath) throws NullPointerException {

        ClassLoader classLoader = ResUtils.class.getClassLoader();
        return new File(classLoader.getResource(filePath).getFile());
    }

    public static String loadFileAsString (String filePath) throws IOException {
        File file = loadFile(filePath);
        return FileUtils.readFileToString(file, "UTF-8");
    }
}

