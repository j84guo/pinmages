package models;

import java.util.Date;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Image {

    private Date pinDate = new Date();
    private String description;
    private String creator;
    private String id;

    private int pinCount;
    private Path path;

    public Image(String description, String creator, String dir, String id) {
        this.description = description;
        this.creator = creator;
        this.id = id;
        this.path = Paths.get(dir, id);
        this.pinCount = 1;
    }

    public String getDescription() {
        return description;
    }

    public String getCreator() {
        return creator;
    }

    public String getPinDate() {
        return pinDate.toString();
    }

    @JsonIgnore
    public Path getPath() {
        return path;
    }

    @JsonIgnore
    public int getPinCount() {
        return pinCount;
    }

    public void incPinCount() {
        ++pinCount;
    }

    public void decrementPins() {
        if (pinCount == 0) {
            throw new IllegalStateException("Pins may not be negative.");
        }
        --pinCount;
    }
}
