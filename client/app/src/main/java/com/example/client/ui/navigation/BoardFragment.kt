package com.example.client.ui.navigation

//import android.app.Fragment
import android.os.Bundle
//import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.*
import com.example.client.data.AppDatabase
import com.example.client.databinding.FragmentBoardBinding
import kotlinx.android.synthetic.main.fragment_setting.*

class BoardFragment : androidx.fragment.app.Fragment(), View.OnClickListener {

    private val data=listOf(
        RecordData(0,"17일"),
        RecordData(1,"","배달의 민족", "11:00","10000원",
            "철수랑 배달","@drawable/ic_category_food",),
        RecordData(2, "","배달의 민족", "11:00",
            "10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(0,"15일"),
        RecordData(1,"","배달의 민족", "11:00","10000원",
            "철수랑 배달","@drawable/ic_category_food",),
        RecordData(2, "","배달의 민족", "11:00",
            "10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(0,"13일"),
        RecordData(1,"","배달의 민족", "11:00","10000원",
            "철수랑 배달","@drawable/ic_category_food",),
        RecordData(2, "","배달의 민족", "11:00",
            "10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(0,"11일"),
        RecordData(1,"","배달의 민족", "11:00","10000원",
            "철수랑 배달","@drawable/ic_category_food",),
        RecordData(2, "","배달의 민족", "11:00",
            "10000원","철수랑 배달","@drawable/ic_category_food",),


        )

    private lateinit var viewBinding: FragmentBoardBinding
    private var linearLayoutManager: RecyclerView.LayoutManager? = null
    private var recyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    private var bottomNavActivity :BottomNavigationActivity?= context as BottomNavigationActivity?



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBoardBinding.inflate(layoutInflater)




        recyclerAdapter = RecordAdapter(data)
        linearLayoutManager=LinearLayoutManager(bottomNavActivity)

        viewBinding.boardList.layoutManager= LinearLayoutManager(bottomNavActivity)
        viewBinding.boardList.adapter=RecordAdapter(data)

        val decoration = ItemDecoration(20)
        viewBinding.boardList.addItemDecoration(decoration)

        //viewBinding.boardIntegrate.setOnClickListener(bottomNavActivity)
        //viewBinding.boardDelete.setOnClickListener(bottomNavActivity)

        return viewBinding.root




    }



    //하단 버튼 눌렀을 때

    override fun onClick(view: View?) {
        /*


        when(view?.id){
            //통합하기 버튼 눌렀을 때
            viewBinding.boardIntegrate.id -> {
                val dialog = BoardChooseModal(bottomNavActivity)
                dialog.setOnChooseClickedListener() //버튼 클릭 시 모달에 넘겨줄 값 변수에 넣기
                dialog.show()
            }
            //삭제하기 버튼 눌렀을 때
            viewBinding.boardDelete.id->{
                val dialog = BoardDeleteModal(bottomNavActivity)
                dialog.setOnDeleteClickedListener(object: BoardDeleteModal.OnDialogDeleteClickListener{
                    override fun onClick(text: String) {

                    }
                })
                dialog.show()
            }


        }

         */






    }



}