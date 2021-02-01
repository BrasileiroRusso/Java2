package ru.geekbrains.vklimovich.chatserver;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class UnblockDataChannel implements Runnable {
    private static final String END_WORD = "/end";
    private static final int BUFF_SIZE = 48;

    private InputStream inputStream;
    private OutputStream outputStream;
    private PrintWriter outputWriter;
    private ReadableByteChannel channel;
    private ByteBuffer buffer;
    private StringBuilder strBuilder;
    private Pattern pattern = Pattern.compile("(?m)^.*$");
    private String endWord;
    private String channelClient;
    private List<Thread> threads;

    public UnblockDataChannel(InputStream inputStream, OutputStream outputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;

        channel = Channels.newChannel(inputStream);
        buffer = ByteBuffer.allocate(BUFF_SIZE);
        strBuilder = new StringBuilder();
        Pattern pattern = Pattern.compile("(?m)^.*$");
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

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }

    @Override
    public void run() {
        int endIndex;
        String curLine;

        try{
            THREAD_RUN:
            while(true) {
                if (channel.read(buffer) >= 0) {
                    buffer.flip();
                    while (buffer.hasRemaining())
                        strBuilder.append((char) buffer.get());
                    buffer.clear();

                    Matcher matcher = pattern.matcher(strBuilder.toString());
                    endIndex = -1;
                    while(matcher.find()){
                        curLine = matcher.group();
                        if(!curLine.isEmpty()){
                            if (curLine.equals(endWord)) {
                                outputWriter.println(channelClient.isEmpty()?curLine:(channelClient + " " + "disconnected"));
                                break THREAD_RUN;
                            }
                            else{
                                outputWriter.println((channelClient.isEmpty()?"":channelClient + ": ") + curLine);
                            }
                        }
                        endIndex = matcher.end();
                    }
                    if(endIndex >= 0)
                        strBuilder = new StringBuilder(strBuilder.toString().substring(endIndex));
                }

            }
        }
        catch(IOException e){

        }
        finally{
            for(Thread t: threads)
                t.interrupt();
        }

    }
}
