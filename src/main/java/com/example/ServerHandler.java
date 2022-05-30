package com.example;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.example.Settings.*;

public class ServerHandler extends Thread {

    private Socket socket;
    private EntityRepository entityRepository;

    public ServerHandler(Socket socket, EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
            System.out.println("New connection established");
            /**
             * Начинаем игру.
             */
            while (true) {
                /**
                 * Получаем Ball от клиента. Записываем его в repository для статистики
                 */
                Ball ball = (Ball) input.readObject();
                System.out.println("Receive " + ball);
                long serverDate = System.currentTimeMillis();
                entityRepository.store(ball, serverDate);
                /**
                 * Устанавливаем в Ball параметры Name = "Сервер" и Counter + 1
                 */
                ball.setName("Сервер");
                ball.setCounter(ball.getCounter() + 1);
                /**
                 * Если Counter > MAX_BALL_COUNTER, то устанавливаем в Ball параметры Name = Error и Counter = -1, который для клиента означает ошибку
                 */
                if (ball.getCounter() > MAX_BALL_COUNTER){
                    ball.setName(ERROR_MESSAGE);
                    ball.setCounter(-1);
                }
                /**
                 * Update-им в Ball параметр Date и отправляем клиенту
                 */
                ball.setDate(serverDate);
                output.writeObject(ball);
                System.out.println("Send " + ball);
            }
        }catch (EOFException e){
            System.err.println("Receive terminate signal from client");
        }catch (ClassNotFoundException | IOException e) {
            System.err.println(e);
        }
        System.out.println("Connection closed");
    }
}