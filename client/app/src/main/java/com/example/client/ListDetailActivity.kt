package com.example.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.databinding.ActivityListDetailBinding

class ListDetailActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityListDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.listDetailButton.text = getText(R.string.finish_button)
        viewBinding.listDetailMemoContent.setText("공대에서 초밥")
        viewBinding.listDetailTime.text = "2022년 11월 12일 17:00"
        viewBinding.listDetailPlace.text = "(주)우아한형제들"
        viewBinding.listDetailPrice.text = "11,650 원"
        viewBinding.listDetailCategory.text = "카페·간식"
        viewBinding.listDetailBubble.text = "(주)우아한형제들 내역의 카테고리가 선택한 카테고리로 모두 바뀌게 돼요!"
    }
}