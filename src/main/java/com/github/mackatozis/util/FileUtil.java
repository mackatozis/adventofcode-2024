package com.github.mackatozis.util;

import io.vavr.control.Try;

import java.io.File;
import java.nio.file.Files;

public final class FileUtil {

    private FileUtil() {
    }

    public static File resolveInputFile(String dayOfChallenge) {
        return new File(System.getProperty("user.dir")
                + "/adventofcode-2024/src/main/java/com/github/mackatozis/"
                + dayOfChallenge
                + "/input.data");
    }

    public static String resolveInputFileContent(String dayOfChallenge) {
        File inputFile = resolveInputFile(dayOfChallenge);
        return Try.of(() -> Files.readString(inputFile.toPath()))
                .getOrNull();
    }
}
