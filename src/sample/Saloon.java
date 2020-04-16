package sample;

public class Saloon {
    private Hall firstHall;
    private Hall secondHall;
    private Hall thirdHall;

    public Saloon(int amountFirstHall, int amountSecondHall, int amountThirdHall) {
        this.firstHall = new Hall(amountFirstHall);
        this.secondHall = new Hall(amountSecondHall);
        this.thirdHall = new Hall(amountThirdHall);
    }

    public void receiveRequest(Request request) {
        if (request.getFirstService()) {
            firstHall.addToQueue(request);
        } else if (request.getSecondService()) {
            secondHall.addToQueue(request);
        } else {
            thirdHall.addToQueue(request);
        }
    }

    public void giveRequestMasters(int currentTime) {
        firstHall.giveRequestMasters(currentTime);
        secondHall.giveRequestMasters(currentTime);
        thirdHall.giveRequestMasters(currentTime);
    }

    public Hall getFirstHall() {
        return firstHall;
    }

    public Hall getSecondHall() {
        return secondHall;
    }

    public Hall getThirdHall() {
        return thirdHall;
    }

    public void updateDataForNextDay() {
        firstHall.updateData();
        secondHall.updateData();
        thirdHall.updateData();
    }
}
