package controllers;

import models.Image;
import play.mvc.*;
import utils.PgUtils;
import play.libs.Json;
import services.PgService;
import com.fasterxml.jackson.databind.JsonNode;

import javax.inject.Inject;

public class PgController extends Controller {

    private PgService service;

    @Inject
    public PgController(PgService service) {
        this.service = service;
    }

    public Result index() {
        return ok("Welcome to the index of Image Pins!");
    }

    public Result getUser(String name) {
        try {
            return ok(Json.toJson(service.getUser(name)));
        } catch(IllegalArgumentException e) {
            return notFound("Bad user name.");
        }
    }

    public Result getAllUsers() {
        return ok(Json.toJson(service.getAllUsers()));
    }

    public Result createUser(String name) {
        try {
            return ok(Json.toJson(service.createUser(name)));
        } catch(IllegalArgumentException e) {
            return badRequest("User already exists.");
        }
    }

    public Result deleteUser(String name) {
        service.deleteUser(name);
        return ok();
    }

    public Result createImage(String name) {
        JsonNode body = request().body().asJson();

        String description = body.findPath("description").textValue();
        if (description == null) {
            return badRequest("Expecting \"description\" key.");
        }

        String creator = body.findPath("creator").textValue();
        if (creator == null) {
            return badRequest("Expecting \"creator\" key.");
        }

        String encoded = body.findPath("image").textValue();
        if (encoded == null) {
            return badRequest("Expecting \"image\" key.");
        }
        byte[] binary = PgUtils.decodeB64(encoded);

        Image image = service.createImage(description, creator, binary);
        if (image == null) {
            return internalServerError("Error creating image.");
        }
        return ok(Json.toJson(image));
    }

    public Result getAllImages() {
        return ok(Json.toJson(service.getAllImages()));
    }
}
