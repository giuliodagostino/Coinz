package com.coinz.cryptomarketwatcher;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class CoinDetailActivity extends AppCompatActivity {

    private static Coin coin;

    private TextView name;
    private TextView symbol;
    private TextView rank;
    private TextView priceUsd;
    private TextView priceBtc;
    private TextView volumeUsd24h;
    private TextView marketCapUsd;
    private TextView availableSupply;
    private TextView totalSupply;
    private TextView percentChange1h;
    private TextView percentChange24h;
    private TextView percentChange7d;
    private TextView buyBitcoin;

    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detail);

        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId(getString(R.string.admob_intertisial_id));

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequestBanner = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequestBanner);

        initView();
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void initView(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null) {
            mToolbar.setTitle(coin.getName());
            setSupportActionBar(mToolbar);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        name = (TextView)findViewById(R.id.name);
        symbol = (TextView)findViewById(R.id.symbol);
        rank = (TextView)findViewById(R.id.rank);
        priceUsd = (TextView)findViewById(R.id.priceUsd);
        priceBtc = (TextView)findViewById(R.id.priceBtc);
        volumeUsd24h = (TextView)findViewById(R.id.volumeUsd24h);
        marketCapUsd = (TextView)findViewById(R.id.marketCapUsd);
        availableSupply = (TextView)findViewById(R.id.availableSupply);
        totalSupply = (TextView)findViewById(R.id.totalSupply);
        percentChange1h = (TextView)findViewById(R.id.percentChange1h);
        percentChange24h = (TextView)findViewById(R.id.percentChange24h);
        percentChange7d = (TextView)findViewById(R.id.percentChange7d);
        buyBitcoin = (TextView)findViewById(R.id.buyBitcoin);

        if (coin.getName() != null){
            name.setText(coin.getName());
        }else {
            name.setText("-");
        }

        if (coin.getSymbol() != null){
            symbol.setText(coin.getSymbol());
        }else {
            symbol.setText("-");
        }

        if (coin.getRank() != null){
            rank.setText(coin.getRank());
        }else {
            rank.setText("-");
        }

        if (coin.getPriceUsd() != null){
            priceUsd.setText(coin.getPriceUsd());
        }else {
            priceUsd.setText("-");
        }

        if (coin.getPriceBtc() != null){
            priceBtc.setText(coin.getPriceBtc());
        }else {
            priceBtc.setText("-");
        }

        if (coin.getVolumeUsd24h() != null){
            volumeUsd24h.setText(coin.getVolumeUsd24h());
        }else {
            volumeUsd24h.setText("-");
        }

        if (coin.getMarketCapUsd() != null){
            marketCapUsd.setText(coin.getMarketCapUsd());
        }else {
            marketCapUsd.setText("-");
        }

        if (coin.getAvailableSupply() != null){
            availableSupply.setText(coin.getAvailableSupply());
        }else {
            availableSupply.setText("-");
        }

        if (coin.getTotalSupply() != null){
            totalSupply.setText(coin.getTotalSupply());
        }else {
            totalSupply.setText("-");
        }

        if (coin.getPercentChange1h() != null){

            if (Double.valueOf(coin.getPercentChange1h()) > 0){
                percentChange1h.setTextColor(Color.parseColor("#5cbb26"));
                percentChange1h.setText("▲ " + coin.getPercentChange1h());
            }else {
                percentChange1h.setTextColor(Color.parseColor("#c95e22"));
                percentChange1h.setText("▼ " + coin.getPercentChange1h());
            }
        }else {
            percentChange1h.setText("--");
        }

        if (coin.getPercentChange24h() != null){
            if (Double.valueOf(coin.getPercentChange24h()) > 0){
                percentChange24h.setTextColor(Color.parseColor("#5cbb26"));
                percentChange24h.setText("▲ " + coin.getPercentChange24h());
            }else {
                percentChange24h.setTextColor(Color.parseColor("#c95e22"));
                percentChange24h.setText("▼ " + coin.getPercentChange24h());
            }
        }else {
            percentChange24h.setText("--");
        }

        if (coin.getPercentChange7d() != null){

            if (Double.valueOf(coin.getPercentChange7d()) > 0){
                percentChange7d.setTextColor(Color.parseColor("#5cbb26"));
                percentChange7d.setText("▲ " + coin.getPercentChange7d());
            }else {
                percentChange7d.setTextColor(Color.parseColor("#c95e22"));
                percentChange7d.setText("▼ " + coin.getPercentChange7d());
            }
        }else {
            percentChange7d.setText("--");
        }

        buyBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://localbitcoins.com/?ch=iyb6"));
                startActivity(browserIntent);
            }
        });
    }

    public static void setCoin(Coin coin) {
        CoinDetailActivity.coin = coin;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
