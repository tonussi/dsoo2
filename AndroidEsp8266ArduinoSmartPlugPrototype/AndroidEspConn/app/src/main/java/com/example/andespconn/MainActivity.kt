package com.example.andespconn

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.io.IOException
import java.net.ConnectException
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var espIP: String = "192.168.0.25"
    private var searchingEspIpFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        // Set an checked change listener for switch button
        tomadaLampadaSala.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                sendRelayOn()
                Log.i("RELAY", "ON")
            } else {
                // The switch is disabled
                sendRelayOff()
                Log.i("RELAY", "OFF")
            }
        }

        if (esp_ip_address.text.isNullOrBlank()) {
            espIP = "192.168.0.31"
        }

        b_use_this_server.setOnClickListener {
            espIP = esp_ip_address.text.toString()
        }

        b_try_find_esp_server.setOnClickListener {
            if (!searchingEspIpFlag) findEspServer() else toast("Ainda não...")
        }

        b_check_pir_status.setOnClickListener {
            getPirStatus()
        }
    }

    private fun getPirStatus() {
        doAsync {
            var url = URL("http://${espIP}/pir")
            val response = url.readText()
            uiThread {
                toast(response)
            }
        }
    }

    private fun findEspServer() {
        searchingEspIpFlag = true

        doAsync {
            var ipText: String = ""

            for (i in 50..255) {
                try {
                    var url = URL("http://192.168.0.${i}/up")
                    ipText = "192.168.0.${i}"

                    var http_get_result: String = url.readText()
                    Log.i("READTEXT URL: ", http_get_result)

                    var respJsonAux = JSONObject(http_get_result)
                    println(respJsonAux)

                    if (http_get_result.contains("esp8266", ignoreCase = true)) {
                        break
                    }

                } catch (e: ConnectException) {
                    Log.i("CONNECT_EX CAUSE: ", e.cause.toString())
                }
            }

            uiThread {
                toast(ipText)
                esp_ip_address.setText(ipText)
                searchingEspIpFlag = false
            }
        }
    }

    /**
     * Given a URL, sets up a connection and gets the HTTP response body from the server.
     * If the network request is successful, it returns the response body in String form. Otherwise,
     * it will throw an IOException.
     */
    @Throws(IOException::class)
    private fun hitUrl(url: URL) {
        doAsync {
            val response = url.readText()
            uiThread {
                toast(response)
            }
        }
    }

    private fun sendRelayOn() {
        hitUrl(URL("http://${espIP}/relay/on"))
    }

    private fun sendRelayOff() {
        hitUrl(URL("http://${espIP}/relay/off"))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
