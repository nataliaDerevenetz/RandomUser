package com.example.randomuserapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class DetailUser(val id: String)

@Serializable
object CreateUser

@Serializable
object Listing


@Composable
fun MainScreen(modifier: Modifier = Modifier,viewModel: RandomUserViewModel) {

    val navController = rememberNavController()
    val onNavigateToListing = { navController.navigate(Listing) }
    val onNavigateToBack = { navController.popBackStack()}
    val onNavigateToDetailUser = {it: String ->  navController.navigate(DetailUser(it)) }

    NavHost(navController = navController, startDestination = CreateUser, modifier) {
        composable<CreateUser> {
            CreateUserScreen(viewModel,onNavigateToListing)
        }
        composable<Listing> {
            ListingScreen(viewModel,onNavigateToBack,onNavigateToDetailUser)
        }
        composable<DetailUser> {
            backStackEntry -> val detailUser: DetailUser = backStackEntry.toRoute()
            DetailUserScreen(viewModel,detailUser.id,onNavigateToBack)
        }
    }
}