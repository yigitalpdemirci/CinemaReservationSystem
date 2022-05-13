import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SeatSelectWindow {

    public static void seatSelectProcess(Stage primaryStage, Image emptySeat, Image reservedSeat, User u, ComboBox<Film> films, MediaPlayer trailerPlayer, ComboBox<Hall> halls, Button goToSeatSelect, Scene trailerScene) {
        goToSeatSelect.setOnAction(event2 -> {
            trailerPlayer.stop();
            GridPane seatPane = new GridPane();
            seatPane.setVgap(5);
            seatPane.setHgap(5);
            if (halls.getValue() != null) {
                Label seatSelectLabel = new Label(films.getValue() + "(" + films.getValue().getDuration() + "minutes)" + "Hall: " + halls.getValue().getHallName());
                int maxColumn = 0;
                int maxRow = 0;

                ComboBox<User> seatUsers = new ComboBox<>();
                for (User user : Reader.userList) {
                    seatUsers.getItems().add(user);
                    seatUsers.setValue(u);
                }

                GridPane buttonPane = new GridPane();
                buttonPane.setHgap(10);
                buttonPane.setVgap(10);

                Label[] purchaseMessage = new Label[1];
                purchaseMessage[0] = new Label("");

                for (Seat s : Reader.seatList) {
                    if (s.getHallName().equals(halls.getValue().getHallName())) {
                        Button seat;
                        if (!s.getOwnerName().equals("null")) {
                            seat = new Button("", new ImageView(reservedSeat));
                            if (!(u.getIsAdmin()) && !(u.getName().equals(s.getOwnerName()))) {
                                seat.setDisable(true);
                            }
                        } else {
                            seat = new Button("", new ImageView(emptySeat));
                        }

                        if (s.getColumn() >= maxColumn) {
                            maxColumn = s.getColumn();
                        }
                        if (s.getRow() >= maxRow) {
                            maxRow = s.getRow();
                        }

                        Label[] seatStatus = new Label[1];
                        seatStatus[0] = new Label(" ");
                        GridPane.setHalignment(seatStatus[0], HPos.CENTER);
                        seatPane.add(seatStatus[0], 0, 3);
                        seatPane.add(new Label(""), 0, 4);

                        seat.setOnAction(event3 -> {
                            seatPane.getChildren().remove(seatStatus[0]);
                            seatPane.getChildren().remove(purchaseMessage[0]);
                            if (u.getIsAdmin()) {
                                if (s.getOwnerName().equals("null")) {
                                    seat.setGraphic(new ImageView(reservedSeat));
                                    s.setOwnerName(seatUsers.getValue().getName());
                                    if (seatUsers.getValue().getIsClubMember()) {
                                        s.setBoughtPrice((int) Math.round(halls.getValue().getPrice() - ((halls.getValue().getPrice() * Reader.discountPercentage) / 100.0)));
                                    } else {
                                        s.setBoughtPrice(halls.getValue().getPrice());
                                    }
                                    seatStatus[0] = new Label("Bought by " + s.getOwnerName() + " for " + s.getBoughtPrice());
                                    purchaseMessage[0] = new Label("Seat at " + (s.getRow() + 1) + "-" + (s.getColumn() + 1) + " is bought for " + s.getBoughtPrice() + " TL successfully!");
                                } else {
                                    seat.setGraphic(new ImageView(emptySeat));
                                    seatStatus[0] = new Label("Refunded to " + s.getOwnerName() + " for " + s.getBoughtPrice());
                                    purchaseMessage[0] = new Label("Seat at " + (s.getRow() + 1) + "-" + (s.getColumn() + 1) + " is refunded successfully!");
                                    s.setOwnerName("null");
                                    s.setBoughtPrice(0);
                                }
                            } else {
                                if (u.getName().equals(s.getOwnerName())) {
                                    seat.setGraphic(new ImageView(emptySeat));
                                    purchaseMessage[0] = new Label("Seat at " + (s.getRow() + 1) + "-" + (s.getColumn() + 1) + " is refunded successfully!");
                                    s.setOwnerName("null");
                                    s.setBoughtPrice(0);
                                } else {
                                    seat.setGraphic(new ImageView(reservedSeat));
                                    s.setOwnerName(u.getName());
                                    if (u.getIsClubMember()) {
                                        s.setBoughtPrice((int) Math.round(halls.getValue().getPrice() - ((halls.getValue().getPrice() * Reader.discountPercentage) / 100.0)));
                                    } else {
                                        s.setBoughtPrice(halls.getValue().getPrice());
                                    }
                                    purchaseMessage[0] = new Label("Seat at " + (s.getRow() + 1) + "-" + (s.getColumn() + 1) + " is bought for " + s.getBoughtPrice() + " TL successfully!");
                                }
                            }
                            seatPane.add(seatStatus[0], 0, 3);
                            seatPane.add(purchaseMessage[0], 0, 4);
                            GridPane.setHalignment(seatStatus[0], HPos.CENTER);
                            GridPane.setHalignment(purchaseMessage[0], HPos.CENTER);
                        });

                        InnerShadow shadow = new InnerShadow();
                        seat.addEventHandler(MouseEvent.MOUSE_ENTERED,
                                e -> {
                                    seatPane.getChildren().remove(seatStatus[0]);
                                    seat.setEffect(shadow);
                                    if (u.getIsAdmin()) {
                                        if (s.getOwnerName().equals("null")) {
                                            seatStatus[0] = new Label("Not bought yet.");
                                        } else {
                                            seatStatus[0] = new Label("Bought by " + s.getOwnerName() + " for " + s.getBoughtPrice() + "TL.");
                                        }
                                        seatPane.add(seatStatus[0], 0, 3);
                                        GridPane.setHalignment(seatStatus[0], HPos.CENTER);
                                    }
                                });
                        seat.addEventHandler(MouseEvent.MOUSE_EXITED,
                                e -> {
                                    seat.setEffect(null);
                                    seatPane.getChildren().remove(seatStatus[0]);
                                });
                        buttonPane.add(seat, s.getColumn(), s.getRow());
                    }
                }

                RowConstraints row1 = new RowConstraints();
                row1.setPercentHeight(2);
                RowConstraints row2 = new RowConstraints();
                RowConstraints row3 = new RowConstraints();
                RowConstraints row4 = new RowConstraints();
                RowConstraints row5 = new RowConstraints();
                row3.setPercentHeight(2);
                row4.setPercentHeight(2);
                row5.setPercentHeight(2);
                seatPane.getRowConstraints().addAll(row1, row2, row3, row4, row5);

                seatPane.add(buttonPane, 0, 1);
                buttonPane.setAlignment(Pos.CENTER);
                Button backToTrailer = new Button("< BACK");
                backToTrailer.setOnAction(event3 -> primaryStage.setScene(trailerScene));
                seatPane.add(seatSelectLabel, 0, 0);
                GridPane.setHalignment(seatSelectLabel, HPos.CENTER);
                if (u.getIsAdmin()) {
                    seatPane.add(seatUsers, 0, 2);
                    GridPane.setHalignment(seatUsers, HPos.CENTER);
                    row3.setPercentHeight(4);
                }
                seatPane.add(backToTrailer, 0, 5);
                seatPane.setAlignment(Pos.CENTER);
                Scene seatScene = new Scene(seatPane, 800, 800);
                primaryStage.setScene(seatScene);
            }
        });
    }
}
