/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author Shoumik
 */
public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket sock = new ServerSocket(12345);
        System.out.println("Inside Server");
        HashMap<String, NetworkUtil> ClientTable = new HashMap<>();

        String[] ClientName = new String[20];

        //while(true){
        Socket shooter = sock.accept();
        Socket keeper = sock.accept();

        Thread shoot = new Thread(new ServerThread(shooter, ClientTable, ClientName));
        Thread keep = new Thread(new ServerThread(keeper, ClientTable, ClientName));

        shoot.start();
        keep.start();
        shoot.join();
        keep.join();

    }
}
