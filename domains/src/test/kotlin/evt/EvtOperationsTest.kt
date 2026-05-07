package evt

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.evt.operations.ActivateEnemy
import com.jwhi.som.domains.evt.operations.ActivateItem
import com.jwhi.som.domains.evt.operations.ActivateNPC
import com.jwhi.som.domains.evt.operations.ChangeCounter
import com.jwhi.som.domains.evt.operations.ChangePlayerParameter
import com.jwhi.som.domains.evt.operations.CompareType
import com.jwhi.som.domains.evt.operations.DisplayFormattedMessage
import com.jwhi.som.domains.evt.operations.DisplayMessage
import com.jwhi.som.domains.evt.operations.IfCounterCondition
import com.jwhi.som.domains.evt.operations.IfMessagePrompt
import com.jwhi.som.domains.evt.operations.PlayerParameter
import com.jwhi.som.domains.evt.operations.SetPlayerParameterInCounter
import com.jwhi.som.domains.evt.operations.ShopOpen
import com.jwhi.som.domains.evt.operations.WarpEnemy
import com.jwhi.som.domains.evt.operations.WarpNPC
import com.jwhi.som.domains.evt.operations.WarpPlayerBasic
import com.jwhi.som.domains.evt.operations.WarpPlayerDetailed
import com.jwhi.som.domains.evt.operations.WarpScreenEffect
import com.jwhi.som.domains.evt.operations.WayChanged
import com.jwhi.som.domains.helpers.asBufferLittleEndian
import com.jwhi.som.domains.helpers.byteArrayFrom
import com.jwhi.som.domains.helpers.getUShort
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.Tuple2
import io.kotest.core.Tuple5
import io.kotest.core.Tuple6
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import sun.security.krb5.Confounder.bytes

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

    context("Warp NPC") {
        withData(
            nameFn = { it.b.toString() },
            Tuple2(
                byteArrayFrom(0x19u, 0x00u, 0x18u, 0x00u, 0x02u, 0x00u, 0x5Fu, 0x01u, 0xC8u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x80u, 0xBFu, 0x00u, 0x00u, 0xA0u, 0xC1u, 0x00u, 0x00u, 0x80u, 0x3Fu),
                WarpNPC(
                    npcId = 2u,
                    x = 95u,
                    z = 1u,
                    direction = 200u,
                    fineX = -1.0f,
                    fineY = -20.0f,
                    fineZ = 1.0f,
                    bytes = listOf(25, 0, 24, 0, 2, 0, 95, 1, -56, 0, 0, 0, 0, 0, -128, -65, 0, 0, -96, -63, 0, 0, -128, 63)
                )
            ),
            Tuple2(
                byteArrayFrom(0x19u, 0x00u, 0x18u, 0x00u, 0x03u, 0x00u, 0x01u, 0x01u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, ),
                WarpNPC(
                    npcId = 3u,
                    x = 1u,
                    z = 1u,
                    direction = 0u,
                    fineX = 0.0f,
                    fineY = 0.0f,
                    fineZ = 0.0f,
                    bytes = listOf(25, 0, 24, 0, 3, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                )
            )
        ) { (bytes, expected) ->
            val buffer = bytes.asBufferLittleEndian()

            val opId = buffer.getUShort()
            val opSize = buffer.getUShort()
            val actual = WarpNPC.fromByteBuffer(buffer)

            actual shouldBe expected
            opId shouldBe expected.opId
            opSize shouldBe expected.opSize
        }
    }

    context("Warp Enemy") {
        withData(
            nameFn = { it.b.toString() },
            Tuple2(
                byteArrayFrom(0x1Au, 0x00u, 0x18u, 0x00u, 0x01u, 0x00u, 0x01u, 0x63u, 0x04u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x80u, 0x3Fu, 0x00u, 0x00u, 0xA0u, 0x41u, 0x00u, 0x00u, 0x80u, 0x3Fu),
                WarpEnemy(
                    enemyId = 1u,
                    x = 1u,
                    z = 99u,
                    direction = 4u,
                    fineX = 1.0f,
                    fineY = 20.0f,
                    fineZ = 1.0f,
                    bytes = listOf(26, 0, 24, 0, 1, 0, 1, 99, 4, 0, 0, 0, 0, 0, -128, 63, 0, 0, -96, 65, 0, 0, -128, 63)
                )
            ),
            Tuple2(
                byteArrayFrom(0x1Au, 0x00u, 0x18u, 0x00u, 0x00u, 0x00u, 0x63u, 0x5Fu, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u),
                WarpEnemy(
                    enemyId = 0u,
                    x = 99u,
                    z = 95u,
                    direction = 0u,
                    fineX = 0.0f,
                    fineY = 0.0f,
                    fineZ = 0.0f,
                    bytes = listOf(26, 0, 24, 0, 0, 0, 99, 95, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                )
            )
        ) { (bytes, expected) ->
            val buffer = bytes.asBufferLittleEndian()

            val opId = buffer.getUShort()
            val opSize = buffer.getUShort()
            val actual = WarpEnemy.fromByteBuffer(buffer)

            actual shouldBe expected
            opId shouldBe expected.opId
            opSize shouldBe expected.opSize
        }
    }

    context("Activate") {
        withData(
            nameFn = { it.b.toString() },
            Tuple2(
                byteArrayFrom(0x14u, 0x00u, 0x08u, 0x00u, 0x02u, 0x00u, 0x00u, 0x00u),
                ActivateNPC(npcId = 2u, bytes = listOf(20, 0, 8, 0, 2, 0, 0, 0))
            ),
            Tuple2(
                byteArrayFrom(0x15u, 0x00u, 0x08u, 0x00u, 0x02u, 0x00u, 0x00u, 0x00u),
                ActivateEnemy(enemyId = 2u, bytes = listOf(21, 0, 8, 0, 2, 0, 0, 0))
            ),
            Tuple2(
                byteArrayFrom(0x16u, 0x00u, 0x08u, 0x00u, 0x04u, 0x00u, 0x00u, 0x00u),
                ActivateItem(itemId = 4u, bytes = listOf(22, 0, 8, 0, 4, 0, 0, 0))
            )
        ) { (bytes, expected) ->
            val buffer = bytes.asBufferLittleEndian()

            val opId = buffer.getUShort()
            val opSize = buffer.getUShort()
            val actual = when (expected) {
                is ActivateNPC -> ActivateNPC.fromByteBuffer(buffer)
                is ActivateEnemy -> ActivateEnemy.fromByteBuffer(buffer)
                is ActivateItem -> ActivateItem.fromByteBuffer(buffer)
                else -> {}
            }

            actual shouldBe expected
            opId shouldBe expected.opId
            opSize shouldBe expected.opSize
        }
    }

    context("Warp Player Detailed") {
        withData(
            nameFn = { it.b.toString() },
            Tuple2(
                byteArrayFrom(0x3Cu, 0x00u, 0x1Cu, 0x00u, 0x3Fu, 0x01u, 0xFFu, 0x03u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u),
                WarpPlayerDetailed(
                    warpToMapId = 63u,
                    useDefaultStartPoint = true,
                    screenEffectAsLeave = WarpScreenEffect.NONE,
                    screenEffectAsEnter = WarpScreenEffect.WHITE_FADES_ON,
                    x = 0u,
                    z = 0u,
                    useDirection = false,
                    useFineX = false,
                    useFineY = false,
                    useFineZ = false,
                    bytes = listOf(60, 0, 28, 0, 63, 1, -1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3Cu, 0x00u, 0x1Cu, 0x00u, 0x3Fu, 0x00u, 0x01u, 0x02u, 0x5Eu, 0x04u, 0xFBu, 0x00u, 0x00u, 0x00u, 0x80u, 0xBFu, 0x00u, 0x00u, 0xA0u, 0x41u, 0x00u, 0x00u, 0x80u, 0x3Fu, 0x0Fu, 0x00u, 0x00u, 0x00u),
                WarpPlayerDetailed(
                    warpToMapId = 63u,
                    useDefaultStartPoint = false,
                    screenEffectAsLeave = WarpScreenEffect.BLACK_FADES_ON,
                    screenEffectAsEnter = WarpScreenEffect.WHITE_FADES_OFF,
                    x = 94u,
                    z = 4u,
                    direction = 251u,
                    fineX = -1.0f,
                    fineY = 20.0f,
                    fineZ = 1.0f,
                    useDirection = true,
                    useFineX = true,
                    useFineY = true,
                    useFineZ = true,
                    bytes = listOf(60, 0, 28, 0, 63, 0, 1, 2, 94, 4, -5, 0, 0, 0, -128, -65, 0, 0, -96, 65, 0, 0, -128, 63, 15, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3Cu, 0x00u, 0x1Cu, 0x00u, 0x02u, 0x00u, 0xFFu, 0x01u, 0x01u, 0x32u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x05u, 0x00u, 0x00u, 0x00u),
                WarpPlayerDetailed(
                    warpToMapId = 2u,
                    useDefaultStartPoint = false,
                    screenEffectAsLeave = WarpScreenEffect.NONE,
                    screenEffectAsEnter = WarpScreenEffect.BLACK_FADES_ON,
                    x = 1u,
                    z = 50u,
                    direction = 0u,
                    fineY = 0.0f,
                    useDirection = true,
                    useFineX = false,
                    useFineY = true,
                    useFineZ = false,
                    bytes = listOf(60, 0, 28, 0, 2, 0, -1, 1, 1, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3Cu, 0x00u, 0x1Cu, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x02u, 0x4Bu, 0x00u, 0x00u, 0x00u, 0x00u, 0x80u, 0x3Fu, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x02u, 0x00u, 0x00u, 0x00u),
                WarpPlayerDetailed(
                    warpToMapId = 0u,
                    useDefaultStartPoint = false,
                    screenEffectAsLeave = WarpScreenEffect.BLACK_FADES_OFF,
                    screenEffectAsEnter = WarpScreenEffect.BLACK_FADES_OFF,
                    x = 2u,
                    z = 75u,
                    fineX = 1.0f,
                    useDirection = false,
                    useFineX = true,
                    useFineY = false,
                    useFineZ = false,
                    bytes = listOf(60, 0, 28, 0, 0, 0, 0, 0, 2, 75, 0, 0, 0, 0, -128, 63, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3Cu, 0x00u, 0x1Cu, 0x00u, 0x00u, 0x00u, 0x01u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x9Au, 0x99u, 0x19u, 0xBFu, 0x08u, 0x00u, 0x00u, 0x00u),
                WarpPlayerDetailed(
                    warpToMapId = 0u,
                    useDefaultStartPoint = false,
                    screenEffectAsLeave = WarpScreenEffect.BLACK_FADES_ON,
                    screenEffectAsEnter = WarpScreenEffect.BLACK_FADES_OFF,
                    x = 0u,
                    z = 0u,
                    fineZ = -0.6f,
                    useDirection = false,
                    useFineX = false,
                    useFineY = false,
                    useFineZ = true,
                    bytes = listOf(60, 0, 28, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -102, -103, 25, -65, 8, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3Cu, 0x00u, 0x1Cu, 0x00u, 0x3Fu, 0x00u, 0x02u, 0x02u, 0x63u, 0x63u, 0x17u, 0x01u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x01u, 0x00u, 0x00u, 0x00u),
                WarpPlayerDetailed(
                    warpToMapId = 63u,
                    useDefaultStartPoint = false,
                    screenEffectAsLeave = WarpScreenEffect.WHITE_FADES_OFF,
                    screenEffectAsEnter = WarpScreenEffect.WHITE_FADES_OFF,
                    x = 99u,
                    z = 99u,
                    direction = 279u,
                    useDirection = true,
                    useFineX = false,
                    useFineY = false,
                    useFineZ = false,
                    bytes = listOf(60, 0, 28, 0, 63, 0, 2, 2, 99, 99, 23, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0)
                )
            )
        ) { (bytes, expected) ->
            val buffer = bytes.asBufferLittleEndian()
            val opId = buffer.getUShort()
            val opSize = buffer.getUShort()
            val actual = WarpPlayerDetailed.fromByteBuffer(buffer)

            assertSoftly(actual) {
                actual.shouldBeTypeOf<WarpPlayerDetailed>()
                actual.warpToMapId shouldBe expected.warpToMapId
                actual.useDefaultStartPoint shouldBe expected.useDefaultStartPoint
                actual.screenEffectAsLeave shouldBe expected.screenEffectAsLeave
                actual.screenEffectAsEnter shouldBe expected.screenEffectAsEnter
                actual.x shouldBe expected.x
                actual.z shouldBe expected.z
                actual.direction shouldBe expected.direction
                actual.fineX shouldBe (expected.fineX plusOrMinus 0.01f)
                actual.fineY shouldBe (expected.fineY plusOrMinus 0.01f)
                actual.fineZ shouldBe (expected.fineZ plusOrMinus 0.01f)
                actual.useDirection shouldBe expected.useDirection
                actual.useFineX shouldBe expected.useFineX
                actual.useFineY shouldBe expected.useFineY
                actual.useFineZ shouldBe expected.useFineZ
                actual.bytes shouldBe expected.bytes
            }
            opId shouldBe expected.opId
            opSize shouldBe expected.opSize
        }
    }

    context("Warp Player Basic") {
        withData(
            nameFn = { it.b.toString() },
            Tuple2(
                byteArrayFrom(0x3Du, 0x00u, 0x18u, 0x00u, 0x1Au, 0x63u, 0x28u, 0x00u, 0x00u, 0x00u, 0x80u, 0x3Fu, 0x00u, 0x00u, 0xA0u, 0xC1u, 0x33u, 0x33u, 0x33u, 0xBFu, 0x0Fu, 0x00u, 0x00u, 0x00u),
                WarpPlayerBasic(
                    x = 26u,
                    z = 99u,
                    direction = 40u,
                    fineX = 1.0f,
                    fineY = -20.0f,
                    fineZ = -0.7f,
                    useDirection = true,
                    useFineX = true,
                    useFineY = true,
                    useFineZ = true,
                    bytes = listOf(61, 0, 24, 0, 26, 99, 40, 0, 0, 0, -128, 63, 0, 0, -96, -63, 51, 51, 51, -65, 15, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3Du, 0x00u, 0x18u, 0x00u, 0x01u, 0x02u, 0x00u, 0x00u, 0xCDu, 0xCCu, 0xCCu, 0xBEu, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x02u, 0x00u, 0x00u, 0x00u),
                WarpPlayerBasic(
                    x = 1u,
                    z = 2u,
                    fineX = -0.4f,
                    useDirection = false,
                    useFineX = true,
                    useFineY = false,
                    useFineZ = false,
                    bytes = listOf(61, 0, 24, 0, 1, 2, 0, 0, -51, -52, -52, -66, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3Du, 0x00u, 0x18u, 0x00u, 0x01u, 0x02u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0xCDu, 0xCCu, 0x9Cu, 0xC0u, 0x00u, 0x00u, 0x00u, 0x00u, 0x04u, 0x00u, 0x00u, 0x00u),
                WarpPlayerBasic(
                    x = 1u,
                    z = 2u,
                    fineY = -4.9f,
                    useDirection = false,
                    useFineX = false,
                    useFineY = true,
                    useFineZ = false,
                    bytes = listOf(61, 0, 24, 0, 1, 2, 0, 0, 0, 0, 0, 0, -51, -52, -100, -64, 0, 0, 0, 0, 4, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3Du, 0x00u, 0x18u, 0x00u, 0x02u, 0x03u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x67u, 0x66u, 0x66u, 0x3Fu, 0x08u, 0x00u, 0x00u, 0x00u),
                WarpPlayerBasic(
                    x = 2u,
                    z = 3u,
                    fineZ = 0.9f,
                    useDirection = false,
                    useFineX = false,
                    useFineY = false,
                    useFineZ = true,
                    bytes = listOf(61, 0, 24, 0, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 103, 102, 102, 63, 8, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3Du, 0x00u, 0x18u, 0x00u, 0x05u, 0x06u, 0x96u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x01u, 0x00u, 0x00u, 0x00u),
                WarpPlayerBasic(
                    x = 5u,
                    z = 6u,
                    direction = 150u,
                    useDirection = true,
                    useFineX = false,
                    useFineY = false,
                    useFineZ = false,
                    bytes = listOf(61, 0, 24, 0, 5, 6, -106, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3Du, 0x00u, 0x18u, 0x00u, 0x0Au, 0x0Fu, 0x00u, 0x00u, 0xCDu, 0xCCu, 0xCCu, 0xBEu, 0x9Au, 0x99u, 0x59u, 0x40u, 0x00u, 0x00u, 0x00u, 0x00u, 0x06u, 0x00u, 0x00u, 0x00u),
                WarpPlayerBasic(
                    x = 10u,
                    z = 15u,
                    fineX = -0.4f,
                    fineY = 3.4f,
                    useDirection = false,
                    useFineX = true,
                    useFineY = true,
                    useFineZ = false,
                    bytes = listOf(61, 0, 24, 0, 10, 15, 0, 0, -51, -52, -52, -66, -102, -103, 89, 64, 0, 0, 0, 0, 6, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x3Du, 0x00u, 0x18u, 0x00u, 0x15u, 0x61u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u),
                WarpPlayerBasic(
                    x = 21u,
                    z = 97u,
                    useDirection = false,
                    useFineX = false,
                    useFineY = false,
                    useFineZ = false,
                    bytes = listOf(61, 0, 24, 0, 21, 97, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                )
            )
        ) { (bytes, expected) ->
            val buffer = bytes.asBufferLittleEndian()
            val opId = buffer.getUShort()
            val opSize = buffer.getUShort()
            val actual = WarpPlayerBasic.fromByteBuffer(buffer)

            assertSoftly(actual) {
                actual.shouldBeTypeOf<WarpPlayerBasic>()
                actual.x shouldBe expected.x
                actual.z shouldBe expected.z
                actual.direction shouldBe expected.direction
                actual.fineX shouldBe (expected.fineX plusOrMinus 0.01f)
                actual.fineY shouldBe (expected.fineY plusOrMinus 0.01f)
                actual.fineZ shouldBe (expected.fineZ plusOrMinus 0.01f)
                actual.useDirection shouldBe expected.useDirection
                actual.useFineX shouldBe expected.useFineX
                actual.useFineY shouldBe expected.useFineY
                actual.useFineZ shouldBe expected.useFineZ
                actual.bytes shouldBe expected.bytes
            }
            opId shouldBe expected.opId
            opSize shouldBe expected.opSize
        }
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
