package com.example.elaniin_test.regions

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elaniin_test.regions.model.Region

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionsScreen(state: RegionsState, regions: List<Region>) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorMessage) {
        state.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = { RegionsTopBar() },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) { padding ->
        if (regions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(contentPadding = padding, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(regions) {
                    RegionItem(name = it.name)
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegionsTopBar() {
    val userName = "uriel"
    TopAppBar(title = {
        Text(text = "Hola $userName")
    }, modifier = Modifier.fillMaxWidth())
}

@Composable
private fun RegionItem(name: String) {
    TextButton(onClick = { }, modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
    ) {
        Text(text = name, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
    }

}
