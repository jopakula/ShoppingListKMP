package di

import domain.useCases.AuthorizationUseCase
import domain.useCases.GetRegistrationTokenUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetRegistrationTokenUseCase> { GetRegistrationTokenUseCase(repository = get()) }

    factory<AuthorizationUseCase> { AuthorizationUseCase(repository = get()) }
}