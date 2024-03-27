package com.ksnk.tiktokdownloader.di.module

import android.app.Application
import androidx.room.Room
import org.koin.dsl.module

//fun provideDataBase(application: Application): PostDataBase =
//        Room.databaseBuilder(
//            application,
//            PostDataBase::class.java,
//            "table_post"
//        ).
//        fallbackToDestructiveMigration().build()
//
//    fun provideDao(postDataBase: PostDataBase): PostDao = postDataBase.getPostDao()
//
//
//    val dataBaseModule= module {
//        single { provideDataBase(get()) }
//        single { provideDao(get()) }
//    }
