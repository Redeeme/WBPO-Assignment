package sk.doxxbet.doxxbetui.wpbo_assignment.client

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FollowedUsersDao {

    @Query("SELECT id FROM users_table")
    suspend fun getAll(): List<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: FollowedUsersEntity)

    @Query("DELETE FROM users_table WHERE id = :id")
    suspend fun deleteById(id: Int)
}
