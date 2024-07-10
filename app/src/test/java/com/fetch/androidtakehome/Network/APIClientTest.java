package com.fetch.androidtakehome.Network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Unit tests for the APIClient class.
 * These tests ensure that the Retrofit instance is correctly configured.
 */
public class APIClientTest {

    // Test to ensure the Retrofit instance is not null
    @Test
    public void testRetrofitInstance() throws NoSuchFieldException, IllegalAccessException {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        assertNotNull(retrofit);

        // Use reflection to access the BASE_URL field in APIClient
        Field baseUrlField = APIClient.class.getDeclaredField("BASE_URL");
        baseUrlField.setAccessible(true);
        String baseUrl = (String) baseUrlField.get(null);

        // Verify that the base URL of the Retrofit instance matches the expected base URL
        assertEquals(baseUrl, retrofit.baseUrl().toString());

        // Verify that the Retrofit instance contains a GsonConverterFactory
        List<?> converterFactories = retrofit.converterFactories();
        boolean hasGsonConverterFactory = false;
        for (Object factory : converterFactories) {
            if (factory instanceof GsonConverterFactory) {
                hasGsonConverterFactory = true;
                break;
            }
        }
        assertTrue("GsonConverterFactory should be present", hasGsonConverterFactory);
    }
}

