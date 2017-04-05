package edu.nyu.cs9053.homework8;

import java.util.*;

/**
 * Created by yossie on 4/4/17.
 */
public class TestJ {



    public static void main(String[] args) {
        LamdaJob job1 = new LamdaJob(3, 10, 20.0);
        LamdaJob job2 = new LamdaJob(1, 2, 50.0);
        LamdaJob job3 = new LamdaJob(6, 19, 100.0);
        LamdaJob job4 = new LamdaJob(2, 100, 200.0);

        List<LamdaJob> jobs = Arrays.asList(job1, job2, job3, job4);

        System.out.println(job1);
    }

}
