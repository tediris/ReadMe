package projectbabylon.readme;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.view.View;

import com.androguide.cardsui.generator.MyPlayCard;
import com.fima.cardsui.views.CardUI;

import java.util.ArrayList;

import tediris.feeds.RssFeed;
import tediris.feeds.RssItem;
import tediris.feeds.RssTask;

public class FeedActivity extends Activity {

    private CardUI cardUI;
    public String feedURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_layout);
        // Show the Up button in the action bar.
        setupActionBar();
        cardUI = (CardUI) findViewById(R.id.cardsview);
        String url = getIntent().getExtras().getString("FEED");
        feedURL = url;
        new FeedTask().execute(url);
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        String url = extras.getString("FEED");
    }

    private class FeedTask extends RssTask.RssFeedTask {
        @Override
        public void onPostExecute(RssFeed result) {
            parseFeedToCards(result);
        }
    }

    private void parseFeedToCards(RssFeed result) {
        ArrayList<RssItem> itemsList = result.getRssItems();
        for (int i = 0; i < itemsList.size(); i++) {
            RssItem current = itemsList.get(i);
            final MyPlayCard card = new MyPlayCard(current.getTitle(), current.getContentSnippet(), "#ffbf3f", "#ff5339", true, true);
            card.setUrl(current.getUrl());
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url = card.getUrl();
                    Intent readIntent = new Intent(FeedActivity.this, ReadActivity.class);
                    readIntent.putExtra("URL", url);
                    readIntent.putExtra("PARENT", FeedActivity.this.feedURL);
                    startActivity(readIntent);
                }
            });
            cardUI.addCard(card);
        }
        cardUI.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
