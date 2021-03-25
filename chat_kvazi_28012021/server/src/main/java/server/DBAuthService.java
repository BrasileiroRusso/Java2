package server;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBAuthService implements AuthService {
    private static Connection conn = null;
    private static final Logger logger = LogManager.getLogger(DBAuthService.class);

    private static String DB_NAME;
    private static final String TABLE_NAME = "ChatUser";
    private static final int USERS_NUM = 13;

    private static final String getNickSQL = "SELECT nickname, user_id" +
                                             "  FROM " + TABLE_NAME +
                                             " WHERE login = ? AND pass = ?";

    private static final String checkUserSQL = "SELECT user_id" +
                                               "  FROM " + TABLE_NAME +
                                               " WHERE login = ? OR nickname = ?";

    private static final String addUserSQL = "INSERT into " + TABLE_NAME +
                                             "      (login, pass, nickname) " +
                                             "VALUES(?, ?, ?)";

    private static final String updateNickSQL = "UPDATE " + TABLE_NAME +
                                                "   SET nickname = ?" +
                                                "  where user_id = ?";

    private static final String checkStateSQL = "SELECT TABLE_NAME" +
                                                "  FROM information_schema.tables" +
                                                " WHERE TABLE_NAME = ? AND TABLE_SCHEMA = ?";

    private static final String createTableSQL = "CREATE TABLE " + TABLE_NAME +
                                                      " (" +
                                                      "  user_id  int        not null auto_increment" +
                                                      " ,login    varchar(30)" +
                                                      " ,pass     varchar(30)" +
                                                      " ,nickname varchar(40)" +
                                                      " ,PRIMARY KEY (user_id)" +
                                                      " )";

    private static String[][] defaultUsers = new String[USERS_NUM][3];

    static{
        defaultUsers[0] = new String[]{"david", "david", "David"};
        defaultUsers[1] = new String[]{"marina", "marina", "Marina"};
        defaultUsers[2] = new String[]{"victor", "victor", "Victor"};
        for(int i = 3;i < defaultUsers.length;i++){
            String login = "user" + (i - 2);
            defaultUsers[i] = new String[]{login, login, login};
        }
    }

    private PreparedStatement getNickStmt;
    private PreparedStatement checkUserStmt;
    private PreparedStatement addUserStmt;
    private PreparedStatement updateNickStmt;
    private PreparedStatement checkStateStmt;

    public DBAuthService() {
        boolean isInitialized;

        try {
            if(conn == null)
                conn = getConnection();

            logger.log(Level.INFO, "Установлено подключение к БД");

            checkStateStmt = conn.prepareStatement(checkStateSQL);
            isInitialized = isInitialized();

            if(isInitialized)
                logger.log(Level.INFO, "Таблица пользователей инициализирована");
            else
                logger.log(Level.WARN, "Требуется инициализация для таблицы пользователей в БД!");

            if(!isInitialized){
                createDBTable();
                logger.log(Level.INFO, "Таблица пользователей создана");
            }

            if(!isInitialized){
                initialize();
                logger.log(Level.INFO, "Добавлен список пользователей по умолчанию");
            }
        } catch (SQLException throwables) {
            if(conn == null)
                logger.log(Level.FATAL, "База данных пользователей чата недоступна: " + throwables.getMessage());
            else
                logger.log(Level.ERROR, "SQL-ошибка: " + throwables.getMessage());

        } catch (IOException e) {
            logger.log(Level.ERROR, e.getStackTrace().toString());
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "Адаптер JDBC не найден: " + e.getMessage());
        }

    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        String nickname = null;

        try {
            if(getNickStmt == null)
                getNickStmt = conn.prepareStatement(getNickSQL);
            getNickStmt.setString(1, login);
            getNickStmt.setString(2, password);
            ResultSet rs = getNickStmt.executeQuery();
            while(rs.next())
                nickname = rs.getString("nickname");
        } catch (SQLException throwables) {
            logger.log(Level.ERROR, "SQL-ошибка: " + throwables.getMessage());
        }

        return nickname;
    }

    private int getUserID(String login, String password) {
        int userID = 0;

        try {
            getNickStmt.setString(1, login);
            getNickStmt.setString(2, password);
            ResultSet rs = getNickStmt.executeQuery();
            while(rs.next())
                userID = rs.getInt("user_id");
        } catch (SQLException throwables) {
            logger.log(Level.ERROR, "SQL-ошибка: " + throwables.getMessage());
        }

        return userID;
    }

    private boolean checkUser(String login, String nickname){
        int userID = 0;

        try {
            if(checkUserStmt == null)
                checkUserStmt = conn.prepareStatement(checkUserSQL);
            checkUserStmt.setString(1, login);
            checkUserStmt.setString(2, nickname);
            ResultSet rs = checkUserStmt.executeQuery();
            while(rs.next())
                userID = rs.getInt("user_id");
        } catch (SQLException throwables) {
            logger.log(Level.ERROR, "SQL-ошибка: " + throwables.getMessage());
        }

        return userID > 0;
    }

    private void addUser(String login, String password, String nickname){
        try {
            if(addUserStmt == null)
                addUserStmt = conn.prepareStatement(addUserSQL);
            addUserStmt.setString(1, login);
            addUserStmt.setString(2, password);
            addUserStmt.setString(3, nickname);
            addUserStmt.executeUpdate();
        } catch (SQLException throwables) {
            logger.log(Level.ERROR, "SQL-ошибка: " + throwables.getMessage());
        }
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        if(!checkUser(login, nickname)){
            addUser(login, password, nickname);
            return true;
        }
        int userID = getUserID(login, password);
        if(userID == 0)
            return false;

        try {
            if(updateNickStmt == null)
                updateNickStmt = conn.prepareStatement(updateNickSQL);
            updateNickStmt.setString(1, nickname);
            updateNickStmt.setInt(2, userID);
            updateNickStmt.executeUpdate();
        } catch (SQLException throwables) {
            logger.log(Level.ERROR, "SQL-ошибка: " + throwables.getMessage());
            return false;
        }

        return true;
    }

    private static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        Properties props = new Properties();
        try (InputStream in = DBAuthService.class.getResourceAsStream("/database.properties")) {
            props.load(in);
        }
        String driver = props.getProperty("jdbc.driver");
        if (driver != null)
            Class.forName(driver);
        String url = props.getProperty("jdbc.url");

        Pattern pattern = Pattern.compile("/(\\w+)");
        Matcher match = pattern.matcher(url);
        while(match.find())
            DB_NAME = match.group(1);

        String options = props.getProperty("jdbc.options");
        if (options != null)
            url = url + "?" + options;
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        logger.log(Level.INFO, "driver = " + driver);
        logger.log(Level.INFO, "url = " + url);
        logger.log(Level.INFO, "username = " + username);
        logger.log(Level.INFO, "password = " + password);
        logger.log(Level.INFO, "DB_NAME = " + DB_NAME);

        return DriverManager.getConnection(url, username, password);

    }

    private boolean isInitialized(){
        boolean result = false;

        try {
            checkStateStmt.setString(1, TABLE_NAME);
            checkStateStmt.setString(2, DB_NAME);
            ResultSet rs = checkStateStmt.executeQuery();
            while(rs.next())
                result = true;
        } catch (SQLException throwables) {
            logger.log(Level.ERROR, "SQL-ошибка: " + throwables.getMessage());
        }

        return result;

    }

    private void createDBTable() throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(createTableSQL);
    }

    private void initialize(){
        for(int i = 0;i < defaultUsers.length;i++)
            registration(defaultUsers[i][0], defaultUsers[i][1], defaultUsers[i][2]);

    }

    public static void main(String[] args) throws SQLException, IOException {
        DBAuthService auth = new DBAuthService();
    }
}
