package com.example.antique_pavbhaji.ui.addItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.antique_pavbhaji.R
import com.example.antique_pavbhaji.add_item
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_additem.*


class AddItemFragment : Fragment() {

    private lateinit var additemViewModel: AddItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        additemViewModel =
            ViewModelProviders.of(this).get(AddItemViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_additem, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnadditem.setOnClickListener { view ->
            var category = spincatvalue.selectedItem.toString()
            var item_name = txtlblitem.text.toString()
            var amount = txtlblamount.text.toString()
            var description = txtlbldesc.text.toString()

            if(category.isEmpty() || item_name.isEmpty() || amount.isEmpty() || description.isEmpty())
            {

                Snackbar.make(
                    view,
                    "All Fields Must Be Required!!",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()

            }
            else{
                    prgbar.visibility = View.VISIBLE
                    val query2: Query = FirebaseDatabase.getInstance().reference.child("admin/items/")
                        .orderByChild("item_name").equalTo(item_name)

                    query2.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.childrenCount > 0) {
                                // 1 or more Item exist"
                                txtlblitem.text.clear()
                                prgbar.visibility = View.INVISIBLE
                                clearField()
                                Snackbar.make(
                                    view,
                                    "Item Is Already Exists!!",
                                    Snackbar.LENGTH_LONG
                                ).setAction("Action", null).show()
                            }
                            else{
                                val database = FirebaseDatabase.getInstance()
                                val myRef = database.getReference("admin/items/$category/")
                                val key = myRef.push().key
                                myRef.child("$item_name").setValue(key?.let { add_item(it,category,item_name, amount, description) }).addOnCompleteListener{
                                    prgbar.visibility = View.INVISIBLE
                                    clearField()
                                    Snackbar.make(
                                        view,
                                        "Item Insert Successfully",
                                        Snackbar.LENGTH_LONG
                                    ).setAction("Action", null).show()
                                }.addOnFailureListener{
                                    prgbar.visibility = View.INVISIBLE
                                    clearField()
                                    Snackbar.make(
                                        view,
                                        "Something Wrong",
                                        Snackbar.LENGTH_LONG
                                    ).setAction("Action", null).show()
                                }
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
            }

        }
    }

    fun clearField()
    {
        spincatvalue.setSelection(0)
        txtlblitem.text.clear()
        txtlblamount.text.clear()
        txtlbldesc.text.clear()
    }
}