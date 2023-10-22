package com.example.mobprogtk4.profile

import android.widget.Space
import android.window.BackEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobprogtk4.Login.UserData



@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val userData = UserData( // Create a sample UserData object
        userId = "000",
        userName = "John Doe",
        profilePictureUrl = "https://example.com/profile.jpg"
    )

    ProfileScreen(userData = userData)
}

@Composable
fun ProfileScreen(
    userData: UserData?,
//    onSignOut: () -> Unit
    onSignOut: () -> Unit = {},
    onReturn: () -> Unit = {}
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (userData?.userName != null){
            Text(
                text = userData.userName,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }


        // Header and Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            Button(
                onClick = onSignOut,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Sign Out")
            }
            Button(
                onClick = onReturn,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Back")
            }
        }

    }

}