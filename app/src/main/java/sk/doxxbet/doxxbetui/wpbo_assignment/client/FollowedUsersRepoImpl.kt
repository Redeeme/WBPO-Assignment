package sk.doxxbet.doxxbetui.wpbo_assignment.client


class FollowedUsersRepoImpl(
    private val followedUsersDao: FollowedUsersDao
) : FollowedUsersRepository {
    override suspend fun getAll(): List<Int> = followedUsersDao.getAll()

    override suspend fun insert(user: FollowedUsersEntity) = followedUsersDao.insert(user)

    override suspend fun delete(id: Int) = followedUsersDao.deleteById(id)
}