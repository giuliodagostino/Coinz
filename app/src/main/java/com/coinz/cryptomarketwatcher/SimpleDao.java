package com.coinz.cryptomarketwatcher;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SimpleDao {

    @Query("SELECT COUNT(*) from favorite")
    int count();

    @Delete
    void delete(Favorite favorite);

    @Query("SELECT * FROM favorite where symbol LIKE :symbol")
    Favorite findBySymbol(String symbol);

    @Query("SELECT * FROM favorite")
    List<Favorite> getAll();

    @Insert
    void insertAll(Favorite... favorites);
}
