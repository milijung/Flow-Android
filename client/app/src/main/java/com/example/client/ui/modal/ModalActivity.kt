package com.example.client.ui.modal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.R
import com.example.client.data.Detail
import com.example.client.databinding.ActivityAddCategoryBinding
import com.example.client.databinding.ActivityModalBinding

class ModalActivity : AppCompatActivity() {
    private lateinit var viewBinding:ActivityModalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityModalBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var list = arrayListOf(Detail(1,1,"2023","1","4",
            "11","12","dks",1,true,false,
            "hi",-1,0))
        list.add(Detail(1,1,"2023","1","4",
            "11","12","dks",1,true,false,
            "hi",-1,5))
        list.add(Detail(1,1,"2023","1","4",
            "11","12","dks",1,true,false,
            "hi",-1,6))

        val detailId=list.map { it.detailId }


        //val dialog = BoardChooseModal(this,1, list)

        //dialog.show()

        val dialog = BoardDeleteModal(this,1,detailId)
        dialog.show()



    }
}