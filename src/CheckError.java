import javafx.geometry.HPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;

public class CheckError {
    public static void signUpCheck(MediaPlayer errorPlayer, GridPane pane, Label checkCondition, Label other1, Label other2, Label other3, Label other4, boolean error, TextField newUsername, PasswordField newPassword) {
        if (error) {
            errorPlayer.play();
        }

        if (!pane.getChildren().contains(checkCondition)) {
            pane.getChildren().remove(other1);
            pane.getChildren().remove(other2);
            pane.getChildren().remove(other3);
            pane.getChildren().remove(other4);
            pane.add(checkCondition, 0, 5, 2, 1);
            GridPane.setHalignment(checkCondition, HPos.CENTER);
            if (!error) {
                Reader.userList.add(new User(newUsername.getText(), Reader.hashPassword(newPassword.getText()), false,false));
            }
        }
    }

    public static void loginCheck(MediaPlayer errorPlayer, GridPane pane, Label checkCondition, Label other1, Label other2) {
        errorPlayer.play();
        if (!pane.getChildren().contains(checkCondition)) {
            pane.getChildren().remove(other1);
            pane.getChildren().remove(other2);
            pane.add(checkCondition, 0, 4, 2, 1);
            GridPane.setHalignment(checkCondition, HPos.CENTER);
        }
    }

    public static void checkAddFilm(MediaPlayer errorPlayer, GridPane addFilmPane, ArrayList<String> filmNameList, TextField addFilmName, TextField trailerPath, TextField duration, Label checkCondition, Label other1, Label other2, Label other4, Label other5, Label other3, Label other6, boolean listClear, boolean error) {
        if (error) {
            errorPlayer.play();
        }
        if (!addFilmPane.getChildren().contains(checkCondition)) {
            addFilmPane.getChildren().remove(other2);
            addFilmPane.getChildren().remove(other3);
            addFilmPane.getChildren().remove(other5);
            addFilmPane.getChildren().remove(other1);
            addFilmPane.getChildren().remove(other6);
            addFilmPane.getChildren().remove(other4);
            addFilmPane.add(checkCondition, 0, 5, 2, 1);
            GridPane.setHalignment(checkCondition, HPos.CENTER);
            if (listClear) {
                filmNameList.clear();
            }
            if (!error) {
                Reader.filmList.add(new Film(addFilmName.getText(), trailerPath.getText(), Integer.parseInt(duration.getText())));
            }
        }
    }

    public static void checkAddHall(MediaPlayer errorPlayer, GridPane addHallPane, ComboBox<Film> films, TextField addHallName, TextField price, ComboBox<Integer> rows, ComboBox<Integer> columns, Label checkCondition, Label other1, Label other2, Label other3, Label other4, boolean error) {
        if (error) {
            errorPlayer.play();

        }
        if (!addHallPane.getChildren().contains(checkCondition)) {
            addHallPane.getChildren().remove(other1);
            addHallPane.getChildren().remove(other2);
            addHallPane.getChildren().remove(other3);
            addHallPane.getChildren().remove(other4);
            addHallPane.add(checkCondition, 0, 6,2,1);
            GridPane.setHalignment(checkCondition,HPos.CENTER);
        }
        if (!error) {
            Reader.hallList.add(new Hall(films.getValue().getName(), addHallName.getText(), Integer.parseInt(price.getText()), rows.getValue(), columns.getValue()));
            for (int i = 0; i < rows.getValue(); i++) {
                for (int j = 0; j < columns.getValue(); j++) {
                    Reader.seatList.add(new Seat(films.getValue().getName(), addHallName.getText(), i, j, "null", 0));
                }
            }
        }
    }

    public static void clear(GridPane pane, TextField tf1, PasswordField tf2, PasswordField tf3, Label l1, Label l2, Label l3, Label l4, Label l5) {
        tf1.clear();
        tf2.clear();
        tf3.clear();
        pane.getChildren().remove(l1);
        pane.getChildren().remove(l2);
        pane.getChildren().remove(l3);
        pane.getChildren().remove(l4);
        pane.getChildren().remove(l5);
    }
}
