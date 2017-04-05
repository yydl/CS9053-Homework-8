package edu.nyu.cs9053.homework8;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph on 3/30/17.
 *
 * Schedules jobs to maximize the number of jobs run.
 */
public class LambdaScheduler extends AbstractScheduler {

    public LambdaScheduler(LambdaContainer lambdaContainer) {
        super(lambdaContainer);
    }

    /**
     * Schedules a subset of the given jobs to be run. The maximum number of jobs are chosen such that
     * no two jobs may overlap.
     * @param jobs a list of LamdaJobs to run
     * @return a List of the jobs that were rejected
     */
    @Override
    public List<LamdaJob> schedule(List<? extends LamdaJob> jobs) {
        if (jobs == null) {
            throw new IllegalArgumentException("jobs may not be null");
        }

        List<? extends LamdaJob> sortedJobsByFinishTime = sortJobsByFinishTime(jobs);

        List<LamdaJob> rejectedJobs = new ArrayList<>(jobs.size());

        long lastFinishTime = 0;

        for (LamdaJob job : sortedJobsByFinishTime) {
            if (job.getStartTime() >= lastFinishTime) {
                sendJobToLambdaContainer(job);
                lastFinishTime = job.getFinishTime();
            }
            else {
                rejectedJobs.add(job);
            }
        }

        return rejectedJobs;
    }

}
