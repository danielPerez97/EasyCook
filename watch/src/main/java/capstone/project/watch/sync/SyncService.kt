package capstone.project.watch.sync

import capstone.project.core.sync.SyncRecipe
import capstone.project.core.sync.SyncStep
import capstone.project.core.sync.SyncStepRequest
import capstone.project.watchsync.AvailabilityResult

import io.reactivex.Observable

interface SyncService
{
    /**
     * Returns a cold observable of a List<SyncRecipe>. Begins with an empty list.
     */
    fun getRecipes(destinationId: String): Observable<List<SyncRecipe>>

    /**
     * Returns a cold observable of a List<SyncSteps>. Begins with an empty list
     */
    fun getSteps(destinationId: String, request: SyncStepRequest): Observable<List<SyncStep>>

    /**
     * Returns a hot Observable that signals if a watch is available
     */
    fun available(): Observable<AvailabilityResult>
}