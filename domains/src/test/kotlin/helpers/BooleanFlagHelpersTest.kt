package helpers

import com.jwhi.som.domains.helpers.asBufferLittleEndian
import com.jwhi.som.domains.helpers.byteArrayFrom
import com.jwhi.som.domains.helpers.getBooleanFlags
import io.kotest.core.Tuple2
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class BooleanFlagHelpersTest : FunSpec({
    context("Hex value gives expected boolean flag in little endian") {
        withData(
            nameFn = { it.a.toByte().toHexString(HexFormat.UpperCase) },
            Tuple2(
                0x00,
                listOf(false, false, false, false)
            ),
            Tuple2(
                0x0F,
                listOf(true, true, true, true)
            ),
            Tuple2(
                0x02,
                listOf(false, true, false, false)
            ),
            Tuple2(
                0x04,
                listOf(false, false, true, false)
            ),
            Tuple2(
                0x08,
                listOf(false, false, false, true)
            ),
            Tuple2(
                0x01,
                listOf(true, false, false, false)
            ),
            Tuple2(
                0x06,
                listOf(false, true, true, false)
            )
        ) { (value, expected) ->
            val actual = byteArrayFrom(value).asBufferLittleEndian().get().getBooleanFlags()

            actual shouldBe expected
        }
    }
})
