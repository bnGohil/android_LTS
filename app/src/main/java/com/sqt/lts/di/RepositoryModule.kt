package com.example.lts.di


import android.content.Context
import com.sqt.lts.datasource.remote.RestApiService
import com.example.lts.navigation.repository.NavigationRepository
import com.example.lts.repository.AuthRepository
import com.example.lts.repository.CategoryRepository
import com.example.lts.ui.categories.repository.CategoriesRepositoryImp
import com.example.lts.ui.channels.repository.ChannelRepositoryImp
import com.example.lts.ui.dummy.repository.DummyRepository
import com.example.lts.ui.sharedPreferences.SharedPreferencesHelper
import com.example.lts.ui.sharedPreferences.repository.SharedPrefRepository
import com.sqt.lts.ui.trending.repository.TrendingRepositoryImp
import com.sqt.lts.repository.ChannelRepository
import com.sqt.lts.repository.HistoryAndWatchListRepository
import com.sqt.lts.repository.SettingRepository
import com.sqt.lts.repository.TabRepository
import com.sqt.lts.repository.TrendingRepository
import com.sqt.lts.ui.auth.repository.AuthenticationRepositoryImp
import com.sqt.lts.ui.history.repository.HistoryAndWatchListRepositoryImp
import com.sqt.lts.ui.profile.repository.SettingImpRepository
import com.sqt.lts.ui.tab.repository.TabRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(restApiService: RestApiService) : AuthRepository = AuthenticationRepositoryImp(restApiService = restApiService)

    @Singleton
    @Provides
    fun provideTrendingRepository(restApiService: RestApiService) : TrendingRepository = TrendingRepositoryImp(restApiService = restApiService)

    @Provides
    @Singleton
    fun provideCategoriesRepository(restApiService: RestApiService) : CategoryRepository = CategoriesRepositoryImp(restApiService = restApiService)

    @Provides
    @Singleton
    fun provideSharedPrefHelper(@ApplicationContext context: Context): SharedPreferencesHelper {
        return SharedPreferencesHelper(context)
    }

    @Provides
    @Singleton
    fun provideSharedPrefRepository(sharedPrefHelper: SharedPreferencesHelper): SharedPrefRepository {
        return SharedPrefRepository(sharedPrefHelper)
    }

    @Provides
    @Singleton
    fun provideHistoryAndWatchRepository(restApiService: RestApiService): HistoryAndWatchListRepository = HistoryAndWatchListRepositoryImp(restApiService = restApiService)

    @Provides
    @Singleton
    fun providerChannelRepository(restApiService: RestApiService,@ApplicationContext context: Context) : ChannelRepository = ChannelRepositoryImp(restApiService = restApiService,context=context)

    @Provides
    @Singleton
    fun providerSettingRepository(restApiService: RestApiService,@ApplicationContext context: Context): SettingRepository = SettingImpRepository(restApiService = restApiService, context = context)

    @Provides
    @Singleton
    fun providerNavigationRepository(): NavigationRepository {
        return NavigationRepository()
    }

    @Provides
    @Singleton
    fun providerTabRepository(restApiService: RestApiService) : TabRepository = TabRepositoryImp(restApiService)

    @Provides
    @Singleton
    fun provideDummyRepository(@ApplicationContext context: Context): DummyRepository {
        return DummyRepository(context)
    }


}