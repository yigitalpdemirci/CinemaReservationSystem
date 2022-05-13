import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class RemoveHallWindow {
    public static void removeHallProcess(Stage primaryStage, ComboBox<Film> films, MediaPlayer trailerPlayer, Button removeHall, ComboBox<Hall> halls, Scene trailerScene) {
        removeHall.setOnAction(event2 -> {
            trailerPlayer.stop();
            GridPane removeHallPane = new GridPane();
            removeHallPane.setVgap(10);
            removeHallPane.setHgap(10);
            Label removeHallLabel = new Label("Select the hall that you desire to remove from " + films.getValue() + " and then click OK.");
            Button backToTrailer2 = new Button("< BACK");
            Button confirm2 = new Button("OK");
            ComboBox<Hall> removeHalls = new ComboBox<>();
            for (Hall h : Reader.hallList) {
                if (films.getValue().getName().equals(h.getFilmName())) {
                    removeHalls.getItems().add(h);
                    removeHalls.setValue(h);
                }
            }
            removeHalls.setPlaceholder(new Label("WARNING: There are no halls."));
            removeHallPane.add(removeHallLabel, 0, 0, 2, 1);
            removeHallPane.add(removeHalls, 0, 1, 2, 1);
            removeHallPane.add(backToTrailer2, 0, 2);
            removeHallPane.add(confirm2, 1, 2);
            removeHallPane.getColumnConstraints().addAll(new ColumnConstraints(300), new ColumnConstraints(300));

            GridPane.setHalignment(removeHallLabel, HPos.CENTER);
            GridPane.setHalignment(removeHalls, HPos.CENTER);
            GridPane.setHalignment(backToTrailer2, HPos.RIGHT);
            GridPane.setHalignment(confirm2, HPos.LEFT);

            Scene removeHallScene = new Scene(removeHallPane, 600, 120);
            primaryStage.setScene(removeHallScene);

            backToTrailer2.setOnAction(event3 -> primaryStage.setScene(trailerScene));

            confirm2.setOnAction(event3 -> {
                Reader.hallList.removeIf(h -> h.getHallName().equals(removeHalls.getValue().getHallName()));
                Reader.seatList.removeIf(s -> s.getHallName().equals(removeHalls.getValue().getHallName()));
                removeHalls.getItems().removeAll(removeHalls.getItems());
                halls.getItems().removeAll(halls.getItems());
                for (Hall h : Reader.hallList) {
                    if (h.getFilmName().equals(films.getValue().getName())) {
                        removeHalls.getItems().add(h);
                        halls.getItems().add(h);
                        removeHalls.setValue(h);
                    }
                }
            });
        });
    }
}
