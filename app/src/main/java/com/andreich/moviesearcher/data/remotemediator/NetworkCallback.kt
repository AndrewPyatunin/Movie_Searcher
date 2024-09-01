package com.andreich.moviesearcher.data.remotemediator

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network

class NetworkCallback(
    private val context: Context
) : ConnectivityManager.NetworkCallback() {

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
    }

    init {
        connectivityManager.registerDefaultNetworkCallback(this)
    }
}