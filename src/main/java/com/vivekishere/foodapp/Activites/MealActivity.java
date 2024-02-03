package com.vivekishere.foodapp.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.vivekishere.foodapp.Pojo.MealsItem;
import com.vivekishere.foodapp.R;
import com.vivekishere.foodapp.Utility.helper;
import com.vivekishere.foodapp.ViewModal.MealViewModal;
import com.vivekishere.foodapp.databinding.ActivityMealBinding;

public class MealActivity extends AppCompatActivity {
    ActivityMealBinding binding ;
    private String MealId , MealName , MealThumb , youtubeLink;
   private MealViewModal mealViewModal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mealViewModal =ViewModelProviders.of(this).get(MealViewModal.class);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black_shade_3 , null));
        getMealInfo();
        loadingCase();
        mealViewModal.getMealInfoData(MealId);
        ObserveMealLiveData();
        OnYoutubeClick();
    }
    private void OnYoutubeClick() {
        binding.youtube.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(youtubeLink));
            startActivity(intent);
        });
    }

    private void ObserveMealLiveData() {
        mealViewModal.ObserveMealData().observe(this, new Observer<MealsItem>() {
            @Override
            public void onChanged(MealsItem mealsItem) {
                OnResponseCase();
                MealsItem item = mealsItem;
                youtubeLink = item.getStrYoutube();
                binding.category.setText(item.getStrCategory());
                binding.area.setText(item.getStrArea());
                binding.instructions.setText(item.getStrInstructions());
                binding.fab.setOnClickListener(view -> {
                    mealViewModal.InsertMeal(MealActivity.this , item);
                    helper.Toast(MealActivity.this , "Saved");
                });

            }
        });
    }

    private void setInfoInViews() {
        helper.LoadImage(this , MealThumb , binding.mealImg);
        binding.collapsingToolbar.setTitle(MealName);
        binding.collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white , null));
        binding.collapsingToolbar.setExpandedTitleTextSize(60);
    }

    private void getMealInfo() {
        MealId = getIntent().getStringExtra(helper.MEAL_ID);
        MealName = getIntent().getStringExtra(helper.MEAL_NAME);
        MealThumb = getIntent().getStringExtra(helper.MEAL_THUMb);
        setInfoInViews();
    }
    private void loadingCase(){
        binding.fab.setVisibility(View.INVISIBLE);
        binding.category.setVisibility(View.INVISIBLE);
        binding.area.setVisibility(View.INVISIBLE);
        binding.youtube.setVisibility(View.INVISIBLE);
        binding.instructions.setVisibility(View.INVISIBLE);
        binding.progressbar.setVisibility(View.VISIBLE);
    }
    private void OnResponseCase(){
        binding.fab.setVisibility(View.VISIBLE);
        binding.category.setVisibility(View.VISIBLE);
        binding.area.setVisibility(View.VISIBLE);
        binding.youtube.setVisibility(View.VISIBLE);
        binding.instructions.setVisibility(View.VISIBLE);
        binding.progressbar.setVisibility(View.INVISIBLE);
    }
}