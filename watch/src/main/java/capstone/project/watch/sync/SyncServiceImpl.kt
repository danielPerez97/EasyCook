package capstone.project.watch.sync

import android.content.Context
import capstone.project.core.ACQUIRE_LIST_RECIPES
import capstone.project.core.ACQUIRE_LIST_STEPS
import capstone.project.core.sync.SyncRecipe
import capstone.project.core.sync.SyncStep
import capstone.project.core.sync.SyncStepRequest
import capstone.project.core.sync.SyncStepRequestJsonAdapter
import capstone.project.watchsync.AvailabilityResult
import capstone.project.watchsync.capabilityAvailable
import capstone.project.watchsync.send
import com.google.android.gms.wearable.Wearable
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Observable
import java.lang.reflect.Type

class SyncServiceImpl(private val context: Context, private val moshi: Moshi): SyncService
{
    private val stepsType: Type = Types.newParameterizedType(List::class.java, SyncStep::class.java)
    private val recipeType: Type = Types.newParameterizedType(List::class.java, SyncRecipe::class.java)
    private val syncStepAdapter: JsonAdapter<List<SyncStep>> = moshi.adapter(stepsType)
    private val syncRecipeAdapter: JsonAdapter<List<SyncRecipe>> = moshi.adapter(recipeType)
    private val stepRequestAdapter = SyncStepRequestJsonAdapter(moshi)


    override fun getRecipes(destinationId: String): Observable<List<SyncRecipe>>
    {
        return Wearable.getMessageClient(context)
            .send(ACQUIRE_LIST_RECIPES, destinationId)
            .map { byteArray -> syncRecipeAdapter.fromJson(byteArray.toString(Charsets.UTF_8)) }
    }

    override fun getSteps(destinationId: String, request: SyncStepRequest): Observable<List<SyncStep>>
    {
        return Wearable.getMessageClient(context)
            .send(ACQUIRE_LIST_STEPS, destinationId, request.toByteArray())
            .map { byteArray -> syncStepAdapter.fromJson(byteArray.toString(Charsets.UTF_8)) }
    }

    override fun available(): Observable<AvailabilityResult>
    {
        return Wearable.getCapabilityClient(context).capabilityAvailable()
    }

    private fun SyncStepRequest.toByteArray(): ByteArray
    {
        return stepRequestAdapter.toJson(this).toByteArray(Charsets.UTF_8)
    }
}