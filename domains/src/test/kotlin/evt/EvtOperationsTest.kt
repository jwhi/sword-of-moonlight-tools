package evt

import com.jwhi.som.domains.evt.EvtOpDisplayMessage
import com.jwhi.som.domains.evt.EvtOpDisplayMessageFormat
import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.evt.EvtOpIfMessage
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.nio.ByteBuffer
import java.nio.ByteOrder

class EvtOperationsTest : FunSpec({
    test("Display Message from bytes") {
        val bytes = listOf(0u, 0u, 16u, 0u, 84u, 104u, 97u, 110u, 107u, 32u, 121u, 111u, 117u, 33u, 0u, 0u).map {
            it.toByte()
        }.toByteArray()
        val byteBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
        val expected = EvtOpDisplayMessage(
            opId = EvtOpIds.MESSAGE.value,
            opSize = 16u,
            text = "Thank you!",
            bytes = bytes.toList()
        )

        val actualOpId = byteBuffer.getShort().toUShort()
        val actualOpSize = byteBuffer.getShort().toUShort()
        val actual = EvtOpDisplayMessage.fromByteBuffer(
            actualOpId,
            actualOpSize,
            byteBuffer,
        )

        actual shouldBe expected
    }

    test("Formatted Message from bytes") {
        val bytes = listOf(1u, 0u, 44u, 0u, 255u, 0u, 0u, 0u, 43u, 2u, 0u, 0u, 70u, 73u, 78u, 69u, 46u, 32u, 73u, 39u, 76u, 76u, 32u, 66u, 69u, 32u, 70u, 73u, 78u, 69u, 46u, 0u, 77u, 83u, 32u, 71u, 111u, 116u, 104u, 105u, 99u, 0u, 0u, 0u).map {
            it.toByte()
        }.toByteArray()
        val byteBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
        val expected = EvtOpDisplayMessageFormat(
            opId = EvtOpIds.FORMAT_MESSAGE.value,
            opSize = 44u,
            textColorRed = 255u,
            textColorGreen = 0u,
            textColorBlue = 0u,
            textColorExtra = 0u,
            fontWeight = 555u,
            fontWeightPadding = 0u,
            text = "FINE. I'LL BE FINE.",
            fontName = "MS Gothic",
            bytes = bytes.toList()
        )

        val actualOpId = byteBuffer.getShort().toUShort()
        val actualOpSize = byteBuffer.getShort().toUShort()
        val actual = EvtOpDisplayMessageFormat.fromByteBuffer(
            actualOpId,
            actualOpSize,
            byteBuffer,
        )

        actual shouldBe expected
    }

    test("If Message from bytes") {
        val bytes = listOf(141u, 0u, 56u, 0u, 89u, 111u, 117u, 32u, 104u, 97u, 118u, 101u, 32u, 115u, 111u, 32u, 109u, 97u, 110u, 121u, 32u, 104u, 101u, 97u, 108u, 105u, 110u, 103u, 32u, 104u, 101u, 114u, 98u, 115u, 33u, 32u, 13u, 10u, 71u, 105u, 109u, 109u, 101u, 32u, 111u, 110u, 101u, 63u, 0u, 89u, 101u, 115u, 0u, 78u, 111u, 0u).map {
            it.toByte()
        }.toByteArray()
        val byteBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
        val expected = EvtOpIfMessage(
            opId = EvtOpIds.IF_MESSAGE.value,
            opSize = 56u,
            text = "You have so many healing herbs! \r\n" +
                "Gimme one?",
            option1 = "Yes",
            option2 = "No",
            bytes = bytes.toList()
        )

        val actualOpId = byteBuffer.getShort().toUShort()
        val actualOpSize = byteBuffer.getShort().toUShort()
        val actual = EvtOpIfMessage.fromByteBuffer(
            actualOpId,
            actualOpSize,
            byteBuffer,
        )

        actual shouldBe expected
    }
})
