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

import java.io.File;
import java.util.ArrayList;

public class AddFilmWindow {
    public static void addFilmProcess(Stage primaryStage, MediaPlayer errorPlayer, Button addFilm, ComboBox<Film> films, Scene welcomeScene) {
        addFilm.setOnAction(event1 -> {
            GridPane addFilmPane = new GridPane();
            addFilmPane.setHgap(10);
            addFilmPane.setVgap(10);
            Label addFilmLabel = new Label("Please give name, relative path of the trailer and duration of film.");
            TextField addFilmName = new TextField();
            TextField trailerPath = new TextField();
            TextField duration = new TextField();
            Button backToWelcome = new Button("< BACK");
            Button confirm1 = new Button("OK");

            addFilmPane.add(addFilmLabel, 0, 0, 2, 1);
            GridPane.setHalignment(addFilmLabel, HPos.CENTER);
            addFilmPane.add(new Label("Name: "), 0, 1);
            addFilmPane.add(addFilmName, 1, 1);
            addFilmPane.add(new Label("Trailer (Path): "), 0, 2);
            addFilmPane.add(trailerPath, 1, 2);
            addFilmPane.add(new Label("Duration (m): "), 0, 3);
            addFilmPane.add(duration, 1, 3);
            addFilmPane.add(backToWelcome, 0, 4);
            addFilmPane.add(confirm1, 1, 4);

            GridPane.setHalignment(backToWelcome, HPos.CENTER);
            GridPane.setHalignment(confirm1, HPos.RIGHT);
            addFilmPane.setAlignment(Pos.CENTER);
            Scene addFilmScene = new Scene(addFilmPane, 500, 300);
            primaryStage.setScene(addFilmScene);

            backToWelcome.setOnAction(event2 -> {
                films.getItems().removeAll(films.getItems());
                for (Film f : Reader.filmList) {
                    films.getItems().add(f);
                    films.setValue(f);
                }
                primaryStage.setScene(welcomeScene);
            });

            Label noFilmName = new Label("ERROR: Film name cannot be blank.");
            Label existsFilm = new Label("ERROR: Film already exists.");
            Label noTrailerPath = new Label("ERROR: Trailer path cannot be blank.");
            Label noDuration = new Label("ERROR: Duration cannot be blank.");
            Label falseTrailerPath = new Label("ERROR: There is no such a trailer.");
            Label falseDuration = new Label("ERROR: Duration is not valid.");
            Label successfulAddFilm = new Label("SUCCESS: Film added:" + addFilmName.getText());

            confirm1.setOnAction(event2 -> {
                ArrayList<String> filmNameList = new ArrayList<>();
                for (Film f : Reader.filmList) {
                    filmNameList.add(f.getName());
                }

                if (addFilmName.getText().length() == 0) {
                    CheckError.checkAddFilm(errorPlayer, addFilmPane, filmNameList, addFilmName, trailerPath, duration, noFilmName, existsFilm, noTrailerPath, falseTrailerPath, falseDuration, noDuration, successfulAddFilm, false, true);
                } else if (trailerPath.getText().length() == 0) {
                    CheckError.checkAddFilm(errorPlayer, addFilmPane, filmNameList, addFilmName, trailerPath, duration, noTrailerPath, existsFilm, noFilmName, falseTrailerPath, falseDuration, noDuration, successfulAddFilm, false, true);
                } else if (duration.getText().length() == 0) {
                    CheckError.checkAddFilm(errorPlayer, addFilmPane, filmNameList, addFilmName, trailerPath, duration, noDuration, falseDuration, noTrailerPath, falseTrailerPath, existsFilm, noFilmName, successfulAddFilm, false, true);
                } else if (!duration.getText().chars().allMatch(Character::isDigit) || !(Integer.parseInt(duration.getText()) > 0)){
                    CheckError.checkAddFilm(errorPlayer, addFilmPane, filmNameList, addFilmName, trailerPath, duration, falseDuration, existsFilm, noTrailerPath, falseTrailerPath, noFilmName, noDuration, successfulAddFilm, false, true);
                } else if (filmNameList.contains(addFilmName.getText())) {
                    CheckError.checkAddFilm(errorPlayer, addFilmPane, filmNameList, addFilmName, trailerPath, duration, existsFilm, noTrailerPath,noDuration, successfulAddFilm, falseTrailerPath, noFilmName, falseDuration, true, true);
                } else if (!new File("assets/trailers/" + trailerPath.getText()).isFile()) {
                    CheckError.checkAddFilm(errorPlayer, addFilmPane, filmNameList, addFilmName, trailerPath, duration, falseTrailerPath, falseDuration, noTrailerPath, successfulAddFilm, noFilmName, noDuration, existsFilm, false, true);
                } else {
                    CheckError.checkAddFilm(errorPlayer, addFilmPane, filmNameList, addFilmName, trailerPath, duration, successfulAddFilm, falseDuration, noTrailerPath, falseTrailerPath, noFilmName, noDuration, existsFilm, false, false);
                }
            });
        });
    }
}
