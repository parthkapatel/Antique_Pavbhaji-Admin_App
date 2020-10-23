package com.example.antique_pavbhaji.ui.updateItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.antique_pavbhaji.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_updateitem.*


class UpdateItemFragment : Fragment() {


    private lateinit var updateitemViewModel: UpdateItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updateitemViewModel =
            ViewModelProviders.of(this).get(UpdateItemViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_updateitem, container, false)

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Fetch Category Key Names And Display In Category Spinner Value */
        FetchCategory()
        /* on Category Selected Value Fill the Item Spinner From Fetching Data */
        //FetchItem()
        /* on Item Selected Value Fill the Other Data From Fetching Data */
        //FetchData()

        /* Update Data*/
        btnupdateitem.setOnClickListener {
            prgbarup.visibility = View.VISIBLE
            var Category_name = spincatvalueup.selectedItem.toString()
            var Item_name = spinitemvalueup.selectedItem.toString()
            var myRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
            var AmtValue = txtlblamountup.text.toString()
            var DescValue = txtlbldescup.text.toString()

            var c = 0
            if(DescValue.isEmpty() && AmtValue.isNotEmpty())
            {
                c = 1
                myRef.child("admin").child("items").child("$Category_name").child("$Item_name").child("amount").setValue(AmtValue)
            }
            else if(DescValue.isNotEmpty() && AmtValue.isEmpty())
            {
                c=1
                myRef.child("admin").child("items").child("$Category_name").child("$Item_name").child("description").setValue(DescValue)
            }
            if(c==1)
            {
                prgbarup.visibility = View.INVISIBLE
                clearField()
                Snackbar.make(
                    view,
                    "Item Update Successfully",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()
            }
            else
                prgbarup.visibility = View.INVISIBLE

        }

    }

    fun clearField()
    {
        FetchCategory()
        FetchItem()
        txtlblamountup.text.clear()
        txtlbldescup.text.clear()
    }

    fun  FetchCategory(){
        var adap: ArrayAdapter<String>
        var myRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("admin/items/")
        var spinnerDataList: ArrayList<String> = ArrayList()
        //adap = ArrayAdapter(Context,android.R.layout.simple_spinner_dropdown_item,spinnerDataList)
        adap = activity?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                spinnerDataList
            )
        }!!

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    spinnerDataList.add(ds.key.toString())
                }
                adap.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        spincatvalueup.adapter = adap
        FetchItem()
    }

    fun FetchItem(){
        spincatvalueup.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                /* Fetch Item Key Names And Display In Item Spinner Value Based On Category Selected*/

                var adap: ArrayAdapter<String>
                var Category_name = spincatvalueup.selectedItem.toString()
                var myRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("admin/items/$Category_name")

                var spinnerDataList: ArrayList<String> = ArrayList()
                //adap = ArrayAdapter(Context,android.R.layout.simple_spinner_dropdown_item,spinnerDataList)
                adap = activity?.let {
                    ArrayAdapter(
                        it,
                        R.layout.support_simple_spinner_dropdown_item,
                        spinnerDataList
                    )
                }!!

                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (ds in dataSnapshot.children) {
                            spinnerDataList.add(ds.child("item_name").getValue().toString())
                        }
                        adap.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
                spinitemvalueup.adapter = adap
                FetchData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }

    fun FetchData(){
        spinitemvalueup.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                /* Fetch other data Based On Item Selected*/

                var adap: ArrayAdapter<String>
                var Category_name = spincatvalueup.selectedItem.toString()
                var Item_name = spinitemvalueup.selectedItem.toString()
                var myRef: DatabaseReference =
                    FirebaseDatabase.getInstance().getReference("admin/items/$Category_name/$Item_name/")

                var spinnerDataList: ArrayList<String> = ArrayList()
                //adap = ArrayAdapter(Context,android.R.layout.simple_spinner_dropdown_item,spinnerDataList)
                adap = activity?.let {
                    ArrayAdapter(
                        it,
                        R.layout.support_simple_spinner_dropdown_item,
                        spinnerDataList
                    )
                }!!

                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        txtlblamountup.hint = dataSnapshot.child("amount").value.toString()
                        txtlbldescup.hint = dataSnapshot.child("description").value.toString()


                        adap.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }
}