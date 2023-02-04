package com.example.client.ui.modal

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import com.example.client.databinding.ModalBoardChooseBinding

class BoardChooseModal(private val context : AppCompatActivity) {

    private lateinit var viewBinding : ModalBoardChooseBinding
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogChooseClickListener


    fun show(){
        viewBinding =ModalBoardChooseBinding.inflate(context.layoutInflater)

        dialog.setContentView(viewBinding.root) //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false) //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        //선택 완료 버튼 눌렀을 때
        viewBinding.modalChoose.setOnClickListener {
            //TODO: 부모 액티비티로 내용을 돌려주기 위해 작성할 코드

            dialog.dismiss()
        }
        // 'X' 눌렀을 때
        viewBinding.modalClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

    fun setOnChooseClickedListener(listener: OnDialogChooseClickListener)
    {
        onClickListener = listener
    }

    interface OnDialogChooseClickListener{
        fun onClicked(content: String)
    }
}