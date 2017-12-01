package com.coinz.cryptomarketwatcher;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mindorks.placeholderview.InfinitePlaceHolderView;

import java.util.ArrayList;

public class AllFragment extends BaseMainFragment {

    private InfinitePlaceHolderView mLoadMoreView;
    private ProgressDialog mProgressDialog;

    private static final String sTAG = "tagTwo";
    private AppDatabase appDatabase;

    public static AllFragment newInstance() {
        return new AllFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.appDatabase = AppDatabase.getAppDatabase(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_all, container, false);

        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle("All");
        initToolbarNav(mToolbar);

        mLoadMoreView = (InfinitePlaceHolderView) view.findViewById(R.id.mLoadMoreView);
        performRequest();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void performRequest() {
        showProgressDialog();
        final GsonGetRequest<ArrayList<Coin>> gsonGetRequest =
                ApiRequests.getCoins
                        (
                                new Response.Listener<ArrayList<Coin>>() {
                                    @Override
                                    public void onResponse(ArrayList<Coin> coins) {
                                        hideProgressDialog();

                                        for(Coin coin : coins){
                                            mLoadMoreView.addView(new CoinViewHolder(getContext(), coin, appDatabase));
                                        }

                                        mLoadMoreView.setLoadMoreResolver(new LoadMoreView(mLoadMoreView, coins, getContext()));
                                    }
                                }
                                ,
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // Deal with the error here
                                        onApiError();
                                    }
                                }
                        );

        App.addRequest(gsonGetRequest, sTAG);
    }

    private void onApiError() {
        hideProgressDialog();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        App.cancelAllRequests(sTAG);

        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppDatabase.destroyInstance();
    }
}

