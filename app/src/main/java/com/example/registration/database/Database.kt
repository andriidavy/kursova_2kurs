//package com.example.registration.database
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.registration.model.users.Customer
//
//@Database(entities = [Customer::class], version = 1, exportSchema = true)
//abstract class Database_s: RoomDatabase() {
//abstract val customerDAO: CustomerDAO
//
//    companion object {
//        @Volatile
//        private var INSTANCE: Database_s? = null
//        fun getInstance(context: Context): Database_s {
//            synchronized(this) {
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        Database_s::class.java,
//                        "data_database"
//                    ).build()
//                }
//                return instance
//            }
//        }
//    }
//}