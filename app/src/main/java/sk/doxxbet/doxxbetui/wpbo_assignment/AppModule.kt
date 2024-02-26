package sk.doxxbet.doxxbetui.wpbo_assignment

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sk.doxxbet.doxxbetui.wpbo_assignment.client.FollowedUsersDao
import sk.doxxbet.doxxbetui.wpbo_assignment.client.FollowedUsersRepoImpl
import sk.doxxbet.doxxbetui.wpbo_assignment.client.FollowedUsersRepository
import sk.doxxbet.doxxbetui.wpbo_assignment.client.MyDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MyDatabase {
        return Room
            .databaseBuilder(
                context,
                MyDatabase::class.java,
                MyDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFollowedUsersDao(
        myDatabase: MyDatabase
    ) = myDatabase.followedUsersDao()

    @Provides
    fun provideEveningRoutineRepository(
        followedUsersDao: FollowedUsersDao
    ): FollowedUsersRepository = FollowedUsersRepoImpl(
        followedUsersDao = followedUsersDao
    )
}
