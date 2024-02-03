package com.vivekishere.foodapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vivekishere.foodapp.Activites.MealActivity;
import com.vivekishere.foodapp.Pojo.MealsItem;
import com.vivekishere.foodapp.Utility.helper;
import com.vivekishere.foodapp.ViewModal.HomeViewModal;
import com.vivekishere.foodapp.databinding.FragmentLongTouchBSBinding;

public class LongTouchBS extends BottomSheetDialogFragment {
    FragmentLongTouchBSBinding binding;
    HomeViewModal homeViewModal;
    MealsItem meal;

    public LongTouchBS() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModal = ViewModelProviders.of(this).get(HomeViewModal.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLongTouchBSBinding.inflate(inflater);
        String mealid = (String)getArguments().get(helper.MEAL_ID);
        // Inflate the layout for this fragment
        homeViewModal.getMealById(mealid);
        ObserveBottomSheetLiveData();
        OnOpenDetails();
        return binding.getRoot();
    }

    private void OnOpenDetails() {
        binding.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(getContext() , MealActivity.class);
            intent.putExtra(helper.MEAL_ID , meal.getIdMeal());
            intent.putExtra(helper.MEAL_NAME, meal.getStrMeal());
            intent.putExtra(helper.MEAL_THUMb , meal.getStrMealThumb());
            startActivity(intent);
        });
    }

    private void ObserveBottomSheetLiveData() {
        homeViewModal.ObserveBottomSheetLiveData().observe(getViewLifecycleOwner(), new Observer<MealsItem>() {
            @Override
            public void onChanged(MealsItem mealsItem) {
                helper.LoadImage(getContext() , mealsItem.getStrMealThumb() , binding.MealImg);
                binding.mealName.setText(mealsItem.getStrMeal());
                binding.area.setText(mealsItem.getStrArea());
                binding.category.setText(mealsItem.getStrCategory());
                meal = mealsItem;
            }
        });
    }
}