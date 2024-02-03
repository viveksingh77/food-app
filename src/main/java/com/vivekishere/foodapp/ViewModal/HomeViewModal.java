package com.vivekishere.foodapp.ViewModal;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vivekishere.foodapp.Pojo.CategoryList;
import com.vivekishere.foodapp.Pojo.MealsByCategoryList;
import com.vivekishere.foodapp.Pojo.MealsItem;
import com.vivekishere.foodapp.Pojo.MealResponse;
import com.vivekishere.foodapp.Retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModal extends ViewModel {
    private static final String TAG = "HomeViewModal";
    private MutableLiveData<MealsItem> randomMeal = new MutableLiveData<>();
    private MutableLiveData<MealsByCategoryList> categoryLiveData = new MutableLiveData<>();
    private MutableLiveData<CategoryList> MCategoryLiveData = new MutableLiveData<>();
    private MutableLiveData<MealsItem> BottomSheetMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MealsItem>> searchMutableLiveData= new MutableLiveData<>();

    public LiveData<MealsItem> ObserveBottomSheetLiveData() {
        return BottomSheetMutableLiveData;
    }
    public LiveData<MealsItem> observeRandomMeal() {
        return randomMeal;
    }
    //this is for popular item
    public LiveData<MealsByCategoryList> ObserveCategoryList(){
        return categoryLiveData;
    }

    //this is for categories
    public LiveData<CategoryList> ObserveCategories(){
        return MCategoryLiveData;
    }

    public LiveData<List<MealsItem>> ObserveSearchMealLiveData(){
        return searchMutableLiveData;
    }

    private MealsItem saveMeal = null;
    public void getRandomMeal(){
//        if (saveMeal!=null){
//            randomMeal.postValue(randomMealItem);
//            return;
//        }
        RetrofitInstance.getInstance().retrofitApi()
                .getRandomFood()
                .enqueue(new Callback<MealResponse>() {
                    @Override
                    public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                        MealsItem meal = response.body().getMeals().get(0);
                        if (response.isSuccessful() && response.body()!=null){
                            randomMeal.setValue(meal);
                        } else {
                            Log.d(TAG, "onResponse: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MealResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage());
                    }
                });
    }
    public void getPopularItem(){
        RetrofitInstance.getInstance().retrofitApi()
                .getPopularItems("Seafood")
                .enqueue(new Callback<MealsByCategoryList>() {
                    @Override
                    public void onResponse(Call<MealsByCategoryList> call, Response<MealsByCategoryList> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            categoryLiveData.setValue(response.body());
                        } else {
                            Log.d(TAG, "onResponse: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MealsByCategoryList> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage());
                    }
                });
    }
    public void getCategories() {
        RetrofitInstance.getInstance().retrofitApi()
                .getCategoryList().enqueue(new Callback<CategoryList>() {
                    @Override
                    public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            MCategoryLiveData.setValue(response.body());
                        }
                        else {
                            Log.d(TAG, "onResponse: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryList> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage());
                    }
                });

    }
    public void getMealById(String id){
        RetrofitInstance.getInstance()
                .retrofitApi()
                .getMealInfo(id).enqueue(new Callback<MealResponse>() {
                    @Override
                    public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            MealsItem mealsItem = response.body().getMeals().get(0);
                            BottomSheetMutableLiveData.postValue(mealsItem);
                        } else {
                            Log.d(TAG, "onResponse: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MealResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage());
                    }
                });
    }

    public void getSearchMeal(String query){
        RetrofitInstance.getInstance().retrofitApi()
                .SearchMeals(query)
                .enqueue(new Callback<MealResponse>() {
                    @Override
                    public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                        if (response.isSuccessful() && response.body()!=null){
                            searchMutableLiveData.postValue(response.body().getMeals());
                        } else {
                            Log.d(TAG, "onResponse: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MealResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage());
                    }
                });
    }

}
