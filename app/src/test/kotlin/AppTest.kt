import com.jwhi.som.domains.evt.CompareType
import com.jwhi.som.domains.evt.ComparisonType
import com.jwhi.som.domains.evt.EvtCondition
import com.jwhi.som.domains.evt.EvtOpIds
import com.jwhi.som.domains.evt.TargetType
import com.jwhi.som.domains.evt.TriggerType
import com.jwhi.som.domains.evt.operations.DisplayMessage
import com.jwhi.som.domains.evt.operations.IfMessagePrompt
import com.jwhi.som.domains.evt.operations.PlayerParameter
import com.jwhi.som.domains.evt.operations.SetPlayerParameterInCounter
import com.jwhi.som.domains.evt.operations.UnimplementedOperation
import com.jwhi.som.domains.reader.BinaryBufferReader
import com.jwhi.som.domains.reader.BinaryReader
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
        val filteredEvents = evtEvents.filter {
            it.definition.targetType != TargetType.NONE
        }
        val evt = BinaryReader.getEvt("/ExampleProject/DATA/MAP/00.evt")
        val evtFiltered = evt.definitions().filter {
            (it?.targetType() ?: TargetType.NONE) != TargetType.NONE &&
                it?.name()?.isNotEmpty() ?: false
        }
        val sysDat = BinaryReader.getSys("/ExampleProject/PARAM/SYS.DAT")

        test("Event length") {
            evtEvents.size shouldBe 251
            evt.definitions() shouldHaveSize 1024
        }

        test("Defined events") {
            val definedEvents = filteredEvents.map {
                it.definition.name
            }
            val definedEvtEvents = evtFiltered.mapNotNull {
                it?.name()
            }
            val expected = listOf(
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
                "Save Point",
                "Set Gold to # both herbs held",
                "RED BOTTLE USE",
                "Warp Player Extreme",
                "WARP NPC EXTREME",
                "Warp Enemy Extreme",
                "Timers",
                "Chair",
                "Play Movie",
                "Sign",
                "Sign BMP",
                "Warp",
                "Events",
                "Stool Event",
                "Play Enemy Music",
                "Bridge BGM Stop",
                "Bridge Music Change",
                "Grass Music Begin"
            )
            definedEvtEvents shouldBe definedEvents

            definedEvents.filter { it !in expected } shouldBe emptyList()
            definedEvents shouldBe expected
        }

        test("Verify counter names") {
            val counters = sysDat.counterNames().toList().mapIndexed {
                index, counterName -> index to counterName
            }.filter {
                it.second?.isNotEmpty() ?: false
            }.toMap()

            counters shouldBe mapOf(
                4 to "Player Health",
                6 to "Player Strength",
                7 to "Player Magic",
                9 to "Timer 0",
                10 to "Timer 1",
                11 to "Timer 2",
                12 to "Timer 3",
                15 to "Random Counter 1",
                16 to "Random 2",
                240 to "RED BOTTLE COUNT",
                249 to "EMPTY BOTTLE COUNT",
                777 to "Man's shop open",
                876 to "Fed Dwarf Herbs",
                1020 to "Herbs",
                1022 to "Antidote Herbs"
            )
        }

        test("Get all operations and validate they are defined") {
            val operations = filteredEvents.flatMap { it.pageOperations.values }.flatten()
            val unimplementedOperations = operations.filterIsInstance<UnimplementedOperation>()
            val unimplementedOpIds = unimplementedOperations.map { it.opId.toUInt() to it.opSize.toUInt() }.toSet().toMap()
            val eventsWithUnimplementedOps = filteredEvents.map {
                it.definition.name to it.pageOperations.values.flatten().filterIsInstance<UnimplementedOperation>().map { it.opId.toUInt() }
            }.filter {
                it.second.isNotEmpty()
            }.toMap()

            val evtPages = evtFiltered.mapNotNull { it?.page() }.flatten().filter { it?.body() != null }
            val evtOperations = evtPages.mapNotNull {
                it?.body()?.filter {
                    op -> op?.opId()?.id() in EvtOpIds.entries.map { opId -> opId.value.toLong() }
                }
            }.flatten()

            assertSoftly {
                operations shouldHaveSize 242
                unimplementedOperations shouldHaveSize 0
                unimplementedOpIds shouldBe mapOf()

                evtOperations shouldHaveSize 242

                eventsWithUnimplementedOps shouldBe mapOf()
            }
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
                    it.pageOperations[pagePayloadOffset]!!.first().shouldBeTypeOf<DisplayMessage>()
                    val ifDisplayMessage = it.pageOperations[pagePayloadOffset]!!.first() as DisplayMessage
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
                    pagePayloadOffset shouldBe 259904u

                    it.definition.pages[0].startCondition shouldBe EvtCondition(
                        compareType = CompareType.NONE,
                        compareId = 0u,
                        comparisonType = ComparisonType.EQUALS,
                        comparedValue = 0u
                    )

                    it.pageOperations.size shouldBe 1
                    it.pageOperations[pagePayloadOffset]!!.first().shouldBeTypeOf<IfMessagePrompt>()
                    val ifDisplayMessage = it.pageOperations[pagePayloadOffset]!!.first() as IfMessagePrompt
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
                    it.pageOperations[pagePayloadOffset]!!.first().shouldBeTypeOf<SetPlayerParameterInCounter>()
                    val setPlayerParameterInCounter = it.pageOperations[pagePayloadOffset]!!.first() as SetPlayerParameterInCounter
                    setPlayerParameterInCounter.opId shouldBe 84u
                    setPlayerParameterInCounter.opSize shouldBe 8u
                    setPlayerParameterInCounter.playerParameter shouldBe PlayerParameter.HP
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
                    operationList.first().shouldBeTypeOf<IfMessagePrompt>()
                    val ifMessage = operationList.first() as IfMessagePrompt
                    ifMessage.opId shouldBe EvtOpIds.IF_MESSAGE_PROMPT.value
                    ifMessage.opSize shouldBe 68u
                    ifMessage.text shouldBe "Oh you gave some herbs to that \r\ndwarf. May I have one?"
                    ifMessage.option1 shouldBe "Yes"
                    ifMessage.option2 shouldBe "No"

                    val conditionMetMessage = operationList[1] as DisplayMessage
                    conditionMetMessage.opId shouldBe EvtOpIds.DISPLAY_MESSAGE.value
                    conditionMetMessage.opSize shouldBe 120u
                    conditionMetMessage.text shouldBe "Thanks! I like selling these. Here \r\n" +
                        "is some gold. See me again if you \r\n" +
                        "want to buy some herbs in the \r\n" +
                        "future."


                    it.pageOperations[pagePayloadOffset2]!!.first().shouldBeTypeOf<DisplayMessage>()
                    val secondOperation = it.pageOperations[pagePayloadOffset2]!!.first() as DisplayMessage
                    secondOperation.opId shouldBe EvtOpIds.DISPLAY_MESSAGE.value
                    secondOperation.opSize shouldBe 48u
                    secondOperation.text shouldBe "Let me know if you see anything \r\nyou like."
                }
            }
        }
    }
})
