package com.github.mackatozis.util;

import java.io.File;

public final class FileUtil {

    private FileUtil() {
    }

    public static File resolveInputFile(String dayOfChallenge) {
        return new File(System.getProperty("user.dir")
                + "/adventofcode-2024/src/main/java/com/github/mackatozis/"
                + dayOfChallenge
                + "/input.data");
    }
}
