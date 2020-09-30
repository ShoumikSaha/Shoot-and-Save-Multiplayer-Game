/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import static javafx.util.Duration.seconds;

/**
 *
 * @author Shoumik
 */
public class Keeper extends MainDesign {

    double glovex;
    double glovey;
    double x;
    double y;
    String str = new String();
    Timer timer;

    Keeper() {
        super();

    }

    public void GlovesReposition(Scene scene, NetworkUtil nu) {
        glovesview.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("Gloves moved");

                glovex = event.getX();
                glovey = event.getY();
                if ((glovex <= 355 && glovex >= 80) && (glovey <= 150 && glovey >= 50)) {
                    glovesview.setX(glovex);
                    glovesview.setY(glovey);
                }

                //String str=new String(glovesview.getX()+" "+glovesview.getY());
                str = glovesview.getX() + " " + glovesview.getY();
                nu.write((Object) str);
                System.out.println(str);
                //design.getChildren().add(glovesview);
                event.consume();

            }
        });
    }

    public void BallUpdate(double h, double a, double v, double side) {
        h = (h * 3.1416) / 180;
        a = (a * 3.1416) / 180;
        double t = 12.5 / (v * cos(h));

        y = (v * sin(h) * t) - (.5 * 9.8 * (t * t));
        if (y > 0) {
            y = 147 - (y * 38.93);
        } else {
            y = 147;
        }

        x = 0;
        if (a > 0) {
            x = 25 * tan(a);
        } else if (a < 0) {
            x = 25 * tan(1 * a);
        }
        x = 230 + (x / 3.73) * 142;

        double z = 25;

        double swing = 29.5 - v;
        swing = swing * 15;

        System.out.println(swing);
        System.out.println(x + " " + y);

        ScaleTransition scaletrans = new ScaleTransition(
                Duration.seconds(t), ballview);

        scaletrans.setToX(.5);
        scaletrans.setToY(.5);

        scaletrans.play();

        Path path = new Path();

        MoveTo moveTo = new MoveTo();
        moveTo.setX(230);
        moveTo.setY(420);

        QuadCurveTo quadCurveTo = new QuadCurveTo();
        quadCurveTo.setX(x);
        quadCurveTo.setY(y);
        quadCurveTo.setControlX(((x - 273) / 2) + 273 + (swing * side));
        quadCurveTo.setControlY(((420 - y) / 2) + y);

        path.getElements().add(moveTo);
        path.getElements().add(quadCurveTo);

        PathTransition pathTransition = new PathTransition(seconds(t), path, ballview);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();

        //implementing translate transition
        TranslateTransition tt2 = new TranslateTransition(Duration.seconds(2), ballview);

        tt2.setToX(0);
        tt2.setToY(0);

        //implementing another scale transition
        ScaleTransition sc2 = new ScaleTransition(
                Duration.seconds(2), ballview);

        sc2.setToX(1);
        sc2.setToY(1);

        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //pathTransition.stop();

                tt2.play();
                sc2.play();

                if (x < (glovesview.getX() + 40) && x > glovesview.getX() && y > glovesview.getY() && y < (glovesview.getY() + 40)) {
                    FinalProject.goal = false;
                    System.out.println("not goal");

                    saveview.setVisible(true);
                    timer = new Timer();
                    TimerTask task = new TimerTask() {
                        public void run() {

                            saveview.setVisible(false);
                            timer.cancel();
                        }

                    };

                    timer.schedule(task, 2000);

                } else {
                    if (x < 376 && x > 84 && y > 52 && y < 150) {
                        FinalProject.goal = true;
                        System.out.println("goal");
                        goalview.setVisible(true);
                        timer = new Timer();
                        TimerTask task = new TimerTask() {
                            public void run() {

                                goalview.setVisible(false);
                                timer.cancel();
                            }

                        };

                        timer.schedule(task, 2000);
                    } else {
                        FinalProject.goal = false;
                        System.out.println("not goal");

                        missview.setVisible(true);
                        timer = new Timer();
                        TimerTask task = new TimerTask() {
                            public void run() {

                                missview.setVisible(false);
                                timer.cancel();
                            }

                        };

                        timer.schedule(task, 2000);
                    }
                    //scene.setRoot(design);
                    System.out.println("Keeper Finished!");
                }
            }
        });
    }
}
