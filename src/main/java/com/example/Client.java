package com.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Random;

public class Client {

    public static void main(String[] args) {
        /**
         * Задаём параметры по Default-у
         */
        String serverIp = "localhost";
        int tcpPort = 8023;
        int nameLength = 5;
        String name = "Noname";
        int initball = 0;
        int timeout = 1000;
        /**
         * Проверка на количество аргументов. Если аргументов < 2 выдаём --help
         */
        switch (args.length) {
            case 0:
            case 1:
                System.err.println("usage: Client INITBALL TIMEOUT(sec)");
                return;
        }
        /**
         * Инициализируем переменные для работы приложения
         */
        try {
            initball = Integer.valueOf(args[0]);
            timeout = Integer.valueOf(args[1]) * 1000;
        } catch (NumberFormatException e) {
            System.err.println("Parameters must be nubmers.\nApplication starts with default parameters:\nINITBALL=0, TIMEOUT=1");
        }
        /**
         * Генерируем случайное имя шарика размером nameLength символов
         */
        Random random = new Random();
        StringBuilder sb = new StringBuilder(nameLength);
        for (int i = 0; i < nameLength; i++)
            sb.appendCodePoint(random.nextInt(0x19) + 0x41);
        name = sb.toString();
        /**
         * Подключаемся к серверу
         * Cериализация в Socket
         */
        try (Socket socket = new Socket(serverIp, tcpPort);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) 
            /**
             *  Сериализация в файл
             *  ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("filename"))
             *  ObjectInputStream i = new ObjectInputStream(new FileInputStream("filename"))
             *
             *  Сериализация в ByteArray  
             *  ObjectOutputStream o = new ObjectOutputStream(new ByteArrayOutputStream());
             *  ObjectInputStream i = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
             *  
             *  Запиcь/чтение
             *  o.writeObject(ball);
             *  Ball b = (Ball) i.readObject()
             */        
        {
            System.out.println("Client " + name + " connected to server");
            /**
             *  Создаём init ball и отправляем его на сервер
             */
            {
                Ball ball = new Ball();
                ball.setName(name);
                ball.setCounter(initball);
                ball.setDate(System.currentTimeMillis());
                output.writeObject(ball);
                System.out.println("Send init " + ball);
            }
            /**
             * Начинаем игру. Игра заканчивается, когда от сервера прилетает Ball с Counter = -1.
             */
            while (true) {
                Ball ball = (Ball) input.readObject();
                System.out.println("Receive " + ball);
                if (ball.getCounter() == -1) break;
                /**
                 * От сервера принимаем Ball с именем "Сервер". Имя изменяем на Name, Counter ++, update-им Date. Ждём Timeout. Отправляем Ball обратно серверу.
                 */
                ball.setName(name);
                ball.setCounter(ball.getCounter() + 1);
                ball.setDate(System.currentTimeMillis());
                Thread.sleep(timeout);
                output.writeObject(ball);
                System.out.println("Send " + ball);
            }
        } catch (ConnectException e) {
            System.err.println("Server " + serverIp + " is not available on TCP port " + tcpPort);
        } catch (InterruptedException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Game over");
    }
}
