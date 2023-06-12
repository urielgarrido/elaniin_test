package com.example.elaniin_test.regions

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elaniin_test.R
import com.example.elaniin_test.regions.model.Region
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionsScreen(state: RegionsState, regions: List<Region>, userName: String, onRegionClick: (Region) -> Unit) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorMessage) {
        state.errorMessage?.let { error ->
            Toast.makeText(context, context.getString(error), Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = { RegionsTopBar(context, userName) },
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
                item {
                    Text(
                        text = context.getString(R.string.click_a_region_to_continue),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(regions) {
                    RegionItem(name = it.name) { onRegionClick(it) }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegionsTopBar(context: Context, userName: String) {
    TopAppBar(title = {
        Text(text = "Hola $userName")
    }, modifier = Modifier.fillMaxWidth(), actions = {
        IconButton(onClick = {
            Firebase.auth.signOut()
            (context as ComponentActivity).finishAffinity()
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_logout), contentDescription = "")
        }
    })
}

@Composable
private fun RegionItem(name: String, onRegionClick: () -> Unit) {
    TextButton(
        onClick = onRegionClick, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = name.replaceFirstChar { it.uppercaseChar() }, fontSize = 24.sp)
        Spacer(modifier = Modifier.weight(1f))
    }

}
