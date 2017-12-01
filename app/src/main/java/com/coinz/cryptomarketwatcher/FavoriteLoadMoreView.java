package com.coinz.cryptomarketwatcher;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mindorks.placeholderview.InfinitePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.infinite.LoadMore;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.load_more_view)
public class FavoriteLoadMoreView {

    private static final String sTAG = "tagTwo";

    private InfinitePlaceHolderView mLoadMoreView;
    private List<Coin> coins;
    private Context context;

    private int start = 100;

    public FavoriteLoadMoreView(InfinitePlaceHolderView loadMoreView, List<Coin> coins, Context context) {
        this.mLoadMoreView = loadMoreView;
        this.coins = coins;
        this.context = context;
    }

    @LoadMore
    private void onLoadMore(){
        int limit = 100;
        performRequest(start, limit);
        start = start + limit;
    }

    private void performRequest(int start, int limit) {

        final GsonGetRequest<ArrayList<Coin>> gsonGetRequest =
                ApiRequests.getMoreCoins
                        (
                                new Response.Listener<ArrayList<Coin>>() {
                                    @Override
                                    public void onResponse(ArrayList<Coin> coins) {

                                        for(Coin coin : coins){
                                            if(coins.size() <= 0){
                                                mLoadMoreView.noMoreToLoad();
                                                break;
                                            }
                                            if (AppDatabase.getAppDatabase(mLoadMoreView.getContext()).simpleDao().findBySymbol(coin.getSymbol()) != null) {
                                                mLoadMoreView.addView(new CoinViewHolder(mLoadMoreView.getContext(), coin, AppDatabase.getAppDatabase(mLoadMoreView.getContext())));
                                            }
                                        }
                                        mLoadMoreView.loadingDone();
                                    }
                                }
                                ,
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // Deal with the error here
                                        /*onApiError();*/
                                    }
                                }, start, limit
                        );

        App.addRequest(gsonGetRequest, sTAG);
    }
}
