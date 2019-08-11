package ungdung.vaynhanh

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun isConnected(context: Context): Boolean{
        val connected: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connected.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connected.getNetworkInfo(
            ConnectivityManager.TYPE_WIFI
        )!!.state == NetworkInfo.State.CONNECTED
    }
}