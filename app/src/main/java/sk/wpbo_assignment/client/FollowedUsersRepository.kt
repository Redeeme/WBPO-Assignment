package sk.wpbo_assignment.client

interface FollowedUsersRepository {
    suspend fun getAll(): List<Int>

    suspend fun insert(user: FollowedUsersEntity)

    suspend fun delete(id: Int)
}