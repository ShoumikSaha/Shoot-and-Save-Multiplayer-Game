/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author Shoumik
 */
public class ServerThread implements Runnable {
    HashMap<String, NetworkUtil> ClientTable;
    Socket sock;
    String[] ClientName;
    Thread thread;
    boolean isShooter=false;

    static int i = 0;
    
    ServerThread(Socket s, HashMap<String, NetworkUtil> table, String[] clientName) {
        sock = s;

        ClientTable = table;
        ClientName = clientName;
        
    }

    @Override
    public void run() {
        NetworkUtil nu = new NetworkUtil(sock);
        /*=(String)nu.read();
        System.out.println(ClientName[i]);
        if(ClientName[i].charAt(0)=='k'){
            Object o=(Object)"k connected";
            nu.write(o);
        }
        else if(ClientName[i].charAt(0)=='s'){
            Object o=(Object)"s connected";
            nu.write(o);
        }
        
        Object ob1=ClientTable.get(ClientName[0]).read();
        String string1=(String)ob1;
        if(string1.length()<4)
        {
            ClientTable.get(ClientName[1]).write(ob1);
        }*/
        
        String str= new String();
        
        while (true) {
            //NetworkUtil nu = new NetworkUtil(sock);
            str = (String) nu.read();

            if (str.charAt(0) == 'k') {
                isShooter=false;
                Object o = (Object) "k connected";
                String name = new String(str.substring(2, (str.length())));
                ClientName[0] = name;
                ClientTable.put(name, nu);
                nu.write(o);
                System.out.println("Connection Successful for " + name);
                
            } else if (str.charAt(0) == 's') {
                isShooter=true;
                Object o = (Object) "s connected";
                String name = new String(str.substring(2, (str.length())));
                ClientName[1] = name;
                ClientTable.put(name, nu);
                nu.write(o);
                System.out.println("Connection Successful for " + name);
            } else {
                
                if(isShooter){                   
                    System.out.println(str);
                    ClientTable.get(ClientName[0]).write((Object)str);
                }
                else{
                    System.out.println(str);
                    ClientTable.get(ClientName[1]).write((Object) str);
                }
            }
            

        }

        
    
    }
}
