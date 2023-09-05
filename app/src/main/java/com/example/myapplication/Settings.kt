package com.example.myapplication

val leakCanaryEnabled = Booleans(
    current = true,
    default = false,
    name = "leakCanaryEnabled",
    sectionId = "fcs"
)

val chuckerEnabled = Booleans(
    current = true,
    default = false,
    name = "chuckerEnabled",
    sectionId = "fcs"
)

val imitateApplication = Strings(
    current = "finance",
    options = listOf("News", "Finance", "Sports"),
    default = "News",
    name = "imitateApp",
    sectionId = "gcs"
)

val endPoint = Strings(
    current = "prod",
    options = listOf("prod", "staging", "local"),
    default = "local",
    name = "endPoint",
    sectionId = "gcs"
)


//fun main() {
//    runBlocking {
//
//        val x = listOf(
//            booleans1,
//            bool2,
//            booleans1,
//            bool2
//        )
//            .asFlow()
//            .onEach { value ->
//                //println("value : ${value.id}")
//                val y = value.copy()
//                println(y)
//
//            }.collect()
//    }
//}



