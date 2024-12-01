package com.github.mackatozis.day01;

import io.vavr.control.Try;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class SolutionDay01Part1 {
    public static void main(String[] args) {
        File inputFile = new File(System.getProperty("user.dir") + "/adventofcode-2024/src/main/java/com/github/mackatozis/day01/input.data");
        List<int[]> parsedData = parseInputFile(inputFile);

        if (parsedData.isEmpty()) {
            System.err.println("input file is empty / invalid");
            return;
        }

        List<Integer> leftList = parsedData.stream()
                .map(SolutionDay01Part1::getLeftListNumber)
                .sorted()
                .toList();

        List<Integer> rightList = parsedData.stream()
                .map(SolutionDay01Part1::getRightListNumber)
                .sorted()
                .toList();

        int totalDistance = calculateTotalDistance(leftList, rightList);
        System.out.println("total distance: " + totalDistance);
    }

    private static List<int[]> parseInputFile(File file) {
        return Try.of(() -> Files.lines(Path.of(file.getPath())))
                .map(lines -> lines
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .map(SolutionDay01Part1::parseLine)
                        .flatMap(Optional::stream)
                        .toList())
                .getOrElse(Collections.emptyList());
    }

    private static Optional<int[]> parseLine(String line) {
        String[] pair = line.split("\\s+");
        return Try.of(() -> new int[]{Integer.parseInt(pair[0]), Integer.parseInt(pair[1])}).toJavaOptional();
    }

    private static int getLeftListNumber(int[] pair) {
        return pair[0];
    }

    private static int getRightListNumber(int[] pair) {
        return pair[1];
    }

    private static int calculateTotalDistance(List<Integer> leftList, List<Integer> rightList) {
        return IntStream.range(0, leftList.size())
                .map(i -> Math.abs(leftList.get(i) - rightList.get(i)))
                .sum();
    }
}
