package projectbabylon.readme;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.androguide.cardsui.generator.MyPlayCard;
import com.fima.cardsui.views.CardUI;

import java.util.ArrayList;

public class StartActivity extends Activity implements SearchView.OnQueryTextListener{

    private CardUI cardUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        cardUI = (CardUI) findViewById(R.id.cardsview);
        ArrayList<String> examples = new ArrayList<String>();
        examples.add("Google");
        examples.add("Apple");
        examples.add("Music");
        examples.add("ESPN");
        examples.add("Tennis");
        for (int i = 0; i < examples.size(); i++) {
            final MyPlayCard card = new MyPlayCard(examples.get(i), "", "#ffbf3f", "#ff5339", true, true);
            card.setKeyWord(examples.get(i));
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key = card.getKeyWord();
                    Intent listIntent = new Intent(StartActivity.this, LauncherActivity.class);
                    listIntent.putExtra("SEARCH", key);
                    startActivity(listIntent);
                }
            });
            cardUI.addCard(card);
        }
        cardUI.refresh();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent listIntent = new Intent(StartActivity.this, LauncherActivity.class);
        listIntent.putExtra("SEARCH", query);
        startActivity(listIntent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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
