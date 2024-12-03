package com.github.mackatozis.day03;

import com.github.mackatozis.util.FileUtil;
import com.github.mackatozis.util.Pair;
import io.vavr.control.Try;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SolutionDay03Part2 {
    public static void main(String[] args) {
        String inputFileContent = FileUtil.resolveInputFileContent("day03");

        int totalSum = extractValidInstructions(inputFileContent)
                .mapToInt(pair -> pair.left() * pair.right())
                .sum();

        System.out.println("total sum of instructions: " + totalSum);
    }

    private static Stream<Pair<Integer, Integer>> extractValidInstructions(String input) {
        AtomicBoolean isInstructionDisabled = new AtomicBoolean(false);

        return IntStream.range(0, input.length() - 4)
                .mapToObj(i -> {
                    if (input.startsWith("don't()", i)) {
                        isInstructionDisabled.set(true);
                    }

                    if (input.startsWith("do()", i)) {
                        isInstructionDisabled.set(false);
                    }

                    if (input.startsWith("mul(", i) && !isInstructionDisabled.get()) {
                        Optional<String> instruction = findClosingParenthesis(input, i + 4);
                        return instruction.flatMap(SolutionDay03Part2::parseInstruction);
                    }

                    return Optional.<Pair<Integer, Integer>>empty();
                })
                .filter(Optional::isPresent)
                .map(Optional::get);
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
