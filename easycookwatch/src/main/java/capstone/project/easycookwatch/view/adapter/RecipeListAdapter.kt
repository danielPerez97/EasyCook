package capstone.project.easycookwatch.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import capstone.project.core.ViewRecipe
import capstone.project.easycookwatch.databinding.RecipeEntryBinding

class RecipeListAdapter: RecyclerView.Adapter<RecipeListAdapter.ViewHolder>()
{
    private var data: List<ViewRecipe> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeEntryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(data[position])
    }

    fun setData(newData: List<ViewRecipe>)
    {
        data = newData
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RecipeEntryBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(recipe: ViewRecipe)
        {
            binding.recipeName.text = recipe.name
        }
    }

}