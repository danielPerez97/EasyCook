package capstone.project.watch.viewmodel

import capstone.project.core.ViewRecipe
import capstone.project.core.sync.SyncRecipe
import capstone.project.watchsync.AvailabilityResult
import capstone.project.watchsync.Request
import capstone.project.watch.sync.SyncService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val syncService: SyncService)
{

    private val subject = PublishSubject.create<Unit>()
    fun recipes(): Observable<Request<List<ViewRecipe>>>
    {

        return subject
            .flatMap { syncService.available() }
            .flatMap {result ->
                return@flatMap when(result)
                {
                    is AvailabilityResult.Available ->
                    {
                        syncService.getRecipes(result.info.nodes.first().id)
                            .retry(3)
                            .map <Request<List<ViewRecipe>>> { syncRecipes: List<SyncRecipe> ->
                                Request.Success(syncRecipes.map { ViewRecipe(it.id, it.name) })
                            }
                            .onErrorReturn { Request.Failure(it) }
                            .startWith(Request.RequestInTransit)
                            .startWith(Request.PhoneAvailable(result.info))
                            .subscribeOn(Schedulers.io())
                    }
                    is AvailabilityResult.Unavailable -> Observable.just(Request.Failure(result.exception))
                    AvailabilityResult.InTransit -> Observable.just(Request.CheckingForPhone)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun onResume()
    {
        subject.onNext(Unit)
    }
}