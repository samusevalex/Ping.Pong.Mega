package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Ведём статистику шариков
 */
public class EntityRepository {

    private HashMap<String, Entity> entityStorage = new HashMap<>();
    private ArrayList<Ball> history = new ArrayList<>();

    public void store(Ball ball, long serverDate) {
        String name = ball.getName();
        history.add(ball);
        Entity entity = entityStorage.get(name);
        /**
         * Если Ball с именем name ещё нет в Repository, создаём новый объект Bucket.
         * Инициализируем у него поля name и initBall.
         */
        if (entity == null) {
            entity = new Entity();
            entity.setName(name);
            entity.setInitBall(ball.getCounter());
        }
        /**
         * Если Ball с именем name уже есть в Repository, то достаём соответствующий объект Bucket из Repository.
         * Update-им поля counter, clientDate, serverDate. И записываем обратно в Repository.
         */
        entity.setCounter(ball.getCounter());
        entity.setClientDate(ball.getDate());
        entity.setServerDate(serverDate);
        entityStorage.put(name, entity);
    }

    public Collection<Entity> getAllEntitsAsCollection() {
        return entityStorage.values();
    }
}
