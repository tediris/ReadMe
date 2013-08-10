package projectbabylon.readme;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androguide.cardsui.generator.MyPlayCard;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;

import java.util.ArrayList;

import tediris.feeds.RssFeed;
import tediris.feeds.RssList;
import tediris.feeds.RssTask;

public class LauncherActivity extends Activity {

    private CardUI cardUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_menu_layout);
        setupActionBar();
        //console = (TextView) findViewById(R.id.console);
        //queryBox = (EditText) findViewById(R.id.queryBox);
        String search = getIntent().getExtras().getString("SEARCH");
        getActionBar().setTitle(search);
        cardUI = (CardUI) findViewById(R.id.cardsview);
        cardUI.setSwipeable(false);
        //cardUI.addStack(new CardStack());

        new LaunchRead().execute(search);
    }

    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.launcher, menu);
        return true;
    }

    private class LaunchRead extends RssTask.RssListTask {
        @Override
        public void onPostExecute(RssList result) {
            RssFeed show = result.getRssFeeds().get(0);
            //console.setText(show.toString());
            parseIntoCards(result);
        }
    }

    private void parseIntoCards(RssList resultList) {
        ArrayList<RssFeed> feeds = resultList.getRssFeeds();
        for (int i = 0; i < feeds.size(); i++) {
            RssFeed current = feeds.get(i);
            final MyPlayCard card = new MyPlayCard(current.getTitle(), current.getDescription(), "#fffdf0", "#ff9d4c", true, true);
            card.setFeed(current);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RssFeed toLaunch = card.getFeed();
                    Intent rssIntent = new Intent(LauncherActivity.this, FeedActivity.class);
                    rssIntent.putExtra("FEED", toLaunch.getUrl());
                    startActivity(rssIntent);
                }
            });
            cardUI.addCard(card);
        }
        cardUI.refresh();
    }

    /*
    public void getFeeds(View view) {
        //String query = queryBox.getText().toString();
        new LaunchRead().execute("Intel");
    }
    */
    
}
