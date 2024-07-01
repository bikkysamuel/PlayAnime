package io.github.bikkysamuel.playanime.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.bikkysamuel.playanime.R
import io.github.bikkysamuel.playanime.ui.nav.BottomBarScreen
import io.github.bikkysamuel.playanime.ui.theme.Orange40
import io.github.bikkysamuel.playanime.ui.theme.Orange80
import io.github.bikkysamuel.playanime.ui.theme.OrangeGrey40
import io.github.bikkysamuel.playanime.ui.theme.OrangeGrey80
import io.github.bikkysamuel.playanime.ui.theme.Pink40
import io.github.bikkysamuel.playanime.ui.theme.Pink80
import io.github.bikkysamuel.playanime.ui.theme.Purple40
import io.github.bikkysamuel.playanime.ui.theme.Purple80
import io.github.bikkysamuel.playanime.ui.theme.PurpleGrey40
import io.github.bikkysamuel.playanime.ui.theme.PurpleGrey80
import io.github.bikkysamuel.playanime.ui.theme.Yellow40
import io.github.bikkysamuel.playanime.ui.theme.Yellow80
import io.github.bikkysamuel.playanime.ui.viewmodels.MainViewModel

@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(10.dp)
    ) {
        SettingTitle()
        ThemePicker(viewModel = viewModel)

        FontStylePicker(viewModel = viewModel)
    }
}

@Composable
fun SettingTitle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(10.dp)
    ) {
        Text(
            text = BottomBarScreen.Settings.title,
            fontSize = 18.sp
        )
    }
}

@Composable
fun ThemePicker(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
    ) {
        SubSettingTitle(name = "Theme")

        Row(
            modifier = Modifier
                .padding(10.dp)
                .horizontalScroll(scrollState)
        ) {

            ThemeButton(
                primaryColor = Color.White,
                secondaryColor = Color.Gray,
                tertiaryColor = Color.Black,
                themeName = "System Default",
                isCurrentSelection = {
                    viewModel.compareCurrentAppTheme(
                        themeSet = MainViewModel.ThemeSet.SYSTEM_DEFAULT
                    )
                },
                onClick = {
                    viewModel.setSystemDefaultTheme()
                })

            ThemeButton(
                primaryColor = Purple80,
                secondaryColor = PurpleGrey80,
                tertiaryColor = Pink80,
                themeName = "Dark",
                isCurrentSelection = {
                    viewModel.compareCurrentAppTheme(
                        themeSet = MainViewModel.ThemeSet.DARK
                    )
                },
                onClick = {
                    viewModel.setDarkTheme()
                })

            ThemeButton(
                primaryColor = Purple40,
                secondaryColor = PurpleGrey40,
                tertiaryColor = Pink40,
                themeName = "Light",
                isCurrentSelection = {
                    viewModel.compareCurrentAppTheme(
                        themeSet = MainViewModel.ThemeSet.LIGHT
                    )
                },
                onClick = {
                    viewModel.setLightTheme()
                })

            ThemeButton(
                primaryColor = Orange80,
                secondaryColor = OrangeGrey80,
                tertiaryColor = Yellow80,
                themeName = "Orange Dark",
                isCurrentSelection = {
                    viewModel.compareCurrentAppTheme(
                        themeSet = MainViewModel.ThemeSet.ORANGE_DARK
                    )
                },
                onClick = {
                    viewModel.setOrangeDarkTheme()
                })

            ThemeButton(
                primaryColor = Orange40,
                secondaryColor = OrangeGrey40,
                tertiaryColor = Yellow40,
                themeName = "Orange Light",
                isCurrentSelection = {
                    viewModel.compareCurrentAppTheme(
                        themeSet = MainViewModel.ThemeSet.ORANGE_LIGHT
                    )
                },
                onClick = {
                    viewModel.setOrangeLightTheme()
                })
        }
    }
}

@Composable
fun FontStylePicker(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        SubSettingTitle(name = "Fonts")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .horizontalScroll(state = scrollState)
        ) {

            FontStyleButton(
                fontStyleSetObject = viewModel.getFontStyleObject(
                    fontStyleSet = MainViewModel.FontStyleSet.SYSTEM_DEFAULT
                ),
                isCurrentSelection = {
                    viewModel.compareCurrentFontStyle(MainViewModel.FontStyleSet.SYSTEM_DEFAULT)
                },
                onClick = {
                    viewModel.setSystemDefaultFontStyle()
                }
            )

            FontStyleButton(
                fontStyleSetObject = viewModel.getFontStyleObject(
                    fontStyleSet = MainViewModel.FontStyleSet.NOTABLE
                ),
                isCurrentSelection = {
                    viewModel.compareCurrentFontStyle(MainViewModel.FontStyleSet.NOTABLE)
                },
                onClick = {
                    viewModel.setNotableFontStyle()
                }
            )
        }
    }
}

@Composable
fun SubSettingTitle(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        fontSize = 18.sp,
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewThemeButton(modifier: Modifier = Modifier) {
    ThemeButton(
        primaryColor = Color.White,
        secondaryColor = Color.Gray,
        tertiaryColor = Color.Black,
        themeName = "System Default",
        isCurrentSelection = { true },
        onClick = { })
}

@Composable
fun ThemeButton(
    primaryColor: Color,
    secondaryColor: Color,
    tertiaryColor: Color,
    themeName: String,
    isCurrentSelection: () -> Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(100.dp)
                .border(width = 3.dp, color = secondaryColor, shape = RoundedCornerShape(5.dp))
                .clickable {
                    onClick()
                }
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(30.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(primaryColor)
            )

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(20.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(tertiaryColor)
                    .align(Alignment.BottomEnd)
            )

            if (isCurrentSelection()) {
                CheckMark(modifier = Modifier.align(Alignment.TopEnd))
            }
        }

        Text(
            text = themeName,
            maxLines = 1
        )
    }
}

@Composable
fun FontStyleButton(
    fontStyleSetObject: MainViewModel.FontStyleObject,
    isCurrentSelection: () -> Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        OutlinedButton(
            shape = RoundedCornerShape(5.dp),
            onClick = {
                onClick()
            }
        ) {
            Text(
                text = fontStyleSetObject.name,
                fontFamily = fontStyleSetObject.fontFamily
            )
        }

        if (isCurrentSelection()) {
            CheckMark(modifier = Modifier.align(Alignment.TopEnd))
        }
    }

    Spacer(modifier = Modifier.size(5.dp))
}

@Composable
fun CheckMark(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .padding(5.dp),
        painter = painterResource(id = R.drawable.baseline_check_circle_24),
        contentDescription = null
    )
}