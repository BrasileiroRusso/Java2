package server;

import commands.Command;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;

public class ClientHandler {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);
    private static final int WAIT_TIME = 120000;

    private Server server;
    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    private String nickname;
    private String login;

    public ClientHandler(Server server, Socket socket, ExecutorService execService) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            logger.log(Level.INFO, "Создано новое подключение с " + socket.getInetAddress());

            execService.execute(() -> {
                try {
                    socket.setSoTimeout(WAIT_TIME);
                    logger.log(Level.INFO, "Ожидаем аутентификацию пользователя");
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals(Command.END)) {
                                System.out.println("client want to disconnected ");
                                out.writeUTF(Command.END);
                                logger.log(Level.INFO, "Пользователь решил отключиться");
                                throw new RuntimeException("client want to disconnected");
                            }
                            if (str.startsWith(Command.AUTH)) {
                                String[] token = str.split("\\s");
                                logger.log(Level.INFO, "Попытка аутентификации для пользователя " + token[1]);
                                String newNick = server.getAuthService()
                                        .getNicknameByLoginAndPassword(token[1], token[2]);
                                login = token[1];
                                if (newNick != null) {
                                    if (!server.isLoginAuthenticated(login)) {
                                        nickname = newNick;
                                        logger.log(Level.INFO, "Аутентификация пользователя " + login + " пройдена успешно");
                                                sendMsg(Command.AUTH_OK + " " + nickname);
                                        server.subscribe(this);
                                        break;
                                    } else {
                                        sendMsg("С этим логином уже вошли");
                                        logger.log(Level.WARN, "Попытка повторного входа пользователя " + login);
                                    }
                                } else {
                                    sendMsg("Неверный логин / пароль");
                                    logger.log(Level.WARN, "Неверные учетные данные для пользователя " + login);
                                }
                            }

                            if (str.startsWith(Command.REG)) {
                                String[] token = str.split("\\s");
                                if (token.length < 4) {
                                    continue;
                                }
                                logger.log(Level.WARN, "Регистрация пользователя: login = " + token[1] + ", nick = " + token[3]);
                                boolean regSuccessful = server.getAuthService()
                                        .registration(token[1], token[2], token[3]);
                                if (regSuccessful) {
                                    sendMsg(Command.REG_OK);
                                    logger.log(Level.INFO, "Успешная регистрация пользователя " + token[1]);
                                } else {
                                    sendMsg(Command.REG_NO);
                                    logger.log(Level.WARN, "Ошибка при регистрации пользователя " + token[1]);
                                }
                            }
                        }
                    }
                    socket.setSoTimeout(0);

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();
                        logger.log(Level.INFO, "Получено сообщение от пользователя " + login + ": {" + str + "}");

                        if (str.startsWith("/")) {
                            if (str.equals(Command.END)) {
                                out.writeUTF(Command.END);
                                logger.log(Level.INFO, "Пользователь " + login + " вышел");
                                break;
                            }

                            if (str.startsWith(Command.PRIVATE_MSG)) {
                                String[] token = str.split("\\s+", 3);
                                if (token.length < 3) {
                                    continue;
                                }
                                logger.log(Level.INFO, "Личное сообщение от пользователя " + login + ": {" + token[2] + "}");
                                server.privateMsg(this, token[1], token[2]);
                            }
                        } else {
                            logger.log(Level.INFO, "Сообщение для всех от пользователя " + login + ": {" + str + "}");
                            server.broadcastMsg(this, str);
                        }
                    }

                } catch (RuntimeException e) {
                    logger.log(Level.ERROR, e.getStackTrace().toString());
                } catch (SocketTimeoutException e) {
                    logger.log(Level.WARN, "Истекло время ожидания входя пользователя, разрываем соединение");
                    try {
                        out.writeUTF(Command.END);
                    } catch (IOException ioException) {
                        logger.log(Level.ERROR, ioException.getStackTrace().toString());
                    }
                } catch (IOException e) {
                    logger.log(Level.ERROR, e.getStackTrace().toString());
                } finally {
                    logger.log(Level.INFO, "Закрываем соединение: " + socket.getInetAddress());
                    server.unsubscribe(this);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        logger.log(Level.ERROR, e.getStackTrace().toString());
                    }
                }
            });

        } catch (IOException e) {
            logger.log(Level.ERROR, e.getStackTrace().toString());
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getStackTrace().toString());
        }
    }

    public String getNickname() {
        return nickname;
    }

    public String getLogin() {
        return login;
    }
}
