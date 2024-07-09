package com.fetch.androidtakehome.Adapter;

import com.fetch.androidtakehome.Model.Item;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import static org.junit.Assert.assertEquals;

public class ListIDAdapterTest {

    private ListIDAdapter adapter;
    private List<Item> items;

    @Before
    public void setUp() {
        items = Arrays.asList(
                new Item(1, 1, "Item 1"),
                new Item(2, 1, "Item 2"),
                new Item(3, 2, "Item 3"),
                new Item(4, 2, "Item 4"),
                new Item(5, 3, "Item 5"),
                new Item(8, 4, "Item 5")
        );

        adapter = new ListIDAdapter(items);
    }

    @Test
    public void testNumLists() {
        assertEquals(4, adapter.getItemCount());
    }

    @Test
    public void testGroupingAndSorting() {
        List<Map.Entry<Integer, List<Item>>> sortedGroupedItems = adapter.getGroupedItemsForTesting();

        assertEquals(4, sortedGroupedItems.size());
        assertEquals(1, (int) sortedGroupedItems.get(0).getKey());
        assertEquals(2, (int) sortedGroupedItems.get(1).getKey());
        assertEquals(3, (int) sortedGroupedItems.get(2).getKey());
        assertEquals(4, (int) sortedGroupedItems.get(3).getKey());

        assertEquals(2, sortedGroupedItems.get(0).getValue().size()); // Two items with listId 1
        assertEquals(2, sortedGroupedItems.get(1).getValue().size()); // Two items with listId 2
        assertEquals(1, sortedGroupedItems.get(2).getValue().size()); // One item with listId 3
        assertEquals(1, sortedGroupedItems.get(3).getValue().size());
    }
}
