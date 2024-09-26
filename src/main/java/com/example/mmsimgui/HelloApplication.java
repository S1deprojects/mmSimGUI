package com.example.mmsimgui;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("playerlist.csv"));
        String line = br.readLine();

        ArrayDeque<Player> players = new ArrayDeque<>();
        //line = br.readLine();
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            UUID uuid = UUID.fromString(data[0].toString());
            String name = data[1];
            double skill = Double.parseDouble(data[2]);
            int rating  = Integer.parseInt(data[3]);
            double volatility = Double.parseDouble(data[4]);
            double confidence = Double.parseDouble(data[5]);
            int gamesWon  = Integer.parseInt(data[6]);
            int gamesPlayed = Integer.parseInt(data[7]);
            ArrayList eloHistory = new ArrayList();
            for(int i = 8; i < data.length; i++) {
                if(i == 8) {
                    eloHistory.add(Integer.parseInt(data[i].substring(2, data[i].length())));
                }
                else if (i == data.length-1) {
                    eloHistory.add(Integer.parseInt(data[i].substring(0, data[i].length() - 2)));
                }
                else {
                    eloHistory.add(Integer.parseInt(data[i]));
                }
            }
            Player p = new Player(uuid, name, skill, rating, volatility, confidence, gamesWon, gamesPlayed, eloHistory);
            players.add(p);
            line = br.readLine();
        }


        stage.setTitle("Player Data Visualiser");
        //defining the axes
        VBox box1 = new VBox();
        //MenuBar menubar = new MenuBar();
        SplitPane mainPane = new SplitPane();
        HBox bottomPane = new HBox();
        box1.getChildren().addAll(mainPane, bottomPane);

        AnchorPane leftAnchor = new AnchorPane();
        Label leftLabel = new Label("test1Left");
        TableView leftTable = new TableView();

        leftAnchor.getChildren().addAll(leftLabel, leftTable);

        TableColumn<Player, String> name = new TableColumn<>("Name");
        leftTable.getColumns().add(name);
        name.setCellValueFactory(new PropertyValueFactory<>("skill"));

        TableColumn<Player, String> skill = new TableColumn<>("Skill");
        leftTable.getColumns().add(skill);
        skill.setCellValueFactory(new PropertyValueFactory<>("skill"));

        TableColumn<Player, String> rating = new TableColumn<>("Rating");
        leftTable.getColumns().add(rating);
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        TableColumn<Player, String> gamesWon = new TableColumn<>("gamesWon");
        leftTable.getColumns().add(gamesWon);
        gamesWon.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));

        TableColumn<Player, String> gamesPlayed = new TableColumn<>("gamesPlayed");
        leftTable.getColumns().add(gamesPlayed);
        gamesPlayed.setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));

        for(Player p : players)
        {
            leftTable.getItems().add(p);
        }

        TableView.TableViewSelectionModel<Player> selectionModel = leftTable.getSelectionModel();

        ObservableList<Player> selectedItems =
                selectionModel.getSelectedItems();

        ScrollPane datavis = new ScrollPane();

        Label bottomLabel = new Label("test1LeftBottom");
        bottomPane.getChildren().addAll(bottomLabel);
        mainPane.getItems().addAll(leftAnchor, datavis);

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Game");
        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Elo Analysis");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Games");
        //populating the series with data


        selectedItems.addListener(
                new ListChangeListener<Player>() {
                    @Override
                    public void onChanged(
                            Change<? extends Player> change) {
                        series.getData().clear();
                        for(int i = 0; i < change.getList().get(0).getGamesPlayed(); i++)
                        {
                            series.getData().add(new XYChart.Data(i, change.getList().get(0).getEloHistory().get(i)));
                        }
                    }
                });

        datavis.setContent(lineChart);
        Scene scene = new Scene(box1, 800, 600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}