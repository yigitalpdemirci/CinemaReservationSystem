import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SignUpWindow {
    public static void signUpProcess(Stage primaryStage, MediaPlayer errorPlayer, TextField username, PasswordField password, Button toSignUp, Scene loginScene, GridPane pane, Label emptyUsername, Label emptyPassword, Label noSuchACredential, Label userBlockedLabel, Label waitBlockTime) {
        toSignUp.setOnAction(e -> {
            CheckError.clear(pane, username, password,new PasswordField(), emptyUsername, emptyPassword, noSuchACredential, userBlockedLabel, waitBlockTime);
            GridPane registerPane = new GridPane();
            registerPane.setVgap(10);
            registerPane.setHgap(10);
            TextField newUsername = new TextField();
            newUsername.setPrefWidth(150);
            PasswordField newPassword = new PasswordField();
            newPassword.setPrefWidth(150);
            PasswordField newCheckPassword = new PasswordField();
            newCheckPassword.setPrefWidth(150);
            Button toLogin = new Button("LOG IN");
            Button signUp = new Button("SIGN UP");
            Label welcomeMessage2 = new Label("Welcome to HUCS Cinema Reservation System! \nPlease enter your credentials below and click LOGIN.\nYou can create a new account by clicking SIGN UP button.");
            Label passwordMatchProblem = new Label("ERROR: Passwords do not match.");
            Label existsUser = new Label("ERROR: User already exists.");
            Label successfulRegister = new Label("SUCCESS: User registered successfully.");

            registerPane.add(welcomeMessage2, 0, 0, 2, 1);
            registerPane.add(new Label("Username :"), 0, 1);
            registerPane.add(newUsername, 1, 1);
            registerPane.add(new Label("Password: "), 0, 2);
            registerPane.add(newPassword, 1, 2);
            registerPane.add(new Label("Password: "), 0, 3);
            registerPane.add(newCheckPassword, 1, 3);
            registerPane.add(toLogin, 0, 4);
            welcomeMessage2.setTextAlignment(TextAlignment.CENTER);

            signUp.setOnAction(event -> {
                ArrayList<String> userNamesList = new ArrayList<>();
                for (User u: Reader.userList) {
                    userNamesList.add(u.getName());
                }
                if (newUsername.getText().length() == 0) {
                    CheckError.signUpCheck(errorPlayer, registerPane, emptyUsername, emptyPassword, passwordMatchProblem, existsUser, successfulRegister,true, newUsername, newPassword);
                } else if ((newPassword.getText().length() == 0 || newCheckPassword.getText().length() == 0)) {
                    CheckError.signUpCheck(errorPlayer, registerPane, emptyPassword, emptyUsername, passwordMatchProblem, existsUser, successfulRegister,true, newUsername, newPassword);
                } else if (userNamesList.contains(newUsername.getText())) {
                    CheckError.signUpCheck(errorPlayer, registerPane, existsUser, emptyUsername, emptyPassword, passwordMatchProblem, successfulRegister,true, newUsername, newPassword);
                } else if (!newPassword.getText().equals(newCheckPassword.getText())) {
                    CheckError.signUpCheck(errorPlayer, registerPane, passwordMatchProblem, emptyUsername, emptyPassword, existsUser, successfulRegister,true, newUsername, newPassword);
                } else {
                    CheckError.signUpCheck(errorPlayer, registerPane, successfulRegister,emptyUsername,emptyPassword,passwordMatchProblem,existsUser,false,newUsername,newPassword);
                }
                errorPlayer.setOnEndOfMedia(errorPlayer::stop);
            });

            toLogin.setOnAction(event -> {
                CheckError.clear(registerPane,newUsername,newPassword,newCheckPassword,emptyUsername,emptyPassword,passwordMatchProblem,existsUser,successfulRegister);
                primaryStage.setScene(loginScene);
            });

            registerPane.add(signUp, 1, 4);
            registerPane.setAlignment(Pos.CENTER);
            GridPane.setHalignment(signUp, HPos.RIGHT);
            GridPane.setHalignment(welcomeMessage2, HPos.CENTER);
            Scene registerScene = new Scene(registerPane, 800, 300);
            primaryStage.setScene(registerScene);
        });
    }
}
