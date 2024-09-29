package com.example.mmsimgui;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class mmsimGUI extends Application {
    ArrayList<Player> players = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        //TODO: compile to exe


        stage.setTitle("Player Data Visualiser");
        //defining the axes
        VBox box1 = new VBox();
        MenuBar menubar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem file = new MenuItem("Open File");

        fileMenu.getItems().add(file);
        menubar.getMenus().addAll(fileMenu);

        SplitPane mainPane = new SplitPane();
        TextArea eloHistoryText = new TextArea("EloHistory");
        eloHistoryText.setEditable(false);
        eloHistoryText.setWrapText(true);
        Text avgRD = new Text("");
        Text winrate = new Text("");
        box1.getChildren().addAll(menubar, mainPane, avgRD, winrate, eloHistoryText);
        VBox.setVgrow(mainPane, Priority.ALWAYS);
        VBox.setVgrow(menubar, Priority.ALWAYS);
        VBox.setVgrow(eloHistoryText, Priority.ALWAYS);

        AnchorPane leftAnchor = new AnchorPane();
        TableView<Player> leftTable = new TableView<>();

        leftAnchor.getChildren().addAll(leftTable);

        TableColumn<Player, Integer> name = new TableColumn<>("Name");
        leftTable.getColumns().add(name);
        name.setCellValueFactory(data -> data.getValue().getName().asObject());

        TableColumn<Player, Double> skill = new TableColumn<>("Skill");
        leftTable.getColumns().add(skill);
        skill.setCellValueFactory(data -> data.getValue().getSkill().asObject());

        TableColumn<Player, Integer> rating = new TableColumn<>("Rating");
        leftTable.getColumns().add(rating);
        rating.setCellValueFactory(data -> data.getValue().getRating().asObject());

        TableColumn<Player, Integer> gamesWon = new TableColumn<>("gamesWon");
        leftTable.getColumns().add(gamesWon);
        gamesWon.setCellValueFactory(data -> data.getValue().getGamesWon().asObject());

        TableColumn<Player, Integer> gamesPlayed = new TableColumn<>("gamesPlayed");
        leftTable.getColumns().add(gamesPlayed);
        gamesPlayed.setCellValueFactory(data -> data.getValue().getGamesPlayed().asObject());

        TableColumn<Player, Integer> skillRank = new TableColumn<>("skillRank");
        leftTable.getColumns().add(skillRank);
        skillRank.setCellValueFactory(data -> data.getValue().getSkillRank().asObject());

        TableColumn<Player, Integer> eloRank = new TableColumn<>("eloRank");
        leftTable.getColumns().add(eloRank);
        eloRank.setCellValueFactory(data -> data.getValue().getEloRank().asObject());

        TableColumn<Player, Integer> rankDifference = new TableColumn<>("rankDifference");
        leftTable.getColumns().add(rankDifference);
        rankDifference.setCellValueFactory(data -> data.getValue().getRankDifference().asObject());


        FileChooser fileChooser = new FileChooser();
        EventHandler<ActionEvent> menuEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                players.clear();
                leftTable.getItems().clear();
                File selectedFile = fileChooser.showOpenDialog(stage);
                try {
                    players = readFile(selectedFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                stage.setTitle("Player Data Visualiser: " + selectedFile.getName());
                players.sort(Comparator.comparing(Player::getSkillValue));
                int i = 1;
                for (Player player : players) {
                    player.setSkillRank(i);
                    i++;
                }

                int totalRankDifference = 0;
                players.sort(Comparator.comparing(Player::getRatingValue));
                i = 1;
                for (Player player : players) {
                    player.setEloRank(i);
                    player.setRankDifference(Math.abs(player.getEloRankValue() - player.getSkillRankValue()));
                    totalRankDifference += player.getRankDifferenceValue();
                    i++;
                }

                double avgRankDiff = totalRankDifference / players.size();

                String output = "Average rank difference: " + String.format("%.2f", avgRankDiff);
                avgRD.setText(output);

                for (Player p : players) {
                    leftTable.getItems().add(p);
                }
            }
        };

        file.setOnAction(menuEvent);


        TableView.TableViewSelectionModel<Player> selectionModel = leftTable.getSelectionModel();

        ObservableList<Player> selectedItems = selectionModel.getSelectedItems();

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
                        for (int i = 0; i < change.getList().get(0).getGamesPlayedValue(); i++) {
                            series.getData().add(new XYChart.Data(i, change.getList().get(0).getEloHistory().get(i)));
                        }
                        eloHistoryText.setText(selectedItems.get(0).getEloHistory().toString());
                        winrate.setText("winrate: " + Double.toString((double) selectedItems.get(0).getGamesWonValue() / selectedItems.get(0).getGamesPlayedValue()));
                    }
                });

        mainPane.getItems().addAll(leftAnchor, lineChart);
        Scene scene = new Scene(box1, 800, 600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    public ArrayList<Player> readFile(File file) throws IOException {
        ArrayList<Player> players = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            UUID uuid = UUID.fromString(data[0]);
            int name = Integer.parseInt(data[1]);
            double skill = Double.parseDouble(data[2]);
            int rating = Integer.parseInt(data[3]);
            double volatility = Double.parseDouble(data[4]);
            double confidence = Double.parseDouble(data[5]);
            int gamesWon = Integer.parseInt(data[6]);
            int gamesPlayed = Integer.parseInt(data[7]);
            ArrayList<Integer> eloHistory = new ArrayList<>();
            for (int i = 8; i < data.length; i++) {
                if (i == 8) {
                    eloHistory.add(Integer.parseInt(data[i].substring(2)));
                } else if (i == data.length - 1) {
                    eloHistory.add(Integer.parseInt(data[i].substring(0, data[i].length() - 2)));
                } else {
                    eloHistory.add(Integer.parseInt(data[i]));
                }
            }
            Player p = new Player(uuid, name, skill, rating, volatility, confidence, gamesWon, gamesPlayed, eloHistory);
            players.add(p);
        }

        return players;
    }

    public static void main(String[] args) {
        launch(args);
    }
}