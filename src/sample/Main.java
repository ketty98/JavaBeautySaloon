package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    private final static int PERIOD_OF_SIMULATION = 7;
    private String[] variantsOfAmountMasters = new String[]{"3", "4", "5"};
    private String[] variantsOfTimeStep = new String[]{"5мин", "10мин", "15мин", "30мин", "1час", "1день"};
    private Model model;
    private GridPane dataForStart;
    private GridPane currentInformation;
    private GridPane firstHall;
    private GridPane secondHall;
    private GridPane thirdHall;
    private GridPane statistics;
    private List<Circle> mastersHall1;
    private List<Circle> mastersHall2;
    private List<Circle> mastersHall3;
    private Text time;
    private Text requests;
    private Text lengthQueue1;
    private Text lengthQueue2;
    private Text lengthQueue3;
    private Button buttonNextStep;
    private Button buttonFinish;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mastersHall1 = new ArrayList<>();
        mastersHall2 = new ArrayList<>();
        mastersHall3 = new ArrayList<>();

        addPanelForData();
        addPanelCurrentInformation();
        addPanelStatistic();

        firstHall = new GridPane();
        lengthQueue1 = new Text("0");
        decorateHall(firstHall, "-fx-background-color: #9e9bf5;", "ЗАЛ 1", lengthQueue1, 450, 0);
        secondHall = new GridPane();
        lengthQueue2 = new Text("0");
        decorateHall(secondHall, "-fx-background-color: #8faef5;", "ЗАЛ 2", lengthQueue2, 900, 0);
        thirdHall = new GridPane();
        lengthQueue3 = new Text("0");
        decorateHall(thirdHall, "-fx-background-color: #8595f5;", "ЗАЛ 3", lengthQueue3, 900, 450);


        Group root = new Group(dataForStart, currentInformation, firstHall, secondHall, thirdHall, statistics);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Beauty Saloon");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void nextStep() {
        DaylyStatistic stat = model.nextStep();
        String str = model.getNumberOfDay() + " д. " + model.getTimePerOneDay() / 60 + " ч. " + model.getTimePerOneDay() % 60 + " мин ";
        time.setText(str);
        requests.setText(model.getCurrentAmountOfRequestPerDay().toString());
        lengthQueue1.setText(model.getSaloon().getFirstHall().getQueueSize().toString());
        lengthQueue2.setText(model.getSaloon().getSecondHall().getQueueSize().toString());
        lengthQueue3.setText(model.getSaloon().getThirdHall().getQueueSize().toString());
        changeColorsOfCircles();

        if (stat != null) {
            int day = stat.getNumberOfDay() + 1;
            String completedRequests = stat.getCompletedRequests().toString();
            String lostRequests = stat.getLostRequests().toString();
            String profit = stat.getProfit().toString() + "руб. ";
            String averageSalary = stat.getAverageSalary().toString() + "руб.";
            String averageSpentTime = stat.getAverageWorkingTime().toString() + "мин";
            String freeTime = stat.getFreeTime().toString() + "%";
            addStatistic(day, completedRequests, lostRequests, profit, averageSalary, averageSpentTime, freeTime);
        }
        if (model.getNumberOfDay() == PERIOD_OF_SIMULATION) {
            buttonNextStep.setDisable(true);
            buttonFinish.setDisable(true);
            int day = PERIOD_OF_SIMULATION + 1;
            String completedRequests = model.getTotalCompletedRequests().toString();
            String lostRequests = model.getTotalLostRequests().toString();
            String profit = model.getTotalProfit() + "руб. ";
            String averageSalary = model.getTotalAverageSalary() + "руб.";
            String averageSpentTime = model.getTotalAverageSpentTime() + "мин";
            String freeTime = model.getTotalFreeTime() / 7 + "%";
            addStatistic(day, completedRequests, lostRequests, profit, averageSalary, averageSpentTime, freeTime);
        }
    }

    private void addStatistic(int day, String completedRequests, String lostRequests, String profit, String averageSalary, String averageSpentTime, String freeTime) {
        Text text = new Text(completedRequests);
        text.setFont(new Font(20));
        statistics.add(text, 1, day);
        text = new Text(lostRequests);
        text.setFont(new Font(20));
        statistics.add(text, 2, day);
        text = new Text(profit);
        text.setFont(new Font(20));
        statistics.add(text, 3, day);
        text = new Text(averageSalary);
        text.setFont(new Font(20));
        statistics.add(text, 4, day);
        text = new Text(averageSpentTime);
        text.setFont(new Font(20));
        statistics.add(text, 5, day);
        text = new Text(freeTime);
        text.setFont(new Font(20));
        statistics.add(text, 6, day);

    }

    private void changeColorsOfCircles() {
        int currentTime = model.getTimePerOneDay();
        List<Master> masters = model.getSaloon().getFirstHall().getMasters();
        for (int i = 0; i < masters.size(); i++) {
            if (masters.get(i).getReadyTakeRequest() <= currentTime) {
                mastersHall1.get(i).setFill(Color.GREEN);
            } else {
                mastersHall1.get(i).setFill(Color.RED);
            }
        }
        masters = model.getSaloon().getSecondHall().getMasters();
        for (int i = 0; i < masters.size(); i++) {
            if (masters.get(i).getReadyTakeRequest() <= currentTime) {
                mastersHall2.get(i).setFill(Color.GREEN);
            } else {
                mastersHall2.get(i).setFill(Color.RED);
            }
        }
        masters = model.getSaloon().getThirdHall().getMasters();
        for (int i = 0; i < masters.size(); i++) {
            if (masters.get(i).getReadyTakeRequest() <= currentTime) {
                mastersHall3.get(i).setFill(Color.GREEN);
            } else {
                mastersHall3.get(i).setFill(Color.RED);
            }
        }
    }

    private void addCircles(int amountOfMasters, GridPane hall, List<Circle> mastersHall) {
        Circle master;
        for (int i = 0; i < amountOfMasters; i++) {
            master = new Circle();
            master.setRadius(30);
            master.setFill(Color.GREEN);
            mastersHall.add(master);
            hall.add(master, 0, i + 2);
        }
    }

    private void decorateHall(GridPane hall, String color, String titleText, Text lengthQueue, int x, int y) {
        hall.setMinSize(450, 450);
        hall.setVgap(5);
        hall.setHgap(5);
        hall.setAlignment(Pos.TOP_LEFT);
        hall.setStyle(color);
        hall.setLayoutX(x);
        hall.setLayoutY(y);
        Text titleHall = new Text(titleText);
        titleHall.setFont(new Font(45));
        hall.add(titleHall, 0, 0);
        Text queueH = new Text("Количество людей в очереди:");
        queueH.setFont(new Font(25));
        hall.add(queueH, 0, 1);
        lengthQueue.setFont(new Font(25));
        hall.add(lengthQueue, 1, 1);
    }

    private void addPanelStatistic(){
        statistics = new GridPane();
        Text text = new Text("  День");
        text.setFont(new Font(15));
        statistics.add(text, 0, 0);
        for (int i = 1; i <= PERIOD_OF_SIMULATION; i++) {
            text = new Text("  " + i);
            text.setFont(new Font(25));
            statistics.add(text, 0, i);
        }
        text = new Text("За неделю ");
        text.setFont(new Font(15));
        statistics.add(text, 0, 8);
        text = new Text(" Обслужили ");
        text.setFont(new Font(15));
        statistics.add(text, 1, 0);
        text = new Text(" Ушло ");
        text.setFont(new Font(15));
        statistics.add(text, 2, 0);
        text = new Text(" Прибыль ");
        text.setFont(new Font(15));
        statistics.add(text, 3, 0);
        text = new Text(" Средняя з/п мастеров ");
        text.setFont(new Font(15));
        statistics.add(text, 4, 0);
        text = new Text(" Среднее время работы ");
        text.setFont(new Font(15));
        statistics.add(text, 5, 0);
        text = new Text(" Простой ");
        text.setFont(new Font(15));
        statistics.add(text, 6, 0);
        statistics.setMinSize(500, 200);
        statistics.setVgap(5);
        statistics.setHgap(5);
        statistics.setAlignment(Pos.TOP_LEFT);
        statistics.setLayoutY(470);
    }

    private void addPanelCurrentInformation(){
        currentInformation = new GridPane();
        currentInformation.setMinSize(300, 200);
        currentInformation.setVgap(5);
        currentInformation.setHgap(5);
        currentInformation.setAlignment(Pos.TOP_LEFT);
        currentInformation.setLayoutY(200);
        Text amountTime = new Text("Время с момента открытия:");
        amountTime.setFont(new Font(22));
        currentInformation.add(amountTime, 0, 0);
        time = new Text("0 д. 0 ч. 0 мин");
        time.setFont(new Font(22));
        currentInformation.add(time, 0, 1);
        Text amountOfPeople = new Text("Всего заявок получено:");
        amountOfPeople.setFont(new Font(22));
        currentInformation.add(amountOfPeople, 0, 2);
        requests = new Text("0");
        requests.setFont(new Font(22));
        currentInformation.add(requests, 0, 3);
        buttonNextStep = new Button("Следующий шаг");
        buttonNextStep.setOnAction(event -> {
            nextStep();
        });
        buttonNextStep.setStyle("-fx-background-color: rgba(96,51,211,0.72); -fx-textfill: #000000;");
        currentInformation.add(buttonNextStep, 0, 7);
        buttonFinish = new Button("Завершить");
        buttonFinish.setOnAction(event -> {
            while (model.getNumberOfDay() != PERIOD_OF_SIMULATION) {
                nextStep();
            }
        });
        buttonFinish.setStyle("-fx-background-color: rgba(211,16,21,0.72); -fx-textfill: #000000;");
        currentInformation.add(buttonFinish, 0, 8);

    }
    private void addPanelForData() {
        dataForStart = new GridPane();
        dataForStart.setMinSize(300, 200);
        dataForStart.setVgap(5);
        dataForStart.setHgap(5);
        dataForStart.setAlignment(Pos.TOP_LEFT);

        Text infoLabel = new Text("Введите начальные данные для симуляции");
        infoLabel.setFont(new Font(13));
        dataForStart.add(infoLabel, 0, 0);

        Text infoFirstHall = new Text("Количество мастеров в зале 1");
        dataForStart.add(infoFirstHall, 0, 1);

        ChoiceBox employeesFirstHall = new ChoiceBox();
        employeesFirstHall.getItems().addAll(variantsOfAmountMasters);
        dataForStart.add(employeesFirstHall, 1, 1);

        Text infoSecondHall = new Text("Количество мастеров в зале 2");
        dataForStart.add(infoSecondHall, 0, 2);

        ChoiceBox employeesSecondHall = new ChoiceBox();
        employeesSecondHall.getItems().addAll(variantsOfAmountMasters);
        dataForStart.add(employeesSecondHall, 1, 2);

        Text infoThirdHall = new Text("Количество мастеров в зале 3");
        dataForStart.add(infoThirdHall, 0, 3);

        ChoiceBox employeesThirdHall = new ChoiceBox();
        employeesThirdHall.getItems().addAll(variantsOfAmountMasters);
        dataForStart.add(employeesThirdHall, 1, 3);

        Text stepOfSimulation = new Text("Шаг моделирования");
        dataForStart.add(stepOfSimulation, 0, 4);

        ChoiceBox timeSteps = new ChoiceBox();
        timeSteps.getItems().addAll(variantsOfTimeStep);
        dataForStart.add(timeSteps, 1, 4);

        Button buttonBegin = new Button("Начать симуляцию");
        buttonBegin.setOnAction(event -> {
            int amountFirstHall = Integer.parseInt(employeesFirstHall.getValue().toString());
            int amountSecondHall = Integer.parseInt(employeesSecondHall.getValue().toString());
            int amountThirdHall = Integer.parseInt(employeesThirdHall.getValue().toString());
            String timeStep = timeSteps.getValue().toString();
            dataForStart.setDisable(true);
            addCircles(amountFirstHall, firstHall, mastersHall1);
            addCircles(amountSecondHall, secondHall, mastersHall2);
            addCircles(amountThirdHall, thirdHall, mastersHall3);
            model = new Model(amountFirstHall, amountSecondHall, amountThirdHall, timeStep);
        });
        buttonBegin.setStyle("-fx-background-color: rgba(55,211,42,0.72); -fx-textfill: #000000;");
        dataForStart.add(buttonBegin, 0, 6);
    }
}
