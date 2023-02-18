package com.example.client.ui.navigation

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.adapter.RecordAdapter
import com.example.client.databinding.ActivityBottomNavigationBinding
import com.example.client.ui.modal.BoardChooseModal
import com.example.client.ui.modal.BoardDeleteModal
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.properties.Delegates

@InternalCoroutinesApi
class BottomNavigationActivity() : AppCompatActivity(), RecordAdapter.OnRecordLongClickListener {
    private lateinit var viewBinding: ActivityBottomNavigationBinding
    private var pageId by Delegates.notNull<Int>()
    private lateinit var bottomSheetLayout : LinearLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout> // bottomSheetBehavior

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        bottomSheetLayout = viewBinding.bottomSheetLayout.bottomSheetLinearLayout
        val intent : Intent = intent
        pageId = intent.getIntExtra("pageId",0)
        AndroidThreeTen.init(this)
        setContentView(viewBinding.root)
        val roomDb = AppDatabase.getInstance(this)
        val userId = roomDb!!.UserDao().getUserId()
        // run을 쓰면 연결된 요소에 코드를 바로 작성 가능
        viewBinding.bottomNav.run{
            setOnItemSelectedListener {
                when(it.itemId){
                    R.id.menu_home -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.navContainer.id, HomeFragment())
                            .commitAllowingStateLoss()
                        viewBinding.bottomSheetRoot.visibility = View.GONE
                    }
                    R.id.menu_board ->{
                        val boardFragment = BoardFragment()
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.navContainer.id, boardFragment)
                            .commitAllowingStateLoss()
                        initializePersistentBottomSheet()
                        viewBinding.bottomSheetLayout.bottomListDelete.setOnClickListener {
                            val boardList = boardFragment.getBoardList()
                            val boardDeleteModal = BoardDeleteModal(this@BottomNavigationActivity, userId,boardList)
                            boardDeleteModal.show()
                        }
                        viewBinding.bottomSheetLayout.bottomListIntegrate.setOnClickListener {
                            val boardList = boardFragment.getBoardList()
                            val selectedDetails = boardFragment.getSelectedDetail()
                            val boardChooseModal = BoardChooseModal(this@BottomNavigationActivity, userId, boardList, selectedDetails)
                            boardChooseModal.show()
                        }
                    }
                    R.id.menu_calendar ->{
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.navContainer.id, CalendarFragment())
                            .commitAllowingStateLoss()
                        viewBinding.bottomSheetRoot.visibility = View.GONE
                    }
                    R.id.menu_setting ->{
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.navContainer.id, SettingFragment())
                            .commitAllowingStateLoss()
                        viewBinding.bottomSheetRoot.visibility = View.GONE
                    }
                }
                // 리턴값을 true와 false로 받음. 일반적으로는 true로 바로 변경되도록 하면 됨
                true
            }
            // 함수지만 변수처럼 쓸 수 있음. 현재 선택한 item을 알려줄 수 있음
            changeSelectedFragment(pageId)
        }
    }
    private fun changeSelectedFragment(index: Int){
        when(index){
            0 -> viewBinding.bottomNav.selectedItemId = R.id.menu_home
            1 -> viewBinding.bottomNav.selectedItemId = R.id.menu_board
            2 -> viewBinding.bottomNav.selectedItemId = R.id.menu_calendar
            3 -> viewBinding.bottomNav.selectedItemId = R.id.menu_setting
        }
    }

    override fun onRecordLongClickStart() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onRecordLongClickFinish() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
    // Persistent BottomSheet 초기화
    private fun initializePersistentBottomSheet() {
        // BottomSheetBehavior에 layout 설정
        viewBinding.bottomSheetRoot.visibility = View.VISIBLE
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

}