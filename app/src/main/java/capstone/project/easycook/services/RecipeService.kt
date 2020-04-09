package capstone.project.easycook.services

import capstone.project.database.recipe.Database
import capstone.project.easycook.BaseApplication
import capstone.project.watchsync.capabilities.ACQUIRE_LIST_RECIPES
import capstone.project.watchsync.capabilities.ACQUIRE_LIST_STEPS
import capstone.project.watchsync.model.SyncRecipe
import capstone.project.watchsync.model.SyncStep
import capstone.project.watchsync.model.SyncStepRequest
import capstone.project.watchsync.model.SyncStepRequestJsonAdapter
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import timber.log.Timber
import java.lang.reflect.Type
import javax.inject.Inject

class RecipeService : WearableListenerService()
{
    @Inject lateinit var database: Database
    @Inject lateinit var moshi: Moshi

    override fun onCreate()
    {
        (application as BaseApplication).injector().inject(this)
        super.onCreate()
    }

    override fun onMessageReceived(event: MessageEvent)
    {
        Timber.i("Message Received")
        if (event.path == ACQUIRE_LIST_RECIPES)
        {
            val json = getRecipesListJson()

            Timber.i("Sending $json")

            // Send the List<SyncRecipe>
            Wearable.getMessageClient(this)
                .sendMessage(event.sourceNodeId, ACQUIRE_LIST_RECIPES, json.toByteArray(Charsets.UTF_8))
        }
        else if(event.path == ACQUIRE_LIST_STEPS)
        {
            val json = getStepsListJson(event)
            Timber.i("Sending $json")

            // Send the
            Wearable.getMessageClient(this)
                .sendMessage(event.sourceNodeId, ACQUIRE_LIST_STEPS, json.toByteArray(Charsets.UTF_8))
        }
    }

    private fun getRecipesListJson(): String
    {
        val type: Type = Types.newParameterizedType(List::class.java, SyncRecipe::class.java)
        val adapter: JsonAdapter<List<SyncRecipe>> = moshi.adapter(type)
        val dbRecipes: List<SyncRecipe> = database.recipeQueries.selectAll().executeAsList().map { SyncRecipe(it._id, it.name) }
        return adapter.toJson(dbRecipes)
    }

    private fun getStepsListJson(event: MessageEvent): String
    {
        // Parse the Sync Step request
        val payload: SyncStepRequest = SyncStepRequestJsonAdapter(moshi).fromJson(event.data.toString(Charsets.UTF_8))!!

        // Create the adapter for the List<SyncStep>
        val type: Type = Types.newParameterizedType(List::class.java, SyncStep::class.java)
        val adapter: JsonAdapter<List<SyncStep>> = moshi.adapter(type)

        // Get the List<SyncStep> and convert it to json
        val dbSteps: List<SyncStep> = database.recipeQueries.selectAllRecipeSteps(payload.recipeId).executeAsList().map { SyncStep(it.step_number, it.description) }
        return adapter.toJson(dbSteps)
    }
}