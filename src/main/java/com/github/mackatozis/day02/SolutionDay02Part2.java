package com.github.mackatozis.day02;

import com.github.mackatozis.util.FileUtil;
import io.vavr.control.Try;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SolutionDay02Part2 {
    public static void main(String[] args) {
        File inputFile = FileUtil.resolveInputFile("day02");
        long safeReportsTotal = Try.of(() -> Files.lines(Path.of(inputFile.getPath())))
                .map(lines -> lines.map(SolutionDay02Part2::parseReports))
                .map(reports -> reports.filter(Optional::isPresent)
                        .map(Optional::get)
                        .filter(SolutionDay02Part2::isSafeWithDampener)
                        .count())
                .getOrElse(0L);

        System.out.println("safe reports total: " + safeReportsTotal);
    }

    private static Optional<List<Integer>> parseReports(String report) {
        return Optional.ofNullable(report)
                .map(String::trim)
                .filter(l -> !l.isEmpty())
                .map(l -> Stream.of(l.split("\\s+"))
                        .map(s -> Try.of(() -> Integer.parseInt(s)).getOrNull())
                        .toList())
                .filter(list -> list.stream().allMatch(Objects::nonNull));
    }

    private static boolean isSafeWithDampener(List<Integer> report) {
        if (isSafe(report)) {
            return true;
        }

        return IntStream.range(0, report.size())
                .mapToObj(i -> {
                    List<Integer> modifiedReport = new ArrayList<>(report);
                    modifiedReport.remove(i);
                    return modifiedReport;
                })
                .anyMatch(SolutionDay02Part2::isSafe);
    }

    private static boolean isSafe(List<Integer> report) {
        if (isSingleLevelReport(report)) {
            return true;
        }

        boolean isIncreasing = true;
        boolean isDecreasing = true;

        for (int i = 1; i < report.size(); i++) {
            int diff = report.get(i) - report.get(i - 1);

            if (hasInvalidDifference(diff)) {
                return false;
            }

            if (diff > 0) {
                isDecreasing = false;
            } else if (diff < 0) {
                isIncreasing = false;
            }
        }
        return isIncreasing || isDecreasing;
    }

    private static boolean isSingleLevelReport(List<Integer> report) {
        return report.size() < 2;
    }

    private static boolean hasInvalidDifference(int diff) {
        return diff < -3 || diff > 3 || diff == 0;
    }
}
