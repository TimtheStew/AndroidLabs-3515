package edu.temple.lab09stockportfolio.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing stock content
 */
public class StockContent {

    /**
     * An array of sample (stock) items.
     */
    public static final List<StockItem> ITEMS = new ArrayList<StockItem>();

    /**
     * A map of stock items, by ID.
     */
    public static final Map<String, StockItem> ITEM_MAP = new HashMap<String, StockItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createStockItem(i));
        }
    }

    private static void addItem(StockItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.symbol, item);
    }

    private static StockItem createStockItem(int position) {
        return new StockItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A stock item representing a piece of content.
     */
    public static class StockItem {
        public final String symbol;
        public final String content;
        public final String details;

        /**
         * Here we should do the read from the file.
         * @param symbol
         * @param content
         * @param details
         */
        public StockItem(String symbol, String content, String details) {

            this.symbol = symbol;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
