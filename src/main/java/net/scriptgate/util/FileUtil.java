package net.scriptgate.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    private static final File DEFAULT_OUTPUT_DIRECTORY = new File("out");

    private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    public static File getUniqueFileNameWithTimestamp(File parentDirectory, String name, String extension) {
        String timestamp = TIMESTAMP_FORMAT.format(new Date());
        int index = 1;

        File result;
        do {
            result = new File(parentDirectory, timestamp + (index == 1 ? "" : "_" + index) + "_" + name + "." + extension);
            ++index;
        } while (result.exists());
        return result;
    }

    public static File getOutputDirectoryOrDefault(String outputPath) {
        if (outputPath == null) {
            return DEFAULT_OUTPUT_DIRECTORY;
        }
        File outputDirectory = new File(outputPath);
        requireDirectory(outputDirectory, "'" + outputPath + "' is not a valid output directory!");
        return outputDirectory;
    }

    private static void requireDirectory(File outputDirectory, String message) {
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new IllegalArgumentException(message);
        }
    }
}
