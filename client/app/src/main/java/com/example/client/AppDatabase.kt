package com.example.client

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.newSingleThreadContext

@Database(entities = [Category::class, List::class], version = 2, autoMigrations = [AutoMigration(from = 1, to=2)],exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun CategoryDao() : CategoryDao
    abstract fun ListDao() : ListDao

    // 전역적으로 사용할 수 있는 함수, 변수를 담을 수 있음.
    companion object{
        private var appDatabase:AppDatabase? = null

        // 여러 thread에서 동시에 하나의 자원에 접근하는 것을 방지하기 위해, synchronized 작성
        @Synchronized
        fun getCategoryInstance(context: Context): AppDatabase?{
            // null 체크 필수
            if(appDatabase == null){
                // null이면 database 점유
                synchronized(AppDatabase::class.java){
                    appDatabase = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "category"
                    ).allowMainThreadQueries().build() // allowMainThreadQueries()는 Main thread에서 query를 허용하는 함수
                }

            }
            return appDatabase
        }
        @Synchronized
        fun getListInstance(context: Context): AppDatabase?{
            // null 체크 필수
            if(appDatabase == null){
                // null이면 database 점유
                synchronized(AppDatabase::class.java){
                    appDatabase = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "list"
                    ).allowMainThreadQueries().build() // allowMainThreadQueries()는 Main thread에서 query를 허용하는 함수

                }


            }
            return appDatabase
        }
    }
}