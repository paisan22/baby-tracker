package nl.paisan.babytracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import nl.paisan.babytracker.domain.enums.PhysicalType
import nl.paisan.babytracker.ui.layout.DefaultLayout
import nl.paisan.babytracker.ui.screen.addActivity.AddActvitivyScreen
import nl.paisan.babytracker.ui.screen.addPhysicals.AddPhysicalsScreen
import nl.paisan.babytracker.ui.screen.addPhysicals.addWeight.AddWeightScreen
import nl.paisan.babytracker.ui.screen.bio.BioScreen
import nl.paisan.babytracker.ui.screen.overviewActivity.OverviewActivityScreen
import nl.paisan.babytracker.ui.screen.physicalOverview.PhysicalOverviewScreen

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Destinations.BIO_ROUTE
    ) {
        composable(
            route = Destinations.BIO_ROUTE,
            content = {
                DefaultLayout(
                    route = Destinations.BIO_ROUTE,
                    title = "Bio",
                    navHostController = navHostController,
                    content = {
                        BioScreen()
                    },
                )
            }
        )
        composable(
            route = Destinations.ADD_ACTIVITY_ROUTE,
            content = {
                DefaultLayout(
                    route = Destinations.ADD_ACTIVITY_ROUTE,
                    title = "Add Activity",
                    navHostController = navHostController,
                    content = {
                        AddActvitivyScreen(navHostController = navHostController)
                    },
                )
            }
        )
        composable(
            route = Destinations.ADD_PHYSICALS_ROUTE,
            content = {
                DefaultLayout(
                    route = Destinations.ADD_PHYSICALS_ROUTE,
                    title = "Add Physical Growth",
                    navHostController = navHostController,
                    content = {
                        AddPhysicalsScreen(navHostController = navHostController)
                    }
                )
            }
        )
        composable(
            route = Destinations.OVERVIEW_ACTIVITY_ROUTE,
            content = {
                DefaultLayout(
                    route = Destinations.OVERVIEW_ACTIVITY_ROUTE,
                    title = "Activity Overview",
                    navHostController = navHostController,
                    content = {
                        OverviewActivityScreen(navHostController = navHostController)
                    },
                )
            }
        )
        composable(
            route = Destinations.OVERVIEW_PHYSICAL_ROUTE,
            arguments = listOf(navArgument("physicalType") { type = NavType.StringType}),
        ) { backStackEntry ->
            val physicalTypeString = backStackEntry.arguments?.getString("physicalType")
            val type = if(physicalTypeString != null) {
                PhysicalType.valueOf(physicalTypeString)
            } else {
                PhysicalType.Weight
            }

            DefaultLayout(
                route = Destinations.OVERVIEW_PHYSICAL_ROUTE,
                title = "Physical Growth Overview",
                    navHostController = navHostController,
                    content = {
                        PhysicalOverviewScreen(physicalType = type)
                    }
                )
        }
    }
}