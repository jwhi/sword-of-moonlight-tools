import com.jwhi.som.domains.evt.CompareType
import com.jwhi.som.domains.evt.ComparisonType
import com.jwhi.som.domains.evt.EvtCondition
import com.jwhi.som.domains.evt.EvtOpDisplayMessage
import com.jwhi.som.domains.evt.EvtOpIfMessage
import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.evt.EvtOpPlayerParameters
import com.jwhi.som.domains.evt.EvtOpSetPlayerParameterInCounter
import com.jwhi.som.domains.evt.TargetType
import com.jwhi.som.domains.evt.TriggerType
import com.jwhi.som.domains.reader.BinaryBufferReader
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

class AppTest : FunSpec({
    context("Read EVT file - 00") {
        val inputFile = "/ExampleProject/DATA/MAP/00.evt"
        val bufferReader = BinaryBufferReader(inputFile)
        val evtEvents = bufferReader.readMapEvent()

        test("Event length") {
            evtEvents.size shouldBe 251
        }

        test("Defined events") {
            val definedEvents = evtEvents.filter {
                it.definition.targetType != TargetType.NONE
            }.map {
                it.definition.name
            }

            definedEvents shouldBe listOf(
                "OPEN MAP",
                "CLOSE MAP",
                "PLAYER DEATH",
                "APPOINT",
                "APPOINT",
                "APPOINT",
                "APPOINT",
                "APPOINT",
                "APPOINT",
                "APPOINT",
                "First Event",
                "Man Response To Feeding Dwarf",
                "Greeting before feeding dwarf",
                "NEW EVENT",
                "Talking",
                "Rect Test 1",
                "Circ Event 1",
                "Heal Bump",
                "Hurt Player",
                "Chair Always On Return WhenLow",
                "Timer Enable Bandit",
                "Display Message",
                "Display Message Formatted",
                "Activate NPC (Bandit)",
                "Activate Enemy (0002 Ooze)",
                "Activate Item (0004 FirDagger)",
                "Shop (5 Man's Shop)",
                "WarpNPC2(x6z69x.1y.2z.5d120)",
                "WarpEne2(x7z69x-.1y-.2z-.5d270",
                "Terminate NPC (2 Bandit)",
                "Terminate Enemy (2 Ooze)",
                "Screen Effect Black Fades Off",
                "Screen Effect Black Fades On",
                "Screen Effect White Fades Off",
                "Screen Effect White Fades On",
                "Screen Effect Red",
                "Screen Effect Red Loop",
                "Screen Effect Green",
                "Screen Effect Blue",
                "DISPLAY BMP",
                "IF PlayerHealthCounter > 25",
                "IF Timer1 > 0 Otherwise",
                "IF MESSAGE",
                "Player Strength + Magic Check",
                "Page change tests",
                "Save Point"
            )
        }

        test("Validate display operation") {
            val basicIfDisplayMessage = evtEvents[21]

            withClue("Event Definition validation") {
                assertSoftly(basicIfDisplayMessage) {
                    it.definition.name shouldBe "Display Message"
                    it.definition.targetType shouldBe TargetType.OBJECT
                    it.definition.targetId shouldBe 3u
                    it.definition.triggerType shouldBe TriggerType.EXAMINE
                    it.definition.triggerItem shouldBe 0xFF.toUByte()
                    it.definition.triggerCone shouldBe 360u
                    it.definition.condition.compareType shouldBe CompareType.NONE
                    it.definition.pages.size shouldBe 1
                }
            }

            withClue("PageOperation validation") {
                assertSoftly(basicIfDisplayMessage) {
                    val pagePayloadOffset = it.definition.pages[0].payloadOffset
                    pagePayloadOffset shouldBe 259280u

                    it.definition.pages[0].startCondition shouldBe EvtCondition(
                        compareType = CompareType.NONE,
                        compareId = 0u,
                        comparisonType = ComparisonType.EQUALS,
                        comparedValue = 0u
                    )

                    it.pageOperations.size shouldBe 1
                    it.pageOperations[pagePayloadOffset]!!.first().shouldBeTypeOf<EvtOpDisplayMessage>()
                    val ifDisplayMessage = it.pageOperations[pagePayloadOffset]!!.first() as EvtOpDisplayMessage
                    ifDisplayMessage.text shouldBe "Test message"
                }
            }
        }

        test("Validate if display operation") {
            val basicIfDisplayMessage = evtEvents[42]

            withClue("Event Definition validation") {
                assertSoftly(basicIfDisplayMessage) {
                    it.definition.name shouldBe "IF MESSAGE"
                    it.definition.targetType shouldBe TargetType.OBJECT
                    it.definition.targetId shouldBe 17u
                    it.definition.triggerType shouldBe TriggerType.EXAMINE
                    it.definition.triggerItem shouldBe 0xFF.toUByte()
                    it.definition.triggerCone shouldBe 360u
                    it.definition.condition.compareType shouldBe CompareType.NONE
                    it.definition.pages.size shouldBe 1
                }
            }

            withClue("PageOperation validation") {
                assertSoftly(basicIfDisplayMessage) {
                    val pagePayloadOffset = it.definition.pages[0].payloadOffset
                    pagePayloadOffset shouldBe 259828u

                    it.definition.pages[0].startCondition shouldBe EvtCondition(
                        compareType = CompareType.NONE,
                        compareId = 0u,
                        comparisonType = ComparisonType.EQUALS,
                        comparedValue = 0u
                    )

                    it.pageOperations.size shouldBe 1
                    it.pageOperations[pagePayloadOffset]!!.first().shouldBeTypeOf<EvtOpIfMessage>()
                    val ifDisplayMessage = it.pageOperations[pagePayloadOffset]!!.first() as EvtOpIfMessage
                    ifDisplayMessage.text shouldBe "IF message. First choice is True. \r\nSecond is False."
                    ifDisplayMessage.option1 shouldBe "True"
                    ifDisplayMessage.option2 shouldBe "False"
                }
            }
        }

        test("Heal player with checking counter") {
            val healEvent = evtEvents[17]

            withClue("Event Definition validation") {
                assertSoftly(healEvent) {
                    it.definition.name shouldBe "Heal Bump"
                    it.definition.targetType shouldBe TargetType.NPC
                    it.definition.targetId shouldBe 3u // Elf (Forest Guardian)
                    it.definition.triggerType shouldBe TriggerType.APPROACH_CIRCLE
                    it.definition.triggerItem shouldBe 0xFF.toUByte()
                    it.definition.triggerCone shouldBe 360u
                    it.definition.triggerRadius shouldBe 0.2f
                    it.definition.condition.compareType shouldBe CompareType.NONE
                    it.definition.pages.size shouldBe 1
                }
            }

            withClue("PageOperation validation") {
                assertSoftly(healEvent) {
                    val pagePayloadOffset = it.definition.pages[0].payloadOffset
                    pagePayloadOffset shouldBe 258872u

                    it.definition.pages[0].startCondition shouldBe EvtCondition(
                        compareType = CompareType.NONE,
                        compareId = 0u,
                        comparisonType = ComparisonType.EQUALS,
                        comparedValue = 0u
                    )

                    it.pageOperations.size shouldBe 1
                    it.pageOperations[pagePayloadOffset]!!.first().shouldBeTypeOf<EvtOpSetPlayerParameterInCounter>()
                    val setPlayerParameterInCounter = it.pageOperations[pagePayloadOffset]!!.first() as EvtOpSetPlayerParameterInCounter
                    setPlayerParameterInCounter.opId shouldBe 84u
                    setPlayerParameterInCounter.opSize shouldBe 8u
                    setPlayerParameterInCounter.playerParameter shouldBe EvtOpPlayerParameters.HP
                    setPlayerParameterInCounter.targetCounter shouldBe 4u

                }
            }
        }

        test("Event with multiple pages") {
            val healEvent = evtEvents[11]

            withClue("Event Definition validation") {
                assertSoftly(healEvent) {
                    it.definition.name shouldBe "Man Response To Feeding Dwarf"
                    it.definition.targetType shouldBe TargetType.NPC
                    it.definition.targetId shouldBe 1u // Man
                    it.definition.triggerType shouldBe TriggerType.EXAMINE
                    it.definition.triggerItem shouldBe 0xFF.toUByte()
                    it.definition.triggerCone shouldBe 360u
                    it.definition.triggerRadius shouldBe 0.0f
                    it.definition.condition.compareType shouldBe CompareType.COUNTER
                    it.definition.condition.compareId shouldBe 876u // Fed Dwarf Counter
                    it.definition.pages.size shouldBe 2
                }
                assertSoftly(healEvent.definition.pages) {
                    it.size shouldBe 2
                    it[0].startCondition shouldBe EvtCondition(
                        compareType = CompareType.COUNTER,
                        compareId = 777u,
                        comparisonType = ComparisonType.NOT_EQUALS,
                        comparedValue = 1u
                    )
                    it[1].startCondition shouldBe EvtCondition(
                        compareType = CompareType.COUNTER,
                        compareId = 777u,
                        comparisonType = ComparisonType.EQUALS,
                        comparedValue = 1u
                    )
                }
            }

            withClue("PageOperation validation") {
                assertSoftly(healEvent) {
                    val pagePayloadOffset1 = it.definition.pages[0].payloadOffset
                    pagePayloadOffset1 shouldBe 258360u

                    val pagePayloadOffset2 = it.definition.pages[1].payloadOffset
                    pagePayloadOffset2 shouldBe 258664u

                    it.pageOperations.size shouldBe 2
                    val operationList = it.pageOperations[pagePayloadOffset1] ?: emptyList()
                    operationList shouldHaveSize 10
                    operationList.first().shouldBeTypeOf<EvtOpIfMessage>()
                    val ifMessage = operationList.first() as EvtOpIfMessage
                    ifMessage.opId shouldBe EvtOpIds.IF_MESSAGE.value
                    ifMessage.opSize shouldBe 68u
                    ifMessage.text shouldBe "Oh you gave some herbs to that \r\ndwarf. May I have one?"
                    ifMessage.option1 shouldBe "Yes"
                    ifMessage.option2 shouldBe "No"

                    val conditionMetMessage = operationList[1] as EvtOpDisplayMessage
                    conditionMetMessage.opId shouldBe EvtOpIds.MESSAGE.value
                    conditionMetMessage.opSize shouldBe 120u
                    conditionMetMessage.text shouldBe "Thanks! I like selling these. Here \r\n" +
                        "is some gold. See me again if you \r\n" +
                        "want to buy some herbs in the \r\n" +
                        "future."


                    it.pageOperations[pagePayloadOffset2]!!.first().shouldBeTypeOf<EvtOpDisplayMessage>()
                    val secondOperation = it.pageOperations[pagePayloadOffset2]!!.first() as EvtOpDisplayMessage
                    secondOperation.opId shouldBe EvtOpIds.MESSAGE.value
                    secondOperation.opSize shouldBe 48u
                    secondOperation.text shouldBe "Let me know if you see anything \r\nyou like."
                }
            }
        }
    }
})
