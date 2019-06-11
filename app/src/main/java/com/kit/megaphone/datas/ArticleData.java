package com.kit.megaphone.datas;

public class ArticleData {

    private String name;
    private String date;
    private String topic;
    private String title;
    private String content;

    public ArticleData() {}

    public ArticleData(String name, String date, String topic, String title, String content) {
        this.name = name;
        this.date = date;
        this.topic = topic;
        this.title = title;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
