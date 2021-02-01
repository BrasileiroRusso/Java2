package ru.geekbrains.vklimovich.chat;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ChatServer {
    static final int PORT = 8189;
    static final String END_WORD = "/end";
    static ExecutorService exec;

    private ServerSocket server;
    private Socket socket;
    private StreamHandler streamHandler;

    public ChatServer(){
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started");

            socket = server.accept();
            System.out.println("Client connected");

            streamHandler = new StreamHandler(socket.getInputStream(), System.out, System.in, socket.getOutputStream());
            streamHandler.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
