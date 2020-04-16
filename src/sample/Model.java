package sample;

public class Model {
    private final int ONE_WORK_DAY = 480;
    private final int TIME_STEP;
    private Saloon saloon;
    private int timePerOneDay;
    private int numberOfDay;
    private int currentTimePerStep;
    private int currentAmountOfRequestPerDay;
    private int totalLostRequests;
    private int totalAverageSalary;
    private int totalAverageSpentTime;
    private int totalCompletedRequests;
    private int totalProfit;
    private int totalFreeTime;

    public Model(int amountFirstHall, int amountSecondHall, int amountThirdHall, String timeStep) {
        this.saloon = new Saloon(amountFirstHall, amountSecondHall, amountThirdHall);
        this.TIME_STEP = recognizeStep(timeStep);
        timePerOneDay = 0;
        numberOfDay = 0;
        currentTimePerStep = 0;
        currentAmountOfRequestPerDay = 0;
        totalLostRequests = 0;
        totalAverageSalary = 0;
        totalAverageSpentTime = 0;
        totalCompletedRequests = 0;
        totalProfit = 0;
        totalFreeTime = 0;
    }

    private int recognizeStep(String timeStep) {
        switch (timeStep) {
            case "5мин":
                return 5;
            case "10мин":
                return 10;
            case "15мин":
                return 15;
            case "30мин":
                return 30;
            case "1час":
                return 60;
            case "1день":
                return 480;
            default:
                return 60;
        }
    }

    public DaylyStatistic nextStep() {
        while (currentTimePerStep < TIME_STEP) {
            saloon.giveRequestMasters(currentTimePerStep + timePerOneDay);
            currentTimePerStep += sendRequest(currentTimePerStep + timePerOneDay);
            currentAmountOfRequestPerDay++;
        }
        timePerOneDay += TIME_STEP;
        saloon.giveRequestMasters(timePerOneDay);
        if (timePerOneDay == ONE_WORK_DAY) {
            numberOfDay++;
            timePerOneDay = 0;
            currentTimePerStep = 0;
            return collectStatistics();
        } else {
            currentTimePerStep -= TIME_STEP;
        }
        return null;
    }

    private DaylyStatistic collectStatistics() {
        int lostRequests = saloon.getFirstHall().getWentAway() + saloon.getSecondHall().getWentAway() + saloon.getThirdHall().getWentAway();
        totalLostRequests += lostRequests;
        int averageSalary = (int) ((saloon.getFirstHall().getAverageSalary() + saloon.getSecondHall().getAverageSalary() + saloon.getThirdHall().getAverageSalary()) / 3);
        totalAverageSalary += averageSalary;
        int averageSpentTime = (int) ((saloon.getFirstHall().getAverageSpentTime() + saloon.getSecondHall().getAverageSpentTime() + saloon.getThirdHall().getAverageSpentTime()) / 3);
        totalAverageSpentTime += averageSpentTime;
        int completedRequests = saloon.getFirstHall().getCompletedRequests() + saloon.getSecondHall().getCompletedRequests() + saloon.getThirdHall().getCompletedRequests();
        totalCompletedRequests += completedRequests;
        int profit = (int) (saloon.getFirstHall().getProfit() + saloon.getSecondHall().getProfit() + saloon.getThirdHall().getProfit());
        totalProfit += profit;
        int freeTime = (ONE_WORK_DAY - averageSpentTime) * 100 / ONE_WORK_DAY;
        totalFreeTime += freeTime;
        saloon.updateDataForNextDay();
        return new DaylyStatistic(numberOfDay - 1, completedRequests, lostRequests, profit, averageSalary, averageSpentTime, freeTime);

    }

    private int sendRequest(int currentTime) {
        double r = Math.random();
        boolean firstService = false;
        boolean secondService = false;
        boolean thirdService = false;
        if (r < 0.15) {
            firstService = true;
        } else if (r < 0.45) {
            secondService = true;
        } else {
            thirdService = true;
        }
        saloon.receiveRequest(new Request(firstService, secondService, thirdService, currentTime));
        int timeUntilNextRequest;
        r = Math.random();
        if (numberOfDay > 4 || timePerOneDay > 300) {
            timeUntilNextRequest = (int) (r * 10);
        } else {
            timeUntilNextRequest = (int) (r * 20);
        }
        return timeUntilNextRequest;
    }

    public Integer getCurrentAmountOfRequestPerDay() {
        return currentAmountOfRequestPerDay;
    }

    public int getTimePerOneDay() {
        return timePerOneDay;
    }

    public int getNumberOfDay() {
        return numberOfDay;
    }

    public Saloon getSaloon() {
        return saloon;
    }

    public Integer getTotalLostRequests() {
        return totalLostRequests;
    }

    public Integer getTotalAverageSalary() {
        return totalAverageSalary;
    }

    public Integer getTotalAverageSpentTime() {
        return totalAverageSpentTime;
    }

    public Integer getTotalCompletedRequests() {
        return totalCompletedRequests;
    }

    public Integer getTotalProfit() {
        return totalProfit;
    }

    public Integer getTotalFreeTime() {
        return totalFreeTime;
    }
}
