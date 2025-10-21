package com.example.contactlistmidterm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.contactlistmidterm.ui.theme.ContactListMidTermTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactListMidTermTheme {
                AppNav()
            }
        }
    }
}

@Composable
fun AppNav() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            ListScreen(
                navController = navController,
                onAddClick = {
                    navController.navigate("add")
                },
                onEditPress = {
                    navController.navigate("edit")
                }
            )
        }
        composable("add") {
            AddScreen(navController = navController, onBackClick = { navController.popBackStack() })
        }
        composable("edit") {
            EditScreen(navController = navController, onBackClick = { navController.popBackStack() })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController, onAddClick: () -> Unit, onEditPress: () -> Unit) {
    val contacts: List<Contacts> = listOf(
        Contacts("Kobo", "1234567890", "JJ Street", "kobokanaeru@gmail.com"),
        Contacts("Doni", "1234567890", "Priu Street", "Duar@gmail.com"),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Dashboard")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            contacts.forEach { contact ->
                ElevatedButton(
                    onClick = {
                        onEditPress()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    onEditPress()
                                }
                            )
                        }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = contact.name,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = contact.address
                        )
                    }
                }
            }
        }
    }
}