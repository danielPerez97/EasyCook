package capstone.project.easycook.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import capstone.project.core.ViewIngredient
import capstone.project.easycook.databinding.IngredientEntryBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class IngredientsListAdapter: RecyclerView.Adapter<IngredientsListAdapter.ViewHolder>()
{
    private var data: List<ViewIngredient> = emptyList()

    // Used to create a brand new entry in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val binding = IngredientEntryBinding.inflate(inflater, parent, false)
        return ViewHolder(
            binding
        )
    }

    // Binds data from the data set into the entry
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        // Bind data into the entry in the list
        holder.bind(data[position])
    }

    // Returns the size of the data set
    override fun getItemCount(): Int = data.size

    // Easy entry point to change the data
    fun setData(newData: List<ViewIngredient>)
    {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: IngredientEntryBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(recipe: ViewIngredient)
        {
            binding.entryIngredientName.text = recipe.name

        }
    }
}