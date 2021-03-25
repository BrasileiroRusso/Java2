package server;

import commands.Command;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);

    private ServerSocket server;
    private Socket socket;
    private final int PORT = 8189;
    private List<ClientHandler> clients;
    private AuthService authService;
    private ExecutorService execService;

    public Server() {
        clients = new CopyOnWriteArrayList<>();
        authService = new DBAuthService();
        try {
            server = new ServerSocket(PORT);
            logger.log(Level.INFO, "server started");
            execService = Executors.newCachedThreadPool();

            while (true) {
                socket = server.accept();
                logger.log(Level.INFO, "client connected" + socket.getRemoteSocketAddress());
                new ClientHandler(this, socket, execService);
            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.ERROR, "Ошибка при запуске сервера");
        } finally {
            try {
                execService.shutdownNow();
                server.close();
                logger.log(Level.INFO, "Сервер остановлен");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(ClientHandler sender, String msg) {
        String message = String.format("[ %s ] : %s", sender.getNickname(), msg);
        logger.log(Level.INFO, "Получено сообщение от " + sender.getNickname() + " для всех: {" + msg + "}");
        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
        logger.log(Level.INFO, "Сообщение от " + sender.getNickname() + " отправлено всем активным пользователям: {" + msg + "}");
    }

    public void privateMsg(ClientHandler sender, String receiver, String msg) {
        String message = String.format("[ %s ] to [ %s ]: %s", sender.getNickname(), receiver, msg);
        logger.log(Level.INFO, "Получено личное сообщение для " + receiver + " от " + sender.getNickname() + " для всех: {" + msg + "}");
        for (ClientHandler c : clients) {
            if(c.getNickname().equals(receiver)){
                c.sendMsg(message);
                if(!c.equals(sender)){
                    logger.log(Level.INFO, "Отправляем личное сообщение для " + receiver + " от " + sender.getNickname() + ": {" + msg + "}");
                    sender.sendMsg(message);
                }
                return;
            }
        }
        sender.sendMsg("not found user: "+ receiver);
        logger.log(Level.WARN, "Пользователь " + receiver + " не найден");
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        logger.log(Level.INFO, "Новый активный пользователь: " + clientHandler.getNickname());
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        logger.log(Level.INFO, "Пользователь вышел: " + clientHandler.getNickname());
        broadcastClientList();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public boolean isLoginAuthenticated(String login){
        for (ClientHandler c : clients) {
            if(c.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }

    public void broadcastClientList(){
        StringBuilder sb = new StringBuilder(Command.CLIENT_LIST);
        for (ClientHandler c : clients) {
            sb.append(" ").append(c.getNickname());
        }

        logger.log(Level.INFO, "Сформирован список активных пользователей");
        String message = sb.toString();

        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
        logger.log(Level.INFO, "Список пользователей отправлен всем активным клиентам");
    }
}
