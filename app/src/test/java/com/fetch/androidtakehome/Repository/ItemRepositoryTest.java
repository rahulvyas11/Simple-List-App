package com.fetch.androidtakehome.Repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.fetch.androidtakehome.Model.Item;
import com.fetch.androidtakehome.Network.APIService;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemRepositoryTest {

    private ItemRepository itemRepository;
    private MockWebServer mockWebServer;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        APIService apiService = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);

        itemRepository = new ItemRepository(apiService);
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }


    private @NonNull CountDownLatch getCountDownLatch() {
        LiveData<List<Item>> liveData = itemRepository.getItems();
        CountDownLatch latch = new CountDownLatch(1);
        liveData.observeForever(items -> {

            assertNotNull(items);
            assertEquals(2, items.size());
            assertEquals("Item 1", items.get(0).getName());
            latch.countDown();
        });
        return latch;
    }

    @Test
    public void testGetItems_successfulResponse() throws InterruptedException {
        String mockResponse = "[{\"id\":1,\"listId\":1,\"name\":\"Item 1\"}, {\"id\":2,\"listId\":1,\"name\":\"Item 2\"}]";
        mockWebServer.enqueue(new MockResponse().setBody(mockResponse).setResponseCode(200));

        CountDownLatch latch = getCountDownLatch();

        if (!latch.await(2, TimeUnit.SECONDS)) {
            fail("Latch timed out");
        }
    }

    @Test
    public void testGetItems_failureResponse() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));

        LiveData<List<Item>> liveData = itemRepository.getItems();
        CountDownLatch latch = new CountDownLatch(1);
        liveData.observeForever(items -> {
            assertNull(items);
            latch.countDown();
        });

        if (!latch.await(2, TimeUnit.SECONDS)) {
            fail("Latch timed out");
        }

    }

    @Test
    public void testGetItems_networkFailure() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        LiveData<List<Item>> liveData = itemRepository.getItems();
        CountDownLatch latch = new CountDownLatch(1);
        liveData.observeForever(items -> {
            assertNull(items);
            latch.countDown();
        });

        if (!latch.await(2, TimeUnit.SECONDS)) {
            fail("Latch timed out");
        }
    }
}
