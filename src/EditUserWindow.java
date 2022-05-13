import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditUserWindow {
    public static void editUserProcess(Stage primaryStage, User u, Button editUser, Scene welcomeScene) {
        editUser.setOnAction(event1 -> {
            GridPane editUserPane = new GridPane();
            TableView<User> editUserTable = new TableView<>();
            TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<User, Boolean> isClubMemberColumn = new TableColumn<>("Club Member");
            isClubMemberColumn.setCellValueFactory(new PropertyValueFactory<>("isClubMember"));
            TableColumn<User, Boolean> isAdminColumn = new TableColumn<>("Admin");
            isAdminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
            Button backToWelcome = new Button("< BACK");
            Button clubMemberPermission = new Button("Promote/Demote Club Member");
            Button adminPermission = new Button("Promote/Demote Admin.");

            for (User user : Reader.userList) {
                if (!user.getName().equals(u.getName())) {
                    editUserTable.getItems().add(user);
                }
            }

            editUserTable.getColumns().add(usernameColumn);
            editUserTable.getColumns().add(isClubMemberColumn);
            editUserTable.getColumns().add(isAdminColumn);
            editUserTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            editUserTable.setPlaceholder(new Label("No user in database!!!"));
            editUserTable.getSelectionModel().selectFirst();

            editUserPane.add(editUserTable, 0, 0, 3, 1);
            editUserPane.add(backToWelcome, 0, 1);
            editUserPane.add(clubMemberPermission, 1, 1);
            editUserPane.add(adminPermission, 2, 1);
            editUserPane.setHgap(10);
            editUserPane.setVgap(10);
            editUserPane.setAlignment(Pos.CENTER);


            GridPane.setHalignment(editUserPane, HPos.CENTER);
            Scene editUserScene = new Scene(editUserPane, 500, 500);
            primaryStage.setScene(editUserScene);

            backToWelcome.setOnAction(event2 -> primaryStage.setScene(welcomeScene));
            clubMemberPermission.setOnAction(event2 -> {
                editUserTable.getSelectionModel().getSelectedItem().setIsClubMember(!editUserTable.getSelectionModel().getSelectedItem().getIsClubMember());
                editUserTable.refresh();
            });

            adminPermission.setOnAction(event2 -> {
                editUserTable.getSelectionModel().getSelectedItem().setIsAdmin(!editUserTable.getSelectionModel().getSelectedItem().getIsAdmin());
                editUserTable.refresh();
            });
        });
    }
}
