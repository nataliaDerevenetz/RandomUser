package com.example.randomuserapp.presentation

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.randomuserapp.R
import com.example.randomuserapp.domain.models.User
import com.example.randomuserapp.utils.toFormattedString

data class TabItem(
    val title: (@Composable () -> String),
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)


val tabItems = listOf(
    TabItem(
        title = {"Person"},
        unselectedIcon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person
    ),
    TabItem(
        title = {"Phone"},
        unselectedIcon = Icons.Outlined.Phone,
        selectedIcon = Icons.Filled.Phone
    ),
    TabItem(
        title = {"Email"},
        unselectedIcon = Icons.Outlined.Email,
        selectedIcon = Icons.Filled.Email
    ),
    TabItem(
        title = {"Location"},
        unselectedIcon = Icons.Outlined.LocationOn,
        selectedIcon = Icons.Filled.LocationOn
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailUserScreen(viewModel: RandomUserViewModel, id: String, onNavigateToBack: () -> Unit) {

    val user by viewModel.getUserById(id).collectAsState(null)

    val scrollState = rememberScrollState()

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if(!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon={
                    IconButton({ onNavigateToBack() }) {
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
    ) { innerPadding ->

        user?.let {
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

                Column(
                    modifier = Modifier.verticalScroll(scrollState).fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(horizontalArrangement = Arrangement.Center) {
                        SubcomposeAsyncImage(
                            model = it.picture,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp).size(200.dp).clip(CircleShape).border(1.dp, Color.LightGray, CircleShape),
                            loading = {
                                CircularProgressIndicator(
                                    color = Color.Gray,
                                    modifier = Modifier.requiredSize(48.dp)
                                )
                            },
                            error = { Log.d("TAG", "image load: Error!") }
                        )
                    }

                    Text(it.name.first + " " + it.name.last,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp))

                    Column(modifier = Modifier
                        .fillMaxSize()){
                        PrimaryTabRow(selectedTabIndex = selectedTabIndex) {
                            tabItems.forEachIndexed { index, item ->
                                Tab(
                                    selected = index == selectedTabIndex,
                                    onClick = {
                                        selectedTabIndex = index
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedTabIndex) {
                                                item.selectedIcon
                                            } else item.unselectedIcon,
                                            contentDescription = item.title()
                                        )
                                    }
                                )
                            }
                        }

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth()
                        ) { index ->
                            when (index) {
                                0 -> PersonScreen(it)
                                1 -> PhoneScreen(it)
                                2 -> EmailScreen(it)
                                3 -> LocationScreen(it)
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun PersonScreen(user: User) {
    Column(Modifier.padding(10.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row {
            Text("${stringResource(id = R.string.first_name)} :", fontWeight = FontWeight.Bold,)
            Text(user.name.first)
        }
        Row {
            Text("${stringResource(id = R.string.last_name)} :",fontWeight = FontWeight.Bold,)
            Text(user.name.last)
        }
        Row {
            Text("${stringResource(id = R.string.gender)}  :",fontWeight = FontWeight.Bold,)
            Text(user.gender)
        }

        Row {
            Text("${stringResource(id = R.string.dob)} :",fontWeight = FontWeight.Bold,)
            Text(user.dob.date.toFormattedString())
        }
    }

}

@Composable
fun PhoneScreen(user: User) {
    Column(Modifier.padding(10.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row {
            Text("${stringResource(id = R.string.phone)} :", fontWeight = FontWeight.Bold,)
            Text(user.phone)
        }
    }
}

@Composable
fun EmailScreen(user: User) {
    Column(Modifier.padding(10.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row {
            Text("${stringResource(id = R.string.email)} :", fontWeight = FontWeight.Bold,)
            Text(user.email)
        }
    }
}

@Composable
fun LocationScreen(user: User) {
    Column(Modifier.padding(10.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row {
            Text("${stringResource(id = R.string.country)} :", fontWeight = FontWeight.Bold,)
            Text(user.location.country)
        }
        Row {
            Text("${stringResource(id = R.string.state)} :", fontWeight = FontWeight.Bold,)
            Text(user.location.state)
        }
        Row {
            Text("${stringResource(id = R.string.city)} :", fontWeight = FontWeight.Bold,)
            Text(user.location.city)
        }
        Row {
            Text("${stringResource(id = R.string.street)} :", fontWeight = FontWeight.Bold,)
            Text(user.location.street.name + " " +  user.location.street.number)
        }
    }
}

