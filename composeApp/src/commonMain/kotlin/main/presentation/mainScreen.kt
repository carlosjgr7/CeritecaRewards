package main.presentation

import ProviderDataBase
import multiplatformFunctions.ConfigDevice
import multiplatformFunctions.Platforms
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ceritecareward.composeapp.generated.resources.Res
import ceritecareward.composeapp.generated.resources.configicon
import kotlinx.coroutines.delay
import main.data.DayDataSourceImpl
import main.data.MonthDataSourceImpl
import main.data.interfaces.DayDataSource
import main.data.interfaces.MonthDataSource
import org.jetbrains.compose.resources.painterResource


class MainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var parametersView by remember { mutableStateOf(false) }
        val dialogState = remember { mutableStateOf(false) }

        val database = ProviderDataBase()
        val dayDataSource: DayDataSource = DayDataSourceImpl(database)
        val monthDataSource: MonthDataSource = MonthDataSourceImpl(database)
        val viewModel = rememberScreenModel { MainViewModel(dayDataSource, monthDataSource) }

        var percentage = viewModel.percentSucces.collectAsState()


        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        Box {
            Column(
                Modifier.fillMaxSize().align(Alignment.Center)
                    .clickable { parametersView = !parametersView },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(ConfigDevice.getspacerdivice()),
                    ) {
                        Text(
                            text = "C",
                            fontSize = ConfigDevice.getsizedivice(),
                            color = if (percentage.value >= 12.5) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            ),
                            fontWeight = FontWeight.Bold
                        )


                        Text(
                            text = "E",
                            fontSize = ConfigDevice.getsizedivice(),
                            color = if (percentage.value >= 25) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            ),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "R",
                            fontSize = ConfigDevice.getsizedivice(),
                            color = if (percentage.value >= 37.5) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            ),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "I",
                            fontSize = ConfigDevice.getsizedivice(),
                            color = if (percentage.value >= 50) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            ),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "T",
                            fontSize = ConfigDevice.getsizedivice(),
                            color = if (percentage.value >= 62.5) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            ),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "E",
                            fontSize = ConfigDevice.getsizedivice(),
                            color = if (percentage.value >= 75) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            ),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "C",
                            fontSize = ConfigDevice.getsizedivice(),
                            color = if (percentage.value >= 87.5) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            ),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "A",
                            fontSize = ConfigDevice.getsizedivice(),
                            color = if (percentage.value >= 100) Color(0xFF027017) else Color(
                                0xFFCDCDCD
                            ),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.height(240.dp))

                    if (ConfigDevice.getplatform() == Platforms.Desktop) {
                        Row(
                            Modifier.align(Alignment.BottomEnd)
                        ) {
                            Text(
                                text = "Mí Ferretería",
                                fontSize = ConfigDevice.getsubtitlesizedivice(),
                                color = if (percentage.value >= 100) Color(0xFF027017) else Color(
                                    0xFFCDCDCD
                                ),
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                }
            }
            AnimatedVisibility(
                modifier = Modifier.size(64.dp).fillMaxWidth().align(Alignment.TopEnd)
                    .padding(top = 20.dp, end = 20.dp), visible = parametersView
            ) {
                Image(
                    painter = painterResource(Res.drawable.configicon),
                    contentDescription = "Config Icon",
                    modifier = Modifier.clickable { dialogState.value = true }
                )
            }


            if (dialogState.value) {
                DialogScreen(
                    viewModel,
                ) {
                    dialogState.value = false
                    viewModel.loadData()
                }
            }


        }
    }
}