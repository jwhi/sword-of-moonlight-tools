import com.jwhi.som.domains.evt.CompareType
import com.jwhi.som.domains.evt.EventIds
import com.jwhi.som.domains.evt.EvtOpDisplayMessage
import com.jwhi.som.domains.evt.EvtOpIfMessage
import com.jwhi.som.domains.evt.EvtOpUnimplemented
import com.jwhi.som.domains.evt.TargetType
import com.jwhi.som.domains.evt.TriggerType
import com.jwhi.som.domains.reader.ExampleReader
import com.jwhi.som.domains.reader.toMapEvent
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

class AppTest : FunSpec({
    context("Read EVT file - 00") {
        val fileReader = ExampleReader()
        val evt00Bytes = fileReader.binaryFileReader("/ExampleProject/DATA/MAP/00.evt")
        val evtEvents = evt00Bytes.toMapEvent()

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
                "IF MESSAGE"
            )
        }

        test("Validate display operation") {
            val basicIfDisplayMessage = evtEvents[20]

            withClue("Event Definition validation") {
                assertSoftly(basicIfDisplayMessage) {
                    it.definition.name shouldBe "Display Message"
                    it.definition.targetType shouldBe TargetType.OBJECT
                    it.definition.targetId shouldBe 3
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

                    it.pageOperations.size shouldBe 1
                    it.pageOperations[pagePayloadOffset].shouldBeTypeOf<EvtOpDisplayMessage>()
                    val ifDisplayMessage = it.pageOperations[pagePayloadOffset] as EvtOpDisplayMessage
                    ifDisplayMessage.text shouldBe "Test message"
                }
            }
        }

        test("Validate if display operation") {
            val basicIfDisplayMessage = evtEvents[41]

            withClue("Event Definition validation") {
                assertSoftly(basicIfDisplayMessage) {
                    it.definition.name shouldBe "IF MESSAGE"
                    it.definition.targetType shouldBe TargetType.OBJECT
                    it.definition.targetId shouldBe 17
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

                    it.pageOperations.size shouldBe 1
                    it.pageOperations[pagePayloadOffset].shouldBeTypeOf<EvtOpIfMessage>()
                    val ifDisplayMessage = it.pageOperations[pagePayloadOffset] as EvtOpIfMessage
                    ifDisplayMessage.text shouldBe "IF message. First choise is True. \r\nSecond is False."
                    ifDisplayMessage.option1 shouldBe "True"
                    ifDisplayMessage.option2 shouldBe "False"
                }
            }
        }

        test("Heal player with checking counter") {
            val healEvent = evtEvents[16]

            withClue("Event Definition validation") {
                assertSoftly(healEvent) {
                    it.definition.name shouldBe "Heal Bump"
                    it.definition.targetType shouldBe TargetType.NPC
                    it.definition.targetId shouldBe 3 // Elf (Forest Guardian)
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

                    it.pageOperations.size shouldBe 1
                    it.pageOperations[pagePayloadOffset].shouldBeTypeOf<EvtOpUnimplemented>()
                    val unimplementedMessage = it.pageOperations[pagePayloadOffset] as EvtOpUnimplemented
                    unimplementedMessage.opId shouldBe 84u
                    unimplementedMessage.opSize shouldBe 8u
                }
            }
        }

        test("Event with multiple pages") {
            val healEvent = evtEvents[10]

            withClue("Event Definition validation") {
                assertSoftly(healEvent) {
                    it.definition.name shouldBe "Man Response To Feeding Dwarf"
                    it.definition.targetType shouldBe TargetType.NPC
                    it.definition.targetId shouldBe 1 // Man
                    it.definition.triggerType shouldBe TriggerType.EXAMINE
                    it.definition.triggerItem shouldBe 0xFF.toUByte()
                    it.definition.triggerCone shouldBe 360u
                    it.definition.triggerRadius shouldBe 0.0f
                    it.definition.condition.compareType shouldBe CompareType.COUNTER
                    it.definition.condition.compareId shouldBe 876u // Fed Dwarf Counter
                    it.definition.pages.size shouldBe 1
                }
            }

            withClue("PageOperation validation") {
                assertSoftly(healEvent) {
                    val pagePayloadOffset = it.definition.pages[0].payloadOffset
                    pagePayloadOffset shouldBe 258360u

                    it.pageOperations.size shouldBe 1
                    it.pageOperations[pagePayloadOffset].shouldBeTypeOf<EvtOpIfMessage>()
                    val ifMessage = it.pageOperations[pagePayloadOffset] as EvtOpIfMessage
                    ifMessage.opId shouldBe EventIds.IF_MESSAGE.value
                    ifMessage.opSize shouldBe 68u
                    ifMessage.text shouldBe "Oh you gave some herbs to that \r\ndwarf. May I have one?"
                    ifMessage.option1 shouldBe "Yes"
                    ifMessage.option2 shouldBe "No"
                }
            }
        }
    }
})
