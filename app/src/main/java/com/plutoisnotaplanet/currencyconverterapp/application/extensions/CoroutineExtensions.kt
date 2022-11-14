package com.plutoisnotaplanet.currencyconverterapp.application.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

fun CoroutineScope.launchOnIo(block: suspend CoroutineScope.() -> Unit) =
    launch(SupervisorJob() + Dispatchers.IO) {
        block()
    }