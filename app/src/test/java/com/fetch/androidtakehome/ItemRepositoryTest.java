//package com.fetch.androidtakehome;
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.Observer;
//
//import com.fetch.androidtakehome.Models.Item;
//import com.fetch.androidtakehome.Network.APIService;
//import com.fetch.androidtakehome.Repository.ItemRepository;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class ItemRepositoryTest {
//
//    @Mock
//    private APIService apiService;
//
//    private ItemRepository itemRepository;
//
//    @Rule
//    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        itemRepository = new ItemRepository(apiService);  // Use the constructor with the mocked APIService
//    }
//    private Item createItem(Integer id, Integer listId, String name) {
//        Item item = new Item();
//        item.setId(id);
//        item.setListId(listId);
//        item.setName(name);
//        return item;
//    }
//
//    private void mockApiServiceResponse(List<Item> mockResponse) {
//        // Create a mock call and set up the response
//        Call<List<Item>> mockCall = (Call<List<Item>>) Mockito.mock(Call.class);
//        when(apiService.getItems()).thenReturn(mockCall);
//        doAnswer(invocation -> {
//            Callback<List<Item>> callback = invocation.getArgument(0);
//            callback.onResponse(mockCall, Response.success(mockResponse));
//            return null;
//        }).when(mockCall).enqueue(any(Callback.class));
//    }
//
//    private List<Item> ItemsRepositorySimulator() {
//        // Set up an observer to capture the result
//        Observer<List<Item>> observer = Mockito.mock(Observer.class);
//        LiveData<List<Item>> liveData = itemRepository.getItems();
//        liveData.observeForever(observer);
//
//        // Verify the result
//        ArgumentCaptor<List<Item>> argumentCaptor = ArgumentCaptor.forClass(List.class);
//        verify(observer).onChanged(argumentCaptor.capture());
//        return argumentCaptor.getValue();
//    }
//
//    @Test
//    public void testGetItems() {
//
//        Item item1 = createItem(1,1,"Item 1");
//        Item item2 = createItem(2,2,"Item 2");
//        Item item3 = createItem(3,3,"Item 3");
//        mockApiServiceResponse(Arrays.asList(item1, item2, item3));
//        List<Item> result = ItemsRepositorySimulator();
//
//        // Assert the result
//
//        assertEquals("Item 1", result.get(0).getName());
//        assertEquals("Item 2", result.get(1).getName());
//        assertEquals("Item 3", result.get(2).getName());
//        System.out.println("Test 1 Passed!");
//
//
//    }
//
//    //Test 2: Add Null and Empty Name Items and Check
//    @Test
//    public void testGetNullNameItems() {
//        Item item1 = createItem(1,1,null);
//        Item item2 = createItem(2,2,"Item 2");
//        Item item3 = createItem(3,3,"");
//        Item item4 = createItem(3,3,"        ");
//        mockApiServiceResponse(Arrays.asList(item1, item2, item3));
//        List<Item> result = ItemsRepositorySimulator();
//
//
//        assertEquals(1, result.size());
//        System.out.println("Test 2 Passed!");
//
//    }
//
//    //Test 2: Add Null Items and Check
//    @Test
//    public void testFilterOutNullIdsAndListIds() {
//        // Create items with various combinations of null and non-null values
//        Item item1 = createItem(null, null, "Item 1");
//        Item item2 = createItem(2, 2, "Item 2");
//        Item item3 = createItem(3, null, "Item 3");
//        Item item4 = createItem(null, 5, "Item 4");
//        Item item5 = createItem(6, 6, "Item 6");
//        Item item6 = createItem(7, 7, null);  // Also testing for null name
//
//        // Mock the API service response
//        mockApiServiceResponse(Arrays.asList(item1, item2, null, item3, item4, item5, item6));
//        List<Item> result = ItemsRepositorySimulator();
//
//        // Assert the result
//        assertEquals(2, result.size()); // Only item2 and item5 should be valid
//        assertEquals("Item 2", result.get(0).getName());
//        assertEquals("Item 6", result.get(1).getName());
//        System.out.println("Test 3 Passed!");
//    }
//
//
//
//}
