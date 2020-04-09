package capstone.project.watchsync

import com.google.android.gms.wearable.CapabilityInfo

sealed class Request<out T : Any>
{
    class Success<out T : Any>(val data: T) : Request<T>()
    class Failure(val throwable: Throwable) : Request<Nothing>()
    object RequestInTransit : Request<Nothing>()
    class PhoneAvailable(val info: CapabilityInfo): Request<Nothing>()
    class PhoneUnavailable(val exception: Exception): Request<Nothing>()
    object CheckingForPhone: Request<Nothing>()
}