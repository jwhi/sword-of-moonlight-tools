import com.jwhi.som.domains.evt.CompareType
import com.jwhi.som.domains.evt.EvtOpDisplayMessage
import com.jwhi.som.domains.evt.EvtOpIfMessage
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

        test("Validate event length") {
            evtEvents.size shouldBe 251
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
    }
})
