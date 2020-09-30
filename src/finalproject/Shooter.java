/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;
import static javafx.util.Duration.seconds;

/**
 *
 * @author Shoumik
 */
public class Shooter extends MainDesign {

    Slider vslide;
    Slider hslide;
    ImageView shootview;
    ImageView pointer;
    double height;
    double angle;
    double v;
    double side;
    TranslateTransition tp;
    PathTransition pathTransition;
    PathTransition pt2;
    TranslateTransition translateTransition;
    Keeper keeper;
    RadioButton lb;
    RadioButton rb;
    Timer timer;
    Timer timer2;
    String str = new String();

    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    Date dateobj1;
    Date dateobj2;
    double t1;
    double t2;
    double t3;
    double x;
    double y;
    boolean BallInitial = false;

    Shooter() {
        super();

        //vslide
        vslide = new Slider(0, 35, 0);
        vslide.setOrientation(Orientation.VERTICAL);
        vslide.setLayoutX(420);
        vslide.setLayoutY(300);

        //hslide
        hslide = new Slider(-14, 14, 0);
        hslide.setOrientation(Orientation.HORIZONTAL);
        hslide.setLayoutX(290);
        hslide.setLayoutY(450);

        //adding listener to vslide & hslide
        vslide.valueProperty().addListener((observable, oldValue, newValue)
                -> {
            height = newValue.doubleValue();
            System.out.println(height);
        });

        hslide.valueProperty().addListener((observable, oldValue, newValue)
                -> {
            angle = newValue.doubleValue();
            System.out.println(angle);
        });

        //shoot button
        shootview = new ImageView("shoot.png");

        shootview.setX(350);
        shootview.setY(350);
        shootview.setFitHeight(50);
        shootview.setFitWidth(50);

        //power slider
        ImageView shootslider = new ImageView("shootingslidernew.png");

        shootslider.setX(20);
        shootslider.setY(450);
        shootslider.setFitHeight(20);
        shootslider.setFitWidth(150);

        //pointer in power slider
        pointer = new ImageView("pointernew.png");

        pointer.setX(36);
        pointer.setY(458);
        pointer.setFitHeight(4);
        pointer.setFitWidth(2);
        pointer.setOpacity(20);

        //moving the pointer in power slider
        tp = new TranslateTransition(Duration.seconds(2), pointer);
        tp.setToX(118);

        tp.setAutoReverse(true);
        tp.setCycleCount(Timeline.INDEFINITE);

        tp.play();

        //radio button
        ToggleGroup group = new ToggleGroup();
        lb = new RadioButton("Left");
        rb = new RadioButton("Right");
        lb.setToggleGroup(group);
        rb.setToggleGroup(group);

        lb.setLayoutX(20);
        lb.setLayoutY(470);

        rb.setLayoutX(120);
        rb.setLayoutY(470);
        rb.setSelected(true);

        //impleenting design for shooter
        design.getChildren().add(vslide);
        design.getChildren().add(hslide);
        design.getChildren().add(shootview);
        design.getChildren().add(shootslider);
        design.getChildren().add(pointer);
        design.getChildren().add(lb);
        design.getChildren().add(rb);

    }

    public void BallReposition(Scene scene, NetworkUtil nu) {

        shootview.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                side = 1;

                if (lb.isSelected()) {
                    side = -1;
                }
                if (rb.isSelected()) {
                    side = 1;
                }
                System.out.println("Shoot pressed ");
                System.out.println(height);
                System.out.println(angle);
                //ballrep= new BallReposition(root, height, angle);
                tp.stop();
                v = pointer.getTranslateX();
                v = v - 59;
                v = (v > 0) ? v : (-1 * v);
                v = (59 - v) / 2;

                System.out.println(v);

                //keeper.update(height, angle, v);
                //ball.Reposition(height, angle, v);
                //String str=new String (height+" "+angle+" "+v+" "+side);
                str = height + " " + angle + " " + v + " " + side;

                System.out.println(str);
                nu.write((Object) str);
                BallPositionUpdate(height, angle, v, side, scene, nu);

                event.consume();
                if (event.isConsumed() == true) {

                    System.out.println("event consumed");

                }

            }
        });

    }

    public void BallPositionUpdate(double h, double a, double v, double side, Scene scene, NetworkUtil nu) {
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
        //scaletrans.setCycleCount(50);
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
        path.setManaged(true);

        pathTransition = new PathTransition(seconds(t), path, ballview);

        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        //pathTransition.setCycleCount(2);
        //pathTransition.setAutoReverse(true);

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

        System.out.println(pathTransition.getNode().toString());
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //pt2.play();

                tt2.play();
                sc2.play();
                /*pathTransition.pause();
                pathTransition.setCycleCount(1);
                pathTransition.setAutoReverse(true);
                ballview.setVisible(false);
                pathTransition.play();*/
                //pathTransition.stop();

                //design.getChildren().remove(ballview);
                /*ballview.setX(210);
                ballview.setY(400);
                ballview.setFitWidth(40);
                ballview.setFitHeight(40);
                ballview.setVisible(true);
                System.out.println(ballview.getX());
                System.out.println(ballview.getY());
                System.out.println(ballview.toString());*/
 /*translateTransition = new TranslateTransition(Duration.seconds(1), ballview);
                translateTransition.setFromY(y);
                translateTransition.setToY(400);
                translateTransition.play();*/
                BallInitial = true;

                System.out.println("Shooter Finished!");
                tp.setFromX(0);
                tp.playFromStart();

                timer2 = new Timer();
                TimerTask task2 = new TimerTask() {
                    @Override
                    public void run() {
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
                            System.out.println("Shooter Finished!");

                            /*timer2 = new Timer();
                    TimerTask task = new TimerTask() {
                        public void run() {
                            String strnew= new String("k");
                            nu.write((Object)strnew);
                            System.out.println(strnew);
                            timer2.cancel();
                        }

                    };

                    timer2.schedule(task, 5000);*/
                        }
                    }

                };
                timer2.schedule(task2, 100);

                /*if (x < (glovesview.getX() + 40) && x > glovesview.getX() && y > glovesview.getY() && y < (glovesview.getY() + 40)) {
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
                    System.out.println("Shooter Finished!");

                    timer2 = new Timer();
                    TimerTask task = new TimerTask() {
                        public void run() {
                            String strnew= new String("k");
                            nu.write((Object)strnew);
                            System.out.println(strnew);
                            timer2.cancel();
                        }

                    };

                    timer2.schedule(task, 5000);
                }*/
            }
        });
        ballview.setVisible(true);
        System.out.println(pathTransition.getDuration().toSeconds());

    }

    public void GlovesUpdate(double x, double y) {
        glovesview.setX(x);
        glovesview.setY(y);
    }
}
