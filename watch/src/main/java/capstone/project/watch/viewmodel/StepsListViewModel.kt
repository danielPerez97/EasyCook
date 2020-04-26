package capstone.project.watch.viewmodel

import capstone.project.core.ViewStep
import capstone.project.core.sync.SyncStepRequest
import capstone.project.watch.sync.SyncService
import capstone.project.watchsync.AvailabilityResult
import capstone.project.watchsync.Request
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class StepsListViewModel @Inject constructor(private val syncService: SyncService)
{
    fun steps(nodeId: String, request: SyncStepRequest): Observable<Request<List<ViewStep>>>
    {
        return syncService.available()
            .flatMap { availability ->
                return@flatMap when(availability)
                {
                    is AvailabilityResult.Available ->
                    {
                        syncService.getSteps(nodeId, request)
                            .map <Request<List<ViewStep>>> { Request.Success(it.map { ViewStep(it.stepNumber, it.description) }) }
                            .retry(3) {
                                Timber.e(it)
                                true
                            }
                            .onErrorReturn { Request.Failure(it) }
                            .startWith(Request.PhoneAvailable(availability.info))
                            .startWith(Request.RequestInTransit)
                            .subscribeOn(Schedulers.io())
                    }
                    is AvailabilityResult.Unavailable -> Observable.just(Request.PhoneUnavailable(availability.exception))
                    AvailabilityResult.InTransit -> Observable.just(Request.CheckingForPhone)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
    }
}