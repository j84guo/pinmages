package services;

import play.Logger;
import models.User;

import java.util.*;
import java.io.IOException;
import javax.inject.Inject;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermissions;

import com.typesafe.config.Config;
import com.google.inject.Singleton;

@Singleton
public class PinmagesService {

    private String usersFile;
    private String imagesDir;
    private String pinmagesPath;

    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, Path> images = new HashMap<>();

    @Inject
    public PinmagesService(Config config) throws IOException {
        usersFile = config.getString("usersFile");
        imagesDir = config.getString("imagesDir");
        pinmagesPath = config.getString("pinmagesPath");

        initFilesystem();
    }

    public User createUser(String name) {
        if (users.containsKey(name)) {
            throw new IllegalArgumentException("Key already exists");
        }

        var user = new User(name);
        users.put(name, user);
        return user;
    }

    public User getUser(String name) {
        if (!users.containsKey(name)) {
            throw new IllegalArgumentException("Key does not exist");
        }

        return users.get(name);
    }

    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    private void initFilesystem() throws IOException {
        initDir(Paths.get(pinmagesPath));
        initDir(Paths.get(pinmagesPath, imagesDir));
        initFile(Paths.get(pinmagesPath, usersFile), "rw-r--r--");
    }

    private void initDir(Path path) throws IOException {
        Logger.info(String.format("Creating directory %s", path));

        try {
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException e) {
            Logger.info(String.format("Directory %s already exists", path));
        }
    }

    private void initFile(Path path, String perms) throws IOException {
        Logger.info(String.format("Creating file %s", path));

        var attrs = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(perms));

        try {
            Files.createFile(path, attrs);
        } catch (FileAlreadyExistsException e) {
            Logger.info(String.format("File %s already exists", path));
        }
    }
}
