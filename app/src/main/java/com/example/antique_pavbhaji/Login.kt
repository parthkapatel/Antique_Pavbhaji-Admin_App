package com.example.antique_pavbhaji

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnlogin.setOnClickListener {view ->

            var loginuser ="Manish"
            var loginpwd = "2043"
            lgprgbar.visibility = View.VISIBLE
            if (loguser.text.isEmpty() || logpass.text.isEmpty())
            {
                lgprgbar.visibility = View.INVISIBLE
                Snackbar.make(
                    view,
                    "All Fields Must Be Required!!",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()
            }
            else{
                if(loguser.text.toString() == loginuser && logpass.text.toString() == loginpwd){
                    var intent= Intent(this,dashboard::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    lgprgbar.visibility = View.INVISIBLE
                    Snackbar.make(
                        view,
                        "Incorrect username or password!!",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null).show()

                }
            }
        }
    }
}