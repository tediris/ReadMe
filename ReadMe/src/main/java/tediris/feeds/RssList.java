package tediris.feeds;

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
public class RssList {

    public ArrayList<RssFeed> getRssFeeds() {
        return rssFeeds;
    }

    private ArrayList<RssFeed> rssFeeds;

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < rssFeeds.size(); i++) {
            result += rssFeeds.get(i).toString();
        }
        return result;
    }

    public RssList(String query) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            String request = encodeString(query);
            HttpResponse response = httpClient.execute(new HttpGet("http://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=" + request));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();

                parseResponse(responseString);
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                //bad things happened
            }
        } catch (Exception e) {
            //bad things happened
        }
    }

    private void parseResponse(String responseString) {
        try {
            JSONObject response = new JSONObject(responseString);
            JSONObject responseData = response.getJSONObject("responseData");
            JSONArray entries = responseData.getJSONArray("entries");
            rssFeeds = new ArrayList<RssFeed>();
            for (int i = 0; i < entries.length(); i++) {
                JSONObject current = entries.getJSONObject(i);
                String url = current.getString("url");
                RssFeed newFeed = RssFeed.createFeed(url);
                if (newFeed != null) rssFeeds.add(newFeed);
            }
        } catch (JSONException e) {
            //merp
        }
    }

    private String encodeString(String input) {
        String[] inputs = input.split(" ");
        String result = "";
        for (int i = 0; i < inputs.length; i++) {
            result += inputs[i];
            if (i < inputs.length - 1) result += "%20";
        }
        return result;
    }

}
