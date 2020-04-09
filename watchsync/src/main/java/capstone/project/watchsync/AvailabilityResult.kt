package capstone.project.watchsync

import com.google.android.gms.wearable.CapabilityInfo

sealed class AvailabilityResult
{
    class Available(val info: CapabilityInfo): AvailabilityResult()
    class Unavailable(val exception: Exception): AvailabilityResult()
    object InTransit: AvailabilityResult()
}
