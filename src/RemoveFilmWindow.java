import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RemoveFilmWindow {
    public static void removeFilmProcess(Stage primaryStage, Button removeFilm, ComboBox<Film> films, Scene welcomeScene) {
        removeFilm.setOnAction(event1 -> {
            GridPane removeFilmPane = new GridPane();
            removeFilmPane.setHgap(10);
            removeFilmPane.setVgap(10);
            Label removeFilmLabel = new Label("Select the film that you desire to remove and then click OK.");
            ComboBox<Film> removeFilms = new ComboBox<>();
            for (Film f : Reader.filmList) {
                removeFilms.getItems().add(f);
                removeFilms.setValue(f);
            }
            Button backToWelcome = new Button("< BACK");
            Button confirm1 = new Button("OK");

            removeFilms.setPlaceholder(new Label("WARNING: There are no films."));
            removeFilmPane.add(removeFilmLabel, 0, 0, 2, 1);
            removeFilmPane.add(removeFilms, 0, 1, 2, 1);
            removeFilmPane.add(backToWelcome, 0, 2);
            removeFilmPane.add(confirm1, 1, 2);
            removeFilmPane.setAlignment(Pos.CENTER);
            GridPane.setHalignment(removeFilmLabel, HPos.CENTER);
            GridPane.setHalignment(removeFilms, HPos.CENTER);
            GridPane.setHalignment(backToWelcome, HPos.RIGHT);
            removeFilmPane.getColumnConstraints().addAll(new ColumnConstraints(200), new ColumnConstraints(200));

            Scene removeFilmScene = new Scene(removeFilmPane, 400, 200);
            primaryStage.setScene(removeFilmScene);

            backToWelcome.setOnAction(event2 -> {
                primaryStage.setScene(welcomeScene);
                films.getItems().removeAll(films.getItems());
                for (Film f : Reader.filmList) {
                    films.getItems().add(f);
                    films.setValue(f);
                }
            });

            confirm1.setOnAction(event2 -> {
                Reader.filmList.removeIf(f -> f.getName().equals(removeFilms.getValue().getName()));
                Reader.hallList.removeIf(h -> h.getFilmName().equals(removeFilms.getValue().getName()));
                Reader.seatList.removeIf(s -> s.getFilmName().equals(removeFilms.getValue().getName()));
                removeFilms.getItems().removeAll(removeFilms.getItems());
                for (Film f : Reader.filmList) {
                    removeFilms.getItems().add(f);
                    removeFilms.setValue(f);
                }
            });
        });
    }
}
