package di

import domain.useCases.GetRegistrationTokenUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetRegistrationTokenUseCase> {
        GetRegistrationTokenUseCase(repository = get())
    }
}