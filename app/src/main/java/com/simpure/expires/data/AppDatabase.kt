package com.simpure.expires.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.simpure.expires.AppExecutors
import com.simpure.expires.data.dao.CommodityDao
import com.simpure.expires.data.dao.CommodityHomeDao
import com.simpure.expires.data.entry.CommodityEntity

/**
 * The Room database for this app
 */
@Database(
    entities = [CommodityHome::class, CommodityEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun commodityDao(): CommodityDao
    abstract fun commodityHomeDao(): CommodityHomeDao

    private val mIsDatabaseCreated = MutableLiveData<Boolean>()

    val databaseCreated: LiveData<Boolean>
        get() = mIsDatabaseCreated

    /**
     * Check whether the database already exists and expose it via [.getDatabaseCreated]
     */
    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    companion object {

        private var sInstance: AppDatabase? = null

        @VisibleForTesting
        val DATABASE_NAME = "basic-sample-db"

        fun getInstance(context: Context, executors: AppExecutors): AppDatabase {
            if (sInstance == null) {
                synchronized(AppDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = buildDatabase(context.applicationContext, executors)
                        sInstance!!.updateDatabaseCreated(context.applicationContext)
                    }
                }
            }
            return sInstance!!
        }

        /**
         * Build the database. [Builder.build] only sets up the database configuration and
         * creates a new instance of the database.
         * The SQLite database is only created when it's accessed for the first time.
         */
        private fun buildDatabase(
            appContext: Context,
            executors: AppExecutors
        ): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        executors.diskIO().execute {
                            // Add a delay to simulate a long-running operation
                            addDelay()
                            // Generate the data for pre-population
                            val database = getInstance(appContext, executors)
                            val commodityList = DataGenerator.generateCommodities()
//                            val comments = DataGenerator.generateCommentsForProducts(commodities)

                            insertData(database, commodityList)
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated()
                        }
                    }
                })
                .addMigrations(MIGRATION_1_2)
                .build()
        }

        private fun insertData(
            database: AppDatabase, commodityList: List<CommodityEntity>
        ) {
            database.runInTransaction {
                database.commodityDao().insertAll(commodityList)
//                database.commentDao().insertAll(comments)
            }
        }

        private fun addDelay() {
            try {
                Thread.sleep(4000)
            } catch (ignored: InterruptedException) {
            }

        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `productsFts` USING FTS4(" + "`name` TEXT, `date` TEXT, content=`commodities`)")
                database.execSQL("INSERT INTO productsFts (`rowid`, `name`, `date`) " + "SELECT `id`, `name`, `date` FROM commodities")
            }
        }
    }
}
