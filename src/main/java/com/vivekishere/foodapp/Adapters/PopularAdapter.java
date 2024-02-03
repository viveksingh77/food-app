package com.vivekishere.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vivekishere.foodapp.Pojo.MealsItem;
import com.vivekishere.foodapp.R;
import com.vivekishere.foodapp.Utility.helper;
import com.vivekishere.foodapp.databinding.CardItemsBinding;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PViewHolder> {
    Context context ;
  List<MealsItem> mealsItemList;
  private OnPopularMealClickListener onPopularMealClickListener;
  private OnLongMealCLickListener onLongMealCLickListener;
  public interface OnPopularMealClickListener{
      void OnPopularClick(MealsItem mealsItem);
  }
  public interface OnLongMealCLickListener{
      void OnLongClick(MealsItem mealsItem);
  }

    public void setOnPopularMealClickListener(OnPopularMealClickListener onPopularMealClickListener) {
        this.onPopularMealClickListener = onPopularMealClickListener;
    }

    public void setOnLongMealCLickListener(OnLongMealCLickListener onLongMealCLickListener) {
        this.onLongMealCLickListener = onLongMealCLickListener;
    }

    public PopularAdapter(Context context, List<MealsItem> mealsItemList) {
        this.context = context;
        this.mealsItemList = mealsItemList;
    }

    @NonNull
    @Override
    public PViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PViewHolder(LayoutInflater.from(context).inflate(R.layout.card_items , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull PViewHolder holder, int position) {
      MealsItem mealsItem = mealsItemList.get(position);
        helper.LoadImage(context , mealsItem.getStrMealThumb() , holder.binding.images);
        holder.itemView.setOnClickListener(view -> {
            onPopularMealClickListener.OnPopularClick(mealsItem);
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onLongMealCLickListener.OnLongClick(mealsItem);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealsItemList.size();
    }

    public class PViewHolder extends RecyclerView.ViewHolder{
        CardItemsBinding binding;
        public PViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CardItemsBinding.bind(itemView);
        }
    }



}
