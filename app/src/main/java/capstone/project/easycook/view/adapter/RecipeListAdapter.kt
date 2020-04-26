package capstone.project.easycook.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import capstone.project.core.ViewRecipe
import capstone.project.easycook.databinding.RecipeEntryBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RecipeListAdapter: RecyclerView.Adapter<RecipeListAdapter.ViewHolder>()
{
    private var data: List<ViewRecipe> = emptyList()
    private val clickSubject = PublishSubject.create<ViewRecipe>()

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

    fun clicks(): Observable<ViewRecipe>
    {
        return clickSubject
    }

    inner class ViewHolder(private val binding: RecipeEntryBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(recipe: ViewRecipe)
        {
            binding.entryRecipeName.text = recipe.recipeName

            binding.root.setOnClickListener { clickSubject.onNext(recipe) }
        }
    }
}