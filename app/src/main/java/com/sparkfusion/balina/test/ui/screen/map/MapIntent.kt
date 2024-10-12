package com.sparkfusion.balina.test.ui.screen.map

import com.sparkfusion.balina.test.utils.common.UserIntent

sealed interface MapIntent : UserIntent {
    data object LoadImages : MapIntent
}
