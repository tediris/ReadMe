package tediris.feeds;

import android.os.AsyncTask;

/**
 * Created by tmediris on 8/8/13.
 */
public class RssTask {

    public static class RssListTask extends AsyncTask<String, Integer, RssList> {

        @Override
        protected RssList doInBackground(String... input) {
            return new RssList(input[0]);
        }

    }

    public static class RssFeedTask extends AsyncTask<String, Integer, RssFeed> {

        @Override
        protected RssFeed doInBackground(String... input) {
            return RssFeed.createFeed(input[0]);
        }

    }

}
