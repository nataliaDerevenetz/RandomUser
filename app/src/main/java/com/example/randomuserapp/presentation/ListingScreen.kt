package com.example.randomuserapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.randomuserapp.R
import com.example.randomuserapp.domain.models.User
import com.example.randomuserapp.presentation.components.NetworkImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingScreen(
    viewModel: RandomUserViewModel,
    onNavigateToBack: () -> Boolean,
    onNavigateToDetailUser: (String) -> Unit
) {

    val users by viewModel.users.collectAsStateWithLifecycle(null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon={
                    IconButton({ onNavigateToBack() }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
                    } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.height(56.dp).shadow(6.dp),
                windowInsets = WindowInsets(0)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                content = {
                  //  if(isAdded.value) Icon(Icons.Filled.Clear, contentDescription = "Удалить")
                    Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    onNavigateToBack()
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        users?.let{
            LazyColumn(modifier = Modifier.padding(top = 56.dp )) {
                items(items = it) {
                        user -> CardUser(viewModel,user,onNavigateToDetailUser)
                }
            }
        }
    }
}

@Composable
fun CardUser(viewModel: RandomUserViewModel, user: User,onNavigateToDetailUser: (String) -> Unit) {

    var expandedDropdownMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).fillMaxWidth().clickable{
            onNavigateToDetailUser(user.id)
        },
        shape = RoundedCornerShape(15.dp),
    ){
        Row(horizontalArrangement = Arrangement.SpaceBetween){
            NetworkImage(user.picture, "", 100, 100)
            Column(Modifier.padding(start =  10.dp, top = 20.dp).weight(1f)){
                Text( user.name.first + " " + user.name.last, fontWeight = FontWeight.Bold)
                Text( user.cell)
                Text( user.nat)
            }
            Box(contentAlignment = Alignment.TopEnd) {
                IconButton({
                    expandedDropdownMenu = true
                }) { Icon(Icons.Filled.MoreVert, contentDescription = "") }
                DropdownMenu(
                    expanded = expandedDropdownMenu,
                    onDismissRequest = { expandedDropdownMenu = false }) {
                    DropdownMenuItem(text = { Text(stringResource(id = R.string.delete)) }, onClick = {
                        expandedDropdownMenu = false
                        viewModel.handleEvent(RandomUserUiEvent.DeleteUser(user))
                    })
                }
            }
        }
    }
}