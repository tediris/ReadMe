package tediris.feeds;

import java.util.ArrayList;

/**
 * Created by tmediris on 8/8/13.
 */
public class RssItem {

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

    public String getContentSnippet() {
        return contentSnippet;
    }

    public void setContentSnippet(String contentSnippet) {
        this.contentSnippet = contentSnippet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    private String title;
    private String content;
    private String contentSnippet;
    private String url;
    private String author;
    private ArrayList<String> categories;

    public RssItem(String title, String content, String contentSnippet, String url, String author, ArrayList<String> categories) {
        this.title = title;
        this.categories = categories;
        this.url = url;
        this.contentSnippet = contentSnippet;
        this.content = content;
        this.author = author;
    }

    public RssItem(String title, String contentSnippet, String url) {
        this.title = title;
        this.contentSnippet = contentSnippet;
        this.url = url;
        this.author = null;
        this.categories = null;
        this.content = null;
    }

    @Override
    public String toString() {
        return "{" + this.title + ", " + this.contentSnippet + "}";
    }

}
