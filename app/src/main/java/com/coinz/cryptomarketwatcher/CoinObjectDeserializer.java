package com.coinz.cryptomarketwatcher;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class CoinObjectDeserializer implements JsonDeserializer<Coin>{

    @Override
    public Coin deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final Coin coin = new Coin();
        final JsonObject jsonObject = json.getAsJsonObject();

        coin.setId(jsonObject.get("id").getAsString());
        coin.setName(jsonObject.get("name").getAsString());
        coin.setSymbol(jsonObject.get("symbol").getAsString());
        coin.setRank(jsonObject.get("rank").getAsString());
        coin.setPriceUsd(!jsonObject.get("price_usd").isJsonNull() ? jsonObject.get("price_usd").getAsString() : null);
        coin.setPriceBtc(!jsonObject.get("price_btc").isJsonNull() ? jsonObject.get("price_btc").getAsString() : null);
        coin.setVolumeUsd24h(!jsonObject.get("24h_volume_usd").isJsonNull() ? jsonObject.get("24h_volume_usd").getAsString() : null);
        coin.setMarketCapUsd(!jsonObject.get("market_cap_usd").isJsonNull() ? jsonObject.get("market_cap_usd").getAsString() : null);
        coin.setAvailableSupply(!jsonObject.get("available_supply").isJsonNull() ? jsonObject.get("available_supply").getAsString() : null);
        coin.setTotalSupply(!jsonObject.get("total_supply").isJsonNull() ? jsonObject.get("total_supply").getAsString() : null);
        coin.setPercentChange1h(!jsonObject.get("percent_change_1h").isJsonNull() ? jsonObject.get("percent_change_1h").getAsString() : null);
        coin.setPercentChange24h(!jsonObject.get("percent_change_24h").isJsonNull() ? jsonObject.get("percent_change_24h").getAsString() : null);
        coin.setPercentChange7d(!jsonObject.get("percent_change_7d").isJsonNull() ? jsonObject.get("percent_change_7d").getAsString() : null);
        coin.setLastUpdated(!jsonObject.get("last_updated").isJsonNull() ? jsonObject.get("last_updated").getAsString() : null);

        return coin;
    }

}
