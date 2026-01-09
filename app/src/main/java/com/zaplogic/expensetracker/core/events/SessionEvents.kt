package com.zaplogic.expensetracker.core.events

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class SessionEvents @Inject constructor() {
    private val _logoutEvent = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
     val logoutEvent = _logoutEvent.asSharedFlow()

    fun triggerLogout(){
        _logoutEvent.tryEmit(true)
    }
}