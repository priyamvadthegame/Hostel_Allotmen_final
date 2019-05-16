package com.example.priyamvad.hostel_allotmen_final

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.change_room_status_button.*

class change_room_status_button:AppCompatActivity() {
    var db: FirebaseFirestore? = null
    var hostel_list: ArrayList<String>? = ArrayList<String>()
    var select_hostel_spinner: Spinner? = null
    var room_no_list = ArrayList<String>()
    var floor_list = ArrayList<String>()
    var bed_no_list = ArrayList<String>()
    var hostel_selected: String? = null
    var floor_selected: String? = null
    var room_selected:String?=null
    var bed_selected:String?=null
    var select_floor_spinner: Spinner? = null
    var select_room_spinner: Spinner? = null
    var select_bed_spinner: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_room_status_button)
        db = FirebaseFirestore.getInstance()
        select_hostel_spinner = findViewById(R.id.select_hostel_spinner)
        select_floor_spinner = findViewById(R.id.select_floor_spinner)
        select_room_spinner=findViewById(R.id.select_room_spinner)
        select_bed_spinner=findViewById(R.id.select_bedno__spinner)
        hostel_spinner()
        floor_spinner()
        room_no_spinner()
        bed_no_spinner()

    }

    fun hostel_spinner() {
        db?.collection("Hostel")
                ?.get()
                ?.addOnSuccessListener { result ->
                    for (document in result) {

                        hostel_list?.add(document.id)

                    }
                    var adapter: ArrayAdapter<String> = ArrayAdapter(this@change_room_status_button, android.R.layout.simple_spinner_item, hostel_list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    select_hostel_spinner?.adapter = adapter


                }
                ?.addOnFailureListener { exception ->
                    Log.w("", "Error getting documents.", exception)

                }
    }

    fun floor_spinner() {

        select_hostel_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var selectedItem = parent.getItemAtPosition(position).toString()
                hostel_selected = selectedItem
                db?.collection("Hostel")?.document(selectedItem)?.collection(selectedItem)?.get()?.addOnSuccessListener { result ->
                    floor_list.clear()
                    room_no_list.clear()
                    for (document in result) {

                        floor_list.add(document.id)
                    }

                    var adapter: ArrayAdapter<String> = ArrayAdapter(this@change_room_status_button, android.R.layout.simple_spinner_item, floor_list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    select_floor_spinner?.adapter = adapter

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        }
    }

    fun room_no_spinner() {

        select_floor_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                var selectedItem = parent.getItemAtPosition(position).toString()
                floor_selected=selectedItem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        db?.collection("Hostel")?.document(hostel_selected!!)?.collection(hostel_selected!!)?.document(floor_selected!!)?.collection(floor_selected!!)?.get()?.addOnSuccessListener {
            room_no_list.clear()
            for (document1 in it) {
                room_no_list.add(document1.id + ":")

            }
            var adapter: ArrayAdapter<String> = ArrayAdapter(this@change_room_status_button, android.R.layout.simple_spinner_item, room_no_list)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            select_room_spinner?.adapter = adapter


            select_room_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    var selectedItem = parent.getItemAtPosition(position).toString()
                    room_selected=selectedItem
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }

        }
    }
    fun bed_no_spinner()
    {
        db?.collection("Hostel")?.document(hostel_selected!!)?.collection(hostel_selected!!)?.document(floor_selected!!)?.collection(floor_selected!!)?.document(room_selected!!)?.get()?.addOnSuccessListener({ result ->
            if(result.exists()) {
                var m = result.data
                bed_no_list = ArrayList<String>(m.keys)

                var adapter: ArrayAdapter<String> = ArrayAdapter(this@change_room_status_button, android.R.layout.simple_spinner_item, bed_no_list)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                select_bed_spinner?.adapter = adapter
            }
            else
            {
                Toast.makeText(this@change_room_status_button,"No such document exists",Toast.LENGTH_LONG).show()
            }
        })
        select_bed_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                var selectedItem = parent.getItemAtPosition(position).toString()
              bed_selected=selectedItem
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }
}