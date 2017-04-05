package edu.nyu.cs9053.homework8;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Joseph on 3/31/17.
 */
public class LambdaSchedulerTest {

    @Test
    public void schedule() throws Exception {
        LamdaJob job1 = new LamdaJob(20, 40);
        LamdaJob job2 = new LamdaJob(50, 90);
        LamdaJob job3 = new LamdaJob(80, 110);
        LamdaJob job4 = new LamdaJob(40, 41);
        LamdaJob job5 = new LamdaJob(35, 40);

        List<LamdaJob> jobsToRun = Arrays.asList(job1, job2, job3, job4, job5);

        final List<LamdaJob> jobsThatRan = new ArrayList<>(jobsToRun.size());
        LambdaContainer mockContainer = new LambdaContainer() {
            @Override
            public void runAsync(LamdaJob job) {
                jobsThatRan.add(job);
            }
        };
        Scheduler scheduler = new LambdaScheduler(mockContainer);

        List<LamdaJob> rejectedJobs = scheduler.schedule(jobsToRun);

        assertTrue(jobsThatRan.contains(job1));
        assertTrue(jobsThatRan.contains(job2));
        assertTrue(jobsThatRan.contains(job4));
        assertTrue(jobsThatRan.size() == 3);

        assertTrue(rejectedJobs.contains(job3));
        assertTrue(rejectedJobs.contains(job5));
        assertTrue(rejectedJobs.size() == 2);
    }

}