package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Server {
    ServerSocket serverSocket;
    List<ClientHandler> clientHandlers;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientHandlers = new ArrayList<>();
    }

    public void listen() throws IOException {
        while(true) {
            Socket socket = serverSocket.accept();

            ClientHandler handler = new ClientHandler(socket,this);
            clientHandlers.add(handler);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }

    public void broadcast(String message,String sender) {

        for(ClientHandler handler : clientHandlers) {
            handler.send(sender + ": " + message);
        }
    }
    public void onClientConnected(ClientHandler newClient)
    {
        for(ClientHandler alreadyConnectedClient : clientHandlers){
            if(alreadyConnectedClient != newClient){
                alreadyConnectedClient.send("$login$" + newClient.getLogin());
            }
        }
    }
    public void onClientDisconnected(ClientHandler disconnectedClient){
        clientHandlers.remove(disconnectedClient);
        for(ClientHandler alreadyConnectedClient : clientHandlers){
            alreadyConnectedClient.send("$logout$" + disconnectedClient.getLogin());
        }
    }

    public void sendOnlineList(ClientHandler client){
        client.send("$online$" + clientHandlers.stream()
                .map(ClientHandler::getLogin)
                .collect(Collectors.joining("$")));
    }

    public void whisper(ClientHandler sender, String recipient, String message){
        Optional<ClientHandler> recipientHandler = clientHandlers.stream()
                .filter(client -> client.getLogin().equals(recipient))
                .findFirst();
        if(recipientHandler.isPresent()){
            recipientHandler.get().send(sender.getLogin() + ": " + message);
        } else {
            sender.send("Nie ma uzytkownika " + recipient);
        }
    }
//a
}