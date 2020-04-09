package capstone.project.watchsync

import android.content.Context
import capstone.project.watchsync.internal.capabilityAvailable
import capstone.project.watchsync.internal.recipes
import capstone.project.watchsync.internal.steps
import capstone.project.watchsync.model.SyncRecipe
import capstone.project.watchsync.model.SyncStep
import capstone.project.watchsync.model.SyncStepRequest
import com.google.android.gms.wearable.Wearable
import com.squareup.moshi.Moshi
import io.reactivex.Observable

class SyncServiceImpl(private val context: Context, private val moshi: Moshi): SyncService
{
    override fun getRecipes(destinationId: String): Observable<List<SyncRecipe>>
    {
        return Wearable.getMessageClient(context).recipes(moshi, destinationId)
    }

    override fun getSteps(destinationId: String, request: SyncStepRequest): Observable<List<SyncStep>>
    {
        return Wearable.getMessageClient(context).steps(moshi, destinationId, request)
    }

    override fun available(): Observable<AvailabilityResult>
    {
        return Wearable.getCapabilityClient(context).capabilityAvailable()

    }
}