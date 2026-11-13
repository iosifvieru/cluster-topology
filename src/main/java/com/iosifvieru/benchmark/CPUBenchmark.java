package com.iosifvieru.benchmark;

import java.util.ArrayList;

public class CPUBenchmark {
    private final Integer iterations;
    private final Integer repeats;

    public CPUBenchmark(Integer iterations, Integer repeats){
        this.iterations = iterations;
        this.repeats = repeats;
    }

    public CPUBenchmark(){
        this.iterations = 10_000;
        this.repeats = 10;
    }

    private void workload(){
        double sum = 0;
        int it = 1000;
        for(int i = 0; i < it; i++){
            sum += Math.PI * Math.pow(2, i);
        }

        for(int i = 0; i < it; i++){
            sum += i / Math.PI;
        }

        for(int i = 0; i < it; i++){
            sum += i / (Math.PI * Math.pow(2, i));
        }
    }

    public void run() {
        var results = new ArrayList<Long>();

        for(int r = 0; r < this.repeats; r++) {
            long startTime = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                workload();
            }
            long endTime = System.nanoTime();

            long executionTime = endTime - startTime;
            results.add(executionTime);
        }

        Long sum = results.stream().reduce(0L, Long::sum);
        double sum_seconds = sum / 1E9;
        Double average_duration = (double) ((sum / this.repeats)) / 1E9;

        double ops = Double.NaN;
        if (average_duration > 0){
            ops = this.iterations / average_duration;
        }
        System.out.printf("total time: %.3f, iterations: %d, avg duration: %.4f, OPS: %.3f\n", sum_seconds, this.iterations, average_duration, ops);
    }
}
