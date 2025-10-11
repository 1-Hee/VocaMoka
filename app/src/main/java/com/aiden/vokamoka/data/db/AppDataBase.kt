package com.aiden.vokamoka.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aiden.vokamoka.BuildConfig
import com.aiden.vokamoka.data.dao.*
import com.aiden.vokamoka.data.model.*

@Database(entities = [
    UserInfo::class, Language::class, WordCategory::class,
    Concept::class, Expression::class, WordPool::class, StudySchedule::class
 ], version = 1, exportSchema = false
)
@TypeConverters(AppConverter::class)
abstract class AppDataBase : RoomDatabase(){

    // dao
    abstract fun getUserInfoDao(): UserInfoDao
    abstract fun getLanguageDao(): LanguageDao
    abstract fun getWordCategoryDao(): WordCategoryDao
    abstract fun getConceptDao(): ConceptDao
    abstract fun getExpressionDao(): ExpressionDao
    abstract fun getWordPoolDao(): WordPoolDao
    abstract fun getStudyScheduleDao(): StudyScheduleDao

    companion object {
        const val DB_NAME:String = "${BuildConfig.APPLICATION_ID}_data_base"

        @Volatile
        private var INSTANCE:AppDataBase? = null
        fun getInstance(context: Context):AppDataBase{
            // context.deleteDatabase(DB_NAME)
            if(INSTANCE==null){
                synchronized(this){
                    val instance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDataBase::class.java,
                            DB_NAME
                        )
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }else {
                return INSTANCE!!
            }
        }
    }
}