package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

//  private PostController controller;
//
//  @Override
//  public void init() {
//    final var repository = new PostRepository();
//    final var service = new PostService(repository);
//    controller = new PostController(service);
//  }

    private PostController controller;

    @Override
    public void init() {
        // Создаем репозиторий
        final var repository = new PostRepository();
        // Создаем сервис
        final var service = new PostService(repository);
        // Создаем контроллер
        controller = new PostController(service);
    }
//--------------------------------------------------------------------------------
//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) {
//        // Выставим статус для нашего запроса по методу GET
//        // Какой статус
//        //resp.setStatus(HttpServletResponse.SC_CONFLICT);
//        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
//        // какой контент
//        resp.setContentType("text/plain");
//        try {
//            // Попробовать вывести на экран браузера
//            resp.getWriter().println("Hello from MainServlet!");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//--------------------------------------------------------------------------------

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Получаем путь
            var path = req.getRequestURI();
            // Получаем метод
            var method = req.getMethod();
            // Примитивная маршрутизация
            if (method.equals("GET") && path.equals("/api/posts")) {
                // Если пришел метод GET И путь "/api/posts", то мы хотим получить все посты которые есть в системе
                // Вызываем метод controller.all(resp);
                controller.all(resp);
                return;
            }
            // Если пришел метод GET и /api/posts/\\d+" и любое число \\d+"
            // То мы хотим получить информацию о каком то конкретном посте
            // И именно этот id, мы и буде перредовать в метод controller.getById(id, resp);
            if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
                // Простой способ
                var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                // И именно этот id, мы и буде перредовать в метод controller.getById(id, resp);
                controller.getById(id, resp);
                return;
            }
            // Так же метод POST, Если пришел метод POST И путь "/api/posts",
            if (method.equals("POST") && path.equals("/api/posts")) {
                // Вызываем метод save(), с помощью getReader() - получаем тело, которое нам пришло
                controller.save(req.getReader(), resp);
            }

            // Метод DELETED, удаляем по конкретному (id - \\d+) "/api/posts/\\d+"
            if (method.equals("DELETED") && path.matches("/api/posts/\\d+")) {
                // Простой способ
                var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.removeById(id, resp);
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


//--------------------------------------------------------------------------------
//  @Override
//  protected void service(HttpServletRequest req, HttpServletResponse resp) {
//    // если деплоились в root context, то достаточно этого
//    try {
//      final var path = req.getRequestURI();
//      final var method = req.getMethod();
//      // primitive routing
//      if (method.equals("GET") && path.equals("/api/posts")) {
//        controller.all(resp);
//        return;
//      }
//      if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
//        // easy way
//        final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
//        controller.getById(id, resp);
//        return;
//      }
//      if (method.equals("POST") && path.equals("/api/posts")) {
//        controller.save(req.getReader(), resp);
//        return;
//      }
//      if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
//        // easy way
//        final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
//        controller.removeById(id, resp);
//        return;
//      }
//      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//    } catch (Exception e) {
//      e.printStackTrace();
//      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//    }
//  }
}

