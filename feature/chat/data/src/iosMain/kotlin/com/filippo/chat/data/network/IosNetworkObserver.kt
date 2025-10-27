package com.filippo.chat.data.network

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfiable
import platform.Network.nw_path_status_satisfied
import platform.darwin.dispatch_queue_create

class IosNetworkObserver : NetworkObserver {
    override val isNetworkAvailable = callbackFlow {
        val pathMonitor = nw_path_monitor_create()

        nw_path_monitor_set_update_handler(pathMonitor) { path ->
            if (path == null) {
                trySend(false)
                return@nw_path_monitor_set_update_handler
            }

            when (nw_path_get_status(path)) {
                nw_path_status_satisfied, nw_path_status_satisfiable -> trySend(true)
                else -> trySend(false)
            }
        }

        nw_path_monitor_set_queue(
            monitor = pathMonitor,
            queue = dispatch_queue_create(DISPATCH_QUEUE, null)
        )
        nw_path_monitor_start(pathMonitor)

        awaitClose { nw_path_monitor_cancel(pathMonitor) }
    }
}

private const val DISPATCH_QUEUE = "IosNetworkObserver"