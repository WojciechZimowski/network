package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
   // private List<Client>clients;
    private ServerSocket serverSocket;
    private Map<String, Client> clientMap = new HashMap<>();



    public Server() {
        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.out.println("Could not listen on port: 1234");
        }
    }
    public void clientLoggedIn(Client client){
        clientMap.put(client.getLogin(),client);
                broadcast(String.format("%s się zalogował", client.getLogin()));

        }


    public void clientLoggedOut(Client client){
        clientMap.remove(client.getLogin());
        broadcast(String.format("%s się zalogował", client.getLogin()));

    }
    public void broadcast(String message){
        for(Client c : clientMap.values()){
                c.sendMessage(message);
        }
    }
    public  void listen(){
        while(true){
            try {
                Socket socket = serverSocket.accept();
                Client client = new Client(this, socket);
                new Thread(client).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public Map<String, Client> getClientMap() {
        return clientMap;
    }
}
