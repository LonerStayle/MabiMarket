package kr.loner.mabi_market.data

import kr.loner.mabi_market.R

data class Item(
    val id:Long,
    val name:String,
    val sampleImage:Int,
    val image:String,
    val price:Long,
    val allPrice:Long,
    val count:Int,
    val registerTimeStamp:Long,
    val serverType: ServerType = ServerType.RYUTE,
    val myLike:Boolean = false
){

    companion object{
        //5분
        private val fiveMin = 300_000L

        //10분
        private val tenMin = 600_000L

        //30분
        private val thirtyMin = 1_800_000L

        //1시간
        private val oneHour = 3_500_000L

        //2시간
        private val twoHour = 7_200_000L

        //하루
        private val oneDay = 86_400_000L

        //이틀전
        private val twoDay = 172_800_000L


        val MUCK_DATA = listOf(
            Item(
                id = 1,
                name = "망가진 엠블럼",
                sampleImage = R.drawable.ic_sample01,
                image = "",
                price = 1000,
                allPrice = 8000,
                count = 10,
                registerTimeStamp = System.currentTimeMillis(),
                myLike = false
            ),
            Item(
                id = 2,
                name = "절대영도 냉각제",
                sampleImage = R.drawable.ic_sample02,
                image = "",
                price = 1100,
                allPrice = 1100,
                count = 3,
                registerTimeStamp = System.currentTimeMillis(),
                myLike = false
            ),

            Item(
                id = 3,
                name = "축복받은 엔지니어의 동백나무 낫",
                sampleImage = R.drawable.ic_sample03,
                image = "",
                price = 2200,
                allPrice = 2200,
                count = 1,
                registerTimeStamp = System.currentTimeMillis() - fiveMin,
                myLike = false
            ),
            Item(
                id = 4,
                name = "축복받은 야금용 체",
                sampleImage = R.drawable.ic_sample04,
                image = "",
                price = 2000,
                allPrice = 2000,
                count = 1,
                registerTimeStamp = System.currentTimeMillis() - tenMin,
                myLike = false
            ),
            Item(
                id = 5,
                name = "가고일 소드",
                sampleImage = R.drawable.ic_sample05,
                image = "",
                price = 3000,
                allPrice = 3000,
                count = 1,
                registerTimeStamp = System.currentTimeMillis() - thirtyMin,
                myLike = false
            ),
            Item(
                id = 6,
                name = "대어 낚시용 미끼통",
                sampleImage = R.drawable.ic_sample06,
                image = "",
                price = 300,
                allPrice = 30000,
                count = 100,
                registerTimeStamp = System.currentTimeMillis()  - oneHour,
                myLike = false
            ),
            Item(
                id = 7,
                name = "끈질긴 곡괭이",
                sampleImage = R.drawable.ic_sample07,
                image = "",
                price = 2800,
                allPrice = 2800,
                count = 1,
                registerTimeStamp = System.currentTimeMillis() - twoHour,
                myLike = false
            ),
            Item(
                id = 8,
                name = "라인하르트의 용검레이드",
                sampleImage = R.drawable.ic_sample08,
                image = "",
                price = 18000,
                allPrice = 18000,
                count = 1,
                registerTimeStamp = System.currentTimeMillis() - oneDay,
                myLike = false
            ),
            Item(
                id = 9,
                name = "전용 인첸트 스크롤",
                sampleImage = R.drawable.ic_sample09,
                image = "",
                price = 8000,
                allPrice = 8000,
                count = 1,
                registerTimeStamp = System.currentTimeMillis() - twoDay,
                myLike = false
            ),
            Item(
                id = 10,
                name = "전용 인첸트 스크롤",
                sampleImage = R.drawable.ic_sample09,
                image = "",
                price = 4000,
                allPrice = 4000,
                count = 1,
                registerTimeStamp = System.currentTimeMillis() - thirtyMin,
                serverType = ServerType.MANDOLIN,
                myLike = false
            ),

            Item(
                id = 10,
                name = "가고일 소드",
                sampleImage = R.drawable.ic_sample05,
                image = "",
                price = 3000,
                allPrice = 3000,
                count = 1,
                serverType = ServerType.HARF,
                registerTimeStamp = System.currentTimeMillis() - thirtyMin,
                myLike = false
            ),
            Item(
                id = 11,
                name = "망가진 엠블럼",
                sampleImage = R.drawable.ic_sample01,
                image = "",
                price = 900,
                allPrice = 4800,
                count = 8,
                serverType = ServerType.WOLF,
                registerTimeStamp = System.currentTimeMillis(),
                myLike = false
            ),
        )
    }
}
