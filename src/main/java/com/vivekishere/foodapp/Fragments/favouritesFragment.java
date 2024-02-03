package com.vivekishere.foodapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.snackbar.Snackbar;
import com.vivekishere.foodapp.Activites.MealActivity;
import com.vivekishere.foodapp.Adapters.StrCategoryAdapter;
import com.vivekishere.foodapp.Pojo.MealsItem;
import com.vivekishere.foodapp.Utility.helper;
import com.vivekishere.foodapp.ViewModal.MealViewModal;
import com.vivekishere.foodapp.databinding.FragmentFavouritesBinding;

import java.util.ArrayList;
import java.util.List;

public class favouritesFragment extends Fragment {
    FragmentFavouritesBinding binding ;
    StrCategoryAdapter strCategoryAdapter;
    MealViewModal mealViewModal;
    ItemTouchHelper itemTouchHelper;
    List<MealsItem> SwipeMealsItem = new ArrayList<>();

    public favouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealViewModal = ViewModelProviders.of(this).get(MealViewModal.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouritesBinding.inflate(getLayoutInflater());

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showRecyclerView();
        ObserveFavLiveData();
        swipeDelete();
    }

    private void swipeDelete() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                MealsItem item = SwipeMealsItem.get(viewHolder.getAdapterPosition());
                mealViewModal.DeleteMeal(getContext() ,item);
                Snackbar.make(binding.getRoot() ,"Meal Deleted" ,Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mealViewModal.InsertMeal(getContext() ,item);
                            }
                        }).show();
                strCategoryAdapter.notifyDataSetChanged();
            }
        };
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
    }

    private void ObserveFavLiveData() {
        mealViewModal.ObserveFavouriteLiveData(getContext()).observe(getViewLifecycleOwner(), new Observer<List<MealsItem>>() {
            @Override
            public void onChanged(List<MealsItem> mealsItems) {
                SwipeMealsItem.addAll(mealsItems);
                strCategoryAdapter = new StrCategoryAdapter(getContext() ,mealsItems);
                strCategoryAdapter.notifyDataSetChanged();
                OnFavClick();
                showRecyclerView();
            }
        });
    }

    private void showRecyclerView() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext() , 2 , LinearLayoutManager.VERTICAL , false));
        binding.recyclerView.setAdapter(strCategoryAdapter);
    }
    private void OnFavClick(){
        strCategoryAdapter.setOnItemClickListener(new StrCategoryAdapter.OnItemClickListener() {
            @Override
            public void OnClick(MealsItem mealsItem) {
                Intent intent = new Intent(getContext() , MealActivity.class);
                intent.putExtra(helper.MEAL_ID , mealsItem.getIdMeal());
                intent.putExtra(helper.MEAL_NAME , mealsItem.getStrMeal());
                intent.putExtra(helper.MEAL_THUMb , mealsItem.getStrMealThumb());
                startActivity(intent);
            }
        });
    }

}