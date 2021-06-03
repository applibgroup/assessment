package com.example.news.model;

public class Post
{
    private String author;
    private String description;
    private String publishedAt;
    private String title;
    private String url;
    private String urlToImage;
    private String content;


    public Post()
    {
    }

    public Post(String author, String description, String publishedAt, String title, String url, String urlToImage)
    {
        this.author = author;
        this.description = description;
        this.publishedAt = publishedAt;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
    }

    public String getContent() {return  content; }

    public  void setContent(String content) { this.content = content; }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPublishedAt()
    {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt)
    {
        this.publishedAt = publishedAt;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrlToImage()
    {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage)
    {
        this.urlToImage = urlToImage;
    }
}
