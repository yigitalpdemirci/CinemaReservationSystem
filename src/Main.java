import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    static int[] count = {0};
    boolean blocked = false;

    GridPane pane = new GridPane();
    Label userBlockedLabel = new Label("ERROR: You have been blocked for " + Reader.blockTime + " seconds.");
    Label emptyUsername = new Label("ERROR: Empty User Name!!!");
    Label emptyPassword = new Label("ERROR: Empty password!");
    Label noSuchACredential = new Label("ERROR: No Such A Credential.");
    Label waitBlockTime = new Label("ERROR!: Please wait for " + Reader.blockTime + " seconds.");

    void checkErrorCount() {
        if (count[0] >= Reader.maximumErrorWithoutGettingBlocked) {
            blocked = true;
            if (!pane.getChildren().contains(userBlockedLabel)) {
                pane.getChildren().remove(noSuchACredential);
                pane.getChildren().remove(waitBlockTime);
                pane.add(userBlockedLabel, 0, 4, 2, 1);
                GridPane.setHalignment(userBlockedLabel,HPos.CENTER);
            }
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    blocked = false;
                }
            };
            timer.schedule(task, Reader.maximumErrorWithoutGettingBlocked * 1000L);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileInputStream icon = new FileInputStream("assets/icons/logo.png");
        Media errorSound = new Media(new File("assets/effects/error.mp3").toURI().toString());
        FileInputStream inputImage1 = new FileInputStream("assets/icons/empty_seat.png");
        FileInputStream inputImage2 = new FileInputStream("assets/icons/reserved_seat.png");
        Image emptySeat = new Image(inputImage1, 40, 40, false, false);
        Image reservedSeat = new Image(inputImage2, 40, 40, false, false);
        MediaPlayer errorPlayer = new MediaPlayer(errorSound);

        primaryStage.setTitle(Reader.title);
        primaryStage.getIcons().add(new Image(icon));

        pane.setHgap(10);
        pane.setVgap(10);
        TextField username = new TextField();
        username.setPrefWidth(150);
        PasswordField password = new PasswordField();
        password.setPrefWidth(150);
        Button toSignUp = new Button("SIGN UP");
        Button login = new Button("LOG IN");
        pane.setAlignment(Pos.CENTER);
        Label startMessage = new Label("Welcome to HUCS Cinema Reservation System! \nPlease enter your credentials below and click LOGIN.\nYou can create a new account by clicking SIGN UP button.");
        pane.add(startMessage, 0, 0, 2, 1);
        pane.add(new Label("Username: "), 0, 1);
        pane.add(username, 1, 1);
        pane.add(new Label("Password: "), 0, 2);
        pane.add(password, 1, 2);
        pane.add(toSignUp, 0, 3);
        pane.add(login, 1, 3);
        GridPane.setHalignment(login, HPos.RIGHT);
        startMessage.setTextAlignment(TextAlignment.CENTER);
        GridPane.setHalignment(startMessage, HPos.CENTER);

        Scene loginScene = new Scene(pane, 500, 200);

        login.setOnAction(event -> {
            if (!blocked) {
                if (username.getText().length() == 0) {
                    pane.getChildren().remove(userBlockedLabel);
                    pane.getChildren().remove(waitBlockTime);
                    CheckError.loginCheck(errorPlayer, pane, emptyUsername, emptyPassword, noSuchACredential);
                } else if (password.getText().length() == 0) {
                    pane.getChildren().remove(userBlockedLabel);
                    pane.getChildren().remove(waitBlockTime);
                    CheckError.loginCheck(errorPlayer, pane, emptyPassword, emptyUsername, noSuchACredential);
                } else {
                    pane.getChildren().remove(emptyUsername);
                    pane.getChildren().remove(emptyPassword);
                    int userNumber = Reader.userList.size();
                    int iteration = 0;
                    for (User u : Reader.userList) {
                        if (u.getName().equals(username.getText()) && Objects.requireNonNull(u.getPassword()).equals(Reader.hashPassword(password.getText()))) {
                            count[0] = 0;
                            pane.getChildren().remove(noSuchACredential);
                            GridPane welcomePane = new GridPane();
                            welcomePane.setHgap(10);
                            welcomePane.setVgap(10);
                            Button confirm = new Button("OK");
                            Button logOut = new Button("LOG OUT");
                            Button addFilm = new Button("Add Film");
                            Button removeFilm = new Button("Remove Film");
                            Button editUser = new Button("Edit User");
                            Label welcomeMessage = new Label("Welcome, " + username.getText());
                            ComboBox<Film> films = new ComboBox<>();
                            films.setMinWidth(200);
                            for (Film f : Reader.filmList) {
                                films.getItems().add(f);
                                films.setValue(f);
                            }

                            films.setPlaceholder(new Label(" WARNING: There are no films."));

                            welcomePane.setAlignment(Pos.CENTER);
                            HBox hbox = new HBox(10, logOut);

                            if (u.getIsAdmin()) {
                                if (u.getIsClubMember()) {
                                    welcomeMessage.setText(welcomeMessage.getText() + " (Admin - Club Member)! " + "\n" + "You can either select film below or do edits.");
                                } else {
                                    welcomeMessage.setText(welcomeMessage.getText() + " (Admin)! " + "\n" + "You can either select film below or do edits.");
                                }
                                hbox.getChildren().addAll(addFilm,removeFilm,editUser);
                            } else if (u.getIsClubMember()) {
                                welcomeMessage.setText(welcomeMessage.getText() + " (Club Member)! " + "\n" + "Select a film then click to OK to continue.");
                            } else {
                                welcomeMessage.setText(welcomeMessage.getText() + "!" + "\n" + "Select a film then click to OK to continue.");
                            }
                            welcomeMessage.setTextAlignment(TextAlignment.CENTER);
                            welcomePane.add(welcomeMessage, 0, 0,2,1);
                            welcomePane.add(films, 0, 1);
                            welcomePane.add(confirm, 1, 1);
                            welcomePane.add(hbox, 0, 2,2,1);
                            welcomePane.setPrefSize(300, 300);

                            GridPane.setHalignment(welcomeMessage, HPos.CENTER);
                            GridPane.setHalignment(films, HPos.CENTER);
                            GridPane.setHalignment(confirm, HPos.CENTER);
                            GridPane.setHalignment(hbox, HPos.CENTER);
                            Scene welcomeScene = new Scene(welcomePane, 500, 300);
                            primaryStage.setScene(welcomeScene);

                            AddFilmWindow.addFilmProcess(primaryStage, errorPlayer, addFilm, films, welcomeScene);

                            RemoveFilmWindow.removeFilmProcess(primaryStage, removeFilm, films, welcomeScene);

                            EditUserWindow.editUserProcess(primaryStage, u, editUser, welcomeScene);

                            logOut.setOnAction(event2 -> {
                                CheckError.clear(pane,username,password,new PasswordField(),emptyUsername,emptyPassword,noSuchACredential,userBlockedLabel,waitBlockTime);
                                primaryStage.setScene(loginScene);
                            });

                            TrailerWindow.trailerWindowProcess(primaryStage, emptySeat, reservedSeat, errorPlayer, u, confirm, films, welcomeScene);
                            break;
                        } else {
                            iteration++;
                            if (!pane.getChildren().contains(noSuchACredential)) {
                                pane.add(noSuchACredential, 0, 4, 2, 1);
                                GridPane.setHalignment(noSuchACredential, HPos.CENTER);
                            }
                            if ((iteration) == userNumber) {
                                errorPlayer.play();
                                count[0]++;
                                checkErrorCount();
                            }
                        }
                    }
                }
            } else {
                errorPlayer.play();
                if (!pane.getChildren().contains(waitBlockTime)) {
                    pane.getChildren().remove(userBlockedLabel);
                    pane.add(waitBlockTime,0,4,2,1);
                    GridPane.setHalignment(waitBlockTime, HPos.CENTER);
                }
            }
            errorPlayer.setOnEndOfMedia(errorPlayer::stop);
        });

        SignUpWindow.signUpProcess(primaryStage, errorPlayer, username, password, toSignUp, loginScene, pane, emptyUsername, emptyPassword, noSuchACredential, userBlockedLabel, waitBlockTime);

        primaryStage.setScene(loginScene);
        primaryStage.show();

    }

    @Override
    public void stop() throws IOException {
        FileWriter fileW = new FileWriter("assets/data/backup.dat");
        BufferedWriter output = new BufferedWriter(fileW);
        for (User u: Reader.userList) {
            output.write("user" + "\t" + u.getName() + "\t" + u.getPassword() + "\t" + u.getIsClubMember() + "\t" + u.getIsAdmin() + "\n");
        }
        for (Film f: Reader.filmList) {
            output.write("film" + "\t" + f.getName() + "\t" + f.getTrailerPath() + "\t" + f.getDuration() + "\n");
        }
        for (Hall h: Reader.hallList) {
            output.write("hall" + "\t" + h.getFilmName() + "\t" + h.getHallName() + "\t" + h.getPrice() + "\t" + h.getRow() + "\t" + h.getColumn() + "\n");
        }
        for (Seat s: Reader.seatList) {
            output.write("seat" + "\t" + s.getFilmName() + "\t" + s.getHallName() + "\t" + s.getRow() + "\t" + s.getColumn() + "\t" + s.getOwnerName() + "\t" + s.getBoughtPrice() + "\n");
        }
        output.flush();
        output.close();
    }

    public static void main(String[] args) {
        Reader.backupReader();
        Reader.propertiesReader();
        launch(args);
    }
}