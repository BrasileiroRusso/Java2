package ru.geekbrains.vklimovich.chat;

import java.io.*;
import java.util.*;

public class DataChannel implements Runnable {
    private static final String END_WORD = "/end";

    private InputStream inputStream;
    private OutputStream outputStream;
    private Scanner inputScanner;
    private PrintWriter outputWriter;
    private String endWord;
    private String channelClient;

    public DataChannel(InputStream inputStream, OutputStream outputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        inputScanner = new Scanner(inputStream);
        outputWriter = new PrintWriter(outputStream, true);
        endWord = END_WORD;
        channelClient = "";
    }

    public void setChannelClient(String channelClient) {
        this.channelClient = channelClient;
    }

    public void setEndWord(String endWord) {
        this.endWord = endWord;
    }

    @Override
    public void run() {
        while (true){
            String str = inputScanner.nextLine();

            if (!str.isEmpty()){
                if (str.equals(endWord)) {
                    outputWriter.println(channelClient.isEmpty()?str:(channelClient + " " + "disconnected"));
                    //serverThread.interrupt();
                    break;
                }
                else{
                    outputWriter.println((channelClient.isEmpty()?"":channelClient + ": ") + str);
                }
            }
        }
    }
}
