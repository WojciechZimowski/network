package org.example;

import java.io.*;
import java.net.Socket;

public class Client implements  Runnable {

    private String login;
    private BufferedReader in;
    private PrintWriter out;
    private Server server;
    private Socket socket;

    public String whoIsLoggedIn(){
        String whoIsLoggedIn = "";
        for(String s : server.getClientMap().values()){
            whoIsLoggedIn += s + "\n";
        }
        //4b
    }
    @Override
    public void run() {
        String message;
        while(true){
            try {
                if (((message = in.readLine())!=null)) {
                    if(message.equals("@online")){
                        this.sendMessage(whoIsLoggedIn());
                    }
                }
            } catch (IOException e) {
               //zapisz błąd do pliku z logami
            }

        }

    }

    public String getLogin() {
        return login;
    }
    public void sendMessage(String message){
        this.out.println(message);
    }

    public Client( Server server, Socket socket) {
        this.socket = socket;
        this.server = server;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            //zapisz błąd do pliku z logami
        }
        try {
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            //zapisz błąd do pliku z logami
        }
    }


}
