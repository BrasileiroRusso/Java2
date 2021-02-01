package ru.geekbrains.vklimovich.chat;

import java.io.*;

public class StreamHandler {
    private static final String CLIENT_NAME = "Client";

    private DataChannel readChannel;
    private DataChannel writeChannel;
    private Thread readThread;
    private Thread writeThread;

    public StreamHandler(InputStream readStreamIn, OutputStream readStreamOut,InputStream writeStreamIn, OutputStream writeStreamOut){
        readChannel = new DataChannel(readStreamIn, readStreamOut);
        writeChannel = new DataChannel(writeStreamIn, writeStreamOut);
        readChannel.setChannelClient(CLIENT_NAME);
        readThread = new Thread(readChannel);
        writeThread = new Thread(writeChannel);
    }

    public void start(){
        readThread.start();
        writeThread.start();

        try {
            readThread.join();
            writeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
