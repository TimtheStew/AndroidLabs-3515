package edu.temple.lab09stockportfolio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;

import org.json.JSONObject;

import edu.temple.lab09stockportfolio.stock.StockContent;
import edu.temple.lab09stockportfolio.stock.StockPollService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * An activity representing a list of Stocks.
 */
public class StockListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode
     */
    private boolean mTwoPane;
    private String currSearchSymbol;
    FileWriter portfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a a dialog for a user to enter a stock symbol
                AlertDialog.Builder builder = new AlertDialog.Builder(StockListActivity.this);
                builder.setTitle("Stock Symbol Search: ");

                // Set up the input
                final EditText input = new EditText(StockListActivity.this);
                // Entry should be in all caps
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currSearchSymbol = input.getText().toString();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                // Actually show the dialog
                builder.show();
            }
        });

        if (findViewById(R.id.stock_detail_container) != null) {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.stock_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        // check to see if the portfolio file exists, make it if it doesn't
        String files[] = fileList();
        if (files.length == 0 ){
            File file = new File(StockListActivity.this.getFilesDir(), "portfolio.txt");
        }

        // If it's already running this will just get queue'd up
        Intent mServiceIntent = new Intent(StockListActivity.this, StockPollService.class);
        mServiceIntent.setAction(StockPollService.ACTION_START_POLLING);
        StockListActivity.this.startService(mServiceIntent);
    }

    /**
     * Here we check if a symbol is valid. If it is we write it to the file, then add it to
     * a data structure? If not we'll display an invalid toast or something.
     */
    private void checkSymbolValidity(String symbol){
        try {
            URL queryURL = new URL("http://dev.markitondemand.com/MODApis/Api/v2/Quote/json/?" +
                    "symbol=" + currSearchSymbol);
            BufferedReader in = new BufferedReader(new InputStreamReader(queryURL.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null){
                sb.append(line);
            }
            in.close();
            JSONObject json = new JSONObject(sb.toString());
            if (json.has("Message")){
                // invalid stock
                return;
            } else {
                // append the stock info to the file
                portfolio = new FileWriter("portfolio.txt", true);
                portfolio.write(currSearchSymbol + " " + json.get("Name") + " " + json.get("LastPrice"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, StockContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final StockListActivity mParentActivity;
        private final List<StockContent.StockItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StockContent.StockItem stock = (StockContent.StockItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(StockDetailFragment.ARG_STOCK_SYMBOL, stock.symbol);
                    StockDetailFragment fragment = new StockDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.stock_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StockDetailActivity.class);
                    intent.putExtra(StockDetailFragment.ARG_STOCK_SYMBOL, stock.symbol);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(StockListActivity parent,
                                      List<StockContent.StockItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.stock_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).symbol);
            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
