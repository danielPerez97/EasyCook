package capstone.project.watchsync

import capstone.project.core.ACQUIRE_LIST_RECIPES
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Returns an Observable that signals true if the capability is available from the phone.
 * @return Observable<AvailabilityResult>. Sealed class to tell you the state of the Capabilities availability
 * with data attached.
 */
fun CapabilityClient.capabilityAvailable(): Observable<AvailabilityResult>
{
    return WearableAvailabilityObservable(this)
}

private class WearableAvailabilityObservable(val client: CapabilityClient): Observable<AvailabilityResult>()
{
    override fun subscribeActual(observer: Observer<in AvailabilityResult>)
    {
        val capability = client.getCapability(ACQUIRE_LIST_RECIPES, CapabilityClient.FILTER_REACHABLE)
        val listener = Listener(observer)
        observer.onSubscribe(listener)
        observer.onNext(AvailabilityResult.InTransit)
        capability.addOnSuccessListener(listener)
        capability.addOnFailureListener(listener)
    }

    class Listener(val observer: Observer<in AvailabilityResult>): OnSuccessListener<CapabilityInfo>,
        OnFailureListener, Disposable
    {
        var disposed = false

        override fun onSuccess(info: CapabilityInfo)
        {
            if(!disposed)
            {
                if(info.nodes.size <= 0)
                {
                    observer.onNext(AvailabilityResult.Unavailable(Exception("No nodes found")))
                }
                else
                {
                    observer.onNext(AvailabilityResult.Available(info))
                }
            }
        }

        override fun onFailure(e: Exception)
        {
            if(!disposed)
            {
                observer.onNext(AvailabilityResult.Unavailable(e))
            }
        }

        override fun isDisposed(): Boolean = disposed

        /**
         * The listener lasts for the duration of the context passed to it.
         */
        override fun dispose()
        {
            disposed = true
            observer.onComplete()
        }

    }

}