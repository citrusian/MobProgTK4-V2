package com.example.mobprogtk4.Login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobprogtk4.R


@Preview(showBackground = true)
@Composable
fun SignInScreen(
//    state: SignInState,
//    onSignInClick: () -> Unit
    state: SignInState = SignInState(),
    onSignInClick: () -> Unit = {}
){
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError){
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Logo Dark Mode Check
    val imageResource = if (isSystemInDarkTheme()) {
        painterResource(id = R.drawable.baseline_login_24_dark)
    } else {
        painterResource(id = R.drawable.baseline_login_24_light)
    }

    // Background Color Dark Mode Check
    val backgroundColorModifier = if (isSystemInDarkTheme()) {
        // same as null, use material color
        Modifier
    } else {
        Modifier.background(Color.LightGray)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .padding(8.dp)
            .then(backgroundColorModifier)
        ,
        contentAlignment = Alignment.Center
    ){
        Button(onClick = onSignInClick) {
            Row(
                modifier = Modifier
//                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = imageResource,
                    contentDescription = "Google logo",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = "Sign in with Google",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}