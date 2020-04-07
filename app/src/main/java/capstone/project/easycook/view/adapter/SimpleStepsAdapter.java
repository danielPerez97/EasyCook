package capstone.project.easycook.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import capstone.project.easycook.databinding.SimpleStepEntryBinding;
import capstone.project.easycook.model.ViewStep;

public class SimpleStepsAdapter extends RecyclerView.Adapter<SimpleStepsAdapter.ViewHolder>
{

    private ArrayList<ViewStep> data = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SimpleStepEntryBinding binding = SimpleStepEntryBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.bind( data.get(position) );
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public void setData(List<ViewStep> newData)
    {
        data = new ArrayList<>(newData);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private SimpleStepEntryBinding binding;

        public ViewHolder(SimpleStepEntryBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(ViewStep step)
        {
            binding.stepTextDescription.setText( step.getDescription() );
            binding.stepNumberText.setText( Long.toString( step.getNumber() ) );
        }
    }
}
