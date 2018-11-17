package services;

import models.Image;
import utils.PgUtils;
import models.User;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.IOException;
import java.nio.file.*;
import javax.inject.Inject;

import com.typesafe.config.Config;
import com.google.inject.Singleton;

@Singleton
public class PgService {

    private String usersFile;
    private String imagesDir;
    private String pinmagesPath;

    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, Image> images = new HashMap<>();

    @Inject
    public PgService(Config config) throws IOException {
        usersFile = config.getString("usersFile");
        imagesDir = config.getString("imagesDir");
        pinmagesPath = config.getString("pinmagesPath");

        initFilesystem();
    }

    public Image createImage(String description, String creator, byte[] binary) {
        try {
            var metaHash = PgUtils.hashSha256(
                String.format("%s%s", description, creator)
                    .getBytes(StandardCharsets.UTF_8));
            var binaryHash = PgUtils.hashSha256(binary);
            var imageId = metaHash + "-" + binaryHash;

            if (images.containsKey(imageId)) {
                return images.get(imageId);
            }

            var image = new Image(
                description,
                creator,
                pinmagesPath + imagesDir,
                imageId);

            images.put(imageId, image);
            return image;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public ArrayList<Image> getAllImages() {
        return new ArrayList<>(images.values());
    }

    /** todo: queue file save */
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

    /** todo: delete all songs */
    public boolean deleteUser(String name) {
        if (!users.containsKey(name)) {
            return false;
        }

        users.remove(name);
        return true;
    }

    private void initFilesystem() throws IOException {
        PgUtils.createDir(Paths.get(pinmagesPath));
        PgUtils.createDir(Paths.get(pinmagesPath, imagesDir));
        PgUtils.createFile(Paths.get(pinmagesPath, usersFile), "rw-r--r--");
    }
}
