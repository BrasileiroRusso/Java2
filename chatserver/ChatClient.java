package ru.geekbrains.vklimovich.chatserver;

import java.io.*;
import java.net.*;

public class ChatClient {
    static final int PORT = 8189;
    static final String IP_ADDRESS = "localhost";

    private Socket socket;
    private StreamHandler streamHandler;

    public ChatClient() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            System.out.println("Connected");

            streamHandler = new StreamHandler(socket.getInputStream(), System.out, System.in, socket.getOutputStream());
            streamHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
          }
        }

}
