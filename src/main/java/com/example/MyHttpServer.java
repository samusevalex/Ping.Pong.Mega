package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

import static com.example.Settings.*;

public class MyHttpServer implements HttpHandler {

    private EntityRepository entityRepository;

    public MyHttpServer(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        /**
         * Генерируем HTML-страницу
         */
        StringBuilder response = new StringBuilder();
        response.append("<!DOCTYPE html><html><head><meta http-equiv=\"refresh\" content=\"");
        response.append(PAGE_REFRESH_TIME);
        response.append("\"></head><body>");
        for (Entity s : entityRepository.getAllEntitsAsCollection()) {
            response.append(s);
            response.append("<br>");
        }
        response.append("</body></html>");
        /**
         * Публикуем страницу на сервере
         */
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.toString().getBytes());
    }
}