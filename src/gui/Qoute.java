package gui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class Qoute {
    private String _id;
    private String content;
    private String author;
    @JsonProperty
    ArrayList < Object > tags = new ArrayList< Object >();
    private String authorSlug;
    private float length;
    private String dateAdded;
    private String dateModified;


    // Getter Methods

    public String get_id() {
        return _id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorSlug() {
        return authorSlug;
    }

    public float getLength() {
        return length;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getDateModified() {
        return dateModified;
    }

    // Setter Methods

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthorSlug(String authorSlug) {
        this.authorSlug = authorSlug;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
}