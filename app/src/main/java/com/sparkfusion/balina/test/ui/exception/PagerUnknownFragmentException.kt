package com.sparkfusion.balina.test.ui.exception

import com.sparkfusion.balina.test.utils.exception.common.BalinaException

class PagerUnknownFragmentException(position: Int) : BalinaException("Unexpected position $position")