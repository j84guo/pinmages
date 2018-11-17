package utils;

import play.Logger;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PgUtils {

    public static byte[] decodeB64(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }

    public static String hashSha256(byte[] data) throws NoSuchAlgorithmException {
        byte[] binary = MessageDigest
            .getInstance("SHA-256")
            .digest(data);

        return bytesToHex(binary);
    }

    public static String bytesToHex(byte[] binary) {
        StringBuilder sb = new StringBuilder();

        for (byte b : binary) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static void createDir(Path path) throws IOException {
        Logger.info(String.format("Creating directory %s", path));

        try {
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException e) {
            Logger.info(String.format("Directory %s already exists", path));
        }
    }

    public static void createFile(Path path, String perms) throws IOException {
        Logger.info(String.format("Creating file %s", path));

        var attrs = PosixFilePermissions
            .asFileAttribute(PosixFilePermissions.fromString(perms));

        try {
            Files.createFile(path, attrs);
        } catch (FileAlreadyExistsException e) {
            Logger.info(String.format("File %s already exists", path));
        }
    }
}
