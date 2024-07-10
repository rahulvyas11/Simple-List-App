package com.fetch.androidtakehome.ViewModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fetch.androidtakehome.Model.Item;
import com.fetch.androidtakehome.Repository.ItemRepositoryInterface;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Unit tests for the ItemViewModel class.
 * These tests ensure that the ViewModel correctly fetches and processes data from the repository.
 */
public class ItemViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private ItemViewModel itemViewModel;
    @Mock
    private ItemRepositoryInterface itemRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MutableLiveData<List<Item>> liveData = new MutableLiveData<>();
        when(itemRepository.getItems()).thenReturn(liveData);

        itemViewModel = new ItemViewModel(itemRepository);
    }

    // Test to ensure successful API response is handled correctly
    @Test
    public void testGetItems_successfulResponse() throws InterruptedException {

        List<Item> mockItems = Arrays.asList(
                new Item(1, 1, "Item 1"),
                new Item(2, 1, "Item 2")
        );
        MutableLiveData<List<Item>> liveData = (MutableLiveData<List<Item>>) itemRepository.getItems();
        liveData.setValue(mockItems);

        LiveData<List<Item>> observedItems = itemViewModel.getItems();
        CountDownLatch latch = new CountDownLatch(1);
        observedItems.observeForever(items -> {
            if (items != null && items.size() == 2) {
                latch.countDown();
            }
        });

        if (!latch.await(2, TimeUnit.SECONDS)) {
            fail("Latch timed out");
        }

        List<Item> items = observedItems.getValue();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals("Item 1", items.get(0).getName());
        assertEquals("Item 2", items.get(1).getName());
    }

    // Test to check functionality when itemrepository data is null
    @Test
    public void testGetItems_emptyResponse() throws InterruptedException {

        MutableLiveData<List<Item>> liveData = (MutableLiveData<List<Item>>) itemRepository.getItems();
        liveData.setValue(Collections.emptyList());

        LiveData<List<Item>> observedItems = itemViewModel.getItems();
        CountDownLatch latch = new CountDownLatch(1);
        observedItems.observeForever(items -> {

            if (items != null && items.isEmpty()) {
                latch.countDown();
            }
        });

        if (!latch.await(2, TimeUnit.SECONDS)) {
            fail("Latch timed out");
        }

        List<Item> items = observedItems.getValue();
        assertNotNull(items);
        assertTrue(items.isEmpty());
    }

    // Test to check filteration of null items
    @Test
    public void testGetItems_nullItemsFiltered() throws InterruptedException {
        List<Item> mockItems = Arrays.asList(
                new Item(1, 1, "Item 1"),
                null,
                new Item(2, 1, null),
                new Item(3, 1, " "),
                null,
                new Item(4, 2, "                "),
                new Item(null, 4, "Item 5"),
                new Item(8, null, "Item 8"),
                new Item(6, 8, "Item 6"),
                new Item(12, 4, null),
                new Item(null, null, null),
                null
        );
        MutableLiveData<List<Item>> liveData = (MutableLiveData<List<Item>>) itemRepository.getItems();
        liveData.setValue(mockItems);

        LiveData<List<Item>> observedItems = itemViewModel.getItems();
        CountDownLatch latch = new CountDownLatch(1);
        observedItems.observeForever(items -> {

            if (items != null && items.size() == 2) {
                latch.countDown();
            }
        });

        if (!latch.await(2, TimeUnit.SECONDS)) {
            fail("Latch timed out");
        }

        List<Item> items = observedItems.getValue();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals("Item 1", items.get(0).getName());
        assertEquals("Item 6", items.get(1).getName());
    }

    // Test to check null itemrepository
    @Test
    public void testGetItems_nullResponse() throws InterruptedException {
        MutableLiveData<List<Item>> liveData = (MutableLiveData<List<Item>>) itemRepository.getItems();
        liveData.setValue(null);
        LiveData<List<Item>> observedItems = itemViewModel.getItems();
        CountDownLatch latch = new CountDownLatch(1);
        observedItems.observeForever(items -> {
            if (items.isEmpty()) {
                latch.countDown();
            }
        });

        if (!latch.await(2, TimeUnit.SECONDS)) {
            fail("Latch timed out");
        }
        List<Item> items = observedItems.getValue();
        assertTrue(items.isEmpty());
    }

}
