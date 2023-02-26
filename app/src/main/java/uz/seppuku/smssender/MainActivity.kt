package uz.seppuku.smssender

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import io.ktor.client.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import uz.seppuku.smssender.models.GetData
import uz.seppuku.smssender.repository.MainRepository
import uz.seppuku.smssender.utils.NetworkResponse


class MainActivity : AppCompatActivity() {


    var PERMISSIONS = arrayOf(
        android.Manifest.permission.INTERNET,
        android.Manifest.permission.ACCESS_NETWORK_STATE,
        android.Manifest.permission.READ_SMS,
        android.Manifest.permission.RECEIVE_SMS,
        android.Manifest.permission.SEND_SMS
    )

    private val MY_PERMISSIONS_REQUEST = 777
    val options = PusherOptions().setCluster("ap2")

    val pusher = Pusher("92f20f95b8fa85ab32a6", options)

    private val api: MainRepository by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeToChannel()
       if(hasPermissions(this, PERMISSIONS)){
           subscribeToChannel()
       }else{
           // Should we show an explanation?
           if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                   listOf(android.Manifest.permission.READ_SMS,android.Manifest.permission.SEND_SMS,).toString()
               )) {
               // Show an explanation to the user *asynchronously* -- don't block
               // this thread waiting for the user's response! After the user
               // sees the explanation, try again to request the permission.
               Log.d("Permission", "have description")
           } else {
               // No explanation needed, we can request the permission.
               Log.d("Permission", "no description")
               ActivityCompat.requestPermissions(
                   this, PERMISSIONS,
                   MY_PERMISSIONS_REQUEST
               )

               // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
               // app-defined int constant. The callback method gets the
               // result of the request.
           }
       }


    }

    @SuppressLint("SetTextI18n")
    private fun subscribeToChannel() {
        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
                Log.i(
                    "Pusher",
                    "State changed from ${change.previousState} to ${change.currentState}"
                )
            }

            override fun onError(message: String?, code: String?, e: Exception?) {

            }


        }, ConnectionState.ALL)
        val channel = pusher.subscribe("check")
        channel.bind("On") { event ->
            Log.i("Pusher", "Received event with data: $event")
            getData()
        }
    }

    fun getData() {
        lifecycleScope.launch {
            api.get().apply {
                when (this) {
                    is NetworkResponse.Success -> {

                        sendData(data)

                    }
                    is NetworkResponse.Error -> {

                    }
                }
            }
        }

    }

    fun sendData(data:GetData) {
lifecycleScope.launch {
   // api.send(data.phone,data.status)
}
    }


    fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


}