package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;

  //------------------------------------------
  // Создаем лист для проверки
   List<Post> postList = List.of(new Post(1,"11"),new Post(2,"12"));
  //------------------------------------------

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    //final var data = service.all();
    //-------------------------
    // проверка
    final var data = postList;
    //-------------------------
    final var gson = new Gson();
    response.getWriter().print(gson.toJson(data));
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    //---------------------------------------------------
    // проверка
    int tempId = (int)id;
    final var dataId = postList.get(tempId);
    final var gson = new Gson();
    response.getWriter().print(gson.toJson(dataId));
    //---------------------------------------------------

    // TODO: deserialize request & serialize response
  }

  // тело запроса и ответ
  public void save(Reader body, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    final var post = gson.fromJson(body, Post.class);
    // Метод save() возвращает тот post который мы получили
    // Условно сохраняем, на самом деле возвращаем пользователю
    // В домашнем задании надо именно сохранять.
    final var data = service.save(post);
    // Будем JSON преобразовывать методом toJson()
    response.getWriter().print(gson.toJson(data));
  }

  // Удаление по ID
  public void removeById(long id, HttpServletResponse response) {
    // TODO: deserialize request & serialize response
  }
}
