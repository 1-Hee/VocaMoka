package com.aiden.vokamoka.data.db

import android.content.Context
import com.aiden.vokamoka.data.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Provides
    fun provideUserInfoDao(database: AppDataBase): UserInfoDao {
        return database.getUserInfoDao()
    }

    @Provides
    fun provideLanguageDao (database: AppDataBase): LanguageDao {
        return database.getLanguageDao()
    }

    @Provides
    fun provideWordCategoryDao (database: AppDataBase): WordCategoryDao {
        return database.getWordCategoryDao()
    }

    @Provides
    fun provideConceptDao (database: AppDataBase): ConceptDao {
        return database.getConceptDao()
    }

    @Provides
    fun provideExpressionDao (database: AppDataBase): ExpressionDao {
        return database.getExpressionDao()
    }

    @Provides
    fun provideWordPoolDao (database: AppDataBase): WordPoolDao {
        return database.getWordPoolDao()
    }

    @Provides
    fun provideStudyScheduleDao (database: AppDataBase): StudyScheduleDao {
        return database.getStudyScheduleDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.getInstance(context)
    }
}