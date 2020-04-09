package capstone.project.watchsync.internal

import capstone.project.watchsync.capabilities.ACQUIRE_LIST_RECIPES
import capstone.project.watchsync.model.SyncRecipe
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

internal fun MessageClient.recipes(moshi: Moshi, destinationId: String): Observable<List<SyncRecipe>>
{
    return SyncRecipeObservable(this, destinationId, moshi)
}

private class SyncRecipeObservable(val client: MessageClient, val destinationId: String, val moshi: Moshi): Observable<List<SyncRecipe>>()
{
    override fun subscribeActual(observer: Observer<in List<SyncRecipe>>)
    {
        val listener = Listener(observer, client, moshi)
        observer.onSubscribe(listener)
        client.addListener(listener)

        // Request the data
        client.sendMessage(destinationId, ACQUIRE_LIST_RECIPES, "".toByteArray())
            .addOnSuccessListener{ Timber.i("Sent successfully to $destinationId") }
            .addOnFailureListener(listener)
    }

    class Listener(val observer: Observer<in List<SyncRecipe>>, val client: MessageClient, val moshi: Moshi): MessageClient.OnMessageReceivedListener,
        Disposable,
        OnFailureListener
    {
        var disposed = false

        override fun onMessageReceived(event: MessageEvent)
        {
            if(event.path == ACQUIRE_LIST_RECIPES && !disposed)
            {
                val type: Type = Types.newParameterizedType(List::class.java, SyncRecipe::class.java)
                val adapter: JsonAdapter<List<SyncRecipe>> = moshi.adapter(type)

                val recipes: List<SyncRecipe> = adapter.fromJson(event.data.toString(Charsets.UTF_8))!!
                observer.onNext(recipes)
                observer.onComplete()
            }
        }

        override fun isDisposed(): Boolean = disposed

        override fun dispose()
        {
            client.removeListener(this)
            observer.onComplete()
        }

        override fun onFailure(e: Exception)
        {
            observer.onError(e)
        }
    }
}