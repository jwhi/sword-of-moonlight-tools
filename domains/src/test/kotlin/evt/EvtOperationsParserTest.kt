package evt

import com.jwhi.som.domains.evt.operations.BeginScreenEffect
import com.jwhi.som.domains.evt.operations.ChangePlayerParameter
import com.jwhi.som.domains.evt.operations.DisplayFormattedMessage
import com.jwhi.som.domains.evt.operations.DisplayMessage
import com.jwhi.som.domains.evt.operations.EndIf
import com.jwhi.som.domains.evt.operations.OperationEnd
import com.jwhi.som.domains.evt.operations.Otherwise
import com.jwhi.som.domains.evt.operations.PlayerParameter
import com.jwhi.som.domains.evt.operations.SavePoint
import com.jwhi.som.domains.evt.operations.ScreenEffectType
import com.jwhi.som.domains.evt.operations.ShopOpen
import com.jwhi.som.domains.evt.operations.WarpPlayer
import com.jwhi.som.domains.evt.operations.WarpPlayerDetailed
import com.jwhi.som.domains.evt.operations.WayChanged
import com.jwhi.som.domains.evt.operations.parseEvtOperation
import com.jwhi.som.domains.helpers.asBufferLittleEndian
import com.jwhi.som.domains.helpers.byteArrayFrom
import com.jwhi.som.domains.helpers.byteListFrom
import io.kotest.core.Tuple2
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalUnsignedTypes::class)
class EvtOperationsParserTest : FunSpec({
    context("Parses to correct operation based on id bytes") {
        withData(
            nameFn = { it.b.toString() },
            Tuple2(
                byteArrayFrom(0x00, 0x00, 0x14, 0x00, 0x54, 0x65, 0x73, 0x74, 0x20, 0x6D, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, 0x00, 0x00, 0x00, 0x00),
                DisplayMessage(
                    opSize = 20u,
                    text = "Test message",
                    bytes = listOf(0, 0, 20, 0, 84, 101, 115, 116, 32, 109, 101, 115, 115, 97, 103, 101, 0, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x01, 0x00, 0xB4, 0x00, 0x32, 0x64, 0xFF, 0x00, 0xEE, 0x02, 0x00, 0x00, 0x54, 0x65, 0x73, 0x74, 0x00, 0x53, 0x69, 0x6D, 0x53, 0x75, 0x6E, 0x2D, 0x45, 0x78, 0x74, 0x47, 0x00, 0x00, 0x00,),
                DisplayFormattedMessage(
                    opSize = 180u,
                    text = "Test",
                    textColorRed = 50u,
                    textColorGreen = 100u,
                    textColorBlue = 255u,
                    fontWeight = 750u,
                    fontName = "SimSun-ExtG",
                    bytes = listOf(1, 0, -76, 0, 50, 100, -1, 0, -18, 2, 0, 0, 84, 101, 115, 116, 0, 83, 105, 109, 83, 117, 110, 45, 69, 120, 116, 71, 0, 0, 0)
                )
            ),
            Tuple2(
              byteArrayFrom(0x17, 0x00, 0x08, 0x00, 0x05, 0x00, 0x00, 0x00,),
                ShopOpen(
                    shopId = 5u,
                    bytes = listOf(23, 0, 8, 0, 5, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x28, 0x00, 0x08, 0x00, 0x04, 0x01, 0x00, 0x00),
                BeginScreenEffect(
                    effectType = ScreenEffectType.RED,
                    loop = true,
                    bytes = listOf(40, 0, 8, 0, 4, 1, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3D, 0x00, 0x18, 0x00, 0x1A, 0x63, 0x28, 0x00, 0x00, 0x00, 0x80, 0x3F, 0x00, 0x00, 0xA0, 0xC1, 0x33, 0x33, 0x33, 0xBF, 0x0F, 0x00, 0x00, 0x00),
                WarpPlayer(
                    x = 26u,
                    z = 99u,
                    direction = 40u,
                    fineX = 0f,
                    fineY = 0f,
                    fineZ = 0f,
                    bytes = listOf()
                )
            ),
            Tuple2(
                byteArrayFrom(0x50, 0x00, 0x0C, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x19, 0x00),
                ChangePlayerParameter(
                    playerParameter = PlayerParameter.HP,
                    wayChanged = WayChanged.DECREMENT_BY,
                    value = 25u,
                    bytes = listOf(80, 0, 12, 0, 0, 2, 0, 0, 0, 0, 25, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x50, 0x00, 0x0C, 0x00, 0x05, 0x03, 0x00, 0x00, 0x00, 0x00, 0xFC, 0x03),
                ChangePlayerParameter(
                    playerParameter = PlayerParameter.GOLD_AMOUNT,
                    wayChanged = WayChanged.COUNTER,
                    value = 1020u,
                    bytes = listOf(80, 0, 12, 0, 5, 3, 0, 0, 0, 0, -4, 3)
                )
            ),
            Tuple2(
                byteArrayFrom(0x79, 0x00, 0x04, 0x00),
                SavePoint(bytes = listOf(121, 0, 4, 0))
            ),
            Tuple2(
                byteArrayFrom(0x8E, 0x00, 0x04, 0x00),
                Otherwise(bytes = listOf(-114, 0, 4, 0))
            ),
            Tuple2(
                byteArrayFrom(0x8F, 0x00, 0x04, 0x00),
                EndIf(bytes = byteListFrom(0x8F, 0x00, 0x04, 0x00))
            ),
            Tuple2(
                byteArrayFrom( 0xFF, 0xFF, 0x04, 0x00),
                OperationEnd(bytes = byteListFrom(0xFF, 0xFF, 0x04, 0x00))
            )
        ) { (bytes, expected) ->
            val actual = bytes.asBufferLittleEndian().parseEvtOperation(0)

            actual shouldBe expected
        }
    }
})
