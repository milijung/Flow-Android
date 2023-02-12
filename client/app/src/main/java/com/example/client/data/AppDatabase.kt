package com.example.client.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlin.coroutines.CoroutineContext

@Database(entities = [Category::class, Detail::class, Keyword::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun CategoryDao() : CategoryDao
    abstract fun ListDao() : ListDao
    abstract fun KeywordDao() : KeywordDao
    abstract fun UserDao() : UserDao

    // 전역적으로 사용할 수 있는 함수, 변수를 담을 수 있음.
    companion object{
        private var appDatabase: AppDatabase? = null

        // 여러 thread에서 동시에 하나의 자원에 접근하는 것을 방지하기 위해, synchronized 작성
        @InternalCoroutinesApi
        @Synchronized
        fun getInstance(context: Context): AppDatabase?{
            // null 체크 필수
            if(appDatabase == null){
                // null이면 database 점유
                synchronized(AppDatabase::class.java){
                    appDatabase = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "localDb"
                    ).allowMainThreadQueries().build() // allowMainThreadQueries()는 Main thread에서 query를 허용하는 함수
                }

            }
            return appDatabase
        }
    }
}