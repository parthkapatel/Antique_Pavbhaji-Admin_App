package com.example.antique_pavbhaji

import android.view.View
import androidx.recyclerview.widget.RecyclerView

data class add_item(var Item_Uid:String,var cat_name:String,var item_name:String,var amount:String,var description:String){
    constructor():this("","","","","")
}


