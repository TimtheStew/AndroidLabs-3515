package edu.temple.lab09stockportfolio.stock;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


/**
 * A StockPollingService, that repeatedly checks the current prices of stocks listed in a file
 */
public class StockPollService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_START_POLLING = "edu.temple.lab09stockportfolio.stock.action.START_POLLING";

    public StockPollService() {
        super("StockPollService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START_POLLING.equals(action)) {
                handleActionStartPolling();
            }
        }
    }

    /**
     * Here we start polling. In a loop, we wait a minute, read through the file,
     * check the price of each symbol listed, and update them in the file
     */
    private void handleActionStartPolling() {
        // open reading and writing streams to the portfolio file.
        FileOutputStream portfolioOut;
        try {
            portfolioOut = openFileOutput("portfolio.txt", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        FileInputStream portfolioIn;
        try {
            portfolioIn = openFileInput("portfolio.txt");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        while(true){
            // sleep for a minute
            SystemClock.sleep(1 * 60 * 1000);

        }
    }
}
