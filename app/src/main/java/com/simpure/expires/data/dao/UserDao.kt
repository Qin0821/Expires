package com.simpure.expires.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.simpure.expires.data.entry.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE id= :userId")
    fun loadByIds(userId: Int): LiveData<UserEntity>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserEntity>)

    @Delete
    fun delete(user: UserEntity)
}