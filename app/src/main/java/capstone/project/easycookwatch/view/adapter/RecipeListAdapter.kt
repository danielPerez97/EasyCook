package capstone.project.easycookwatch.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import capstone.project.easycookwatch.model.ViewRecipe
import capstone.project.easycookwatch.databinding.RecipeEntryBinding

class RecipeListAdapter: RecyclerView.Adapter<RecipeListAdapter.ViewHolder>()
{
    private var data: List<ViewRecipe> = emptyList()

    // Used to create a brand new entry in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeEntryBinding.inflate(inflater, parent, false)
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
    fun setData(newData: List<ViewRecipe>)
    {
        data = newData
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RecipeEntryBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(recipe: ViewRecipe)
        {
            binding.entryRecipeName.text = recipe.name
        }
    }
}