package com.aryama0073.tentangdirikita.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material3.Button
import com.aryama0073.tentangdirikita.R
import com.aryama0073.tentangdirikita.navigation.Screen
import com.aryama0073.tentangdirikita.ui.theme.TentangDiriKitaTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    var selectedOperation by rememberSaveable { mutableStateOf("") }
    var isEnglish by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)

                        ) {
                            Switch(
                                checked = isEnglish,
                                onCheckedChange = { isEnglish = it }
                            )
                            Text(
                                text = if (isEnglish) "ENG" else "IDN",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(onClick = {
                            navController.navigate(Screen.About.route)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = stringResource(R.string.tentang_aplikasi),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(
            modifier = Modifier.padding(innerPadding),
            selectedOperation = selectedOperation,
            onOperationChange = { selectedOperation = it },
            onReset = {
                selectedOperation = ""
            },
            isEnglish = isEnglish
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    selectedOperation: String,
    onOperationChange: (String) -> Unit,
    onReset: () -> Unit,
    isEnglish: Boolean,
) {
    var nama by rememberSaveable { mutableStateOf("") }
    var namaerror by rememberSaveable { mutableStateOf(false) }

    var umur by rememberSaveable { mutableStateOf("") }
    var umurerror by rememberSaveable { mutableStateOf(false) }

    val radioOptions = if (isEnglish) listOf("Male", "Female") else listOf("Pria", "Wanita")
    var gender by rememberSaveable { mutableStateOf(radioOptions[0]) }

    var isDatepickerOpen by remember { mutableStateOf(false) }
    var tanggallahir by rememberSaveable { mutableStateOf("") }
    val tanggalerror = tanggallahir.isBlank()
    var date by remember { mutableLongStateOf(0) }

    var hobi by rememberSaveable { mutableStateOf("") }
    var hobierror by rememberSaveable { mutableStateOf(false) }

    val hewanList = if (isEnglish)
        listOf("Cat", "Dog", "Rabbit", "Bird")
    else
        listOf("Kucing", "Anjing", "Kelinci", "Burung")
    var hewan by rememberSaveable { mutableStateOf("") }
    var hewanerror by rememberSaveable { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    var showResult by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    if (isDatepickerOpen) {
        DatePickerModal(
            onDateSelected = {
                if (it != null) {
                    date = it
                    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    tanggallahir = formatter.format(Date(it))
                }
            }
        ) {
            isDatepickerOpen = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isEnglish) {
                "This application is a simple way to manage our own biodata easily, with a clean interface."
            } else {
                "Aplikasi ini adalah cara sederhana untuk mengelola biodata kita sendiri dengan mudah dan tampilan yang bersih."
            },
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 16.sp),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text(if (isEnglish) "Name" else "Nama") },
            trailingIcon = { IconPicker(namaerror, "") },
            supportingText = {
                if (namaerror) Text(if (isEnglish) "Invalid input" else "Input tidak valid")
            },
            isError = namaerror,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = umur,
            onValueChange = { umur = it },
            label = { Text(if (isEnglish) "Age" else "Umur") },
            trailingIcon = { IconPicker(umurerror, "") },
            supportingText = {
                if (umurerror) Text(if (isEnglish) "Invalid input" else "Input tidak valid")
            },
            isError = umurerror,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = tanggallahir,
            onValueChange = {},
            label = { Text(if (isEnglish) "Date of Birth" else "Tanggal Lahir") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { isDatepickerOpen = true }) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = if (isEnglish) "Pick Date" else "Pilih Tanggal"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                GenderOption(
                    label = text,
                    isSelected = gender == text,
                    modifier = Modifier
                        .selectable(
                            selected = gender == text,
                            onClick = { gender = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }
        OutlinedTextField(
            value = hobi,
            onValueChange = { hobi = it },
            label = { Text(if (isEnglish) "Hobby" else "Hobi") },
            trailingIcon = { IconPicker(hobierror, "") },
            supportingText = {
                if (hobierror) Text(if (isEnglish) "Invalid input" else "Input tidak valid")
            },
            isError = hobierror,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = hewan,
                onValueChange = {},
                readOnly = true,
                label = { Text(if (isEnglish) "Favorite Pet" else "Hewan") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                isError = hewanerror,
                supportingText = {
                    if (hewanerror) Text(if (isEnglish) "Invalid input" else "Input tidak valid")
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                hewanList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            hewan = item
                            expanded = false
                        }
                    )
                }
            }
        }
        Button(
            onClick = {
                namaerror = nama.isBlank()
                umurerror = umur.isBlank() || umur.toIntOrNull() == null
                hobierror = hobi.isBlank()
                hewanerror = hewan.isBlank()

                showResult = !(namaerror || umurerror || hobierror || hewanerror || tanggalerror)
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = if (isEnglish) "Submit" else "Kirim")
        }

        if (showResult) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("${if (isEnglish) "Name" else "Nama"}: $nama")
                Text("${if (isEnglish) "Age" else "Umur"}: $umur")
                Text("${if (isEnglish) "Date of Birth" else "Tanggal Lahir"}: $tanggallahir")
                Text("${if (isEnglish) "Gender" else "Jenis Kelamin"}: $gender")
                Text("${if (isEnglish) "Hobby" else "Hobi"}: $hobi")
                Text("${if (isEnglish) "Favorite Pet" else "Hewan Peliharaan Favorit"}: $hewan")
            }
            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = if (isEnglish)
                            "Name: $nama\nAge: $umur\nDate of Birth: $tanggallahir\nGender: $gender\nHobby: $hobi\nFavorite Pet: $hewan"
                        else
                            "Nama: $nama\nUmur: $umur\nTanggal Lahir: $tanggallahir\nJenis Kelamin: $gender\nHobi: $hobi\nHewan Peliharaan Favorit: $hewan"
                    )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = if (isEnglish) "Share" else "Bagikan")
            }
        }
    }
}

@Composable
fun GenderOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    TentangDiriKitaTheme {
        MainScreen(rememberNavController())
    }
}