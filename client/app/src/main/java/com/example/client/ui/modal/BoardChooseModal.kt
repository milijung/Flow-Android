package com.example.client.ui.modal

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.APIObject
import com.example.client.api.HttpConnection
import com.example.client.api.JoinDetailData
import com.example.client.api.ResponseData
import com.example.client.api.api
import com.example.client.data.AppDatabase
import com.example.client.data.Detail
import com.example.client.data.adapter.BoardChooseModalAdapter
import com.example.client.data.adapter.CalendarAdapter
import com.example.client.data.adapter.ItemDecoration
import com.example.client.data.adapter.RecordAdapter
import com.example.client.databinding.ModalBoardChooseBinding
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.abs

class BoardChooseModal(private val context : AppCompatActivity, val userId:Int, val boardList : RecyclerView, val selectedDetails:List<Detail>) {

    private lateinit var viewBinding : ModalBoardChooseBinding
    private val dialog = Dialog(context)
    @InternalCoroutinesApi
    val boardAdapter : RecordAdapter= boardList.adapter as RecordAdapter
    @InternalCoroutinesApi
    val prev = boardAdapter.datas
    @InternalCoroutinesApi
    val itemList = boardAdapter.selectedItem
    private val request: api = APIObject.getInstance().create(api::class.java)
    @InternalCoroutinesApi
    fun show(){
        viewBinding =ModalBoardChooseBinding.inflate(context.layoutInflater)

        dialog.setContentView(viewBinding.root) //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false) //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        val params: ViewGroup.LayoutParams? = dialog.window?.attributes
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
            joinDetail(context,userId, JoinDetailData(integratedId,detailIdList),boardAdapter, boardList)
            dialog.dismiss()
        }
        // 'X' 눌렀을 때
        viewBinding.modalClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    @InternalCoroutinesApi
    fun joinDetail(context: Context, userId:Int, requestBody:JoinDetailData, adapter: RecordAdapter, boardList: RecyclerView){
        val call = request.joinDetail(userId,requestBody)
        call.enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if(response.body()!!.isSuccess){
                    val updateList = ArrayList<Detail>()
                    var integratedItemIndex = -1
                    var price = 0
                    var integratedItem : Detail = prev[0]
                    for(d in prev){
                        when (d.detailId) {
                            !in itemList -> updateList.add(d)
                            requestBody.integratedId -> {
                                integratedItemIndex = updateList.size
                                integratedItem = d
                            }
                            else -> {
                                price += d.price
                            }
                        }
                    }
                    integratedItem.integratedId = integratedItem.detailId
                    integratedItem.price = kotlin.math.abs(price)
                    integratedItem.typeId = when(price>0){
                        true->2
                        else->1
                    }
                    updateList.add(integratedItemIndex,integratedItem)
                    adapter.updateRecordList(updateList,1)
                    boardList.adapter = adapter
                    boardList.adapter?.notifyDataSetChanged()
                    Toast.makeText(context, "내역이 성공적으로 통합되었습니다", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(context, "내역이 통합되지 않았습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(context, "내역이 통합되지 않았습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }

        })
    }


}