package sk.doxxbet.doxxbetui.wpbo_assignment.client

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class FollowedUsersEntity(
    @PrimaryKey
    val id: Int
)