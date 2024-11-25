package di

import data.RepositoryImpl
import data.storage.NetworkStorage
import data.storage.network.CyberprotApi
import data.storage.network.CyberprotSDK
import data.storage.network.NetworkStorageImpl
import domain.Repository
import org.koin.dsl.module

val dataModule = module {

    single<CyberprotApi> { CyberprotApi() }
    single<CyberprotSDK> { CyberprotSDK(api = get()) }
    single<NetworkStorage> { NetworkStorageImpl(sdk = get()) }
    single<Repository> { RepositoryImpl(networkStorage = get()) }
}