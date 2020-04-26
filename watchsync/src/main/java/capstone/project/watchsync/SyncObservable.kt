package capstone.project.watchsync

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Method to send a message to a node.
 * @param capability This is the "endpoint" broadcasted by the device you're trying to access, noted by it's wear.xml file.
 * @param nodeId The id of the node. Use daniel.perez.sync.capabilityAvailable() if you need the node info.
 * @param message Your message encoded in a ByteArray. By default this is an empty string, "".
 * @return Returns an Observable<ByteArray> with the data broadcasted by the Capability
 */
@JvmOverloads
fun MessageClient.send(
    capability: String,
    nodeId: String,
    message: ByteArray = "".toByteArray()
): Observable<ByteArray>
{
    return SyncObservable(this, capability, nodeId, message)
}

private class SyncObservable(
    val client: MessageClient,
    val capability: String,
    val nodeId: String,
    val message: ByteArray
) : Observable<ByteArray>()
{
    override fun subscribeActual(observer: Observer<in ByteArray>)
    {
        val listener = Listener(observer, capability, client)
        observer.onSubscribe(listener)
        client.addListener(listener)

        client.sendMessage(nodeId, capability, message)
            .addOnFailureListener(listener)
    }

    private class Listener(
        val observer: Observer<in ByteArray>,
        val capability: String,
        val client: MessageClient
    ) :
        Disposable, MessageClient.OnMessageReceivedListener, OnFailureListener
    {
        private var disposed = false

        override fun dispose()
        {
            client.removeListener(this)
            disposed = true
        }

        override fun isDisposed(): Boolean = disposed

        override fun onMessageReceived(event: MessageEvent)
        {
            if (event.path == capability && !disposed)
            {
                observer.onNext(event.data)
                observer.onComplete()
            }
        }

        override fun onFailure(e: Exception)
        {
            observer.onError(e)
        }


    }
}
