/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.util.StringTokenizer;
import javafx.application.Platform;

/**
 *
 * @author Shoumik
 */
public class ClientReaderThread implements Runnable {

    NetworkUtil nu;
    Thread thread;
    FinalProject myFinal;

    ClientReaderThread(FinalProject finalObj) {
        myFinal = finalObj;
        this.nu = finalObj.nu;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        String str = new String();

        double pos[] = new double[5];
        int i = 0;

        if (nu.screen == 0) {
            Platform.runLater(()
                    -> {
                myFinal.Login();
            });
        }

        while (true) {
            str = (String) nu.read();

            System.out.println(str);

            if (str.charAt(0) == 'k') {
                Platform.runLater(()
                        -> {
                    myFinal.GameScreenGoalKeeper();
                });
                nu.screen = 1;

            } else if (str.charAt(0) == 's') {

                Platform.runLater(()
                        -> {
                    myFinal.GameScreenShooter();
                });
                nu.screen = 1;

            } else {
                i = 0;
                StringTokenizer tokens = new StringTokenizer(str, " ");

                while (tokens.hasMoreTokens()) {

                    pos[i] = Double.parseDouble(tokens.nextToken());

                    System.out.println(pos[i]);
                    i++;
                }

                if (i == 2) {
                    myFinal.isGloveChange = true;
                    myFinal.glovesx = pos[0];
                    myFinal.glovesy = pos[1];
                } else if (i == 4) {
                    myFinal.h = pos[0];
                    myFinal.a = pos[1];
                    myFinal.vb = pos[2];
                    myFinal.s = pos[3];
                    myFinal.isBallChange = true;
                }
            }
        }
    }
}
