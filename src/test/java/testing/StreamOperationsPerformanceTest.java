package testing;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Benchmark                                             (containerSize)   Mode  Cnt    Score   Error  Units
 * StreamOperationsPerformanceTest.testV1SingleThreaded          1048576  thrpt   20   44.252 ± 0.565  ops/s
 * StreamOperationsPerformanceTest.testV1Parallel                1048576  thrpt   20   77.602 ± 2.713  ops/s
 * StreamOperationsPerformanceTest.testV2                        1048576  thrpt   20  194.500 ± 1.264  ops/s
 * StreamOperationsPerformanceTest.testV2Array                   1048576  thrpt   20  233.928 ± 1.912  ops/s
 *
 * Benchmark                                             (containerSize)   Mode  Cnt  Score   Error  Units
 * StreamOperationsPerformanceTest.testV1SingleThreaded        104857600  thrpt   20  0.420 ± 0.010  ops/s
 * StreamOperationsPerformanceTest.testV1Parallel              104857600  thrpt   20  0.846 ± 0.059  ops/s
 * StreamOperationsPerformanceTest.testV2                      104857600  thrpt   20  1.900 ± 0.107  ops/s
 */
@State(Scope.Benchmark)
@Fork(1)
public class StreamOperationsPerformanceTest { // (un)boxing slows performance down seriously

    @Param({"1048576" /*, "104857600"*/})
    private int containerSize;

    private List<Integer> list;

    private Random random = new Random(0);

    @Setup
    public void setup() {
        list = new ArrayList<>(containerSize);
        int value = 0;
        for (int i = 0; i < containerSize; i++) {
            value += random.nextInt(5);
            list.add(value);
        }
    }

    @Benchmark
    public Object testV1SingleThreaded() {
        Stream<Integer> stream = list.stream();
        return StreamOperationsV1.isSorted(stream, Comparator.naturalOrder());
    }

    @Benchmark
    public Object testV1Parallel() {
        Stream<Integer> stream = list.parallelStream();
        return StreamOperationsV1.isSorted(stream, Comparator.naturalOrder());
    }

    @Benchmark
    public Object testV2() {
        Stream<Integer> stream = list.stream();
        return StreamOperationsV2.isSorted(stream, Comparator.naturalOrder());
    }

    @Benchmark
    public Object testV2List() {
        return ListOperationsV2.isSorted(list, Comparator.naturalOrder());
    }
}
