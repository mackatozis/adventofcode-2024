package com.github.mackatozis.day03;

import com.github.mackatozis.util.FileUtil;
import com.github.mackatozis.util.Pair;
import io.vavr.control.Try;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SolutionDay03Part1 {
    public static void main(String[] args) {
        String inputFileContent = FileUtil.resolveInputFileContent("day03");

        int totalSum = extractValidInstructions(inputFileContent)
                .mapToInt(pair -> pair.left() * pair.right())
                .sum();

        System.out.println("total sum of instructions: " + totalSum);
    }

    private static Stream<Pair<Integer, Integer>> extractValidInstructions(String input) {
        return IntStream.range(0, input.length() - 4)
                .filter(i -> input.startsWith("mul(", i))
                .mapToObj(i -> findClosingParenthesis(input, i + 4))
                .flatMap(Optional::stream)
                .map(SolutionDay03Part1::parseInstruction)
                .flatMap(Optional::stream);
    }

    private static Optional<String> findClosingParenthesis(String input, int start) {
        int end = input.indexOf(")", start);

        if (noClosingParenthesis(end)) {
            return Optional.empty();
        }
        return Optional.of(input.substring(start, end));
    }

    private static boolean noClosingParenthesis(int end) {
        return end == -1;
    }

    private static Optional<Pair<Integer, Integer>> parseInstruction(String instruction) {
        return Try.of(() -> {
            String[] parts = instruction.split(",");

            if (parts.length != 2) {
                throw new IllegalArgumentException("invalid instruction format");
            }

            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());

            return new Pair<>(x, y);
        }).toJavaOptional();
    }
}
