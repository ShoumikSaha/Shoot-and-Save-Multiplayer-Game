/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Shoumik
 */
public class MainDesign {
    public Group design;
    public ImageView ballview;
    public ImageView glovesview;
    public ImageView goalview;
    public ImageView saveview;
    public ImageView missview;
    
    MainDesign()
    {
        design = new Group();
        
        //crowd
        Image crowd=new Image("crowd2.gif");
        ImageView crowdview= new ImageView(crowd);
        
        crowdview.setX(0);
        crowdview.setY(0);
        crowdview.setFitHeight(110);
        crowdview.setFitWidth(450);
        crowdview.setOpacity(25);
        
        //billboard
        Image boar=new Image("billboard1.png");
        ImageView board= new ImageView(boar);
        
        board.setX(0);
        board.setY(110);
        board.setFitHeight(20);
        board.setFitWidth(450);
        board.setOpacity(25);
        
        //field
        ImageView fieldview=new ImageView("field.jpg");
        
        fieldview.setX(0);
        fieldview.setY(130);
        fieldview.setFitWidth(450);
        fieldview.setFitHeight(370);
        fieldview.setOpacity(25);
        
        //net
        ImageView netview=new ImageView("net.png");
        
        netview.setX(80);
        netview.setY(50);
        netview.setFitWidth(300);
        netview.setFitHeight(100);
        netview.toFront();
        
        //imgview.setPreserveRatio(true);
        netview.setSmooth(true);
        netview.setCache(true);
        
        ImageView lineview=new ImageView("barline.png");
        
        lineview.setX(80);
        lineview.setY(150);
        lineview.setFitWidth(300);
        lineview.setFitHeight(1);
        lineview.toFront();
        
        //ball
        ballview=new ImageView("ball.png");
        
        ballview.setX(210);
        ballview.setY(400);
        ballview.setFitWidth(40);
        ballview.setFitHeight(40);
        
        ballview.setPreserveRatio(true);
        ballview.setSmooth(true);
        ballview.setCache(true);
        
        //gloves
        glovesview=new ImageView("gloves7.png");
        
        glovesview.setX(210);
        glovesview.setY(75);
        glovesview.setFitWidth(40);
        glovesview.setFitHeight(40);
        
        glovesview.setPreserveRatio(true);
        glovesview.setSmooth(true);
        glovesview.setCache(true);
        
        //goal
        
        
        goalview=new ImageView("goalnew.png");
        
        goalview.setX(80);
        goalview.setY(50);
        goalview.setFitWidth(300);
        goalview.setFitHeight(100);
        goalview.toFront();
        goalview.setVisible(false);
        
        
        //save
        saveview=new ImageView("savenew.png");
        
        saveview.setX(80);
        saveview.setY(50);
        saveview.setFitWidth(300);
        saveview.setFitHeight(100);
        saveview.toFront();
        saveview.setVisible(false);
        
        //miss
        missview=new ImageView("missnew.png");
        
        missview.setX(80);
        missview.setY(50);
        missview.setFitWidth(300);
        missview.setFitHeight(100);
        missview.toFront();
        missview.setVisible(false);
        
        //implementing design group
        design.getChildren().add(crowdview);
        design.getChildren().add(board);
        design.getChildren().add(fieldview);
        design.getChildren().add(netview);
        design.getChildren().add(lineview);
        design.getChildren().add(glovesview);
        design.getChildren().add(ballview);
        design.getChildren().add(goalview);
        design.getChildren().add(saveview);
        design.getChildren().add(missview);
        
    }
    
    
}


