package com.notepad.notepad;

public class Note {
    private int id;
    private String topic;
    private String content;

    // Constructors
    public Note() {}

    public Note(int id, String topic, String content) {
        this.id = id;
        this.topic = topic;
        this.content = content;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
