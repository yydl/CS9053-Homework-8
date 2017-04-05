package edu.nyu.cs9053.homework8;

/**
 * Created by Joseph on 3/30/17.
 */
public class LamdaJob {

    private final long startTime;

    private final long finishTime;

    private final Double price;

    public LamdaJob(long startTime, long finishTime) {
        this(startTime, finishTime, null);
    }

    public LamdaJob(long startTime, long finishTime, Double price) {
        if (startTime < 0 || finishTime < 0 || finishTime < startTime) {
            throw new IllegalArgumentException("invalid time");
        }

        if (price != null && price < 0) {
            throw new IllegalArgumentException("invalid price");
        }

        this.startTime = startTime;
        this.finishTime = finishTime;
        this.price = price;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        LamdaJob that = (LamdaJob) obj;
        return (startTime == that.startTime)
                && (finishTime == that.finishTime)
                && (price == null ? that.price == null : price.equals(that.price));
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int hash = Long.hashCode(startTime);
        hash = prime * hash + Long.hashCode(finishTime);
        hash = prime * hash + (price == null ? 0 : price.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return String.format("LambdaJob{startTime=%d, finishTime=%d, price=%f}", startTime, finishTime, price);
    }

}
