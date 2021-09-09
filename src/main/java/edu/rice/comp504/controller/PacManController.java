package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.model.DispatchAdapter;

import static spark.Spark.*;

/**
 * PacMan controller.
 */
public class PacManController {
    /**
     * Main method.
     *
     * @param args arguments.
     */
    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        staticFiles.location("/public");

        Gson gson = new Gson();
        DispatchAdapter da = new DispatchAdapter();

        get("/hello", (req, res) -> "Hello Heroku World");
        get("/start", (request, response) -> {
            return gson.toJson(da.startGame());
        });

        get("/update", (request, response) -> {
            return gson.toJson(da.updateGame());
        });

        post("/changeDirection", (request, response) -> {
            da.switchDirection(request.body());
            return gson.toJson("{\"result\" : \"success\"}");
        });

        get("/reset", (request, response) -> {
            da.resetAll();
            return gson.toJson("{\"result\" : \"success\"}");
        });

        get("/mode", (request, response) -> {
            return da.switchMode();
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
