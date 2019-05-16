package com.example.priyamvad.hostel_allotmen_final

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.ArrayAdapter
import android.widget.Toast


class User : AppCompatActivity() {
    var db: FirebaseFirestore? = null
    var hostel_selected: String? = null
    var floor_selected: String? = null
    var hostel_list = ArrayList<String>()
    var result_list = ArrayList<String>()
    var floor_list = ArrayList<String>()
    var select_hostel_spinner: Spinner? = null
    var result: Spinner? = null
    var select_floor_spinner: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        select_floor_spinner = findViewById(R.id.spinner2)
        select_hostel_spinner = findViewById(R.id.spinner3)
        result = findViewById(R.id.spinner1)
        db = FirebaseFirestore.getInstance()
        hostel_spinner()
        floor_spinner()

    }

    fun hostel_spinner() {
        db?.collection("Hostel")
                ?.get()
                ?.addOnSuccessListener { result ->
                    for (document in result) {

                        hostel_list?.add(document.id)

                    }
                    var adapter: ArrayAdapter<String> = ArrayAdapter(this@User, android.R.layout.simple_spinner_item, hostel_list)
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
                        result_list.clear()
                        for (document in result) {

                            floor_list.add(document.id)
                        }

                        var adapter: ArrayAdapter<String> = ArrayAdapter(this@User, android.R.layout.simple_spinner_item, floor_list)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        select_floor_spinner?.adapter = adapter

                    }
                }


                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }

            select_floor_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    var selectedItem = parent.getItemAtPosition(position).toString()
                    floor_selected = selectedItem
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }

        }


    fun get_availability(v: View) {
        if (floor_list.isEmpty()) {
            Toast.makeText(this@User, "Hostel addition is still in progress please check after some time", Toast.LENGTH_LONG).show()
        } else {
            var dummy_list1: ArrayList<String>? = null


            db?.collection("Hostel")?.document(hostel_selected!!)?.collection(hostel_selected!!)?.document(floor_selected!!)?.collection(floor_selected!!)?.get()?.addOnSuccessListener {
                result_list.clear()
                for (document1 in it) {
                    result_list.add(document1.id + ":")
                    dummy_list1?.clear()
                    db?.collection("Hostel")?.document(hostel_selected.toString())?.collection(hostel_selected.toString())?.document(floor_selected!!)?.collection(floor_selected!!)?.document(document1.id)?.get()?.addOnSuccessListener({ result ->
                        if (result.exists()) {
                            var m = result.data
                            dummy_list1 = ArrayList<String>(m.keys)

                            for (bed_nos in dummy_list1!!) {
                                if (m.getValue(bed_nos) == true) {
                                    result_list.add(bed_nos)
                                }

                            }
                        } else {
                            Toast.makeText(this@User, "No such document exists", Toast.LENGTH_LONG)
                        }
                    })
                }
                var adapter: ArrayAdapter<String> = ArrayAdapter(this@User, android.R.layout.simple_spinner_item, result_list)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                result?.adapter = adapter


            }

        }


    }
}








