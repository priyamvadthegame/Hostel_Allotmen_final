package com.example.priyamvad.hostel_allotmen_final

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

public class admin_user_front_page: AppCompatActivity() {

    var mauth:FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_user_front_page)
        mauth=FirebaseAuth.getInstance()
        var welcome_user:TextView=findViewById(R.id.username_welcome_text)
        var current_user_email=mauth?.currentUser?.email.toString()
        var name_of_user=current_user_email?.substring(0,current_user_email.indexOf("@"))
        welcome_user.setText("WELCOME"+" "+name_of_user.toUpperCase())
    }
    fun change_room_status_button(v:View)
    {
        var i=Intent(this@admin_user_front_page,change_room_status_button::class.java)
        startActivityForResult(i,1)
    }


}