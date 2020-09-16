package ca.jrvs.apps.grep;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc {
    @Override
    public Stream<String> createStrStream(String... strings) {
        Stream<String> stream = Arrays.stream(strings.clone());
        return stream;
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {
        return this.createStrStream(strings).map(String::toUpperCase);
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(x->x==pattern);
    }

    @Override
    public IntStream createIntStream(int[] arr) {
        IntStream intStream = Arrays.stream(arr.clone());
        return intStream;
    }

    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream.boxed().collect(Collectors.toList());
    }

    @Override
    public IntStream createIntStream(int start, int end) {
        IntStream intStream = IntStream.range(start,end);
        return intStream;
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.asDoubleStream().map(x->Math.sqrt(x));
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(x ->x%2 == 1);
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        Consumer<String> printer =  s -> System.out.println(prefix+s+suffix);
        return printer;
    }

    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        Arrays.stream(messages).forEach(t -> printer.accept(t));
    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        String[] strings = this.getOdd(intStream).mapToObj(Integer::toString)
                .toArray(size->new String[size]);
        this.printMessages(strings, printer);
    }

    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        Stream<Integer> stream = ints.flatMap(arr -> arr.stream());
        return stream;
    }
}
