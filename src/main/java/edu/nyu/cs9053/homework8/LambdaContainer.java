package edu.nyu.cs9053.homework8;

/**
 * Created by Joseph on 4/3/17.
 */
public interface LambdaContainer {

    /**
     * Submits the given job to be run on the container. Will return immediately; job may run at a later point in time.
     * @param job
    */
    void runAsync(LamdaJob job);

}
