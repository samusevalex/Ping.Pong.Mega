package com.example;

import com.sun.net.httpserver.HttpServer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import static com.example.Settings.*;

public class Server {
    public static void main(String[] args) {
        /**
         *  Читаем настройки из settings.xml и заносим их в Settings.class
         */
        try {
            JAXBContext context = JAXBContext.newInstance(Settings.class);
            Unmarshaller m = context.createUnmarshaller();
            Settings settings = (Settings) m.unmarshal(new FileInputStream("settings.xml"));
        } catch (FileNotFoundException e) {
            System.err.println("File settings.xml not found\nApplication starts with default parameters:\nAPP_TCP_PORT = 8023, HTTP_TCP_PORT = 8080, MAX_BALL_COUNTER = 100, ERROR_MESSAGE = Ошибка");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        /**
         *  Создаём Repository для статистики и истории. Ссылку на Repository передаём как в MyHttpServer, так и в ServerHandler.
         */
        EntityRepository entityRepository = new EntityRepository();
        /**
         * Запускаем http://localhost:8080
         */
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(HTTP_TCP_PORT), 0);
            server.createContext("/", new MyHttpServer(entityRepository));
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server started");
        /**
         *  Начинаем принимать соединения
         */
        try (ServerSocket serverSocket = new ServerSocket(APP_TCP_PORT)) {
            while (true) {
                ServerHandler serverHandler = new ServerHandler(serverSocket.accept(), entityRepository);
                serverHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
