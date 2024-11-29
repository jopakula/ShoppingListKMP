package di

import domain.useCases.AddItemToShoppingListUseCase
import domain.useCases.AuthorizationUseCase
import domain.useCases.CreateShoppingListUseCase
import domain.useCases.FetchAllShoppingListsUseCase
import domain.useCases.FetchRegistrationTokenUseCase
import domain.useCases.FetchShoppingListByIdUseCase
import domain.useCases.RemoveItemFromShoppingListUseCase
import domain.useCases.RemoveShoppingListUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<FetchRegistrationTokenUseCase> { FetchRegistrationTokenUseCase(repository = get()) }

    factory<AuthorizationUseCase> { AuthorizationUseCase(repository = get()) }

    factory<CreateShoppingListUseCase> { CreateShoppingListUseCase(repository = get()) }

    factory<FetchAllShoppingListsUseCase> { FetchAllShoppingListsUseCase(repository = get()) }

    factory<RemoveShoppingListUseCase> { RemoveShoppingListUseCase(repository = get()) }

    factory<FetchShoppingListByIdUseCase> { FetchShoppingListByIdUseCase(repository = get()) }

    factory<AddItemToShoppingListUseCase> { AddItemToShoppingListUseCase(repository = get()) }

    factory<RemoveItemFromShoppingListUseCase> { RemoveItemFromShoppingListUseCase(repository = get()) }
}