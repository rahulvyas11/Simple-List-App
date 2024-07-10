package com.fetch.androidtakehome.Network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientTest {

    @Test
    public void testRetrofitInstance() throws NoSuchFieldException, IllegalAccessException {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        assertNotNull(retrofit);

        Field baseUrlField = APIClient.class.getDeclaredField("BASE_URL");
        baseUrlField.setAccessible(true);
        String baseUrl = (String) baseUrlField.get(null);

        assertEquals(baseUrl, retrofit.baseUrl().toString());

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

