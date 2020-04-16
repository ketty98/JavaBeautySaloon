package sample;

public class Request {
    private Boolean firstService;
    private Boolean secondService;
    private Boolean thirdService;
    private int arrivedTime;

    public Request(Boolean firstService, Boolean secondService, Boolean thirdService, int arrivedTime) {
        this.firstService = firstService;
        this.secondService = secondService;
        this.thirdService = thirdService;
        this.arrivedTime = arrivedTime;
    }

    public int getArrivedTime() {
        return arrivedTime;
    }

    public Boolean getFirstService() {
        return firstService;
    }

    public Boolean getSecondService() {
        return secondService;
    }

    public Boolean getThirdService() {
        return thirdService;
    }
}
