package com.example.priyamvad.hostel_allotmen_final

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth



public class Main_Screen:AppCompatActivity() {

    var useradmin_radiogroup: RadioGroup? = null
    var username: EditText? = null
    var password: EditText? = null
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
        useradmin_radiogroup = findViewById(R.id.user_admin_RadioGroup)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        mAuth = FirebaseAuth.getInstance()
        useradmin_radiogroup?.setOnCheckedChangeListener { radioGroup, i ->
            if(i==R.id.Admin_radioButton)
            {
                username?.visibility = View.VISIBLE
                password?.visibility = View.VISIBLE
            }
            else
            {
                username?.visibility = View.INVISIBLE
                password?.visibility = View.INVISIBLE
            }
        }

    }


    fun continueonclick(v: View) {

        var selectedId = useradmin_radiogroup?.checkedRadioButtonId
        if (selectedId == R.id.User_radioButton) {

            var i = Intent(this@Main_Screen, User::class.java)
            startActivityForResult(i, 1)

        }
        else {

                    if (username?.text.toString() == "" || password?.text.toString() == "") {
                        Toast.makeText(this, "Please fill the required username or password field to continue", Toast.LENGTH_LONG).show()
                    } else {
                        mAuth?.signInWithEmailAndPassword(username?.text.toString(), password?.text.toString())?.addOnCompleteListener({
                            if (it.isSuccessful) {
                                var i = Intent(this@Main_Screen, admin_user_front_page::class.java)
                                startActivityForResult(i, 2)
                            } else {
                                Toast.makeText(this@Main_Screen, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })

                    }
                }
            username?.setText("")
            password?.setText("")

        }
    }



