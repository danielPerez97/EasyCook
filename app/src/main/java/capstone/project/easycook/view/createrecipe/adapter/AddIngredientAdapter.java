package capstone.project.easycook.view.createrecipe.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import capstone.project.easycook.model.ViewIngredient;
import daniel.perez.easycook.databinding.AddIngredientEntryBinding;

public class AddIngredientAdapter extends RecyclerView.Adapter<AddIngredientAdapter.ViewHolder>
{

    private ArrayList<ViewIngredient> data = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AddIngredientEntryBinding binding = AddIngredientEntryBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public void setData(List<ViewIngredient> newData)
    {
        data = new ArrayList<>(newData);
        notifyDataSetChanged();
    }

    public void addData(ViewIngredient ingredient)
    {
        data.add(ingredient);
        notifyItemInserted(data.size() - 1);
    }

    public List<ViewIngredient> ingredients()
    {
        List<ViewIngredient> filtered = new ArrayList<>(data);
        for(ViewIngredient ingredient: filtered)
        {
            if(ingredient.getAmount().isEmpty())
            {
                filtered.remove(ingredient);
            }
            if(ingredient.getName().isEmpty())
            {
                filtered.remove(ingredient);
            }
        }

        return filtered;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        AddIngredientEntryBinding binding;

        ViewHolder(@NonNull AddIngredientEntryBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ViewIngredient ingredient)
        {
            binding.ingredientNameTextInput.setText(ingredient.getName());
            binding.amountTv.setText(ingredient.getAmount());
        }
    }
}
