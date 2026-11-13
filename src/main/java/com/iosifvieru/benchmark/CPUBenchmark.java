package com.iosifvieru.benchmark;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CPUBenchmark {
    private Integer iterations = 10_000;
    private Integer repeats = 10;

    public CPUBenchmark(Integer iterations, Integer repeats){
        this.iterations = iterations;
        this.repeats = repeats;
    }

    private void workload(){
        for(int i = 0; i < 1000; i++){
            var x = Math.PI * Math.pow(2, i);
        }

        for(int i = 0; i < 1000; i++){
            var x = i / Math.PI;
        }

        for(int i = 0; i < 1000; i++){
            var x = i / (Math.PI * Math.pow(2, i));
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
        Double average_duration = (double) ((sum / this.repeats)) / 1E9;

        double ops = Double.NaN;
        if (average_duration > 0){
            ops = this.iterations / average_duration;
        }

        System.out.println("Iterations: " + this.iterations + " Average Duration: " + average_duration + " Ops: " + ops);
    }
}
