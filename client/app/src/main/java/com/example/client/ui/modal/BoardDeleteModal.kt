package com.example.client.ui.modal

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.client.APIObject
import com.example.client.api.DeleteDetailRequestData
import com.example.client.api.ResponseData
import com.example.client.api.api
import com.example.client.data.adapter.RecordAdapter
import com.example.client.databinding.ModalBoardDeleteBinding
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InternalCoroutinesApi
class BoardDeleteModal(private val context : AppCompatActivity, val userId:Int, private val boardList: RecyclerView) {
    private lateinit var viewBinding :ModalBoardDeleteBinding
    private val dialog = Dialog(context)
    private val request: api = APIObject.getInstance().create(api::class.java)

    fun show(){
        viewBinding = ModalBoardDeleteBinding.inflate(context.layoutInflater)

        dialog.setContentView(viewBinding.root) //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false) //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        params?.height = (deviceWidth * 0.7).toInt()
        dialog.window?.attributes = params as WindowManager.LayoutParams

        //삭제하기 버튼 눌렀을 때
        viewBinding.modalDelete.setOnClickListener {
            val adapter = boardList.adapter as RecordAdapter
            val deleteItems = adapter.selectedItem
            deleteDetail(context,userId,DeleteDetailRequestData(deleteItems), adapter, boardList)

            dialog.dismiss()
        }
        //아니요 버튼 눌렀을 때
        viewBinding.modalCancel.setOnClickListener {
            dialog.dismiss()
        }
        // 'X' 눌렀을 때
        viewBinding.modalClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
    private fun deleteDetail(context: Context, userId: Int, requestBody: DeleteDetailRequestData, adapter: RecordAdapter, boardList: RecyclerView){
        val call = request.deleteDetail(userId,requestBody)
        call.enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if(response.body()!!.isSuccess){
                    adapter.deleteRecordList(requestBody.detailList as ArrayList<Int>)
                    boardList.adapter = adapter
                    boardList.adapter?.notifyDataSetChanged()
                    Toast.makeText(context, "내역이 성공적으로 삭제되었습니다", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "내역이 삭제되지 않았습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
                println(response.body()?.message)
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(context, "내역이 삭제되지 않았습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }

        })
    }


}