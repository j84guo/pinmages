package controllers;

import play.mvc.*;
import play.libs.Json;
import services.PinmagesService;

import javax.inject.Inject;

public class PinmagesController extends Controller {

    private PinmagesService service;

    @Inject
    public PinmagesController(PinmagesService service) {
        this.service = service;
    }

    public Result index() {
        return ok("Welcome to the index of Image Pins!");
    }

    public Result getUser(String name) {
        try {
            return ok(Json.toJson(service.getUser(name)));
        } catch(IllegalArgumentException e) {
            return notFound();
        }
    }

    public Result getUsers() {
        return ok(Json.toJson(service.getAllUsers()));
    }

    public Result createUser(String name) {
        try {
            return ok(Json.toJson(service.createUser(name)));
        } catch(IllegalArgumentException e) {
            return badRequest();
        }
    }
}
