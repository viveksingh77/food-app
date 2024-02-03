package com.vivekishere.foodapp.ViewModal;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vivekishere.foodapp.Pojo.MealResponse;
import com.vivekishere.foodapp.Pojo.MealsItem;
import com.vivekishere.foodapp.Retrofit.RetrofitInstance;
import com.vivekishere.foodapp.Room.MealDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealViewModal extends ViewModel {
    MealDatabase mealDatabase;

    private MutableLiveData<MealsItem> mealitem = new MutableLiveData<>();
    public LiveData<List<MealsItem>> favouriteLiveData;
    private static final String TAG = "MealViewModal";

    public void getMealInfoData(String id) {
        RetrofitInstance.getInstance().retrofitApi()
                .getMealInfo(id)
                .enqueue(new Callback<MealResponse>() {
                    @Override
                    public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                        MealsItem meal = response.body().getMeals().get(0);
                        if (response.isSuccessful() && response.body() != null) {
                            mealitem.setValue(meal);
                        } else {
                            Log.d(TAG, "onResponse: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MealResponse> call, Throwable t) {

                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
    public LiveData<MealsItem> ObserveMealData(){
        return mealitem;
    };

    public void InsertMeal(Context context ,MealsItem mealsItem){
        mealDatabase = MealDatabase.getInstance(context);
        mealDatabase.mealDao().Insert(mealsItem);
    }
    public void DeleteMeal(Context context , MealsItem mealsItem){
        mealDatabase = MealDatabase.getInstance(context);
        mealDatabase.mealDao().Delete(mealsItem);
    }
    public void getAllMeals(Context context , MealsItem mealsItem){
        mealDatabase = MealDatabase.getInstance(context);
        mealDatabase.mealDao().getAllMeals();
    }
    public LiveData<List<MealsItem>> ObserveFavouriteLiveData( Context context) {
        mealDatabase=MealDatabase.getInstance(context);
        favouriteLiveData = mealDatabase.mealDao().getAllMeals();
        return favouriteLiveData;
    }
}
