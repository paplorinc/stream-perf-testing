package testing;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Benchmark                                                (containerSize)   Mode  Cnt     Score    Error  Units
 * IntStreamOperationsPerformanceTest.testV1SingleThreaded          1048576  thrpt   20    79.776 ±  1.191  ops/s
 * IntStreamOperationsPerformanceTest.testV1Parallel                1048576  thrpt   20   130.518 ±  3.233  ops/s
 * IntStreamOperationsPerformanceTest.testV2                        1048576  thrpt   20   853.466 ±  7.642  ops/s
 * IntStreamOperationsPerformanceTest.testV2Array                   1048576  thrpt   20  2139.723 ± 33.306  ops/s
 */
@State(Scope.Benchmark)
@Fork(1)
public class IntStreamOperationsPerformanceTest {

    @Param({"1048576"})
    private int containerSize;

    private int[] array;

    private Random random = new Random(0);

    @Setup
    public void setup() {
        array = new int[containerSize];
        int value = 0;
        for (int i = 0; i < array.length; i++) {
            value += random.nextInt(5);
            array[i] = value;
        }
    }

    @Benchmark
    public Object testV1SingleThreaded() {
        IntStream intStream = IntStream.of(array);
        return IntStreamOperationsV1.isSorted(intStream);
    }

    @Benchmark
    public Object testV1Parallel() {
        IntStream intStream = IntStream.of(array);
        return IntStreamOperationsV1.isSorted(intStream.parallel());
    }

    @Benchmark
    public Object testV2() {
        IntStream intStream = IntStream.of(array);
        return IntStreamOperationsV2.isSorted(intStream);
    }

    @Benchmark
    public Object testV2Array() {
        return IntArrayOperationsV2.isSorted(array);
    }
}
