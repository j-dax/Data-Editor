package com.jdax.data.dals

import com.jdax.data.window.AbstractWindow
import com.jdax.data.window.DataWindow
import com.jdax.data.window.FullWindow

interface IDAL {
    public abstract fun readWindow(window: AbstractWindow): DataWindow
    public abstract fun getFullWindow(): FullWindow
}