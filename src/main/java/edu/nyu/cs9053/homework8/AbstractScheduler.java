package edu.nyu.cs9053.homework8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Joseph on 4/2/17.
 *
 * Schedules jobs to run on a given container.
 */
public abstract class AbstractScheduler implements Scheduler {

    /**
     * Sorts the given list of LambdaJobs by startTime (ascending)
     * @param jobs
     * @return a copy of the list - sorted
     */
    protected static List<LamdaJob> sortJobsByStartTime(List<? extends LamdaJob> jobs) {
        if (jobs == null) {
            throw new IllegalArgumentException("jobs may not be null");
        }

        List<LamdaJob> sortedJobsByStartTime = new ArrayList<>(jobs);
        Collections.sort(sortedJobsByStartTime, new Comparator<LamdaJob>() {
            @Override
            public int compare(LamdaJob job1, LamdaJob job2) {
                if (job1 == null || job2 == null) {
                    throw new IllegalArgumentException("jobs may not be null");
                }
                return Long.compare(job1.getStartTime(), job2.getStartTime());
            }
        });
        return sortedJobsByStartTime;
    }

    /**
     * Sorts the given list of LambdaJobs by finishTime (ascending)
     * @param jobs
     * @return a copy of the list - sorted
     */
    protected static List<LamdaJob> sortJobsByFinishTime(List<? extends LamdaJob> jobs) {
        if (jobs == null) {
            throw new IllegalArgumentException("jobs may not be null");
        }

        List<LamdaJob> sortedJobsByFinishTime = new ArrayList<>(jobs);
        Collections.sort(sortedJobsByFinishTime, new Comparator<LamdaJob>() {
            @Override
            public int compare(LamdaJob job1, LamdaJob job2) {
                if (job1 == null || job2 == null) {
                    throw new IllegalArgumentException("jobs may not be null");
                }
                return Long.compare(job1.getFinishTime(), job2.getFinishTime());
            }
        });
        return sortedJobsByFinishTime;
    }

    private final LambdaContainer lambdaContainer;

    protected AbstractScheduler(LambdaContainer lambdaContainer) {
        if (lambdaContainer == null) {
            throw new IllegalArgumentException("lambdaContainer may not be null");
        }

        this.lambdaContainer = lambdaContainer;
    }

    protected void sendJobToLambdaContainer(LamdaJob job) {
        if (job == null) {
            throw new IllegalArgumentException("job may not be null");
        }

        lambdaContainer.runAsync(job);
    }

}
