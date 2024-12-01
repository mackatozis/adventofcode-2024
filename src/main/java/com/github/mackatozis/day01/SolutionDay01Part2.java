package com.github.mackatozis.day01;

import io.vavr.control.Try;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SolutionDay01Part2 {
    public static void main(String[] args) {
        File inputFile = new File(System.getProperty("user.dir") + "/adventofcode-2024/src/main/java/com/github/mackatozis/day01/input.data");
        List<int[]> parsedData = parseInputFile(inputFile);

        if (parsedData.isEmpty()) {
            System.err.println("input file is empty / invalid");
            return;
        }

        int similarityScore = calculateSimilarityScore(parsedData);
        System.out.println("similarity score: " + similarityScore);
    }

    private static List<int[]> parseInputFile(File file) {
        return Try.of(() -> Files.lines(Path.of(file.getPath())))
                .map(lines -> lines
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .map(SolutionDay01Part2::parseLine)
                        .flatMap(Optional::stream)
                        .toList())
                .getOrElse(Collections.emptyList());
    }

    private static Optional<int[]> parseLine(String line) {
        String[] pair = line.split("\\s+");
        return Try.of(() -> new int[]{Integer.parseInt(pair[0]), Integer.parseInt(pair[1])}).toJavaOptional();
    }

    private static int calculateSimilarityScore(List<int[]> data) {
        Map<Integer, Long> rightCountMap = data.stream()
                .map(SolutionDay01Part2::getRightListNumber)
                .collect(Collectors.groupingBy(num -> num, Collectors.counting()));

        return data.stream()
                .mapToInt(pair -> pair[0] * rightCountMap.getOrDefault(pair[0], 0L).intValue())
                .sum();
    }

    private static int getRightListNumber(int[] pair) {
        return pair[1];
    }
}
