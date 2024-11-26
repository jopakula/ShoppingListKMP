package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import presentation.viewModels.AuthorizationViewModel
import presentation.viewModels.RegistrationViewModel

val appModule = module {

    viewModel<RegistrationViewModel> {
        RegistrationViewModel(
            getRegistrationTokenUseCase = get(),
            authorizationUseCase = get()
        )
    }

    viewModel<AuthorizationViewModel> {
        AuthorizationViewModel(
            authorizationUseCase = get()
        )
    }

}