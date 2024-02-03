package com.vivekishere.foodapp.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.vivekishere.foodapp.Pojo.MealsItem;

@Database(entities = MealsItem.class , version = 1 , exportSchema = false )
@TypeConverters(MealTypeConverter.class)
public abstract class MealDatabase extends RoomDatabase {
    public static final String DATABASE_NAME ="MealDB";
    private static MealDatabase mealDatabase;
   public abstract MealDao mealDao();
    public static synchronized MealDatabase getInstance(Context context){
        if (mealDatabase==null){
            mealDatabase = Room.databaseBuilder(context ,MealDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return mealDatabase;
    }
}
