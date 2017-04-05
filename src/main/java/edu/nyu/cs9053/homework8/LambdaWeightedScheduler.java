package edu.nyu.cs9053.homework8;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph on 3/30/17.
 */
public class LambdaWeightedScheduler extends AbstractScheduler {

    /**
     * Ensure none of the LambdaJobs are missing a price
     * @return false if any job is null or missing a price
     */
    private static boolean validateJobs(List<LamdaJob> jobs) {
        for (LamdaJob job : jobs) {
            if (job == null || job.getPrice() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * For each job in the given array, we compute the <em>index</em> of the first non-overlapping job to the left of it.
     * @param sortedByFinishTime given jobs must already be sorted by finish time
     * @return a List of indexes that correspond with the given array
     */
    private static List<Integer> computeLargestCompatibleIndexes(List<LamdaJob> sortedByFinishTime) {
        List<LamdaJob> sortedByStartTime = sortJobsByStartTime(sortedByFinishTime);

        List<Integer> result = new ArrayList<>(sortedByStartTime.size());

        // indexes for each respective list
        int startIndex = 0;
        int finishIndex = 0;

        while (startIndex < sortedByStartTime.size() && finishIndex < sortedByFinishTime.size()) {
            long startTime = sortedByStartTime.get(startIndex).getStartTime();
            long finishTime = sortedByFinishTime.get(finishIndex).getFinishTime();

            if (finishTime <= startTime) {
                // we are not overlapping yet, continue
                finishIndex++;
            }
            else {
                // we overlap! store the index of the last job
                if (finishIndex == 0) {
                    result.add(0);
                }
                else {
                    result.add(finishIndex - 1);
                }
                startIndex++;
            }
        }

        return result;
    }

    /**
     * Computes the memoized table (Dynamic Programming) that contains the solution for each level of recursion
     */
    private static List<Double> computeMemoizedTable(List<LamdaJob> jobs, List<Integer> largestCompatibleIndexes) {
        List<Double> memoizeTable = new ArrayList<>(jobs.size());
        memoizeTable.add(jobs.get(0).getPrice());

        for (int i = 1; i < jobs.size(); i++) {
            LamdaJob job = jobs.get(i);

            int largestCompatibleIndex = largestCompatibleIndexes.get(i);
            double allPricesIncludingThis = job.getPrice() + memoizeTable.get(largestCompatibleIndex);

            memoizeTable.add(Math.max(allPricesIncludingThis, memoizeTable.get(i - 1)));
        }

        return memoizeTable;
    }

    private static List<LamdaJob> backOutSolution(List<Double> memoizedTable,
                                        List<LamdaJob> jobs,
                                        List<Integer> largestCompatibleIndexes,
                                        int i) {
        List<LamdaJob> result = new ArrayList<>(jobs.size());

        while (i >= 0) {
            LamdaJob job = jobs.get(i);
            int largestCompatibleIndex = largestCompatibleIndexes.get(i);
            double allPricesIncludingThis = job.getPrice() + memoizedTable.get(largestCompatibleIndex);

            double previousResult;
            if (i > 0) {
                previousResult = memoizedTable.get(i - 1);
            }
            else {
                previousResult = jobs.get(0).getPrice();
                largestCompatibleIndex = -1;
            }

            if (allPricesIncludingThis > previousResult) {
                result.add(jobs.get(i));
                i = largestCompatibleIndex;
            }
            else {
                i--;
            }
        }

        return result;
    }

    public LambdaWeightedScheduler(LambdaContainer lambdaContainer) {
        super(lambdaContainer);
    }

    @Override
    public List<LamdaJob> schedule(List<LamdaJob> jobs) {
        if (jobs == null) {
            throw new IllegalArgumentException("jobs may not be null");
        }

        if (!validateJobs(jobs)) {
            throw new IllegalArgumentException("all jobs must not be null and contain a price");
        }

        List<LamdaJob> sortedJobsByFinishTime = sortJobsByFinishTime(jobs);

        List<Integer> largestCompatibleIndexes = computeLargestCompatibleIndexes(sortedJobsByFinishTime);

        // memoizeTable holds intermediate results for the highest priced combination
        List<Double> memoizeTable = computeMemoizedTable(sortedJobsByFinishTime, largestCompatibleIndexes);

        List<LamdaJob> optimalSolution = backOutSolution(memoizeTable, sortedJobsByFinishTime, largestCompatibleIndexes, jobs.size() - 1);

        for (LamdaJob job : optimalSolution) {
            sendJobToLambdaContainer(job);
        }

        List<LamdaJob> rejectedJobs = new ArrayList<>(jobs);
        rejectedJobs.removeAll(optimalSolution);

        return rejectedJobs;
    }

}
