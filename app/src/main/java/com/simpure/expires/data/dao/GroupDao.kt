package com.simpure.expires.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.simpure.expires.data.entity.GroupEntity

@Dao
interface GroupDao {

    @Query("SELECT * FROM `group`")
    fun getAllGroup(): LiveData<List<GroupEntity>>

    @Query("SELECT * FROM `group` WHERE id=:id")
    fun loadGroupById(id: String): LiveData<GroupEntity>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<GroupEntity>)

    @Delete
    fun delete(user: GroupEntity)
}