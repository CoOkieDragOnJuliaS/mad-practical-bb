/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.ac.hcw.procrastinot.di

import android.content.ContentValues
import android.content.Context
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import at.ac.hcw.procrastinot.data.DefaultTaskRepository
import at.ac.hcw.procrastinot.data.TaskRepository
import at.ac.hcw.procrastinot.data.source.local.TaskDao
import at.ac.hcw.procrastinot.data.source.local.ToDoDatabase
import at.ac.hcw.procrastinot.data.source.network.NetworkDataSource
import at.ac.hcw.procrastinot.data.source.network.TaskNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.UUID
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTaskRepository(repository: DefaultTaskRepository): TaskRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(dataSource: TaskNetworkDataSource): NetworkDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java,
            "Tasks.db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // === MAD-04.01: Datenbank-Seeding beim ersten Start ===
                // Wir fügen initiale Tasks hinzu, damit der User beim ersten Öffnen der App
                // bereits Beispieldaten sieht.
                seedDatabase(db)
            }
        }).build()
    }

    /**
     * === MAD-04.02: Hilfsfunktion zum Einfügen von Initialdaten ===
     * Nutzt SupportSQLiteDatabase, um direkt SQL-Inserts auszuführen.
     */
    private fun seedDatabase(db: SupportSQLiteDatabase) {
        val initialTasks = listOf(
            Triple("Finish the Android App", "Complete all MAD practical tasks", false),
            Triple("Prepare for Presentation", "Create slides for the final presentation", true),
            Triple("Read the documentation", "Review the Android developer guides", false)
        )

        initialTasks.forEach { (title, description, isCompleted) ->
            val values = ContentValues().apply {
                put("id", UUID.randomUUID().toString())
                put("title", title)
                put("description", description)
                put("isCompleted", if (isCompleted) 1 else 0)
            }
            db.insert("task", OnConflictStrategy.IGNORE, values)
        }
    }

    @Provides
    fun provideTaskDao(database: ToDoDatabase): TaskDao = database.taskDao()
}

