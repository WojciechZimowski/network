package org.example;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Server server = null;
        try {
            server = new Server(3000);
            server.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//a
}