package edu.nyu.cs9053.homework8;

import java.util.List;

/**
 * Created by Joseph on 3/30/17.
 */
public interface Scheduler {

    /**
     * Schedules a selection of the given jobs and rejects the rest.
     * @param jobs a list of jobs
     * @return a list of the jobs that were rejected
     */
    List<LamdaJob> schedule(List<LamdaJob> jobs);

}
