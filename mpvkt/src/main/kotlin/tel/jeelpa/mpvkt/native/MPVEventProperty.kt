package tel.jeelpa.mpvkt.native

import com.sun.jna.Pointer
import com.sun.jna.Structure

class MPVEventProperty(p: Pointer) : Structure(p) {
    init { read() }

    @JvmField var name: String? = null
    @JvmField var format: Int = 0
    @JvmField var data: Pointer? = null

    override fun getFieldOrder() =
        listOf("name", "format", "data")

}

