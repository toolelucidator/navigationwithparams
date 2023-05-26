/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.navigationwithparams.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.navigationwithparams.presentation.theme.NavigationwithparamsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

object NavRoute {
    const val SCREEN_2 = "screen2"
    const val SCREEN_3 = "screen3"
    const val DETAILSCREEN = "detailScreen/{id}"
}

@Composable
fun WearApp() {
    NavigationwithparamsTheme {
    val navController = rememberSwipeDismissableNavController()
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavRoute.SCREEN_2
    ) {
        composable(NavRoute.SCREEN_2) {
            Screen2(navController)
        }
        composable(NavRoute.SCREEN_3) {
            Screen3(navController)
        }
        composable("detailScreen/{id}") { backStackEntry ->
            backStackEntry.arguments?.getString("id")
           //detailScreen(id = it.arguments?.getString("id") ?: "0")
            detailScreen(id = backStackEntry.arguments?.getString("id")?:"0")
        }


    }

}
}

@Composable
fun Screen2(navigation: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background
            ), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "Hello from the start screen",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navigation.navigate("screen3") }) {
        }
    }
}

@Composable
fun Screen3(navigation: NavController) {
    val items = listOf("Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis")
    val state = rememberPickerState(items.size)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart

    ) {
        /*Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp),
            text = "Selected: ${items[state.selectedOption]}"
        )*/
        Picker(
            state = state,
            modifier = Modifier.size(100.dp, 100.dp)

        ) {
            Text(items[it], modifier = Modifier.padding(10.dp))
        }
    }
    //Picker 2
    val items2 = listOf("Siete", "Ocho", "Nueve", "Diez", "Once", "Doce")
    val state2 = rememberPickerState(items.size)
    //var texto = items[state.selectedOption]+[state2.selectedOption]
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp),
            text = "${items[state.selectedOption]+ " " +items2[state2.selectedOption]}"
        )
        Picker(
            state = state2,
            modifier = Modifier.size(100.dp, 100.dp)

        ) {
            Text(items2[it], modifier = Modifier.padding(10.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(10.dp),
            contentAlignment = Alignment.CenterEnd

        ) {
            var position = state.selectedOption
            var position2 = state2.selectedOption
            var value = items[position] + items2[position2]

            Button(
                onClick = {
                    navigation.navigate("detailScreen/$value")
                }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Yellow,
                    contentColor = Color.Magenta
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = "Next",
                    tint = Color.Cyan
                )
            }
        }
    }

}

@Composable
fun detailScreen(id: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background
            ), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "Hello from detail screen, selected value: $id",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))


    }
}


@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}