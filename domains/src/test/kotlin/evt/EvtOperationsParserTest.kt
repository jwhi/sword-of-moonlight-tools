package evt

import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.evt.operations.ActivateEnemy
import com.jwhi.som.domains.evt.operations.ActivateItem
import com.jwhi.som.domains.evt.operations.ActivateNPC
import com.jwhi.som.domains.evt.operations.BeginScreenEffect
import com.jwhi.som.domains.evt.operations.ChangeBGM
import com.jwhi.som.domains.evt.operations.ChangeCounter
import com.jwhi.som.domains.evt.operations.ChangeDashSaveTemporarily
import com.jwhi.som.domains.evt.operations.ChangePage
import com.jwhi.som.domains.evt.operations.ChangePageType
import com.jwhi.som.domains.evt.operations.ChangePlayerParameter
import com.jwhi.som.domains.evt.operations.ChangeTarget
import com.jwhi.som.domains.evt.operations.CompareType
import com.jwhi.som.domains.evt.operations.DisplayBMP
import com.jwhi.som.domains.evt.operations.DisplayFormattedMessage
import com.jwhi.som.domains.evt.operations.DisplayMessage
import com.jwhi.som.domains.evt.operations.DisplayMovie
import com.jwhi.som.domains.evt.operations.DisplayObject
import com.jwhi.som.domains.evt.operations.EndGame
import com.jwhi.som.domains.evt.operations.EndIf
import com.jwhi.som.domains.evt.operations.EndScreenEffect
import com.jwhi.som.domains.evt.operations.Ending
import com.jwhi.som.domains.evt.operations.GenerateRandomCounterValue
import com.jwhi.som.domains.evt.operations.IfCounterCondition
import com.jwhi.som.domains.evt.operations.IfMessagePrompt
import com.jwhi.som.domains.evt.operations.ImageDisplayOptions
import com.jwhi.som.domains.evt.operations.LearnMagic
import com.jwhi.som.domains.evt.operations.ObjectAnimation
import com.jwhi.som.domains.evt.operations.OperationEnd
import com.jwhi.som.domains.evt.operations.Otherwise
import com.jwhi.som.domains.evt.operations.PlaySoundEffect
import com.jwhi.som.domains.evt.operations.PlayerParameter
import com.jwhi.som.domains.evt.operations.RecoverAll
import com.jwhi.som.domains.evt.operations.SavePoint
import com.jwhi.som.domains.evt.operations.ScreenEffectType
import com.jwhi.som.domains.evt.operations.SetPlayerParameterInCounter
import com.jwhi.som.domains.evt.operations.SetTimerValueInCounter
import com.jwhi.som.domains.evt.operations.ShopOpen
import com.jwhi.som.domains.evt.operations.StartTimer
import com.jwhi.som.domains.evt.operations.StopPlayBGM
import com.jwhi.som.domains.evt.operations.TerminateEnemy
import com.jwhi.som.domains.evt.operations.TerminateNPC
import com.jwhi.som.domains.evt.operations.UnimplementedOperation
import com.jwhi.som.domains.evt.operations.WarpEnemy
import com.jwhi.som.domains.evt.operations.WarpNPC
import com.jwhi.som.domains.evt.operations.WarpPlayerBasic
import com.jwhi.som.domains.evt.operations.WarpPlayerDetailed
import com.jwhi.som.domains.evt.operations.WarpScreenEffect
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
            ),
            Tuple2(
                byteArrayFrom(0x1Bu, 0x00u, 0x08u, 0x00u, 0x02u, 0x00u, 0x00u, 0x00u),
                TerminateNPC(npcId = 2u, bytes = listOf(27, 0, 8, 0, 2, 0, 0, 0))
            ),
            Tuple2(
                byteArrayFrom(0x1Cu, 0x00u, 0x08u, 0x00u, 0x02u, 0x00u, 0x00u, 0x00u),
                TerminateEnemy(enemyId = 2u, bytes = listOf(28, 0, 8, 0, 2, 0, 0, 0))
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
                byteArrayFrom(0x29, 0x00, 0x04, 0x00),
                EndScreenEffect(bytes = listOf(41, 0, 4, 0))
            ),
            Tuple2(
              byteArrayFrom(0x2Au, 0x00u, 0x24u, 0x00u, 0x01u, 0xFFu, 0x00u, 0x00u, 0x45u, 0x6Cu, 0x66u, 0x20u, 0x61u, 0x6Eu, 0x64u, 0x20u, 0x43u, 0x68u, 0x61u, 0x69u, 0x72u, 0x20u, 0x46u, 0x6Fu, 0x72u, 0x65u, 0x76u, 0x65u, 0x72u, 0x2Eu, 0x62u, 0x6Du, 0x70u, 0x00u, 0x00u, 0x00u),
                DisplayBMP(
                    opSize = 36u,
                    bmpFilename = "Elf and Chair Forever.bmp",
                    durationSeconds = 255u,
                    waitForKeyPress = true,
                    displayOption = ImageDisplayOptions.FULL_SCREEN_KEEP_RATIO,
                    bytes = listOf(42, 0, 36, 0, 1, -1, 0, 0, 69, 108, 102, 32, 97, 110, 100, 32, 67, 104, 97, 105, 114, 32, 70, 111, 114, 101, 118, 101, 114, 46, 98, 109, 112, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x2Bu,  0x00u,  0x20u,  0x00u,  0x77u,  0x61u,  0x6Cu,  0x6Bu,  0x69u,  0x6Eu,  0x67u,  0x2Du,  0x61u,  0x72u,  0x6Fu,  0x75u,  0x6Eu,  0x64u,  0x2Du,  0x61u,  0x67u,  0x61u,  0x69u,  0x6Eu,  0x2Eu,  0x61u,  0x76u,  0x69u,  0x00u,  0x00u,  0x00u,  0x00u) ,
                DisplayMovie(
                    opId = EvtOpIds.DISPLAY_MOVIE.value,
                    opSize = 32u,
                    movieFilename = "walking-around-again.avi",
                    bytes = listOf(43, 0, 32, 0, 119, 97, 108, 107, 105, 110, 103, 45, 97, 114, 111, 117, 110, 100, 45, 97, 103, 97, 105, 110, 46, 97, 118, 105, 0, 0, 0, 0)
                )
            ),

            Tuple2(
                byteArrayFrom(0x2Cu, 0x00u, 0x08u, 0x00u, 0x02u, 0x00u, 0x00u, 0x00u),
                PlaySoundEffect(
                    soundEffectId = 2u,
                    bytes = listOf(44, 0, 8, 0, 2, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x2Du, 0x00u, 0x1Cu, 0x00u, 0x01u, 0x00u, 0x00u, 0x00u, 0x65u, 0x78u, 0x61u, 0x6Du, 0x70u, 0x6Cu, 0x65u, 0x2Du, 0x65u, 0x78u, 0x70u, 0x6Cu, 0x6Fu, 0x72u, 0x65u, 0x2Eu, 0x77u, 0x61u, 0x76u, 0x00u),
                ChangeBGM(
                    opSize = 28u,
                    bgmFilename = "example-explore.wav",
                    loop = true,
                    bytes = listOf(45, 0, 28, 0, 1, 0, 0, 0, 101, 120, 97, 109, 112, 108, 101, 45, 101, 120, 112, 108, 111, 114, 101, 46, 119, 97, 118, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x2Eu, 0x00u, 0x08u, 0x00u, 0x01u, 0x00u, 0x00u, 0x00u),
                StopPlayBGM(
                    play = true,
                    bytes = listOf(46, 0, 8, 0, 1, 0, 0, 0)
                )
            ),
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
                byteArrayFrom(0x3D, 0x00, 0x18, 0x00, 0x1A, 0x63, 0x28, 0x00, 0x00, 0x00, 0x80, 0x3F, 0x00, 0x00, 0xA0, 0xC1, 0x33, 0x33, 0x33, 0xBF, 0x0F, 0x00, 0x00, 0x00),
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
              byteArrayFrom(0x52u, 0x00u, 0x08u, 0x00u, 0x01u, 0x00u, 0x00u, 0x00u),
                LearnMagic(
                    magicTableId = 1u,
                    bytes = listOf(82, 0, 8, 0, 1, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x53u, 0x00u, 0x04u, 0x00u),
                RecoverAll(bytes = listOf(83, 0, 4, 0))
            ),
            Tuple2(
                byteArrayFrom(0x54u, 0x00u, 0x08u, 0x00u, 0x00u, 0x00u, 0x04u, 0x00u),
                SetPlayerParameterInCounter(
                    playerParameter = PlayerParameter.HP,
                    itemId = 0u,
                    targetCounter = 4u,
                    bytes = listOf(84, 0, 8, 0, 0, 0, 4, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x64u, 0x00u, 0x08u, 0x00u, 0x25u, 0x00u, 0x01u, 0x00u),
                ObjectAnimation(
                    objectId = 37u,
                    play = true,
                    bytes = listOf(100, 0, 8, 0, 37, 0, 1, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x65u, 0x00u, 0x08u, 0x00u, 0x26u, 0x00u, 0x01u, 0x00u),
                DisplayObject(
                    objectId = 38u,
                    show = true,
                    bytes = listOf(101, 0, 8, 0, 38, 0, 1, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x78u, 0x00u, 0x08u, 0x00u, 0x01u, 0x01u, 0x00u, 0x00u),
                ChangeDashSaveTemporarily(
                    target = ChangeTarget.SAVE,
                    enabled = true,
                    bytes = listOf(120, 0, 8, 0, 1, 1, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x79, 0x00, 0x04, 0x00),
                SavePoint(bytes = listOf(121, 0, 4, 0))
            ),
            Tuple2(
                byteArrayFrom(0x7Au, 0x00u, 0x08u, 0x00u, 0x02u, 0x00u, 0x00u, 0x00u),
                EndGame(
                    ending = Ending.ENDING_2,
                    bytes = listOf(122, 0, 8, 0, 2, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x8Cu, 0x00u, 0x0Cu, 0x00u, 0x04u, 0x00u, 0x64u, 0x00u, 0x00u, 0x03u, 0x00u, 0x00u),
                IfCounterCondition(
                    counterId = 4u,
                    value = 100u,
                    valueIsCounterId = false,
                    compareType = CompareType.LESS_THAN,
                    bytes = listOf(-116, 0, 12, 0, 4, 0, 100, 0, 0, 3, 0, 0)
                )
            ),
            Tuple2(
              byteArrayFrom(0x8Du, 0x00u, 0x44u, 0x00u, 0x4Fu, 0x68u, 0x20u, 0x79u, 0x6Fu, 0x75u, 0x20u, 0x67u, 0x61u, 0x76u, 0x65u, 0x20u, 0x73u, 0x6Fu, 0x6Du, 0x65u, 0x20u, 0x68u, 0x65u, 0x72u, 0x62u, 0x73u, 0x20u, 0x74u, 0x6Fu, 0x20u, 0x74u, 0x68u, 0x61u, 0x74u, 0x20u, 0x0Du, 0x0Au, 0x64u, 0x77u, 0x61u, 0x72u, 0x66u, 0x2Eu, 0x20u, 0x4Du, 0x61u, 0x79u, 0x20u, 0x49u, 0x20u, 0x68u, 0x61u, 0x76u, 0x65u, 0x20u, 0x6Fu, 0x6Eu, 0x65u, 0x3Fu, 0x00u, 0x59u, 0x65u, 0x73u, 0x00u, 0x4Eu, 0x6Fu, 0x00u, 0x00u),
                IfMessagePrompt(
                    opSize = 68u,
                    text = "Oh you gave some herbs to that \r\n" +
                        "dwarf. May I have one?",
                    option1 = "Yes",
                    option2 = "No",
                    bytes = listOf(-115, 0, 68, 0, 79, 104, 32, 121, 111, 117, 32, 103, 97, 118, 101, 32, 115, 111, 109, 101, 32, 104, 101, 114, 98, 115, 32, 116, 111, 32, 116, 104, 97, 116, 32, 13, 10, 100, 119, 97, 114, 102, 46, 32, 77, 97, 121, 32, 73, 32, 104, 97, 118, 101, 32, 111, 110, 101, 63, 0, 89, 101, 115, 0, 78, 111, 0, 0)
                )
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
              byteArrayFrom(0x90u, 0x00u, 0x0Cu, 0x00u, 0xFCu, 0x03u, 0xFEu, 0x03u, 0x01u, 0x01u, 0x00u, 0x00u),
                ChangeCounter(
                    counterId = 1020u,
                    value = 1022u,
                    valueIsCounterId = true,
                    wayChanged = WayChanged.INCREMENT_BY,
                    bytes = listOf(-112, 0, 12, 0, -4, 3, -2, 3, 1, 1, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x91u, 0x00u, 0x08u, 0x00u, 0xFFu, 0xFFu, 0x01u, 0x00u),
                ChangePage(
                    target = 65535u,
                    changeType = ChangePageType.BACK,
                    changeSpecificId = 0u,
                    bytes = listOf(-111, 0, 8, 0, -1, -1, 1, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x94u, 0x00u, 0x0Cu, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0xFFu, 0xFFu, 0x10u, 0x00u),
                GenerateRandomCounterValue(
                    counterId = 16u,
                    maxValue = 65535u,
                    maxValueIsCounterId = false,
                    bytes = listOf(-108, 0, 12, 0, 0, 0, 0, 0, -1, -1, 16, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x95u, 0x00u, 0x08u, 0x00u, 0x01u, 0x00u, 0x00u, 0x00u),
                StartTimer(
                    timer = 1u,
                    bytes = listOf(-107, 0, 8, 0, 1, 0, 0, 0)
                )
            ),
            Tuple2(
                byteArrayFrom(0x96u, 0x00u, 0x08u, 0x00u, 0x02u, 0x00u, 0x0Bu, 0x00u),
                SetTimerValueInCounter(
                    timer = 2u,
                    counterId = 11u,
                    bytes = listOf(-106, 0, 8, 0, 2, 0, 11, 0)
                )
            ),
            Tuple2(
                byteArrayFrom( 0xFF, 0xFF, 0x04, 0x00),
                OperationEnd(bytes = byteListFrom(0xFF, 0xFF, 0x04, 0x00))
            ),
            Tuple2(
                byteArrayFrom(0xFA, 0x00, 0x04, 0x00),
                UnimplementedOperation(
                    opId = 250u,
                    opSize = 4u,
                    bytes = listOf(-6, 0, 4, 0)
                )
            ),
        ) { (bytes, expected) ->
            val actual = bytes.asBufferLittleEndian().parseEvtOperation(0)

            actual shouldBe expected
        }
    }
})
