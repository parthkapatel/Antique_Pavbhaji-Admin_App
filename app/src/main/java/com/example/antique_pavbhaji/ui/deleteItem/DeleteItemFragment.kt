package com.example.antique_pavbhaji.ui.deleteItem

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.antique_pavbhaji.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_deleteitem.*
import kotlinx.android.synthetic.main.fragment_updateitem.*

class DeleteItemFragment : Fragment() {

    companion object {
        fun newInstance() = DeleteItemFragment()
    }

    private lateinit var viewModel: DeleteItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_deleteitem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FetchCategory()
        btndeleteitem.setOnClickListener {
            prgbardel.visibility = View.VISIBLE
            var myRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()

            var Category_name = spincatvaluedel.selectedItem.toString()
            var Item_name = spinitemvaluedel.selectedItem.toString()
            myRef.child("admin/items/$Category_name/$Item_name").removeValue()
            clearField()
            prgbardel.visibility = View.INVISIBLE
            Snackbar.make(
                view,
                "Item Delete Successfully",
                Snackbar.LENGTH_LONG
            ).setAction("Action", null).show()


        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DeleteItemViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun clearField()
    {
        FetchCategory()
        FetchItem()
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
        spincatvaluedel.adapter = adap
        FetchItem()
    }

    fun FetchItem(){
        spincatvaluedel.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                /* Fetch Item Key Names And Display In Item Spinner Value Based On Category Selected*/

                var adap: ArrayAdapter<String>
                var Category_name = spincatvaluedel.selectedItem.toString()
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
                spinitemvaluedel.adapter = adap

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }

}