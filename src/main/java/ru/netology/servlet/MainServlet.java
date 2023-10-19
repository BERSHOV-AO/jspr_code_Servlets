package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

    private static final String API_POSTS = "/api/posts";
    private static final String API_POSTS_ID = "/api/posts/\\d+";
    private PostController controller;

    @Override
    public void init() {
        // отдаём список пакетов, в которых нужно искать аннотированные классы
        final var context = new AnnotationConfigApplicationContext("ru.netology");
        // получаем по классу бина
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            if (method.equals("GET") && path.equals(API_POSTS)) {
                controller.all(resp);
                return;
            }

            if (method.equals("GET") && path.matches(API_POSTS_ID)) {
                // Простой способ
                controller.getById(getPostID(path), resp);
                return;
            }
            if (method.equals("POST") && path.equals(API_POSTS)) {
                controller.save(req.getReader(), resp);
            }

            if (method.equals("DELETE") && path.matches(API_POSTS_ID)) {
                controller.removeById(getPostID(path), resp);
                return;
            }
            // Ели пришло что то другое, выбрасываем 404 ошибку
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            // Если исключение то 500 ошибка
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long getPostID(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }
}

