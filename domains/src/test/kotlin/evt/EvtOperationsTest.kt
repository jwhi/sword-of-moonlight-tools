package evt

import com.jwhi.som.domains.evt.operations.CompareType
import com.jwhi.som.domains.evt.operations.DisplayMessage
import com.jwhi.som.domains.evt.operations.DisplayFormattedMessage
import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.evt.operations.ChangeCounter
import com.jwhi.som.domains.evt.operations.ChangePlayerParameter
import com.jwhi.som.domains.evt.operations.IfCounterCondition
import com.jwhi.som.domains.evt.operations.IfMessagePrompt
import com.jwhi.som.domains.evt.operations.PlayerParameter
import com.jwhi.som.domains.evt.operations.SetPlayerParameterInCounter
import com.jwhi.som.domains.evt.operations.ShopOpen
import com.jwhi.som.domains.evt.operations.WayChanged
import com.jwhi.som.domains.helpers.asBufferLittleEndian
import com.jwhi.som.domains.helpers.byteArrayFrom
import com.jwhi.som.domains.helpers.getUShort
import io.kotest.core.Tuple3
import io.kotest.core.Tuple4
import io.kotest.core.Tuple5
import io.kotest.core.Tuple6
import io.kotest.core.Tuple7
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalUnsignedTypes::class)
class EvtOperationsTest : FunSpec({
    test("Display Message from bytes") {
        val bytes = byteArrayFrom(0u, 0u, 16u, 0u, 84u, 104u, 97u, 110u, 107u, 32u, 121u, 111u, 117u, 33u, 0u, 0u)
        val byteBuffer = bytes.asBufferLittleEndian()
        val expected = DisplayMessage(
            opId = EvtOpIds.DISPLAY_MESSAGE.value,
            opSize = 16u,
            text = "Thank you!",
            bytes = bytes.toList()
        )

        val actualOpId = byteBuffer.getUShort()
        val actualOpSize = byteBuffer.getUShort()
        val actual = DisplayMessage.fromByteBuffer(
            actualOpId,
            actualOpSize,
            byteBuffer,
        )

        actual shouldBe expected
    }

    test("Formatted Message from bytes") {
        val bytes = byteArrayFrom(1u, 0u, 44u, 0u, 255u, 0u, 0u, 0u, 43u, 2u, 0u, 0u, 70u, 73u, 78u, 69u, 46u, 32u, 73u, 39u, 76u, 76u, 32u, 66u, 69u, 32u, 70u, 73u, 78u, 69u, 46u, 0u, 77u, 83u, 32u, 71u, 111u, 116u, 104u, 105u, 99u, 0u, 0u, 0u)
        val byteBuffer = bytes.asBufferLittleEndian()
        val expected = DisplayFormattedMessage(
            opId = EvtOpIds.DISPLAY_FORMATTED_MESSAGE.value,
            opSize = 44u,
            textColorRed = 255u,
            textColorGreen = 0u,
            textColorBlue = 0u,
            textColorExtraByte = 0u,
            fontWeight = 555u,
            fontWeightExtraBytes = 0u,
            text = "FINE. I'LL BE FINE.",
            fontName = "MS Gothic",
            bytes = bytes.toList()
        )

        val actualOpId = byteBuffer.getUShort()
        val actualOpSize = byteBuffer.getUShort()
        val actual = DisplayFormattedMessage.fromByteBuffer(
            actualOpId,
            actualOpSize,
            byteBuffer,
        )

        actual shouldBe expected
    }

    test("Shop Open from bytes") {
        val bytes = byteArrayFrom(23u, 0u, 8u, 0u, 5u, 0u, 0u, 0u)
        val expected = ShopOpen(
            opId = EvtOpIds.SHOP_OPEN.value,
            opSize = 8u,
            shopId = 5u,
            additionalBytes = 0u,
            bytes = bytes.toList()
        )

        val buffer = bytes.asBufferLittleEndian()
        val opId = buffer.getUShort()
        val opSize = buffer.getUShort()
        val actual = ShopOpen.fromByteBuffer(buffer)

        actual shouldBe expected
        opId shouldBe expected.opId
        opSize shouldBe expected.opSize
    }

    context("Change Player Parameters") {
        withData(
            nameFn = { it.a },
            Tuple6(
                "Increase health by set value of 5",
                byteArrayFrom(80u, 0u, 12u, 0u, 0u, 1u, 0u, 0u, 0u, 0u, 5u, 0u),
                PlayerParameter.HP,
                WayChanged.INCREMENT_BY,
                0u,
                5u
            ),
            Tuple6(
                "Set Gold to another counter's value",
                byteArrayFrom(80u, 0u, 12u, 0u, 5u, 3u, 0u, 0u, 0u, 0u, 252u, 3u),
                PlayerParameter.GOLD_AMOUNT,
                WayChanged.COUNTER,
                0u,
                1020u
            ),
            Tuple6(
                "Set item quantity to counter of the same id",
                byteArrayFrom(80u, 0u, 12u, 0u, 4u, 3u, 249u, 0u, 0u, 0u, 249u, 0u),
                PlayerParameter.ITEM_QUANTITY,
                WayChanged.COUNTER,
                249u,
                249u
            ),
            Tuple6(
                "Decrease herb (0) item quantity by set value of 1",
                byteArrayFrom(80u, 0u, 12u, 0u, 4u, 3u, 0u, 0u, 0u, 0u, 1u, 0u),
                PlayerParameter.ITEM_QUANTITY,
                WayChanged.COUNTER,
                0u,
                1u
            )
            ) { (_, bytes, playerParameter, wayChanged, itemId, value) ->
            val byteBuffer = bytes.asBufferLittleEndian()
            val expected = ChangePlayerParameter(
                opId = EvtOpIds.CHANGE_PLAYER_PARAMETER.value,
                opSize = 12u,
                playerParameter = playerParameter,
                wayChanged = wayChanged,
                itemId = itemId.toUShort(),
                value = value.toUShort(),
                bytes = bytes.toList()
            )

            val actualOpId = byteBuffer.getUShort()
            val actualOpSize = byteBuffer.getUShort()
            val actual = ChangePlayerParameter.fromByteBuffer(
                byteBuffer
            )

            actual shouldBe expected
            actualOpId shouldBe expected.opId
            actualOpSize shouldBe expected.opSize
        }
    }

    context("Set Player Parameter To Counter from bytes") {
        withData(
            nameFn = { it.a },
            Tuple5(
                "Store strength stat",
                byteArrayFrom(84u, 0u, 8u, 0u, 2u, 0u, 5u, 0u),
                PlayerParameter.STRENGTH_STAT,
                0u,
                5u
            ),
            Tuple5(
                "Store magic stat",
                byteArrayFrom(84u, 0u, 8u, 0u, 3u, 0u, 7u, 0u),
                PlayerParameter.MAGIC_STAT,
                0u,
                7u
            ),
            Tuple5(
                "Store HP",
                byteArrayFrom(84u, 0u, 8u, 0u, 0u, 0u, 4u, 0u),
                PlayerParameter.HP,
                0u,
                4u
            ),
            Tuple5(
                "Store Item Quantity",
                byteArrayFrom(84u, 0u, 8u, 0u, 4u, 1u, 254u, 3u),
                PlayerParameter.ITEM_QUANTITY,
                1u,
                1022u
            )
        ) { (_, bytes, stat, itemId, targetCounter) ->
            val byteBuffer = bytes.asBufferLittleEndian()
            val expected = SetPlayerParameterInCounter(
                opId = EvtOpIds.SET_PLAYER_PARAMETER_IN_COUNTER.value,
                opSize = 8u,
                playerParameter = stat,
                itemId = itemId.toUByte(),
                targetCounter = targetCounter.toUShort(),
                bytes.toList()
            )

            val actualOpId = byteBuffer.getUShort()
            val actualOpSize = byteBuffer.getUShort()
            val actual = SetPlayerParameterInCounter.fromByteBuffer(
                actualOpId,
                actualOpSize,
                byteBuffer
            )

            actual shouldBe expected
        }
    }

    context("If Counter from bytes") {
        withData(
            nameFn = { it.a },
            Tuple6(
                "Compare < exact value",
                byteArrayFrom(140u, 0u, 12u, 0u, 4u, 0u, 100u, 0u, 0u, 3u, 0u, 0u),
                4u,
                100u,
                false,
                CompareType.LESS_THAN
            ),
            Tuple6(
                "Compare > another counter's value",
                byteArrayFrom(140u, 0u, 12u, 0u, 6u, 0u, 7u, 0u, 1u, 2u, 0u, 0u),
                6u,
                7u,
                true,
                CompareType.GREATER_THAN
            )
        ) { (_, bytes, counterId, compareValue, valueIsCounterId, compareType) ->
            val byteBuffer = bytes.asBufferLittleEndian()
            val expected = IfCounterCondition(
                opId = EvtOpIds.IF_COUNTER_CONDITION.value,
                opSize = 12u,
                counterId = counterId.toUShort(),
                value = compareValue.toUShort(),
                valueIsCounterId = valueIsCounterId,
                compareType = compareType,
                bytes = bytes.toList()
            )

            val actualOpId = byteBuffer.getUShort()
            val actualOpSize = byteBuffer.getUShort()
            val actual = IfCounterCondition.fromByteBuffer(
                actualOpId,
                actualOpSize,
                byteBuffer
            )

            actual shouldBe expected
        }
    }

    test("If Message from bytes") {
        val bytes = byteArrayFrom(141u, 0u, 56u, 0u, 89u, 111u, 117u, 32u, 104u, 97u, 118u, 101u, 32u, 115u, 111u, 32u, 109u, 97u, 110u, 121u, 32u, 104u, 101u, 97u, 108u, 105u, 110u, 103u, 32u, 104u, 101u, 114u, 98u, 115u, 33u, 32u, 13u, 10u, 71u, 105u, 109u, 109u, 101u, 32u, 111u, 110u, 101u, 63u, 0u, 89u, 101u, 115u, 0u, 78u, 111u, 0u)
        val byteBuffer = bytes.asBufferLittleEndian()
        val expected = IfMessagePrompt(
            opId = EvtOpIds.IF_MESSAGE_PROMPT.value,
            opSize = 56u,
            text = "You have so many healing herbs! \r\n" +
                "Gimme one?",
            option1 = "Yes",
            option2 = "No",
            bytes = bytes.toList()
        )

        val actualOpId = byteBuffer.getUShort()
        val actualOpSize = byteBuffer.getUShort()
        val actual = IfMessagePrompt.fromByteBuffer(
            actualOpId,
            actualOpSize,
            byteBuffer,
        )

        actual shouldBe expected
    }

    context("Change Counter from bytes") {
        withData(
            nameFn = { it.a },
            Tuple6(
                "Set to exact value",
                byteArrayFrom(144u, 0u, 12u, 0u, 9u, 3u, 1u, 0u, 0u, 0u, 0u, 0u),
                777u,
                1u,
                false,
                WayChanged.SET_TO
            ),
            Tuple6(
                "Increase by another counter's value",
                byteArrayFrom(144u, 0u, 12u, 0u, 252u, 3u, 254u, 3u, 1u, 1u, 0u, 0u),
                1020u,
                1022u,
                true,
                WayChanged.INCREMENT_BY
            )
        ) { (_, bytes, counterId, value, valueIsCounterId, wayChanged) ->
            val byteBuffer = bytes.asBufferLittleEndian()
            val expected = ChangeCounter(
                opId = EvtOpIds.CHANGE_COUNTER.value,
                opSize = 12u,
                counterId = counterId.toUShort(),
                value = value.toUShort(),
                valueIsCounterId = valueIsCounterId,
                wayChanged = wayChanged,
                unimplementedBytes = 0u,
                bytes = bytes.toList()
            )

            val actualOpId = byteBuffer.getUShort()
            val actualOpSize = byteBuffer.getUShort()
            val actual = ChangeCounter.fromByteBuffer(
                actualOpId,
                actualOpSize,
                byteBuffer
            )

            actual shouldBe expected
        }
    }
})
