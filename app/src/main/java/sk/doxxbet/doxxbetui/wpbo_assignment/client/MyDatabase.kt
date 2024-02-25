package sk.doxxbet.doxxbetui.wpbo_assignment.client

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [
    FollowedUsersEntity::class,
], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun followedUsersDao(): FollowedUsersDao

    companion object{
        val DATABASE_NAME: String = "my_database"
    }
}