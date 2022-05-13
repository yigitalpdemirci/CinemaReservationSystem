import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddHallWindow {
    public static void addHallProcess(Stage primaryStage, MediaPlayer errorPlayer, ComboBox<Film> films, MediaPlayer trailerPlayer, Button addHall, ComboBox<Hall> halls, Scene trailerScene) {
        addHall.setOnAction(event2 -> {
            trailerPlayer.stop();
            GridPane addHallPane = new GridPane();
            addHallPane.setHgap(10);
            addHallPane.setVgap(10);
            Label addHallLabel = new Label(films.getValue() + "(" + films.getValue().getDuration() + "minutes)");
            TextField addHallName = new TextField();
            TextField price = new TextField();
            ComboBox<Integer> rows = new ComboBox<>();
            rows.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10);
            ComboBox<Integer> columns = new ComboBox<>();
            rows.setValue(3);
            columns.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10);
            columns.setValue(3);
            Button backToTrailer1 = new Button("< BACK");
            Button confirm2 = new Button("OK");
            addHallPane.add(addHallLabel, 0, 0, 2, 1);
            addHallPane.add(new Label("Row: "), 0, 1);
            addHallPane.add(rows, 1, 1);
            addHallPane.add(new Label("Column: "), 0, 2);
            addHallPane.add(columns, 1, 2);
            addHallPane.add(new Label("Name: "), 0, 3);
            addHallPane.add(addHallName, 1, 3);
            addHallPane.add(new Label("Price: "), 0, 4);
            addHallPane.add(price, 1, 4);
            addHallPane.add(backToTrailer1, 0, 5);
            addHallPane.add(confirm2, 1, 5);

            addHallPane.setAlignment(Pos.CENTER);
            GridPane.setHalignment(rows, HPos.CENTER);
            GridPane.setHalignment(columns, HPos.CENTER);
            GridPane.setHalignment(backToTrailer1, HPos.LEFT);
            GridPane.setHalignment(confirm2, HPos.RIGHT);

            Scene addHallScene = new Scene(addHallPane, 500, 300);
            primaryStage.setScene(addHallScene);

            backToTrailer1.setOnAction(event3 -> {
                halls.getItems().removeAll(halls.getItems());
                for (Hall h : Reader.hallList) {
                    if (h.getFilmName().equals(films.getValue().getName())) {
                        halls.getItems().add(h);
                        halls.setValue(h);
                    }
                }
                primaryStage.setScene(trailerScene);
            });

            Label noHallName = new Label("ERROR: Hall name cannot be blank.");
            Label noPrice = new Label("ERROR: Price cannot be blank.");
            Label existsHall = new Label("ERROR: Hall already exists.");
            Label successfulHallAdd = new Label("SUCCESS: Hall successfully added.");
            Label falsePrice = new Label("ERROR: Price is not valid.");
            addHallPane.setMinSize(400, 250);
            confirm2.setOnAction(event3 -> {
                ArrayList<String> hallNamesList = new ArrayList<>();
                for (Hall h : Reader.hallList) {
                    hallNamesList.add(h.getHallName());
                }

                if (addHallName.getText().length() == 0) {
                    CheckError.checkAddHall(errorPlayer, addHallPane, films, addHallName, price, rows, columns, noHallName, noPrice, existsHall, successfulHallAdd, falsePrice, true);
                } else if (price.getText().length() == 0) {
                    CheckError.checkAddHall(errorPlayer, addHallPane, films, addHallName, price, rows, columns, noPrice, noHallName, existsHall, falsePrice, successfulHallAdd, true);
                } else if (!price.getText().chars().allMatch(Character::isDigit) || !(Integer.parseInt(price.getText()) > 0)) {
                    CheckError.checkAddHall(errorPlayer, addHallPane, films, addHallName, price, rows, columns, falsePrice, noHallName, existsHall, successfulHallAdd, noPrice, true);
                } else if (hallNamesList.contains(addHallName.getText())) {
                    CheckError.checkAddHall(errorPlayer, addHallPane, films, addHallName, price, rows, columns, existsHall, noPrice, noHallName, falsePrice, successfulHallAdd, true);
                } else {
                    CheckError.checkAddHall(errorPlayer, addHallPane, films, addHallName, price, rows, columns, successfulHallAdd, noHallName, noPrice, falsePrice, existsHall, false);
                }
            });
        });
    }
}
