package com.example.news.model;

public class Post
{
    private String author;
    private String publishedAt;
    private String content;
    private String title;
    private String description;

    public Post()
    {
    }

    public Post(String author, String publishedAt, String content, String title, String description)
    {
        this.author = author;
        this.publishedAt = publishedAt;
        this.content = content;
        this.title = title;
        this.description = description;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getPublishedAt()
    {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt)
    {
        this.publishedAt = publishedAt;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
