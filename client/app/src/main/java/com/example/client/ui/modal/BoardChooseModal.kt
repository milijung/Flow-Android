package com.example.client.ui.modal

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.api.HttpConnection
import com.example.client.api.JoinDetailData
import com.example.client.data.AppDatabase
import com.example.client.data.Detail
import com.example.client.data.adapter.BoardChooseModalAdapter
import com.example.client.data.adapter.CalendarAdapter
import com.example.client.data.adapter.ItemDecoration
import com.example.client.data.adapter.RecordAdapter
import com.example.client.databinding.ModalBoardChooseBinding
import kotlinx.coroutines.InternalCoroutinesApi

class BoardChooseModal(private val context : AppCompatActivity, val userId:Int, boardList : RecyclerView, val selectedDetails:List<Detail>) {

    private lateinit var viewBinding : ModalBoardChooseBinding
    private val dialog = Dialog(context)
    private val httpConnection : HttpConnection = HttpConnection()
    @InternalCoroutinesApi
    val adapter : RecordAdapter= boardList.adapter as RecordAdapter
    @InternalCoroutinesApi
    val itemList = adapter.selectedItem

    @InternalCoroutinesApi
    fun show(){
        viewBinding =ModalBoardChooseBinding.inflate(context.layoutInflater)

        dialog.setContentView(viewBinding.root) //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false) //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog.window?.attributes = params as WindowManager.LayoutParams
        //리스트 붙이기
        val adapter=BoardChooseModalAdapter(selectedDetails)
        val decoration = ItemDecoration(20)
        viewBinding.modalList.addItemDecoration(decoration)
        viewBinding.modalList.adapter=adapter
        viewBinding.modalList.layoutManager=LinearLayoutManager(context)


        var integratedId=itemList[0]//첫번째 내역의 아이디 값이 디폴트
        var detailIdList=itemList //선택된 detailId만 가져오기

        adapter.setOnItemClickListener(object : BoardChooseModalAdapter.OnItemClickListener{
            override fun onItemClick(v: View, position: Int) {
                //adapter에서 클릭된 아이템 값 받아오기
                val data = selectedDetails[position]
                integratedId=data.detailId
            }
        })

        //선택 완료 버튼 눌렀을 때
        viewBinding.modalChoose.setOnClickListener {
            //서버에서 통합하기
            httpConnection.joinDetail(context,userId, JoinDetailData(integratedId,detailIdList))
            
            dialog.dismiss()
        }
        // 'X' 눌렀을 때
        viewBinding.modalClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }



}