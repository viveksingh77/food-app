package com.vivekishere.foodapp.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.vivekishere.foodapp.Pojo.MealsItem;

import java.util.List;

@Dao

public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(MealsItem mealsItem);
    @Delete
    void Delete(MealsItem mealsItem);

    @Query("SELECT * FROM mealinformation")
    LiveData<List<MealsItem>> getAllMeals();

}
