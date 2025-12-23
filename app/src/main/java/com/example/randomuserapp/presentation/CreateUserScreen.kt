package com.example.randomuserapp.presentation

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.randomuserapp.R
import com.example.randomuserapp.presentation.components.Loading
import com.example.randomuserapp.utils.Constant
import kotlinx.coroutines.launch

@Composable
fun CreateUserScreen(
    viewModel: RandomUserViewModel,
    onNavigateToListing: () -> Unit
) {

    val stateUI by viewModel.stateCreateUser.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) { data ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(data.visuals.message)
                }
            }
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        when(stateUI.contentState) {
            is CreateUserState.Loading -> {
                Loading()
            }
            is CreateUserState.Success -> {
                viewModel.handleEvent(RandomUserUiEvent.CreateUserSetIdleState)
                onNavigateToListing()
            }
            is CreateUserState.Error -> {
                val msgErr = stringResource(id = R.string.error_create_user)
                LaunchedEffect(stateUI.contentState) {
                    scope.launch {
                        snackbarHostState.showSnackbar(msgErr)
                    }
                }
                viewModel.handleEvent(RandomUserUiEvent.CreateUserSetIdleState)
            }
            else -> {}
        }

        Box(modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 0.dp, start = 15.dp, end = 15.dp)
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .weight(1f, false),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        stringResource(id = R.string.create_user),
                        modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        textAlign =  TextAlign.Center
                    )

                    SelectGenderToModal(viewModel)

                    SelectNationalityToModal(viewModel)

                }

                Button(
                    onClick = { viewModel.handleEvent(RandomUserUiEvent.CreateUser) },
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .height(50.dp)
                        .fillMaxWidth()

                ) {
                    Text(stringResource(id = R.string.generate))
                }
                Button(
                    onClick = { onNavigateToListing() },
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .height(50.dp)
                        .fillMaxWidth()

                ) {
                    Text(stringResource(id = R.string.users))
                }
            }
        }
    }

}

@Composable
private fun SelectGenderToModal(viewModel: RandomUserViewModel) {

    val gender = rememberSaveable{mutableStateOf("male")}

    val male = stringResource(id = R.string.male)
    val female = stringResource(id = R.string.female)

    val genderLocal by remember{derivedStateOf { if (gender.value == "male") male else female }}
    var showModal = rememberSaveable { mutableStateOf(false) }


    Text(
        "${stringResource(id = R.string.select_gender)}:",
        modifier = Modifier.padding(top = 10.dp)
    )
    OutlinedTextField(
        genderLocal,
        {  },
        readOnly = true,
        textStyle = TextStyle(fontSize = 16.sp),
        modifier = Modifier
            .padding(top = 10.dp)
            .height(50.dp)
            .fillMaxWidth()
            .pointerInput(genderLocal) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal.value = true
                    }
                }
            },
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Filled.ArrowDropDown,
                "",
            )
        },
        shape = RoundedCornerShape(12.dp)
    )


    if (showModal.value) {
        CommonDialog(title = "${stringResource(id = R.string.select_gender)} :", state = showModal) {
            SingleGenderChoiceView(gender,viewModel)
        }

    }
}

@Composable
private fun CommonDialog(
    title: String?,
    state: MutableState<Boolean>,
    content: @Composable (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = {
            state.value = false
        },
        title = title?.let {
            {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(text = title)
                }
            }
        },
        text = content,
        confirmButton = {
            TextButton(onClick = { state.value = false }) {
                Text("OK")
            }
        }, modifier = Modifier.padding(vertical = 5.dp)
    )
}


@Composable
fun SingleGenderChoiceView(gender: MutableState<String>, viewModel: RandomUserViewModel) {
    val radioOptions = listOf(stringResource(id = R.string.male),
        stringResource(id = R.string.female)
    )
    val index = if (gender.value == "male") 0 else 1
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[index]) }
    Column(
        Modifier.fillMaxWidth()
    ) {
        radioOptions.forEachIndexed { index, text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            gender.value = if (index == 0) "male" else "female"
                            viewModel.handleEvent(RandomUserUiEvent.OnGenderChanged(gender.value))
                            onOptionSelected(text)
                        }
                    )
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        gender.value = if (index == 0) "male" else "female"
                        viewModel.handleEvent(RandomUserUiEvent.OnGenderChanged(gender.value))
                        onOptionSelected(text)
                    }
                )
                Text(
                    text = text
                )
            }
        }
    }
}


@Composable
private fun SelectNationalityToModal(viewModel: RandomUserViewModel) {
    val nationality = rememberSaveable{mutableStateOf(Constant.nationalities.entries.first().value)}
    val nationalityLocal by remember{derivedStateOf { nationality.value }}
    var showModal = rememberSaveable { mutableStateOf(false) }


    Text(
        "${stringResource(id = R.string.select_nationality)}:",
        modifier = Modifier.padding(top = 10.dp)
    )
    OutlinedTextField(
        nationalityLocal,
        {  },
        readOnly = true,
        textStyle = TextStyle(fontSize = 16.sp),
        modifier = Modifier
            .padding(top = 10.dp)
            .height(50.dp)
            .fillMaxWidth()
            .pointerInput(nationalityLocal) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal.value = true
                    }
                }
            },
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Filled.ArrowDropDown,
                "",
            )
        },
        shape = RoundedCornerShape(12.dp)
    )


    if (showModal.value) {
        CommonDialog(title = "${stringResource(id = R.string.select_nationality)} :", state = showModal) {
            SingleNationalityChoiceView(nationality,viewModel)
        }

    }
}


@Composable
fun SingleNationalityChoiceView(nationality: MutableState<String>,viewModel: RandomUserViewModel) {
    val scrollState = rememberScrollState()
    val radioOptions = Constant.nationalities.values.toList()

    val index  = radioOptions.indexOf(nationality.value)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[index]) }
    Column(
        Modifier.verticalScroll(scrollState).fillMaxWidth()
    ) {

        radioOptions.forEachIndexed { index, text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            nationality.value = radioOptions[index]
                            val nationality_key = Constant.nationalities.entries.find { it.value == nationality.value }?.key ?: Constant.NATIONALITY_DEFAULT
                            viewModel.handleEvent(RandomUserUiEvent.OnNationalityChanged(nationality_key))
                            onOptionSelected(text)
                        }
                    )
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        nationality.value = radioOptions[index]
                        val nationality_key = Constant.nationalities.entries.find { it.value == nationality.value }?.key ?: Constant.NATIONALITY_DEFAULT
                        viewModel.handleEvent(RandomUserUiEvent.OnNationalityChanged(nationality_key))
                        onOptionSelected(text)
                    }
                )
                Text(
                    text = text
                )
            }
        }
    }
}
