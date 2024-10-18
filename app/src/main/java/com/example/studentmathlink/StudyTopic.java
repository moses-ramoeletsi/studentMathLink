package com.example.studentmathlink;

public class StudyTopic {
    private String topic;
    private int duration;
    private String notes;

    public StudyTopic(String topic, int duration, String notes) {
        this.topic = topic;
        this.duration = duration;
        this.notes = notes;
    }

    public String getTopic() {
        return topic;
    }

    public int getDuration() {
        return duration;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return topic + " (" + duration + " minutes)" + (notes.isEmpty() ? "" : " - " + notes);
    }
}