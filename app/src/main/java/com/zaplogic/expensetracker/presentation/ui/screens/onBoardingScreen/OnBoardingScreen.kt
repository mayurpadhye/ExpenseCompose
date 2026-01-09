package com.zaplogic.expensetracker.presentation.ui.screens.onBoardingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.zaplogic.expensetracker.R
import com.zaplogic.expensetracker.presentation.ui.theme.Dimens

@Composable
fun OnBoardingScreen(
    navHostController: NavHostController,
    onGetStartedClick :() -> Unit,
    onLoginClick :() -> Unit
) {
    Box(

    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_on_boarding_background),
                    contentDescription = ""
                )
                Image(
                    modifier = Modifier.padding(top = Dimens.dp_30),
                    painter = painterResource(id = R.drawable.onboarding_image),
                    contentDescription = ""
                )

            }
            Spacer(modifier = Modifier.padding(top = Dimens.dp_15))

            Text(
                textAlign = TextAlign.Center,
                lineHeight = Dimens.sp_40,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.dp_15),
                text =
                    stringResource(R.string.str_spend_smarter),
                color = Color(0xFF63B5AF),
                fontWeight = FontWeight.SemiBold,
                fontSize = Dimens.sp_35
            )
            Spacer(modifier = Modifier.padding(top = Dimens.dp_15))
            ElevatedButton(
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = Dimens.dp_10,
                    pressedElevation = Dimens.dp_15,
                    focusedElevation = Dimens.dp_10
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.color_primary),
                    contentColor = Color.White,
                    disabledContentColor = Color.DarkGray,
                    disabledContainerColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp_50)
                    .padding(horizontal = Dimens.dp_15),
                onClick = {
                   // navHostController.navigate(NavigationItem.SignUpScreenItem.route)
                  onGetStartedClick.invoke()
                }

            ) {
                Text(stringResource(R.string.str_get_started))
            }
            Row(
                modifier = Modifier
                    .padding(top = Dimens.dp_15)
                    .fillMaxWidth()
                    .clickable {
                       onLoginClick.invoke()
                    },
                horizontalArrangement = Arrangement.Center
            ) {
                Text(stringResource(R.string.str_alredy_have_account))
                Text(
                    stringResource(R.string.str_login),
                    color = colorResource(R.color.color_primary),
                    fontWeight = FontWeight.Bold
                )
            }


        }
    }

}