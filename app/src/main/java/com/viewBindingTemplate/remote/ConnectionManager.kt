package com.viewBindingTemplate.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.InetSocketAddress
import javax.net.SocketFactory

object ConnectionManager {

    var context: WeakReference<Context>? = null
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private var connectivityManager: ConnectivityManager? = null
    private var isConnectionAvailable: LiveData<Boolean>? = null
    private var isConnected: Boolean = false

    fun init(context: Context) {
        if (ConnectionManager.context == null) {
            ConnectionManager.context = WeakReference(context)
            connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
        checkNetworkConnection()
    }

    fun isInternetConnected() = isConnected

    fun listenObserver(liveObserver: LiveData<Boolean>) {
        isConnectionAvailable = liveObserver
    }

    private val validNetworks: MutableSet<Network> = HashSet()

    private fun checkNetworkConnection() {
        networkCallback = createNetworkCallback()
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build().also {
                connectivityManager?.registerNetworkCallback(it, networkCallback)
            }
    }

    /**
     * Create Network Callback
     * */
    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        /**
         * On Available Network
         * */
        override fun onAvailable(network: Network) {
            val networkCapabilities = connectivityManager?.getNetworkCapabilities(network)
            val hasInternetCapability =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

            if (hasInternetCapability == true) {
                // Check if this network actually has internet
                CoroutineScope(Dispatchers.IO).launch {
                    val hasInternet = withContext(Dispatchers.Default) {
                            DoesNetworkHaveInternet.execute(network.socketFactory)
                        }
                    runCatching {
                        withContext(Dispatchers.Main) {
                            if (hasInternet) {
                                validNetworks.add(network)
                                sendData()
                            }
                        }
                    }.onFailure { exception ->
                        Log.e(
                            ConnectionManager.javaClass.simpleName,
                            "Error while checking network: ${exception.message}"
                        )
                        sendData()
                    }
                }
            }
        }

        /**
         * On Lost Network
         * */
        override fun onLost(network: Network) {
            validNetworks.remove(network)
            sendData()
        }
    }

    private fun sendData() {
        isConnected = validNetworks.isNotEmpty()
        Log.e(ConnectionManager.javaClass.name, isConnected.toString())
    }

    /**
     * DOes Network Have Internet
     * */
    object DoesNetworkHaveInternet {
        /**
         * Execute Socket Factory
         * */
        fun execute(socketFactory: SocketFactory): Boolean {
            return try {
                Log.d(ConnectionManager.javaClass.simpleName, "PINGING Google...")
                socketFactory.createSocket()?.use {
                    it.connect(InetSocketAddress("8.8.8.8", 53), 7000)
                }
                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }
    }


}