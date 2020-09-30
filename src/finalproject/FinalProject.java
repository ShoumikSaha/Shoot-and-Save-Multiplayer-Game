/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.util.Timer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Shoumik
 */
public class FinalProject extends Application {

    public static Stage stage;
    public NetworkUtil nu;

    //for shooter
    public double glovesx;
    public double glovesy;

    //for keeper
    public double h;
    public double a;
    public double vb;
    public double s;

    public static boolean goal = false;
    Timer timer;

    Keeper keeper = new Keeper();
    Shooter shooter = new Shooter();

    boolean isGloveChange = false;
    boolean isBallChange = false;

    public void myTimerHandleforShooter() {
        if (isGloveChange) {
            shooter.GlovesUpdate(glovesx, glovesy);

            isGloveChange = false;
        }
    }

    public void myTimerHandleforKeeper() {
        if (isBallChange) {
            keeper.BallUpdate(h, a, vb, s);

            isBallChange = false;
        }
    }

    public void myBallInitialPosition() {
        if (shooter.BallInitial) {
            shooter.ballview.setX(210);
            shooter.ballview.setY(400);
            shooter.ballview.setFitHeight(40);
            shooter.ballview.setFitWidth(40);
            //shooter.design.getChildren().add(shooter.ballview);

            shooter.BallInitial = false;
            System.out.println("in ball initial");
        }
    }

    public void Login() {
        Group root = new Group();

        ImageView login = new ImageView("login1.jpg");
        login.setX(0);
        login.setY(0);
        login.setFitWidth(450);
        login.setFitHeight(500);

        ImageView logo = new ImageView("logo3.png");
        logo.setX(100);
        logo.setY(50);
        logo.setFitWidth(250);
        logo.setFitHeight(50);

        TextField tfield = new TextField();
        tfield.setLayoutX(120);
        tfield.setLayoutY(450);
        tfield.setStyle("-fx-control-inner-background:lightgreen");
        tfield.setPrefWidth(220);
        Text text = new Text(70, 670, "Enter you username.");
        text.setFont(Font.font("Verdana", FontPosture.REGULAR, 20));
        text.setStyle("-fx-fill:orange");
        tfield.toFront();

        ToggleGroup group = new ToggleGroup();
        RadioButton keep = new RadioButton("Keeper");
        keep.setStyle("-fx-text-fill:blue");

        RadioButton shoot = new RadioButton("Shooter");
        shoot.setStyle("-fx-text-fill:blue");

        keep.setToggleGroup(group);
        shoot.setToggleGroup(group);
        keep.toFront();
        shoot.toFront();

        keep.setLayoutX(350);
        keep.setLayoutY(430);
        keep.setSelected(true);

        shoot.setLayoutX(350);
        shoot.setLayoutY(470);

        root.getChildren().addAll(login, logo, tfield, text, keep, shoot);

        tfield.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String str = new String();
                if (keep.isSelected()) {
                    str = "k " + tfield.getText();
                } else if (shoot.isSelected()) {
                    str = "s " + tfield.getText();
                }

                Object o = (Object) str;
                nu.write(o);
                //nu.screen = 1;
                System.out.println(str);
                tfield.clear();
                //list.getItems().add(str);

            }

        });

        Scene scene = new Scene(root, 450, 500);
        stage.setScene(scene);
        stage.show();

    }

    public void GameScreenGoalKeeper() {
        Group root = new Group();
        //Keeper keeper = new Keeper();
        /*keeper.design.getChildren().remove(keeper.ballview);
        keeper.ballview.setX(210);
        keeper.ballview.setY(400);
        keeper.ballview.setFitWidth(40);
        keeper.ballview.setFitHeight(40);

        keeper.design.getChildren().add(keeper.ballview);*/

        root.getChildren().add(keeper.design);

        Scene scene = new Scene(root, 450, 500);

        keeper.GlovesReposition(scene, nu);

        new AnimationTimer() {

            @Override
            public void handle(long l) {
                myTimerHandleforKeeper();
            }
        }.start();

        stage.setScene(scene);
        stage.show();

    }

    public void GameScreenShooter() {
        Group root = new Group();

        root.getChildren().add(shooter.design);

        Scene scene = new Scene(root, 450, 500);

        shooter.BallReposition(scene, nu);
        //shooter.GlovesUpdate(scene, glovesx, glovesy);

        stage.setScene(scene);
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                myTimerHandleforShooter();
                //myBallInitialPosition();
            }
        }.start();

    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        nu = new NetworkUtil("127.0.0.1", 12345);
        new ClientReaderThread(this);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
