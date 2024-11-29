package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import presentation.screenViewModels.AuthorizationScreenViewModel
import presentation.screenViewModels.MainScreenViewModel
import presentation.screenViewModels.RegistrationScreenViewModel
import presentation.screenViewModels.ShoppingListScreenViewModel

val appModule = module {

    viewModel<RegistrationScreenViewModel> {
        RegistrationScreenViewModel(
            fetchRegistrationTokenUseCase = get(),
            authorizationUseCase = get(),
        )
    }

    viewModel<AuthorizationScreenViewModel> {
        AuthorizationScreenViewModel(
            authorizationUseCase = get(),
        )
    }

    viewModel<MainScreenViewModel> {
        MainScreenViewModel(
            fetchAllShoppingListsUseCase = get(),
            createShoppingListUseCase = get(),
            removeShoppingListUseCase = get(),
        )
    }

    viewModel<ShoppingListScreenViewModel> {
        ShoppingListScreenViewModel(
            fetchShoppingListByIdUseCase = get(),
            addItemToShoppingListUseCase = get(),
            removeItemFromShoppingListUseCase = get(),
            crossOffItemUseCase = get(),
        )
    }

}