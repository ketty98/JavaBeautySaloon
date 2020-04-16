package sample;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Hall {
    private final static int MAX_SALARY = 1000;
    private final static int MIN_SALARY = 100;
    private List<Master> masters;
    private ArrayDeque<Request> queue;
    private int wentAway;
    private int completedRequests;

    public Hall(int amount) {
        this.masters = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            masters.add(new Master((int) (Math.random() * (MAX_SALARY - MIN_SALARY + 1) + MIN_SALARY)));
            queue = new ArrayDeque<>();
            wentAway = 0;
        }
        completedRequests = 0;
    }

    public void addToQueue(Request request) {
        if (queue.size() == 5) {
            wentAway++;
        } else {
            completedRequests++;
            queue.add(request);
        }
    }

    public int getWentAway() {
        return wentAway;
    }

    public List<Master> getMasters() {
        return masters;
    }

    public Integer getQueueSize() {
        return queue.size();
    }

    public void giveRequestMasters(int currentTime) {
        HashSet<Integer> busyMasters = new HashSet<>();
        while (!queue.isEmpty() && busyMasters.size() != masters.size()) {
            int number = (int) (Math.random() * masters.size());
            if (!busyMasters.contains(number) && masters.get(number).getReadyTakeRequest() <= currentTime) {
                Master master = masters.get(number);
                int arrivedTime = queue.poll().getArrivedTime();
                master.setReadyTakeRequest(Math.max(master.getReadyTakeRequest(), arrivedTime) + 60);
                master.increaseNumberOfClients();
                master.setSpentTime(master.getSpentTime() + 60);
            }
            busyMasters.add(number);
        }
    }

    public double getAverageSalary() {
        return getProfit() / masters.size();
    }

    public double getProfit() {
        int sum = 0;
        for (Master master : masters) {
            sum += 0.5 * master.getSalary() * master.getNumberOfClients();
        }
        return sum;
    }

    public double getAverageSpentTime() {
        int sum = 0;
        for (Master master : masters) {
            sum += master.getSpentTime();
        }
        return sum / masters.size();
    }

    public int getCompletedRequests() {
        return completedRequests - queue.size();
    }

    public void updateData() {
        completedRequests = 0;
        wentAway = 0;
        queue.clear();
        for (Master master : masters) {
            master.updateData();
        }
    }
}
