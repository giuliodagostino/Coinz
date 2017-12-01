package com.coinz.cryptomarketwatcher;

import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ApiRequests {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Coin.class, new CoinObjectDeserializer())
            .create();

    public static GsonGetRequest<ArrayList<Coin>> getCoins(
                    @NonNull
                    final Response.Listener<ArrayList<Coin>> listener,
                    @NonNull
                    final Response.ErrorListener errorListener) {

        final String url = "https://api.coinmarketcap.com/v1/ticker/";

        return new GsonGetRequest<>(
                        url,
                        new TypeToken<ArrayList<Coin>>() {
                        }.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }

    public static GsonGetRequest<ArrayList<Coin>> getMoreCoins(
            @NonNull
            final Response.Listener<ArrayList<Coin>> listener,
            @NonNull
            final Response.ErrorListener errorListener, int start, int limit) {

        final String url = "https://api.coinmarketcap.com/v1/ticker/?start=" + start + "&limit=" + limit;

        return new GsonGetRequest<>(
                url,
                new TypeToken<ArrayList<Coin>>() {
                }.getType(),
                gson,
                listener,
                errorListener
        );
    }

    public static GsonGetRequest<ArrayList<Coin>> getTop10(
            @NonNull
            final Response.Listener<ArrayList<Coin>> listener,
            @NonNull
            final Response.ErrorListener errorListener) {

        final String url = "https://api.coinmarketcap.com/v1/ticker/?limit=10";

        return new GsonGetRequest<>(
                url,
                new TypeToken<ArrayList<Coin>>() {
                }.getType(),
                gson,
                listener,
                errorListener
        );
    }

}
