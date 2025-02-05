package com.dumanyusuf.tabuchallenge.data.di


import com.dumanyusuf.tabuchallenge.data.repo.TabuImpl
import com.dumanyusuf.tabuchallenge.domain.repo.TabuRepo
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesFirebase():FirebaseFirestore=FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun providerRepo(firestore: FirebaseFirestore):TabuRepo{
       return TabuImpl(firestore)
    }

}