package sample;

public class DaylyStatistic {
    private int numberOfDay;
    private int completedRequests;
    private int lostRequests;
    private int profit;
    private int averageSalary;
    private int averageWorkingTime;
    private int freeTime;

    public DaylyStatistic(int numberOfDay, int completedRequests, int lostRequests, int profit, int averageSalary, int averageWorkingTime, int freeTime) {
        this.numberOfDay = numberOfDay;
        this.completedRequests = completedRequests;
        this.lostRequests = lostRequests;
        this.profit = profit;
        this.averageSalary = averageSalary;
        this.averageWorkingTime = averageWorkingTime;
        this.freeTime = freeTime;
    }

    public int getNumberOfDay() {
        return numberOfDay;
    }

    public Integer getCompletedRequests() {
        return completedRequests;
    }

    public Integer getLostRequests() {
        return lostRequests;
    }

    public Integer getProfit() {
        return profit;
    }

    public Integer getAverageSalary() {
        return averageSalary;
    }

    public Integer getAverageWorkingTime() {
        return averageWorkingTime;
    }

    public Integer getFreeTime() {
        return freeTime;
    }
}
