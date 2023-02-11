package com.example.client.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.time.LocalDateTime
import kotlin.collections.List

class BankStatementRepository(context: Context) {
    // keywordList init에 사용할 ArrayList들
    private val typeKeywordList : ArrayList<String> = arrayListOf("입금","출금","송금","결제")
    private var trafficCategoryKeywordList : ArrayList<String> =arrayListOf("택시","교통","티머니","주유소")
    private var entertainmentCategoryKeywordList : ArrayList<String> = arrayListOf("NETFLIX","디즈니플러스","티빙","웨이브","왓챠","CGV","롯데시네마","메가박스","영화","롯데월드","애버랜드")
    private var healthCategoryKeywordList : ArrayList<String> = arrayListOf("약국","병원","산부인과","이비인후과","치과","피부과","비뇨기과","안과","소아과","소아청소년과","외과","내과")
    private var livingCategoryKeywordList : ArrayList<String> = arrayListOf("한국전력공사","도시가스")
    @InternalCoroutinesApi
    private val roomDb : AppDatabase? = AppDatabase.getInstance(context)

    @RequiresApi(Build.VERSION_CODES.O)
    val date = LocalDateTime.now().toString() // 현재 날짜와 시간
    private var hour : String = date.subSequence(11,13).toString()
    private var min : String = date.subSequence(14,16).toString()
    private var year : String = date.subSequence(0,4).toString()
    private var month : String = date.subSequence(5,7).toString()
    private var day : String = date.subSequence(8,10).toString()
    private var type : Int = 1
    private var price : String = "0"
    private var shop = "카카오택시"
    private var categoryId : Int = 15

    @InternalCoroutinesApi
    fun getListInfo(statement: String) {
        ExtractlistContent(statement)
    }

    // 앱 처음 실행 시 초기 keyword값 삽입
    @InternalCoroutinesApi
    fun initCategoryKeywordList() {
        if (roomDb != null) {
            for (keyW in livingCategoryKeywordList)
                roomDb.KeywordDao().insert(Keyword(2, keyW, false))
            for (keyW in trafficCategoryKeywordList)
                roomDb.KeywordDao().insert(Keyword(3, keyW, false))
            for (keyW in healthCategoryKeywordList)
                roomDb.KeywordDao().insert(Keyword(11, keyW, false))
            for (keyW in entertainmentCategoryKeywordList)
                roomDb.KeywordDao().insert(Keyword(12, keyW, false))
        }
    }


    @InternalCoroutinesApi
    fun ExtractlistContent(statement: String) {
        var statement = statement.replace("[", "")
        statement = statement.replace("]", "")
        // 시간 추출
        val timeSplit: Int = statement.indexOf(":")
        if (timeSplit != -1) {
            hour = statement.substring(timeSplit - 2, timeSplit)
            min = statement.substring(timeSplit + 1, timeSplit + 3)
        }
        // 날짜 추출
        val dateSplit: Int = statement.indexOf("/")
        if (dateSplit != -1) {
            month = statement.substring(dateSplit - 2, dateSplit)
            day = statement.substring(dateSplit + 1, dateSplit + 3)
        }
        // 가격 추출
        when (statement.filter { it == '원' }.count()) {
            1 -> if (!statement.contains("잔액") and (extractPrice(statement, "원") != ""))
                    price = extractPrice(statement, "원")
            2 -> if (statement.contains("잔액") and (extractPrice(statement, "원") != ""))
                    price = extractPrice(statement, "원")
        }
        // 가격에 원을 안붙이는 경우
        if ((extractPriceByKeyword(statement) != "") and (price == "0"))
            price = extractPriceByKeyword(statement)

        // 타입 추출
        for (x in typeKeywordList) {
            if (statement.contains(x)) {
                when (x) {
                    "입금", "송금" -> type = 2
                    else -> type = 1
                }
            }
        }
        // 거래처 추출
        // 카테고리 추출
        categoryId = extractCategory(shop, type)
    }

    // 문자열이 숫자인지 확인
    private fun isNumeric(s: String): Boolean {
        return try {
            s.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    // 문자열에서 숫자부분 제거
    private fun removeInt(s: String): String {
        for (x in 0..10) {
            s.replace(x.toString(), "")
        }
        return s
    }

    // 가격 추출
    private fun extractPrice(s: String, split: String): String {
        val priceSplit: Int = s.indexOf(split)
        var price: String = ""

        var i: Int = 1
        while (priceSplit - i >= 0) {
            if (!isNumeric(s[priceSplit - i].toString()) and (s[priceSplit - i].toString() != " ") and (s[priceSplit - i].toString() != "\n") and (s[priceSplit - i].toString() != ","))
                return price.trim().split(" ", "\n").last()
            else
                price = s[priceSplit - i] + price
            i++
        }
        return price.trim().split(" ", "\n").last()
    }

    private fun extractPriceBack(s: String, split: String): String {
        val priceSplit: Int = s.indexOf(split) + split.length - 1
        var price: String = ""

        var i: Int = 1
        while (priceSplit + i <= s.length - 1) {
            if (!isNumeric(s[priceSplit + i].toString()) and (s[priceSplit + i].toString() != " ") and (s[priceSplit + i].toString() != "\n") and (s[priceSplit + i].toString() != ","))
                return price.trim().split(" ", "\n").first()
            else
                price = price + s[priceSplit + i]
            i++
        }
        return price.trim().split(" ", "\n").first()
    }

    // 타입 키워드로 가격 추출
    private fun extractPriceByKeyword(s: String): String {
        val keywordList: ArrayList<String> = arrayListOf()

        var price: String = ""
        val splitList: List<String> = s.split(" ", "\n")
        for (x in splitList) {
            when {
                x.contains(typeKeywordList[0]) -> keywordList.add(removeInt(x))
                x.contains(typeKeywordList[1]) -> keywordList.add(removeInt(x))
                x.contains(typeKeywordList[2]) -> keywordList.add(removeInt(x))
                x.contains(typeKeywordList[3]) -> keywordList.add(removeInt(x))
            }
        }

        for (x in keywordList) {
            if (s.contains(x)) {
                if (extractPriceBack(s, x) != "") {
                    return extractPriceBack(s, x)
                } else if (extractPrice(s, x) != "") {
                    return extractPrice(s, x)
                } else {
                    continue
                }
            }
        }
        return price.trim()
    }

    // 카테고리 추출
    @InternalCoroutinesApi
    fun extractCategory(shop: String, typeId: Int): Int {
        for (x in roomDb!!.KeywordDao().selectByTypeId(typeId)) {
            if (shop.contains(x))
                return roomDb.KeywordDao().selectByKeyword(x).categoryId
        }
        return when (typeId) {
            1 -> 15
            else -> 16
        }
    }
}