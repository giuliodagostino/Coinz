package com.coinz.cryptomarketwatcher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@Layout(R.layout.item_coin)
@NonReusable
public class CoinViewHolder{

    @View(R.id.imageCoin) private ImageView imageCoin;
    @View(R.id.favorite) private ImageView favorite;
    @View(R.id.namesymbole) private TextView namesymbole;
    @View(R.id.price) private TextView price;
    @View(R.id.marketcap) private TextView marketcap;
    @View(R.id.change) private TextView change;

    private AppDatabase mDb;
    private Coin coin;
    private Context mContext;

    public CoinViewHolder(Context context, Coin coin, AppDatabase db) {
        this.coin = coin;
        this.mContext = context;
        this.mDb = db;
    }

    @Resolve
    private void onResolved() {
        namesymbole.setText(coin.getRank() + ". " + coin.getName() + " (" + coin.getSymbol() + ")");
        price.setText("$" + this.coin.getPriceUsd());
        marketcap.setText("$" + this.coin.getMarketCapUsd());
        if (coin.getPercentChange1h() == null) {
            change.setText("--");
        } else if (Double.valueOf(this.coin.getPercentChange1h()).doubleValue() > 0.0d) {
            change.setTextColor(Color.parseColor("#5cbb26"));
            change.setText("+" + this.coin.getPercentChange1h());
        } else {
            change.setTextColor(Color.parseColor("#c95e22"));
            change.setText(this.coin.getPercentChange1h());
        }
        Glide.with(this.mContext).load("http://files.coinmarketcap.com.s3-website-us-east-1.amazonaws.com/static/img/coins/200x200/" + coin.getName().toLowerCase() + ".png").into(imageCoin);
        if (mDb.simpleDao().findBySymbol(coin.getSymbol()) != null) {
            favorite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.liked));
        }

        favorite.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                if (mDb.simpleDao().findBySymbol(coin.getSymbol()) != null) {
                    mDb.simpleDao().delete(mDb.simpleDao().findBySymbol(coin.getSymbol()));
                    favorite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.filled));
                }else {

                    Favorite fav = new Favorite();
                    fav.setSymbol(coin.getSymbol());
                    mDb.simpleDao().insertAll(fav);
                    favorite.setImageDrawable(ContextCompat.getDrawable(CoinViewHolder.this.mContext, R.drawable.liked));
                }

            }
        });
    }

    @Click(R.id.itemCoinClick)
    private void itemClick() {
        CoinDetailActivity.setCoin(this.coin);
        this.mContext.startActivity(new Intent(this.mContext, CoinDetailActivity.class));
    }
}
