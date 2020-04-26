package capstone.project.watch.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import capstone.project.core.ViewRecipe
import capstone.project.easycook.databinding.RecipeItemBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class RecipeListAdapter: RecyclerView.Adapter<RecipeListAdapter.ViewHolder>()
{
    private var data: List<ViewRecipe> = listOf()

    private val clickSubject = PublishSubject.create<ViewRecipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(newData: List<ViewRecipe>)
    {
        data = newData
        notifyDataSetChanged()
    }

    fun clicks(): Observable<ViewRecipe>
    {
        return clickSubject
    }

    inner class ViewHolder(private val binding: RecipeItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(recipe: ViewRecipe)
        {
            binding.recipeName.text = recipe.recipeName
            binding.root.setOnClickListener {
                Timber.i("CLICK")
                clickSubject.onNext(recipe)
            }
        }
    }
}