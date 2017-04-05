package edu.nyu.cs9053.homework8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yossie on 4/1/17.
 */
public class LambdaWeightedSchedulerTest {

    @Test
    public void schedule() throws Exception {

        LamdaJob job1 = new LamdaJob(3, 10, 20.0);
        LamdaJob job2 = new LamdaJob(1, 2, 50.0);
        LamdaJob job3 = new LamdaJob(6, 19, 100.0);
        LamdaJob job4 = new LamdaJob(2, 100, 200.0);

        List<LamdaJob> jobsToRun = Arrays.asList(job1, job2, job3, job4);

        final List<LamdaJob> jobsThatRan = new ArrayList<>(jobsToRun.size());
        LambdaContainer mockContainer = new LambdaContainer() {
            @Override
            public void runAsync(LamdaJob job) {
                jobsThatRan.add(job);
            }
        };
        Scheduler scheduler = new LambdaWeightedScheduler(mockContainer);

        List<LamdaJob> rejectedJobs = scheduler.schedule(jobsToRun);

        assertTrue(rejectedJobs.contains(job1));
        assertTrue(rejectedJobs.contains(job3));

        assertTrue(jobsThatRan.contains(job2));
        assertTrue(jobsThatRan.contains(job4));

    }


}