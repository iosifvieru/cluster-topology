package com.iosifvieru;

import com.iosifvieru.benchmark.CPUBenchmark;

public class Main {
    public static void main(String[] args) {
        var benchmark = new CPUBenchmark(10000, 5);
        benchmark.run();
    }
}
