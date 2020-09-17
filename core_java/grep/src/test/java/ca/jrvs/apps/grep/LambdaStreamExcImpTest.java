package ca.jrvs.apps.grep;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LambdaStreamExcImpTest {
    private LambdaStreamExcImp lambda = new LambdaStreamExcImp();

    @Test
    void createStrStream() {
        Stream<String> stream = lambda.createStrStream("first", "second");
        String str = stream.collect(Collectors.joining("-"));
        assertEquals(str, "first-second");
    }

    @Test
    void toUpperCase() {
        Stream<String> stream = lambda.toUpperCase("first", "Second");
        String str = stream.collect(Collectors.joining("-"));
        assertEquals(str, "FIRST-SECOND");
    }

    @Test
    void filter() {
        Stream<String> stream = lambda.createStrStream("first", "second");
        stream = lambda.filter(stream, "first");
        String str = stream.collect(Collectors.joining("-"));
        assertEquals(str, "first");
    }

    @Test
    void createIntStream() {
        IntStream stream = lambda.createIntStream(new int[]{1,2,3,4});
        assertEquals(10,stream.sum());
    }

    @Test
    void toList() {
        Stream<String> stream = lambda.createStrStream("first", "second");
        List<String> list = lambda.toList(stream);
        assertTrue(list.contains("first"));
        assertTrue(list.contains("second"));
    }

    @Test
    void testToList() {
        IntStream stream = lambda.createIntStream(new int[]{1,2,3,4});
        List<Integer> list = lambda.toList(stream);
        assertTrue(list.contains(1));
        assertTrue(list.contains(2));
        assertTrue(list.contains(3));
        assertTrue(list.contains(4));
    }

    @Test
    void testCreateIntStream() {
        IntStream stream = lambda.createIntStream(1,5);
        assertEquals(10,stream.sum());
    }

    @Test
    void squareRootIntStream() {
        IntStream stream = lambda.createIntStream(new int[]{1,4,9});
        String str = lambda.squareRootIntStream(stream).mapToObj(Double::toString)
                .collect(Collectors.joining("-"));
        assertEquals(str, "1.0-2.0-3.0");
    }

    @Test
    void getOdd() {
        IntStream stream = lambda.createIntStream(new int[]{1,4,9});
        stream = lambda.getOdd(stream);
        assertEquals(10, stream.sum());
    }

    @Test
    void getLambdaPrinter() {
        Consumer<String> printer = lambda.getLambdaPrinter("< prefix ", " suffix >");
        printer.accept("test");
    }

    @Test
    void printMessages() {
        Consumer<String> printer = lambda.getLambdaPrinter("< prefix ", " suffix >");
        lambda.printMessages( new String[]{"t1","t2"}, printer);
    }

    @Test
    void printOdd() {
        Consumer<String> printer = lambda.getLambdaPrinter("< prefix ", " suffix >");
        IntStream stream = lambda.createIntStream(new int[]{1,4,9});
        lambda.printOdd(stream, printer);
    }

    @Test
    void flatNestedInt() {
        List<List<Integer>> arrays = new ArrayList<>();
        List<Integer> arr1 = new ArrayList<>();
        arr1.add(1);
        arr1.add(2);
        List<Integer> arr2 = new ArrayList<>();
        arr1.add(3);
        arr1.add(4);
        arrays.add(arr1);
        arrays.add(arr2);
        Stream<Integer> stream = lambda.flatNestedInt(arrays.stream());
        List<Integer> ints = stream.collect(Collectors.toList());
        assertTrue(ints.contains(1));
        assertTrue(ints.contains(2));
        assertTrue(ints.contains(3));
        assertTrue(ints.contains(4));
    }
}