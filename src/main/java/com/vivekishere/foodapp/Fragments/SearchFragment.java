package com.vivekishere.foodapp.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.vivekishere.foodapp.Adapters.CategoriesAdapter;
import com.vivekishere.foodapp.Adapters.StrCategoryAdapter;
import com.vivekishere.foodapp.Pojo.MealsItem;
import com.vivekishere.foodapp.R;
import com.vivekishere.foodapp.ViewModal.HomeViewModal;
import com.vivekishere.foodapp.databinding.FragmentSearchBinding;

import java.util.List;

public class SearchFragment extends Fragment {
    FragmentSearchBinding binding;
    private HomeViewModal homeViewModal;
    StrCategoryAdapter mealsAdapter ;

    public SearchFragment() {
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
        binding = FragmentSearchBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       prepareRecyclerView();
       ObserveSearchMealLiveData();
       searchMeals();
    }

    private void ObserveSearchMealLiveData() {
        homeViewModal.ObserveSearchMealLiveData().observe(getViewLifecycleOwner(), new Observer<List<MealsItem>>() {
            @Override
            public void onChanged(List<MealsItem> mealsItems) {
                mealsAdapter = new StrCategoryAdapter(getContext() , mealsItems);
                prepareRecyclerView();
            }
        });
    }

    private void prepareRecyclerView() {
        binding.recyclerView.setAdapter(mealsAdapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext() , 2 , LinearLayoutManager.VERTICAL , false));
    }
    private void searchMeals(){
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.searchbox, InputMethodManager.SHOW_IMPLICIT);
        binding.searchbox.requestFocus();
        binding.searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                homeViewModal.getSearchMeal(query);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = binding.searchbox.getText().toString();
                if (searchQuery.isEmpty()){
                    binding.searchbox.setError("Write Query" , getResources().getDrawable(R.drawable.baseline_search_24 , null));
                }else {
                    homeViewModal.getSearchMeal(searchQuery);
                }
            }
        });
    }
}