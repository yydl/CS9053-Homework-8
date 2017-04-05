package edu.nyu.cs9053.homework8;

import java.util.List;

/**
 * Created by Joseph on 3/30/17.
 *
 * Represents something which can schedule jobs.
 */
public interface Scheduler {

    /**
     * Schedules a selection of the given jobs and rejects the rest.
     * @param jobs a list of jobs
     * @return a list of the jobs that were rejected
     */
    List<LamdaJob> schedule(List<? extends LamdaJob> jobs);

}
