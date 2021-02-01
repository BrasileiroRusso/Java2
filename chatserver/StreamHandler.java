package ru.geekbrains.vklimovich.chatserver;

import java.io.*;
import java.util.*;

public class StreamHandler {
    private static final String CLIENT_NAME = "Client";

    private UnblockDataChannel readChannel;
    private UnblockDataChannel writeChannel;
    private Thread readThread;
    private Thread writeThread;

    public StreamHandler(InputStream readStreamIn, OutputStream readStreamOut,InputStream writeStreamIn, OutputStream writeStreamOut){
        readChannel = new UnblockDataChannel(readStreamIn, readStreamOut);
        writeChannel = new UnblockDataChannel(writeStreamIn, writeStreamOut);
        readChannel.setChannelClient(CLIENT_NAME);
        readThread = new Thread(readChannel);
        writeThread = new Thread(writeChannel);

        List<Thread> threads = new ArrayList<Thread>();
        threads.add(readThread);
        threads.add(writeThread);
        readChannel.setThreads(threads);
        writeChannel.setThreads(threads);
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
