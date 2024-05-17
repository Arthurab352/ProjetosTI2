package app;

import static spark.Spark.*;
import service.LoginService;

public class Aplicacao {

    private static LoginService loginService = new LoginService();

    public static void main(String[] args) {
        port(5432);

        staticFiles.location("/public");

        post("/login/insert", (request, response) -> loginService.insert(request, response));

        get("/login/:id", (request, response) -> loginService.get(request, response));

        get("/login/list/:orderby", (request, response) -> loginService.getAll(request, response));

        get("/login/update/:id", (request, response) -> loginService.getToUpdate(request, response));

        post("/login/update/:id", (request, response) -> loginService.update(request, response));

        get("/login/delete/:id", (request, response) -> loginService.delete(request, response));

    }
}