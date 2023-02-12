package com.example.client.ui.modal

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import com.example.client.databinding.ModalBoardDeleteBinding
import com.example.client.databinding.ModalSignupDuplicateBinding

class SignUpDuplicateModal(private val context : AppCompatActivity) {

    private lateinit var viewBinding : ModalSignupDuplicateBinding
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogDeleteClickListener

    fun show(){
        viewBinding = ModalSignupDuplicateBinding.inflate(context.layoutInflater)

        dialog.setContentView(viewBinding.root) //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false) //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        //삭제 버튼 눌렀을 때
        viewBinding.modalDelete.setOnClickListener {
            //TODO: 부모 액티비티로 내용을 돌려주기 위해 작성할 코드

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

    fun setOnDeleteClickedListener(listener: OnDialogDeleteClickListener)
    {
        onClickListener = listener
    }

    interface OnDialogDeleteClickListener{
        fun onClicked(content: String)
    }
}