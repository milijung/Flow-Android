package com.example.client.ui.modal

import android.app.Dialog
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.api.HttpConnection
import com.example.client.api.JoinDetailData
import com.example.client.data.AppDatabase
import com.example.client.data.Detail
import com.example.client.data.adapter.BoardChooseModalAdapter
import com.example.client.data.adapter.CalendarAdapter
import com.example.client.databinding.ModalBoardChooseBinding

class BoardChooseModal(private val context : AppCompatActivity,val userId:Int,
                       private val recordList:List<Detail>) {

    private lateinit var viewBinding : ModalBoardChooseBinding
    private val dialog = Dialog(context)
    private val httpConnection : HttpConnection = HttpConnection()


    fun show(){
        viewBinding =ModalBoardChooseBinding.inflate(context.layoutInflater)

        dialog.setContentView(viewBinding.root) //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false) //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        //리스트 붙이기
        val adapter=BoardChooseModalAdapter(recordList)
        viewBinding.modalList.adapter=adapter
        viewBinding.modalList.layoutManager=LinearLayoutManager(context)


        var integratedId=recordList[0].detailId //첫번째 내역의 아이디 값이 디폴트
        var detailIdList=recordList.map{it.detailId} //선택된 detailId만 가져오기

        adapter.setOnItemClickListener(object : BoardChooseModalAdapter.OnItemClickListener{
            override fun onItemClick(v: View, position: Int) {
                //adapter에서 클릭된 아이템 값 받아오기
                val data = recordList[position]

                integratedId=data.detailId
            }

        })

        //선택 완료 버튼 눌렀을 때
        viewBinding.modalChoose.setOnClickListener {
            //서버에서 통합하기
            httpConnection.joinDetail(userId, JoinDetailData(integratedId,detailIdList))

            dialog.dismiss()
        }
        // 'X' 눌렀을 때
        viewBinding.modalClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }



}