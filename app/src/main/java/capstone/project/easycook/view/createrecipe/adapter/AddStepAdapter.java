package capstone.project.easycook.view.createrecipe.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import capstone.project.easycook.databinding.AddStepEntryBinding;
import capstone.project.easycook.model.ViewStep;
import capstone.project.easycook.view.adapter.ItemTouchHelperAdapter;
import io.reactivex.disposables.CompositeDisposable;

public class AddStepAdapter extends RecyclerView.Adapter<AddStepAdapter.ViewHolder> implements ItemTouchHelperAdapter
{
    private ArrayList<ViewStep> data = new ArrayList<>();
    private CompositeDisposable disposables = new CompositeDisposable();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AddStepEntryBinding binding = AddStepEntryBinding.inflate(inflater, parent, false);
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

    public List<ViewStep> steps()
    {
        return new ArrayList<>(data);
    }

    public void addData(ViewStep step)
    {
        data.add(step);
        notifyItemInserted( data.size() - 1 );
    }
    @Override
    public boolean onItemMove(int fromPosition, int toPosition)
    {
        if(fromPosition < toPosition)
        {
            for(int i = fromPosition; i < toPosition; i++)
            {
                Collections.swap(data, i, i + 1);
            }
        }
        else
        {
            for(int i = fromPosition; i > toPosition; i--)
            {
                Collections.swap(data, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        notifyItemChanged(toPosition);
        notifyItemChanged(fromPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position)
    {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private AddStepEntryBinding binding;

        ViewHolder(AddStepEntryBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(ViewStep step)
        {
            step.setNumber( getAdapterPosition() + 1 );
            binding.cookingStepDescription.setText( step.getDescription() );
            binding.stepNumberTv.setText( Long.toString( step.getNumber() ) );

            disposables.add(
                    RxTextView.textChangeEvents( binding.cookingStepDescription )
                            .takeUntil( RxView.detaches( binding.getRoot() ) )
                            .subscribe(desc -> step.setDescription(desc.getText().toString()))
            );

        }
    }
}
