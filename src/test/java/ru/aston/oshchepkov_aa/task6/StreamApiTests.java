package ru.aston.oshchepkov_aa.task6;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class StreamApiTests {
    @Test
    @DisplayName(
            "Посчитать сумму чисел в массиве [1, 2, 3, 4, 5], используя reduce()"
    )
    void test1() {
        final var expected = 15;

        final var reduceOpt = IntStream.rangeClosed(1, 5)
                .reduce(Integer::sum);

        var result = reduceOpt.getAsInt();

        assertThat(result)
                .as("Must be equal to expected val")
                .isEqualTo(expected);

        log.info("{}", result);
    }

    @Test
    @DisplayName(
            "Создайте два Stream-а: один из массива чисел 1…5, второй из массива 5…10. " +
                    "Объедините эти два Stream-а в один и выведите на экран."
    )
    void test2() {
        var leftStream = IntStream.rangeClosed(1, 5);
        var rightStream = IntStream.rangeClosed(5, 10);

        IntStream.concat(leftStream, rightStream)
                .forEach(el -> log.info("{}", el));

    }

    @Test
    @DisplayName(value =
            "Разделить файлы в Stream на два списка: с расширением .txt и с расширением .doc, " +
                    "посчитать количество файлов в каждом списке."
    )
    void test3() {
        final var sizeLineLength = 6;

        Stream.of("first.txt", "second.txt", "heh.doc",
                        "privet.txt", "another.doc", "whatIsIt.doc",
                        "a.doc", "a.txt", "lol.txt")
                .collect(
                        () -> new HashMap<String, List<String>>(),

                        (accumulator, fname) -> {
                            var pointPos = fname.lastIndexOf('.');
                            var postfix = fname.substring(pointPos);
                            var fileList = accumulator.computeIfAbsent(postfix, k -> new ArrayList<>());
                            fileList.add(fname);
                        },

                        (a, b) -> {/*
                            Игнорю, потому что этот стрим не параллельный, а так надо было бы запилить функцию для
                                группировки значений с нескольких аккумуляторов.
                            */
                        }
                ).entrySet()
                .forEach(fEntry -> {
                    var builder = new StringBuilder(sizeLineLength)
                            .append(fEntry.getKey())
                            .append(" ")
                            .append(fEntry.getValue().size());
                    log.info("{}", builder);
                });
    }

    @Test
    @DisplayName(
            "Задано множество фамилий сотрудников. Разработать программу, " +
                    "которая отображает все фамилии, начинающиеся на букву «J» . " +
                    "Задачу решить с использованием Stream API."
    )
    void test4() {
        Stream.of("Williams", "Ward", "Jackson",
                        "Richards", "Allen", "Casey",
                        "Campbell", "Myers", "Wilson",
                        "Brewer", "Jane")
                .filter(s -> s.startsWith("J"))
                .forEach(s -> log.info("{}", s));
    }

    @Test
    @DisplayName(
            "Создайте Stream, который выводит на экран четные числа от 2 до 30."
    )
    void test5() {
        IntStream.iterate(2, i -> i + 1)
                .limit(30)
                .filter(v -> v % 2 == 0)
                .forEach(v -> log.info("{}", v));
    }

    @Test
    @DisplayName(
            "Собрать числа в Stream в список сумм цифр каждого числа."
    )
    void test6() {
        var r = IntStream.of(2222, 1456, 1234, 5678, 9000, 9)
                .collect(
                        () -> new ArrayList<Integer>(),
                        (accum, val) -> {
                            var sum = 0;
                            do {
                                sum += val % 10;
                                val = val / 10;
                            } while (val != 0);
                            accum.add(sum);
                        },
                        (a, b) -> {
                        }
                );
        log.info("{}", r);
    }

    @Test
    @DisplayName(
            "Собрать числа в Stream в список сумм цифр каждого числа." +
                    "Лаконичная версия."
    )
    void test6_laconically_way() {
        var n = IntStream.of(2222, 1456, 1234, 5678, 9000, 9)
                .mapToObj(integer -> String.valueOf(integer)
                        .chars()
                        .mapToObj(Character::getNumericValue)
                        .reduce(0, Integer::sum))
                .toList();
        log.info("{}", n);
    }

    @Test
    void measure_test_6_time_difference(){
        var maxRepeats = 1000;
        var sum = 0L;
        var ts = 0L;

        var commonWayAvg = 0F;
        var laconicallyWayAvg = 0F;

        for (var i = 0; i < maxRepeats; i++){
            ts = System.currentTimeMillis();
            test6_laconically_way();
            ts = System.currentTimeMillis() - ts;
            sum += ts;
        }
        laconicallyWayAvg = sum / (float) maxRepeats;

        sum = 0L;
        for (var i = 0; i < maxRepeats; i++){
            ts = System.currentTimeMillis();
            test6();
            ts = System.currentTimeMillis() - ts;
            sum += ts;
        }
        commonWayAvg = sum / (float) maxRepeats;

        log.info("Common way.");
        log.info("Avg: {} ms", commonWayAvg);

        log.info("Laconically way.");
        log.info("Avg: {} ms", laconicallyWayAvg);
    }

    @Test
    @DisplayName(
            "Соберите слова в Stream в один текст, где каждое слово начинается с большой буквы и выведите результат."
    )
    void test7() {
        String text = Stream.of("this", "is", "an", "example", "of", "strange", "sentence")
                .map(s -> {
                    var firstLetter = s.substring(0, 1).toUpperCase();

                    return firstLetter.concat(s.substring(1));
                })
                .collect(Collectors.joining(" "));

        log.info("{}", text);
    }

    @Test
    @DisplayName(
            "Отфильтруйте элементы массива, которые не являются числами."
    )
    void test8() {
        var objArr = new Object[]{"", null, 123, 456, 25.4, 36.6, "junk", new Date(), new Timestamp(124), "hehe", 32};

        var result = Arrays.stream(objArr)
                .filter(o -> o instanceof Number)
                .toArray(Number[]::new);

        for (Number number : result) {
            log.info("{}", number);
        }
    }

    @Test
    @DisplayName(
            "Создайте Stream-у чисел от 1 до 20. " +
                    "Создайте новый Stream, который будет выводить на экран только четные числа и числа, кратные 3. " +
                    "Затем объедините эти два Stream-a в один и выведите результирующий Stream."
    )
    void test9() {
        var rootStream = IntStream.rangeClosed(1, 20);
        var specialStream = IntStream.iterate(0, i -> i + 1)
                .limit(50)
                .filter(i -> i % 2 == 0 || i % 3 == 0);

        IntStream.concat(rootStream, specialStream)
                .forEach(i -> log.info("{}", i));
    }

    @Test
    @DisplayName(
            "Создать стрим чисел от 0 до 100. " +
                    "Умножить их на 2 и вывести на экран результат, ограничиться первыми 10 результатами."
    )
    void test10() {
        IntStream.rangeClosed(0, 100)
                .map(v -> v * 2)
                .limit(10)
                .forEach(v -> log.info("{}", v));
    }
}
