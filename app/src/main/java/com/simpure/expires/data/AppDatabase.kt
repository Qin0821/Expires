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
import com.simpure.expires.data.dao.UserDao
import com.simpure.expires.data.entity.CommodityEntity
import com.simpure.expires.data.entity.UserEntity

/**
 * The Room database for this app
 */
@Database(
    entities = [UserEntity::class, CommodityHome::class, CommodityEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
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
        val DATABASE_NAME = "expires-db"

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
                            // Generate the data for pre-population
                            val database = getInstance(appContext, executors)
                            val user = DataGenerator.generateUser()
                            val commodityList = DataGenerator.generateCommodities()
//                            val comments = DataGenerator.generateCommentsForProducts(commodities)

                            insertData(database, user, commodityList)
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated()
                        }
                    }
                })
                .fallbackToDestructiveMigration()
//                .addMigrations(MIGRATION_1_2) todo 保留用户数据
                .build()
        }

        private fun insertData(
            database: AppDatabase,
            userEntity: List<UserEntity>,
            commodityList: List<CommodityEntity>
        ) {
            database.runInTransaction {
                database.userDao().insertAll(userEntity)
                database.commodityDao().insertAll(commodityList)
//                database.commentDao().insertAll(comments)
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
