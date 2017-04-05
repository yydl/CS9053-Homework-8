package edu.nyu.cs9053.homework8;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph on 3/30/17.
 */
public class LambdaScheduler extends AbstractScheduler {

    public LambdaScheduler(LambdaContainer lambdaContainer) {
        super(lambdaContainer);
    }

    /**
     * Schedules the maximum of the given jobs with the constraint that no two jobs
     * may overlap.
     * @param jobs a list of available LamdaJobs
     * @return a List of the jobs that were rejected
     */
    @Override
    public List<LamdaJob> schedule(List<LamdaJob> jobs) {
        if (jobs == null) {
            throw new IllegalArgumentException("jobs may not be null");
        }

        List<LamdaJob> sortedJobsByFinishTime = sortJobsByFinishTime(jobs);

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
