package com.jtortosasen.randomuserapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jtortosasen.randomuserapp.R
import com.jtortosasen.randomuserapp.ui.models.UserUi
import com.jtortosasen.randomuserapp.ui.models.toDomain


@Preview
@Composable
fun DetailScreen(navController: NavController? = null) {
    val backStackEntry = navController?.currentBackStackEntry
    val user = backStackEntry?.arguments?.getParcelable<UserUi>("userKey")?.toDomain()

    user?.let {
        Column {
            Box {
                Column {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(198.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_header),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                        Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                        ) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.topbar_navigation_content_description),
                                    tint = Color.White)
                            }
                            Text(
                                text = user.name.uppercase(),
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = {  }) {
                                Icon(Icons.Default.MoreVert,
                                    contentDescription = null,
                                    tint = Color.White)
                            }
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.camera_button),
                                contentDescription = "Abrir cámara",
                                tint = Color.Black)
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit_button),
                                contentDescription = "Editar perfil",
                                tint = Color.Black)
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 10.dp)
                        .size(78.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color.White, CircleShape)
                        .align(Alignment.BottomStart)
                ) {
                    AsyncImage(
                        model = user.image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(77.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Column(Modifier.verticalScroll(rememberScrollState())) {
                ProfileSection(
                    title = "Nombre y apellidos",
                    value = user.name,
                    iconSource = R.drawable.profile_icon
                )
                ProfileSection(
                    title = "Email",
                    value = user.email,
                    iconSource = R.drawable.mail_icon
                )
                ProfileSection(
                    title = "Género",
                    value = user.genre,
                    iconSource = R.drawable.gender_icon
                )
                ProfileSection(
                    title = "Fecha de registro",
                    value = user.registerDate,
                    iconSource = R.drawable.calendar_icon
                )
                ProfileSection(
                    title = "Teléfono",
                    value = user.phone,
                    iconSource = R.drawable.phone_icon
                )
                AddressMap(title = "Dirección", value = user.address)
            }
        }

    }




}


@Composable
fun ProfileSection(title: String, value: String, iconSource: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Icon(
            painter = painterResource(id = iconSource),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
            .padding(start = 32.dp, end = 32.dp)
        )
        Box(
            Modifier
                .fillMaxSize()
                .drawBehind {
                    val strokeWidth = 1 * density
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        Color(231, 231, 231, 255),
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                }
                .padding(vertical = 18.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(142, 142, 147)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
fun AddressMap(title: String, value: String) {

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 12.dp)


        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = Color(142, 142, 147),
                modifier = Modifier.padding(top = 18.dp, start = 80.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.fake_map),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(143.dp)
                    .padding(start = 4.dp, top = 12.dp)
            )
        }
}
