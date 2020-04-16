package sample;

public class Master {
    private int numberOfClients;
    private int salary;
    private int spentTime;
    private int readyTakeRequest;

    public Master(int salary) {
        this.numberOfClients = 0;
        this.salary = salary;
        this.spentTime = 0;
        this.readyTakeRequest = 0;
    }

    public void increaseNumberOfClients() {
        this.numberOfClients++;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public int getSalary() {
        return salary;
    }

    public int getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(int spentTime) {
        this.spentTime = spentTime;
    }

    public int getReadyTakeRequest() {
        return readyTakeRequest;
    }

    public void setReadyTakeRequest(int readyTakeRequest) {
        this.readyTakeRequest = readyTakeRequest;
    }

    public void updateData() {
        numberOfClients = 0;
        spentTime = 0;
        readyTakeRequest = 0;
    }
}
