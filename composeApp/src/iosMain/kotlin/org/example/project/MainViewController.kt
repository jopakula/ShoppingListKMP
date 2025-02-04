package org.example.project

import App
import androidx.compose.ui.window.ComposeUIViewController
import di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }
