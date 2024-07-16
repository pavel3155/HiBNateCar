package connBD;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ConnectJDBC {
    String HOST_NAME ="jdbc:postgresql://localhost:5432/";
    String LOGIN;
    String PASS;
    public Connection con;


    /**
     * конструктор класса,создает подключение к серверу СУБД
     * @param log - имя пользователя подключающегося к СУБД
     * @param pass - пароль пользователя
     */
    public ConnectJDBC(String log, String pass){
        try {
            Class.forName("org.postgresql.Driver");
            this.LOGIN=log;
            this.PASS=pass;
            String strConn=this.HOST_NAME;
            this.con = DriverManager.getConnection(strConn,LOGIN,PASS);
            System.out.println("соединение с сервером установлено...");
        } catch (ClassNotFoundException e ) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("драйвер подключен...");
    }

    /**
     * метод создает подключение к БД
     * @param nameBD-имя БД
     * @param encoding-кодировка
     */
    public void connToDB(String nameBD, String encoding){
        try {
            String strConn=this.HOST_NAME+nameBD+encoding;
            this.con = DriverManager.getConnection(strConn,LOGIN,PASS);
            System.out.println("соединение с БД установлено...");
        } catch (SQLException e ) {
            throw new RuntimeException(e);
        }
    }
    /**
     * метод закрывает соединение
     */
    public void connClose() {
        try{
            con.close();
            System.out.println("соединение закрыто...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод завершаетв сеанс подключения к БД,
     * удаляет БД
     * @param nameDB
     */

    public void DropDB(String nameDB) {
        try (Statement stmt = con.createStatement();) {
            String backend = "select pg_terminate_backend(pid) from pg_stat_activity where datname='"+nameDB+"'";//завершаем сеанс подключения к БД
            String sql = "drop database if exists " + nameDB;
            stmt.executeQuery(backend);
            stmt.executeUpdate(sql);
            System.out.println("База данных '"+ nameDB+"' удалена...'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод создает БД
     * @param nameDB
     */
    public void CreatDB(String nameDB) {
        try (Statement stmt = con.createStatement();) {
            String sql = "create database " + nameDB;
            stmt.executeUpdate(sql);
            System.out.println("База данных '"+ nameDB+"' создана...'");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод выполняет скрипт, который может включать команды для выполнения манипуляций с таблицами БД
     * скрипт не может включать команды создания/удаления БД
     * @param fileName - имя БД
     * @throws FileNotFoundException
     */
    public void exeScript(String fileName) throws FileNotFoundException {
        InputStream is = new FileInputStream(fileName); // Открываем файл.
        try (Scanner scanner = new Scanner(is).useDelimiter(";")) {
            try (Statement stmt = con.createStatement()) {
                while(scanner.hasNext()) {
                    String sqlStmt = scanner.next() + ";";
                    if (!sqlStmt.trim().isEmpty()) {
                        stmt.executeUpdate(sqlStmt); // Выполняем SQL команду.
                    }
                }
                System.out.println("скрипт '"+fileName+"' выполнен полностью, без ошибок ...");
            }
        } catch ( SQLException e) {
            System.out.println("выполнение скрипта '"+fileName+"' завершено с ошибокой ...");
            e.printStackTrace(); // Обрабатываем исключения.

        }
    }





}
