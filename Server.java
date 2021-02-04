package server;

import commands.Command;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.*;
import java.util.regex.*;


public class Server {
    private ServerSocket server;
    private Socket socket;
    private final int PORT = 8189;
    private List<ClientHandler> clients;
    private AuthService authService;

    private static final Pattern nicksPattern = Pattern.compile("^((\\s*"+Command.NICK_SYM+"\\s+\\w+)+)\\s*(.*)");
    private static final Pattern nickPattern = Pattern.compile(Command.NICK_SYM+"\\s+(\\w+)");

    public Server() {
        clients = new CopyOnWriteArrayList<>();
        authService = new SimpleAuthService();
        try {
            server = new ServerSocket(PORT);
            System.out.println("server started");

            while (true) {
                socket = server.accept();
                System.out.println("client connected" + socket.getRemoteSocketAddress());
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(ClientHandler sender, String msg){
        if (msg.isEmpty())
            return;

        Matcher m = nicksPattern.matcher(msg);
        String message;

        if (m.find()){
            Matcher nickMatch = nickPattern.matcher(m.group(1));
            msg = m.group(m.groupCount());
            Set<String> nicks = new HashSet<String>();
            while(nickMatch.find())
                nicks.add(nickMatch.group(1));

            if(!msg.isEmpty())
                sendToAll(sender, msg, nicks);
        }
        else{
            sendToAll(sender, msg);
        }
    }

    private void sendToAll(ClientHandler sender, String msg){
        String message = String.format("[ %s ] : %s", sender.getNickname(), msg);
        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
    }

    private void sendToAll(ClientHandler sender, String msg, Set<String> nickSet){
        String message = String.format("[ %s ] : %s", sender.getNickname(), msg);
        StringBuilder nicks = new StringBuilder();

        nickSet.remove(sender.getNickname());

        for (ClientHandler c : clients) {
            if(nickSet.contains(c.getNickname())){
                c.sendMsg(message);
            }
        }
        for (String nick: nickSet)
            if (authService.isValidNick(nick))
                nicks.append(nick + ", ");

        if(nicks.length() > 0){
            nicks.delete(nicks.length() - 2, nicks.length());
            String nickList = nicks.toString();
            message = String.format("[ %s ] to [%s]: %s", sender.getNickname(), nickList, msg);
            sender.sendMsg(message);
        }
        else{
            sender.sendMsg("Cant send message + \"" + message + "\"!");
        }


    }

    public void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    public AuthService getAuthService() {
        return authService;
    }
}
