package tediris.feeds;

import android.util.Log;
import android.util.MalformedJsonException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by tmediris on 8/8/13.
 */
public class RssFeed {

    public void setRssItems(ArrayList<RssItem> rssItems) {
        this.rssItems = rssItems;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private ArrayList<RssItem> rssItems;
    private String title;
    private String link;
    private String author;
    private String description;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static final String TAG = "RSSFEED";

    public ArrayList<RssItem> getRssItems() {
        return rssItems;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public static RssFeed createFeed(String url) {

        RssFeed newFeed = new RssFeed();
        newFeed.setUrl(url);
        try {
            HttpClient httpClient = new DefaultHttpClient();
            // String request = encodeString(input[0]);
            HttpResponse response = httpClient.execute(new HttpGet("http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=" + url));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();

                parseResponse(newFeed, responseString);
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                Log.e(TAG, "Failed to get data");
                return null;

            }
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
           return null;
        }
        if (newFeed.getRssItems() != null) return newFeed;
        return null;
    }

    public RssFeed(/*String url*/) {
        /*
        try {
            HttpClient httpClient = new DefaultHttpClient();
           // String request = encodeString(input[0]);
            HttpResponse response = httpClient.execute(new HttpGet("http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=" + url));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();

                this.rssItems = parseResponse(this, responseString);
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                //bad things happened
            }
        } catch (Exception e) {
            //bad things happened
        }
        */
    }

    @Override
    public String toString() {
        String result = "[";
        if (rssItems == null) {
            return "";
        }
        for (int i = 0; i < rssItems.size(); i++) {
            result += " " + rssItems.get(i).toString() + " ";
        }
        result += "]";
        return result;
    }

    private static ArrayList<RssItem> parseResponse(RssFeed rssFeed, String responseString) {
        try {
            JSONObject response = new JSONObject(responseString);
            JSONObject responseData = response.getJSONObject("responseData");
            JSONObject feed = (JSONObject) responseData.get("feed");
            rssFeed.setTitle(feed.getString("title"));
            rssFeed.setLink(feed.getString("link"));
            rssFeed.setAuthor(feed.getString("author"));
            rssFeed.setDescription(feed.getString("description"));
            JSONArray entries = feed.getJSONArray("entries");
            ArrayList<RssItem> items = new ArrayList<RssItem>();

            for (int i = 0; i < entries.length(); i++) {
                JSONObject current = safeGetJSONObject(entries, i);
                if (current == null) continue;
                String title = current.getString("title");
                String author = current.getString("author");
                String url = current.getString("link");
                String contentSnippet = current.getString("contentSnippet");
                String content = current.getString("content");
                JSONArray categoryJSON = current.getJSONArray("categories");
                ArrayList<String> categories = new ArrayList<String>();
                for (int j = 0; j < categoryJSON.length(); j++) {
                    categories.add(categoryJSON.getString(j));
                }
                RssItem newItem = new RssItem(title, content, contentSnippet, url, author, categories);
                items.add(newItem);
            }
            rssFeed.setRssItems(items);
            return items;

        } catch (JSONException e) {
            //throw new MalformedJsonException("JSON parsing of RSS feed failed");
            //do something here...
            return null;
        }
    }

    private String safeGetJSONString(JSONObject obj, String key) {
        try {
            return obj.getString(key);
        } catch (Exception e) {
            return "";
        }
    }

    private static JSONObject safeGetJSONObject(JSONArray arr, int index) {
        try {
            return arr.getJSONObject(index);
        } catch (JSONException e) {
            return null;
        }
    }

}
