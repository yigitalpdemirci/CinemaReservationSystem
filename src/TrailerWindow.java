import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class TrailerWindow {
    public static void trailerWindowProcess(Stage primaryStage, Image emptySeat, Image reservedSeat, MediaPlayer errorPlayer, User u, Button confirm, ComboBox<Film> films, Scene welcomeScene) {
        confirm.setOnAction(event1 -> {
            if (films.getValue() != null) {
                GridPane filmPane = new GridPane();
                filmPane.setVgap(10);
                filmPane.setHgap(10);
                filmPane.setAlignment(Pos.CENTER);
                Label filmTitle = new Label(films.getValue() + "(" + films.getValue().getDuration() + "minutes)");
                String URL = "assets/trailers/" + films.getValue().getTrailerPath();
                Media media = new Media(new File(URL).toURI().toString());
                MediaPlayer trailerPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(trailerPlayer);
                mediaView.setFitWidth(640);
                mediaView.setFitHeight(480);
                Button play = new Button(">");
                Button rewind = new Button("<<");
                Button fastForward = new Button(">>");
                Button backToStart = new Button("|<<");
                Slider volumeSlider = new Slider();
                volumeSlider.setOrientation(Orientation.VERTICAL);
                volumeSlider.setValue(100);
                trailerPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100));
                Button backToFilmSelect = new Button("< BACK");
                Button addHall = new Button("Add Hall");
                Button removeHall = new Button("Remove Hall");

                ComboBox<Hall> halls = new ComboBox<>();
                for (Hall h : Reader.hallList) {
                    if (films.getValue().getName().equals(h.getFilmName())) {
                        halls.getItems().add(h);
                        halls.setValue(h);
                    }
                }

                halls.setPlaceholder(new Label(" WARNING: There are no halls. "));

                Button goToSeatSelect = new Button("OK");
                filmPane.add(filmTitle, 0, 0, 6, 1);
                filmPane.add(mediaView, 0, 1, 5, 5);
                filmPane.add(play, 6, 1);
                filmPane.add(rewind, 6, 2);
                filmPane.add(fastForward, 6, 3);
                filmPane.add(backToStart, 6, 4);
                filmPane.add(volumeSlider, 6, 5);
                filmPane.add(backToFilmSelect, 0, 6);

                if (u.getIsAdmin()) {
                    filmPane.add(addHall, 1, 6);
                    filmPane.add(removeHall, 2, 6);
                }

                filmPane.add(halls, 3, 6);
                filmPane.add(goToSeatSelect, 4, 6);
                GridPane.setHalignment(filmTitle, HPos.CENTER);
                GridPane.setHalignment(backToFilmSelect, HPos.RIGHT);
                Scene trailerScene = new Scene(filmPane, 800, 500);
                primaryStage.setScene(trailerScene);

                play.setOnAction(event2 -> {
                    if (play.getText().equals(">")) {
                        trailerPlayer.play();
                        play.setText("||");
                    } else {
                        trailerPlayer.pause();
                        play.setText(">");
                    }
                });

                rewind.setOnAction(event2 -> {
                    Duration minusFiveSec = new Duration(trailerPlayer.getCurrentTime().toMillis() - 5000);
                    if (minusFiveSec.toMillis() < 0) {
                        trailerPlayer.seek(new Duration(0));
                    } else {
                        trailerPlayer.seek(minusFiveSec);
                    }
                });

                fastForward.setOnAction(event2 -> {
                    Duration plusFiveSec = new Duration(trailerPlayer.getCurrentTime().toMillis() + 5000);
                    if (plusFiveSec.toMillis() > trailerPlayer.getTotalDuration().toMillis()) {
                        trailerPlayer.seek(new Duration(trailerPlayer.getTotalDuration().toMillis()));
                    } else {
                        trailerPlayer.seek(plusFiveSec);
                    }
                });

                backToStart.setOnAction(event2 -> trailerPlayer.seek(Duration.ZERO));

                backToFilmSelect.setOnAction(event2 -> {
                    trailerPlayer.stop();
                    primaryStage.setScene(welcomeScene);
                });

                AddHallWindow.addHallProcess(primaryStage, errorPlayer, films, trailerPlayer, addHall, halls, trailerScene);

                RemoveHallWindow.removeHallProcess(primaryStage, films, trailerPlayer, removeHall, halls, trailerScene);

                SeatSelectWindow.seatSelectProcess(primaryStage, emptySeat, reservedSeat, u, films, trailerPlayer, halls, goToSeatSelect, trailerScene);
            }
        });
    }
}
