package com.fetch.androidtakehome.Network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.fetch.androidtakehome.Model.Item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIServiceTest {

    private MockWebServer mockWebServer;
    private APIService apiService;

    @Before
    public void setUp() throws Exception {
        // Initialize MockWebServer to mock HTTP responses
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Set up Retrofit with MockWebServer's URL and GsonConverterFactory
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(APIService.class);
    }

    // Clean up the test environment after each test
    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    // Test successful response from the API
    @Test
    public void testGetItemsSuccess() throws Exception {
        String mockResponse = "[{\"id\":1,\"listId\":1,\"name\":\"Item 1\"},{\"id\":2,\"listId\":1,\"name\":\"Item 2\"},"
                + "{\"id\":3,\"listId\":2,\"name\":\"Item 3\"},{\"id\":4,\"listId\":2,\"name\":\"Item 4\"},"
                + "{\"id\":5,\"listId\":3,\"name\":\"Item 5\"},{\"id\":8,\"listId\":4,\"name\":\"Item 6\"}]";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockResponse));

        Call<List<Item>> call = apiService.getItems();
        Response<List<Item>> response = call.execute();

        assertTrue(response.isSuccessful());
        List<Item> items = response.body();
        assertNotNull(items);
        assertEquals(6, items.size());
        assertEquals(Integer.valueOf(1), items.get(0).getId());
        assertEquals(Integer.valueOf(1), items.get(0).getListId());
        assertEquals("Item 1", items.get(0).getName());
        assertEquals(Integer.valueOf(2), items.get(1).getId());
        assertEquals(Integer.valueOf(1), items.get(1).getListId());
        assertEquals("Item 2", items.get(1).getName());
        assertEquals(Integer.valueOf(3), items.get(2).getId());
        assertEquals(Integer.valueOf(2), items.get(2).getListId());
        assertEquals("Item 3", items.get(2).getName());
        assertEquals(Integer.valueOf(4), items.get(3).getId());
        assertEquals(Integer.valueOf(2), items.get(3).getListId());
        assertEquals("Item 4", items.get(3).getName());
        assertEquals(Integer.valueOf(5), items.get(4).getId());
        assertEquals(Integer.valueOf(3), items.get(4).getListId());
        assertEquals("Item 5", items.get(4).getName());
        assertEquals(Integer.valueOf(8), items.get(5).getId());
        assertEquals(Integer.valueOf(4), items.get(5).getListId());
        assertEquals("Item 6", items.get(5).getName());
    }

    // Test empty response from the API
    @Test
    public void testGetItemsEmptyResponse() throws Exception {
        String mockResponse = "[]";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockResponse));

        Call<List<Item>> call = apiService.getItems();
        Response<List<Item>> response = call.execute();

        assertTrue(response.isSuccessful());
        List<Item> items = response.body();
        assertNotNull(items);
        assertTrue(items.isEmpty());
    }

    // Test error response from the API
    @Test
    public void testGetItemsErrorResponse() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error"));

        Call<List<Item>> call = apiService.getItems();
        Response<List<Item>> response = call.execute();

        assertFalse(response.isSuccessful());
        assertNull(response.body());
    }
}
