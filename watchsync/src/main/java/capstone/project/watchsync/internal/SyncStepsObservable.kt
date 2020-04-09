package capstone.project.watchsync.internal

import capstone.project.watchsync.capabilities.ACQUIRE_LIST_STEPS
import capstone.project.watchsync.model.SyncStep
import capstone.project.watchsync.model.SyncStepRequest
import capstone.project.watchsync.model.SyncStepRequestJsonAdapter
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.lang.Exception
import java.lang.reflect.Type

internal fun MessageClient.steps(moshi: Moshi, destinationId: String, request: SyncStepRequest): Observable<List<SyncStep>>
{
    return SyncStepsObservable(this, moshi, destinationId, request)
}

private class SyncStepsObservable(val client: MessageClient,
                                  val moshi: Moshi,
                                  val destinationId: String,
                                  val request: SyncStepRequest): Observable<List<SyncStep>>()
{
    override fun subscribeActual(observer: Observer<in List<SyncStep>>)
    {
        val listener = Listener(observer, moshi)
        observer.onSubscribe(listener)
        client.addListener(listener)

        // Request the Steps
        client.sendMessage(destinationId, ACQUIRE_LIST_STEPS, request.toByteArray())
            .addOnSuccessListener { Timber.i("Sent request for steps successfully") }
            .addOnFailureListener(listener)
    }

    private fun SyncStepRequest.toByteArray(): ByteArray
    {
        val adapter = SyncStepRequestJsonAdapter(moshi)
        val json = adapter.toJson(this)
        return json.toByteArray(Charsets.UTF_8)
    }

    class Listener(val observer: Observer<in List<SyncStep>>, val moshi: Moshi): Disposable, MessageClient.OnMessageReceivedListener,
        OnFailureListener
    {
        var disposed = false

        override fun isDisposed(): Boolean = disposed

        override fun dispose()
        {
            disposed = true
        }

        override fun onMessageReceived(event: MessageEvent)
        {
            if(event.path == ACQUIRE_LIST_STEPS && !disposed)
            {
                val type: Type = Types.newParameterizedType(List::class.java, SyncStep::class.java)
                val adapter: JsonAdapter<List<SyncStep>> = moshi.adapter(type)
                val steps: List<SyncStep> = adapter.fromJson(event.data.toString(Charsets.UTF_8))!!
                observer.onNext(steps)
                observer.onComplete()
            }
        }

        override fun onFailure(e: Exception)
        {
            observer.onError(e)
        }

    }

}