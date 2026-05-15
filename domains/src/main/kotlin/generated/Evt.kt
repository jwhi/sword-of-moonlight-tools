package com.jwhi.som.domains.generated

import io.kaitai.struct.ByteBufferKaitaiStream
import io.kaitai.struct.KaitaiStream
import io.kaitai.struct.KaitaiStream.ValidationNotEqualError
import io.kaitai.struct.KaitaiStruct
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Sword of Moonlight EVT file
 * @see [Source](https://doc.swordofmoonlight.com/editor/ff/map-evt-file-format/)
 */
class Evt @JvmOverloads constructor(_io: KaitaiStream?, private val _parent: KaitaiStruct? = null, _root: Evt? = null) :
    KaitaiStruct(_io) {
    enum class BmpDisplayOptions(private val id: Long) {
        KEEP_ORIGINAL_RATIO(0),
        FULL_SCREEN_KEEP_RATIO(1),
        FULL_SCREEN_STRETCH(2);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, BmpDisplayOptions?> = HashMap<Long?, BmpDisplayOptions?>(3)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): BmpDisplayOptions? {
                return byId.get(id)
            }
        }
    }

    enum class ChangePageType(private val id: Long) {
        FORWARD(0),
        BACK(1),
        SPECIFIC(2);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, ChangePageType?> = HashMap<Long?, ChangePageType?>(3)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): ChangePageType? {
                return byId.get(id)
            }
        }
    }

    enum class CompareType(private val id: Long) {
        NONE(0),
        ITEM_QUANTITY(1),
        GOLD_QUANTITY(2),
        STRENGTH(3),
        MAGIC(4),
        LEVEL(5),
        COUNTER(6);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, CompareType?> = HashMap<Long?, CompareType?>(7)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): CompareType? {
                return byId.get(id)
            }
        }
    }

    enum class ComparisonType(private val id: Long) {
        EQUALS(0),
        NOT_EQUALS(1),
        GREATER_THAN(2),
        LESS_THAN(3);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, ComparisonType?> = HashMap<Long?, ComparisonType?>(4)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): ComparisonType? {
                return byId.get(id)
            }
        }
    }

    enum class Ending(private val id: Long) {
        NORMAL_END(0),
        ENDING_1(1),
        ENDING_2(2),
        ENDING_3(3);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, Ending?> = HashMap<Long?, Ending?>(4)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): Ending? {
                return byId.get(id)
            }
        }
    }

    enum class OperationType(private val id: Long) {
        DISPLAY_MESSAGE(0),
        DISPLAY_FORMATTED_MESSAGE(1),
        ACTIVATE_NPC(20),
        ACTIVATE_ENEMY(21),
        ACTIVATE_ITEM(22),
        SHOP_OPEN(23),
        WARP_NPC(25),
        WARP_ENEMY(26),
        TERMINATE_NPC(27),
        TERMINATE_ENEMY(28),
        SCREEN_EFFECT_START(40),
        SCREEN_EFFECT_END(41),
        DISPLAY_BMP(42),
        DISPLAY_MOVIE(43),
        PLAY_SOUND_EFFECT(44),
        CHANGE_BGM(45),
        BGM_PLAYBACK(46),
        WARP_PLAYER_DETAILED(60),
        WARP_PLAYER_BASIC(61),
        CHANGE_PLAYER_PARAMETER(80),
        CHANGE_PLAYER_STATUS(81),
        LEARN_MAGIC(82),
        RECOVER_ALL(83),
        SET_PLAYER_PARAMETER_IN_COUNTER(84),
        OBJECT_ANIMATION(100),
        DISPLAY_OBJECT(101),
        MOVE_OBJECT(102),
        CHANGE_DASH_OR_SAVE_TEMPORARILY(120),
        SAVE_POINT(121),
        END_GAME(122),
        IF_COUNTER(140),
        IF_PROMPT(141),
        OTHERWISE(142),
        END_IF(143),
        CHANGE_COUNTER(144),
        CHANGE_PAGE(145),
        GENERATE_RANDOM_COUNTER_VALUE(148),
        START_TIMER(149),
        SET_TIMER_VALUE_IN_COUNTER(150),
        END(65535);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, OperationType?> = HashMap<Long?, OperationType?>(40)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): OperationType? {
                return byId.get(id)
            }
        }
    }

    enum class Playback(private val id: Long) {
        STOP(0),
        PLAY(1);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, Playback?> = HashMap<Long?, Playback?>(2)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): Playback? {
                return byId.get(id)
            }
        }
    }

    enum class PlayerParameter(private val id: Long) {
        HP(0),
        MP(1),
        STRENGTH(2),
        MAGIC(3),
        ITEM_QUANTITY(4),
        GOLD(5),
        LEVEL(6);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, PlayerParameter?> = HashMap<Long?, PlayerParameter?>(7)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): PlayerParameter? {
                return byId.get(id)
            }
        }
    }

    enum class PlayerStatus(private val id: Long) {
        POISON(0),
        PARALYZE(1),
        DARK(2),
        CURSE(3),
        SLOW(4);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, PlayerStatus?> = HashMap<Long?, PlayerStatus?>(5)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): PlayerStatus? {
                return byId.get(id)
            }
        }
    }

    enum class ScreenEffect(private val id: Long) {
        BLACK_FADES_OFF(0),
        BLACK_FADES_ON(1),
        WHITE_FADES_OFF(2),
        WHITE_FADES_ON(3),
        RED(4),
        GREEN(5),
        BLUE(6),
        NONE(255);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, ScreenEffect?> = HashMap<Long?, ScreenEffect?>(8)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): ScreenEffect? {
                return byId.get(id)
            }
        }
    }

    enum class TargetType(private val id: Long) {
        NPC(0),
        ENEMY(1),
        OBJECT(2),
        SYSTEM(254),
        NONE(255);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, TargetType?> = HashMap<Long?, TargetType?>(5)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): TargetType? {
                return byId.get(id)
            }
        }
    }

    enum class TemporarilyChangeTarget(private val id: Long) {
        DASH(0),
        SAVE(1);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, TemporarilyChangeTarget?> = HashMap<Long?, TemporarilyChangeTarget?>(2)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): TemporarilyChangeTarget? {
                return byId.get(id)
            }
        }
    }

    enum class TriggerType(private val id: Long) {
        NONE(0),
        EXAMINE(1),
        USE_ITEM(2),
        APPROACH_SQUARE(4),
        APPROACH_CIRCLE(8),
        DEATH_ENEMY_OR_NPC(16),
        ALWAYS_ON(32),
        USE_ITEM_ANYWHERE(64),
        PLAYER_DEATH(255);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, TriggerType?> = HashMap<Long?, TriggerType?>(9)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): TriggerType? {
                return byId.get(id)
            }
        }
    }

    enum class WayChanged(private val id: Long) {
        SET_TO(0),
        INCREMENT_BY(1),
        DECREMENT_BY(2),
        COUNTER(3);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, WayChanged?> = HashMap<Long?, WayChanged?>(4)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): WayChanged? {
                return byId.get(id)
            }
        }
    }

    private fun _read() {
        this.magic = this._io.readBytes(4)
        if (!(this.magic.contentEquals(byteArrayOf(0, 4, 0, 0)))) {
            throw ValidationNotEqualError(byteArrayOf(0, 4, 0, 0), this.magic, this._io, "/seq/0")
        }
        this.definitions = ArrayList<EvtDefinition?>()
        for (i in 0..1023) {
            this.definitions!!.add(EvtDefinition(this._io, this, _root))
        }
    }

    fun _fetchInstances() {
        for (i in this.definitions!!.indices) {
            this.definitions!!.get(((i) as Number).toInt())!!._fetchInstances()
        }
    }

    class ActivateEnemy @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.enemyId = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var enemyId = 0

        init {
            _read()
        }

        fun enemyId(): Int {
            return enemyId
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ActivateEnemy {
                return ActivateEnemy(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class ActivateItem @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.itemId = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var itemId = 0

        init {
            _read()
        }

        fun itemId(): Int {
            return itemId
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ActivateItem {
                return ActivateItem(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class ActivateNpc @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.npcId = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var npcId = 0

        init {
            _read()
        }

        fun npcId(): Int {
            return npcId
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ActivateNpc {
                return ActivateNpc(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class BgmPlayback @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.playback = Playback.byId(this._io.readU1().toLong())
        }

        fun _fetchInstances() {
        }

        private var playback: Playback? = null

        init {
            _read()
        }

        fun playback(): Playback? {
            return playback
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): BgmPlayback {
                return BgmPlayback(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class ChangeBgm @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.loopFlag = this._io.readU2le()
            this.padding = this._io.readU2le()
            this.filename = String(this._io.readBytesTerm(0.toByte(), false, true, true), StandardCharsets.UTF_8)
        }

        fun _fetchInstances() {
        }

        private var loop: Boolean? = null
        fun loop(): Boolean {
            if (this.loop != null) return this.loop!!
            this.loop = loopFlag() != 0
            return this.loop!!
        }

        private var loopFlag = 0
        private var padding = 0
        private var filename: String? = null

        init {
            _read()
        }

        fun loopFlag(): Int {
            return loopFlag
        }

        fun padding(): Int {
            return padding
        }

        fun filename(): String? {
            return filename
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ChangeBgm {
                return ChangeBgm(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class ChangeCounter @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.id = this._io.readU2le()
            this.value = this._io.readU2le()
            this.valueIsCounterIdFlag = this._io.readU1()
            this.wayChanged = WayChanged.byId(this._io.readU1().toLong())
        }

        fun _fetchInstances() {
        }

        private var valueIsCounterId: Boolean? = null
        fun valueIsCounterId(): Boolean {
            if (this.valueIsCounterId != null) return this.valueIsCounterId!!
            this.valueIsCounterId = valueIsCounterIdFlag() != 0
            return this.valueIsCounterId!!
        }

        private var id = 0
        private var value = 0
        private var valueIsCounterIdFlag = 0
        private var wayChanged: WayChanged? = null

        init {
            _read()
        }

        fun id(): Int {
            return id
        }

        fun value(): Int {
            return value
        }

        fun valueIsCounterIdFlag(): Int {
            return valueIsCounterIdFlag
        }

        fun wayChanged(): WayChanged? {
            return wayChanged
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ChangeCounter {
                return ChangeCounter(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class ChangeDashOrSaveTemporarily @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.target = TemporarilyChangeTarget.byId(this._io.readU1().toLong())
            this.enabled = this._io.readBitsIntLe(1) != 0L
        }

        fun _fetchInstances() {
        }

        private var target: TemporarilyChangeTarget? = null
        private var enabled = false

        init {
            _read()
        }

        fun target(): TemporarilyChangeTarget? {
            return target
        }

        fun enabled(): Boolean {
            return enabled
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ChangeDashOrSaveTemporarily {
                return ChangeDashOrSaveTemporarily(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class ChangePage @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.target = this._io.readU2le()
            this.changePageType = ChangePageType.byId(this._io.readU1().toLong())
            this.value = this._io.readU1()
        }

        fun _fetchInstances() {
        }

        private var changeToPage: Int? = null
        fun changeToPage(): Int? {
            if (this.changeToPage != null) return this.changeToPage
            if (changePageType() == ChangePageType.SPECIFIC) {
                this.changeToPage = ((value()) as Number).toInt()
            }
            return this.changeToPage
        }

        private var target = 0
        private var changePageType: ChangePageType? = null
        private var value = 0

        init {
            _read()
        }

        fun target(): Int {
            return target
        }

        fun changePageType(): ChangePageType? {
            return changePageType
        }

        fun value(): Int {
            return value
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ChangePage {
                return ChangePage(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class ChangePlayerParameter @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.parameter = PlayerParameter.byId(this._io.readU1().toLong())
            this.wayChanged = WayChanged.byId(this._io.readU1().toLong())
            this.itemId = this._io.readU2le()
            this.padding = this._io.readU2le()
            this.value = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var parameter: PlayerParameter? = null
        private var wayChanged: WayChanged? = null
        private var itemId = 0
        private var padding = 0
        private var value = 0

        init {
            _read()
        }

        /**
         * Player parameter to modify.
         * Not allowed to update level through Change Player Parameter operation.
         * Reused player parameter enum from setting parameter into counter.
         */
        fun parameter(): PlayerParameter? {
            return parameter
        }

        fun wayChanged(): WayChanged? {
            return wayChanged
        }

        fun itemId(): Int {
            return itemId
        }

        fun padding(): Int {
            return padding
        }

        fun value(): Int {
            return value
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ChangePlayerParameter {
                return ChangePlayerParameter(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class ChangePlayerStatus @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.status = PlayerStatus.byId(this._io.readU1().toLong())
            this.enabled = this._io.readBitsIntLe(1) != 0L
        }

        fun _fetchInstances() {
        }

        private var status: PlayerStatus? = null
        private var enabled = false

        init {
            _read()
        }

        fun status(): PlayerStatus? {
            return status
        }

        fun enabled(): Boolean {
            return enabled
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ChangePlayerStatus {
                return ChangePlayerStatus(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class DisplayBmp @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.displayOption = BmpDisplayOptions.byId(this._io.readU1().toLong())
            this.duration = this._io.readU1()
            this.padding = this._io.readU2le()
            this.filename = String(this._io.readBytesTerm(0.toByte(), false, true, true), StandardCharsets.UTF_8)
        }

        fun _fetchInstances() {
        }

        private var waitForKeyPress: Boolean? = null
        fun waitForKeyPress(): Boolean {
            if (this.waitForKeyPress != null) return this.waitForKeyPress!!
            this.waitForKeyPress = duration() == 255
            return this.waitForKeyPress!!
        }

        private var displayOption: BmpDisplayOptions? = null
        private var duration = 0
        private var padding = 0
        private var filename: String? = null

        init {
            _read()
        }

        fun displayOption(): BmpDisplayOptions? {
            return displayOption
        }

        fun duration(): Int {
            return duration
        }

        fun padding(): Int {
            return padding
        }

        fun filename(): String? {
            return filename
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): DisplayBmp {
                return DisplayBmp(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class DisplayFormattedMessage @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.red = this._io.readU1()
            this.green = this._io.readU1()
            this.blue = this._io.readU1()
            this.alpha = this._io.readU1()
            this.fontWeight = this._io.readU2le()
            this.padding = this._io.readU2le()
            this.text = String(this._io.readBytesTerm(0.toByte(), false, true, true), StandardCharsets.UTF_8)
            this.font = String(this._io.readBytesTerm(0.toByte(), false, true, true), StandardCharsets.UTF_8)
        }

        fun _fetchInstances() {
        }

        private var red = 0
        private var green = 0
        private var blue = 0
        private var alpha = 0
        private var fontWeight = 0
        private var padding = 0
        private var text: String? = null
        private var font: String? = null

        init {
            _read()
        }

        fun red(): Int {
            return red
        }

        fun green(): Int {
            return green
        }

        fun blue(): Int {
            return blue
        }

        fun alpha(): Int {
            return alpha
        }

        fun fontWeight(): Int {
            return fontWeight
        }

        fun padding(): Int {
            return padding
        }

        fun text(): String? {
            return text
        }

        fun font(): String? {
            return font
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): DisplayFormattedMessage {
                return DisplayFormattedMessage(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class DisplayMessage @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.text = String(this._io.readBytesTerm(0.toByte(), false, true, true), StandardCharsets.UTF_8)
        }

        fun _fetchInstances() {
        }

        private var text: String? = null

        init {
            _read()
        }

        fun text(): String? {
            return text
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): DisplayMessage {
                return DisplayMessage(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class DisplayMovie @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.filename = String(this._io.readBytesTerm(0.toByte(), false, true, true), StandardCharsets.UTF_8)
        }

        fun _fetchInstances() {
        }

        private var filename: String? = null

        init {
            _read()
        }

        fun filename(): String? {
            return filename
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): DisplayMovie {
                return DisplayMovie(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class DisplayObject @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.id = this._io.readU2le()
            this.display = this._io.readBitsIntLe(1) != 0L
        }

        fun _fetchInstances() {
        }

        private var id = 0
        private var display = false

        init {
            _read()
        }

        fun id(): Int {
            return id
        }

        fun display(): Boolean {
            return display
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): DisplayObject {
                return DisplayObject(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class EndGame @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: KaitaiStruct? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.ending = Ending.byId(this._io.readU1().toLong())
        }

        fun _fetchInstances() {
        }

        private var ending: Ending? = null

        init {
            _read()
        }

        fun ending(): Ending? {
            return ending
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): KaitaiStruct? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): EndGame {
                return EndGame(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class EvtCondition @JvmOverloads constructor(
        io: KaitaiStream?,
        private val _parent: KaitaiStruct? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(io) {
        private fun read() {
            this.compareType = CompareType.byId(this._io.readU2le().toLong())
            this.compareId = this._io.readU2le()
            this.comparedValue = this._io.readU2le()
            this.comparison = ComparisonType.byId(this._io.readU2le().toLong())
        }

        private var compareType: CompareType? = null
        private var compareId = 0
        private var comparedValue = 0
        private var comparison: ComparisonType? = null

        init {
            read()
        }

        fun compareType(): CompareType? {
            return compareType
        }

        fun compareId(): Int {
            return compareId
        }

        fun comparedValue(): Int {
            return comparedValue
        }

        fun comparison(): ComparisonType? {
            return comparison
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): EvtCondition {
                return EvtCondition(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class EvtDefinition @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: Evt? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.name =
                String(KaitaiStream.bytesTerminate(this._io.readBytes(31), 0.toByte(), false), StandardCharsets.UTF_8)
            this.targetType = TargetType.byId(this._io.readU1().toLong())
            this.targetId = this._io.readU2le()
            this.triggerType = TriggerType.byId(this._io.readU1().toLong())
            this.triggerItem = this._io.readU1()
            this.triggerCone = this._io.readU2le()
            this.padding = this._io.readU2le()
            this.triggerRectWidth = this._io.readF4le()
            this.triggerRectHeight = this._io.readF4le()
            this.triggerRadius = this._io.readF4le()
            this.condition = EvtCondition(this._io, this, _root)
            this.page = ArrayList()
            repeat(16) {
                this.page!!.add(EvtPageOffset(this._io, this, _root))
            }
        }

        fun _fetchInstances() {
            for (i in this.page!!.indices) {
                this.page!![i]!!._fetchInstances()
            }
        }

        private var name: String? = null
        private var targetType: TargetType? = null
        private var targetId = 0
        private var triggerType: TriggerType? = null
        private var triggerItem = 0
        private var triggerCone = 0
        private var padding = 0
        private var triggerRectWidth = 0f
        private var triggerRectHeight = 0f
        private var triggerRadius = 0f
        private var condition: EvtCondition? = null
        private var page: MutableList<EvtPageOffset?>? = null

        init {
            _read()
        }

        fun name(): String? {
            return name
        }

        fun targetType(): TargetType? {
            return targetType
        }

        fun targetId(): Int {
            return targetId
        }

        fun triggerType(): TriggerType? {
            return triggerType
        }

        fun triggerItem(): Int {
            return triggerItem
        }

        fun triggerCone(): Int {
            return triggerCone
        }

        fun padding(): Int {
            return padding
        }

        /**
         * Width of the rectangular trigger area.
         * Each map piece = 2x2
         * Minimum: 0.0
         * Maximum: 200.0
         */
        fun triggerRectWidth(): Float {
            return triggerRectWidth
        }

        /**
         * Height of the rectangular trigger area.
         * Each map piece = 2x2
         * Minimum: 0.0
         * Maximum: 200.0
         */
        fun triggerRectHeight(): Float {
            return triggerRectHeight
        }

        /**
         * Radius of the circular trigger area.
         * Each map piece = 2x2
         * Minimum: 0.0
         * Maximum: 100.0
         */
        fun triggerRadius(): Float {
            return triggerRadius
        }

        fun condition(): EvtCondition {
            return condition!!
        }

        fun page(): MutableList<EvtPageOffset?> {
            return page!!
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): Evt? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): EvtDefinition {
                return EvtDefinition(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class EvtOperation @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtPageOffset? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.opId = OperationType.byId(this._io.readU2le().toLong())
            this.opSize = this._io.readU2le()
            run {
                val on = opId()
                if (on != null) {
                    when (opId()) {
                        OperationType.ACTIVATE_ENEMY -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ActivateEnemy(_io_operation, this, _root)
                        }

                        OperationType.ACTIVATE_ITEM -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ActivateItem(_io_operation, this, _root)
                        }

                        OperationType.ACTIVATE_NPC -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ActivateNpc(_io_operation, this, _root)
                        }

                        OperationType.BGM_PLAYBACK -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = BgmPlayback(_io_operation, this, _root)
                        }

                        OperationType.CHANGE_BGM -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ChangeBgm(_io_operation, this, _root)
                        }

                        OperationType.CHANGE_COUNTER -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ChangeCounter(_io_operation, this, _root)
                        }

                        OperationType.CHANGE_DASH_OR_SAVE_TEMPORARILY -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ChangeDashOrSaveTemporarily(_io_operation, this, _root)
                        }

                        OperationType.CHANGE_PAGE -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ChangePage(_io_operation, this, _root)
                        }

                        OperationType.CHANGE_PLAYER_PARAMETER -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ChangePlayerParameter(_io_operation, this, _root)
                        }

                        OperationType.CHANGE_PLAYER_STATUS -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ChangePlayerStatus(_io_operation, this, _root)
                        }

                        OperationType.DISPLAY_BMP -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = DisplayBmp(_io_operation, this, _root)
                        }

                        OperationType.DISPLAY_FORMATTED_MESSAGE -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = DisplayFormattedMessage(_io_operation, this, _root)
                        }

                        OperationType.DISPLAY_MESSAGE -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = DisplayMessage(_io_operation, this, _root)
                        }

                        OperationType.DISPLAY_MOVIE -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = DisplayMovie(_io_operation, this, _root)
                        }

                        OperationType.DISPLAY_OBJECT -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = DisplayObject(_io_operation, this, _root)
                        }

                        OperationType.GENERATE_RANDOM_COUNTER_VALUE -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = GenerateRandomCounterValue(_io_operation, this, _root)
                        }

                        OperationType.IF_COUNTER -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = IfCounter(_io_operation, this, _root)
                        }

                        OperationType.IF_PROMPT -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = IfPrompt(_io_operation, this, _root)
                        }

                        OperationType.LEARN_MAGIC -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = LearnMagic(_io_operation, this, _root)
                        }

                        OperationType.MOVE_OBJECT -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = MoveObject(_io_operation, this, _root)
                        }

                        OperationType.OBJECT_ANIMATION -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ObjectAnimation(_io_operation, this, _root)
                        }

                        OperationType.PLAY_SOUND_EFFECT -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = PlaySoundEffect(_io_operation, this, _root)
                        }

                        OperationType.SCREEN_EFFECT_START -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ScreenEffectStart(_io_operation, this, _root)
                        }

                        OperationType.SET_PLAYER_PARAMETER_IN_COUNTER -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = SetPlayerParameterInCounter(_io_operation, this, _root)
                        }

                        OperationType.SET_TIMER_VALUE_IN_COUNTER -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = SetTimerValueInCounter(_io_operation, this, _root)
                        }

                        OperationType.SHOP_OPEN -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = ShopOpen(_io_operation, this, _root)
                        }

                        OperationType.START_TIMER -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = StartTimer(_io_operation, this, _root)
                        }

                        OperationType.TERMINATE_ENEMY -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = TerminateEnemy(_io_operation, this, _root)
                        }

                        OperationType.TERMINATE_NPC -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = TerminateNpc(_io_operation, this, _root)
                        }

                        OperationType.WARP_ENEMY -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = WarpEnemy(_io_operation, this, _root)
                        }

                        OperationType.WARP_NPC -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = WarpNpc(_io_operation, this, _root)
                        }

                        OperationType.WARP_PLAYER_BASIC -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = WarpPlayerBasic(_io_operation, this, _root)
                        }

                        OperationType.WARP_PLAYER_DETAILED -> {
                            val _io_operation = this._io.substream((opSize() - 4).toLong())
                            this.operation = WarpPlayerDetailed(_io_operation, this, _root)
                        }

                        else -> {
                            this.operation = this._io.readBytes((opSize() - 4).toLong())
                        }
                    }
                } else {
                    this.operation = this._io.readBytes((opSize() - 4).toLong())
                }
            }
        }

        fun _fetchInstances() {
            run {
                val on = opId()
                if (on != null) {
                    when (opId()) {
                        OperationType.ACTIVATE_ENEMY -> {
                            ((this.operation) as ActivateEnemy)._fetchInstances()
                        }

                        OperationType.ACTIVATE_ITEM -> {
                            ((this.operation) as ActivateItem)._fetchInstances()
                        }

                        OperationType.ACTIVATE_NPC -> {
                            ((this.operation) as ActivateNpc)._fetchInstances()
                        }

                        OperationType.BGM_PLAYBACK -> {
                            ((this.operation) as BgmPlayback)._fetchInstances()
                        }

                        OperationType.CHANGE_BGM -> {
                            ((this.operation) as ChangeBgm)._fetchInstances()
                        }

                        OperationType.CHANGE_COUNTER -> {
                            ((this.operation) as ChangeCounter)._fetchInstances()
                        }

                        OperationType.CHANGE_DASH_OR_SAVE_TEMPORARILY -> {
                            ((this.operation) as ChangeDashOrSaveTemporarily)._fetchInstances()
                        }

                        OperationType.CHANGE_PAGE -> {
                            ((this.operation) as ChangePage)._fetchInstances()
                        }

                        OperationType.CHANGE_PLAYER_PARAMETER -> {
                            ((this.operation) as ChangePlayerParameter)._fetchInstances()
                        }

                        OperationType.CHANGE_PLAYER_STATUS -> {
                            ((this.operation) as ChangePlayerStatus)._fetchInstances()
                        }

                        OperationType.DISPLAY_BMP -> {
                            ((this.operation) as DisplayBmp)._fetchInstances()
                        }

                        OperationType.DISPLAY_FORMATTED_MESSAGE -> {
                            ((this.operation) as DisplayFormattedMessage)._fetchInstances()
                        }

                        OperationType.DISPLAY_MESSAGE -> {
                            ((this.operation) as DisplayMessage)._fetchInstances()
                        }

                        OperationType.DISPLAY_MOVIE -> {
                            ((this.operation) as DisplayMovie)._fetchInstances()
                        }

                        OperationType.DISPLAY_OBJECT -> {
                            ((this.operation) as DisplayObject)._fetchInstances()
                        }

                        OperationType.GENERATE_RANDOM_COUNTER_VALUE -> {
                            ((this.operation) as GenerateRandomCounterValue)._fetchInstances()
                        }

                        OperationType.IF_COUNTER -> {
                            ((this.operation) as IfCounter)._fetchInstances()
                        }

                        OperationType.IF_PROMPT -> {
                            ((this.operation) as IfPrompt)._fetchInstances()
                        }

                        OperationType.LEARN_MAGIC -> {
                            ((this.operation) as LearnMagic)._fetchInstances()
                        }

                        OperationType.MOVE_OBJECT -> {
                            ((this.operation) as MoveObject)._fetchInstances()
                        }

                        OperationType.OBJECT_ANIMATION -> {
                            ((this.operation) as ObjectAnimation)._fetchInstances()
                        }

                        OperationType.PLAY_SOUND_EFFECT -> {
                            ((this.operation) as PlaySoundEffect)._fetchInstances()
                        }

                        OperationType.SCREEN_EFFECT_START -> {
                            ((this.operation) as ScreenEffectStart)._fetchInstances()
                        }

                        OperationType.SET_PLAYER_PARAMETER_IN_COUNTER -> {
                            ((this.operation) as SetPlayerParameterInCounter)._fetchInstances()
                        }

                        OperationType.SET_TIMER_VALUE_IN_COUNTER -> {
                            ((this.operation) as SetTimerValueInCounter)._fetchInstances()
                        }

                        OperationType.SHOP_OPEN -> {
                            ((this.operation) as ShopOpen)._fetchInstances()
                        }

                        OperationType.START_TIMER -> {
                            ((this.operation) as StartTimer)._fetchInstances()
                        }

                        OperationType.TERMINATE_ENEMY -> {
                            ((this.operation) as TerminateEnemy)._fetchInstances()
                        }

                        OperationType.TERMINATE_NPC -> {
                            ((this.operation) as TerminateNpc)._fetchInstances()
                        }

                        OperationType.WARP_ENEMY -> {
                            ((this.operation) as WarpEnemy)._fetchInstances()
                        }

                        OperationType.WARP_NPC -> {
                            ((this.operation) as WarpNpc)._fetchInstances()
                        }

                        OperationType.WARP_PLAYER_BASIC -> {
                            ((this.operation) as WarpPlayerBasic)._fetchInstances()
                        }

                        OperationType.WARP_PLAYER_DETAILED -> {
                            ((this.operation) as WarpPlayerDetailed)._fetchInstances()
                        }

                        else -> {}
                    }
                } else {
                }
            }
        }

        private var opId: OperationType? = null
        private var opSize = 0
        private var operation: Any? = null

        init {
            _read()
        }

        fun opId(): OperationType? {
            return opId
        }

        fun opSize(): Int {
            return opSize
        }

        fun operation(): Any {
            return operation!!
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtPageOffset? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): EvtOperation {
                return EvtOperation(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class EvtPageOffset @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtDefinition? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.payloadOffset = this._io.readU4le()
            this.condition = EvtCondition(this._io, this, _root)
        }

        fun _fetchInstances() {
            body()
            if (this.body != null) {
                for (i in this.body!!.indices) {
                    this.body!!.get(((i) as Number).toInt())!!._fetchInstances()
                }
            }
        }

        private var body: MutableList<EvtOperation?>? = null
        fun body(): MutableList<EvtOperation?>? {
            if (this.body != null) return this.body
            if (payloadOffset() > 0) {
                val io = _root()!!._io()
                val _pos = io.pos().toLong()
                io.seek(payloadOffset())
                this.body = ArrayList<EvtOperation?>()
                run {
                    var _it: EvtOperation?
                    var i = 0
                    do {
                        _it = EvtOperation(io, this, _root)
                        this.body!!.add(_it)
                        i++
                    } while (_it.opId() != OperationType.END)
                }
                io.seek(_pos)
            }
            return this.body
        }

        private var payloadOffset: Long = 0
        private var condition: EvtCondition? = null

        init {
            _read()
        }

        fun payloadOffset(): Long {
            return payloadOffset
        }

        fun condition(): EvtCondition {
            return condition!!
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtDefinition? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): EvtPageOffset {
                return EvtPageOffset(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class GenerateRandomCounterValue @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.useCounterForMaxValueFlag = this._io.readU2le()
            this.padding = this._io.readU2le()
            this.maxValue = this._io.readU2le()
            this.id = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var useCounterForMaxValue: Boolean? = null
        fun useCounterForMaxValue(): Boolean {
            if (this.useCounterForMaxValue != null) return this.useCounterForMaxValue!!
            this.useCounterForMaxValue = useCounterForMaxValueFlag() != 0
            return this.useCounterForMaxValue!!
        }

        private var useCounterForMaxValueFlag = 0
        private var padding = 0
        private var maxValue = 0
        private var id = 0

        init {
            _read()
        }

        fun useCounterForMaxValueFlag(): Int {
            return useCounterForMaxValueFlag
        }

        fun padding(): Int {
            return padding
        }

        fun maxValue(): Int {
            return maxValue
        }

        fun id(): Int {
            return id
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): GenerateRandomCounterValue {
                return GenerateRandomCounterValue(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class IfCounter @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.id = this._io.readU2le()
            this.value = this._io.readU2le()
            this.valueIsCounterIdFlag = this._io.readU1()
            this.comparisonType = ComparisonType.byId(this._io.readU1().toLong())
        }

        fun _fetchInstances() {
        }

        private var valueIsCounterId: Boolean? = null
        fun valueIsCounterId(): Boolean {
            if (this.valueIsCounterId != null) return this.valueIsCounterId!!
            this.valueIsCounterId = valueIsCounterIdFlag() != 0
            return this.valueIsCounterId!!
        }

        private var id = 0
        private var value = 0
        private var valueIsCounterIdFlag = 0
        private var comparisonType: ComparisonType? = null

        init {
            _read()
        }

        fun id(): Int {
            return id
        }

        fun value(): Int {
            return value
        }

        fun valueIsCounterIdFlag(): Int {
            return valueIsCounterIdFlag
        }

        fun comparisonType(): ComparisonType? {
            return comparisonType
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): IfCounter {
                return IfCounter(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class IfPrompt @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.text = String(this._io.readBytesTerm(0.toByte(), false, true, true), StandardCharsets.UTF_8)
            this.option1 = String(this._io.readBytesTerm(0.toByte(), false, true, true), StandardCharsets.UTF_8)
            this.option2 = String(this._io.readBytesTerm(0.toByte(), false, true, true), StandardCharsets.UTF_8)
        }

        fun _fetchInstances() {
        }

        private var text: String? = null
        private var option1: String? = null
        private var option2: String? = null

        init {
            _read()
        }

        fun text(): String? {
            return text
        }

        fun option1(): String? {
            return option1
        }

        fun option2(): String? {
            return option2
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): IfPrompt {
                return IfPrompt(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class LearnMagic @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.magicTableId = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var magicTableId = 0

        init {
            _read()
        }

        fun magicTableId(): Int {
            return magicTableId
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): LearnMagic {
                return LearnMagic(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class MoveObject @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.id = this._io.readU2le()
            this.x = this._io.readU1()
            this.z = this._io.readU1()
            this.angleX = this._io.readU2le()
            this.angleY = this._io.readU2le()
            this.angleZ = this._io.readU2le()
            this.moveTime = this._io.readU2le()
            this.fineX = this._io.readF4le()
            this.fineY = this._io.readF4le()
            this.fineZ = this._io.readF4le()
        }

        fun _fetchInstances() {
        }

        private var id = 0
        private var x = 0
        private var z = 0
        private var angleX = 0
        private var angleY = 0
        private var angleZ = 0
        private var moveTime = 0
        private var fineX = 0f
        private var fineY = 0f
        private var fineZ = 0f

        init {
            _read()
        }

        fun id(): Int {
            return id
        }

        fun x(): Int {
            return x
        }

        fun z(): Int {
            return z
        }

        /**
         * X rotation after move.
         * Minimum: 0
         * Maximum: 360
         */
        fun angleX(): Int {
            return angleX
        }

        /**
         * Y rotation after move.
         * Minimum: 0
         * Maximum: 360
         */
        fun angleY(): Int {
            return angleY
        }

        /**
         * Z rotation after move.
         * Minimum: 0
         * Maximum: 360
         */
        fun angleZ(): Int {
            return angleZ
        }

        fun moveTime(): Int {
            return moveTime
        }

        fun fineX(): Float {
            return fineX
        }

        fun fineY(): Float {
            return fineY
        }

        fun fineZ(): Float {
            return fineZ
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): MoveObject {
                return MoveObject(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class ObjectAnimation @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.id = this._io.readU2le()
            this.playback = Playback.byId(this._io.readU2le().toLong())
        }

        fun _fetchInstances() {
        }

        private var id = 0
        private var playback: Playback? = null

        init {
            _read()
        }

        fun id(): Int {
            return id
        }

        fun playback(): Playback? {
            return playback
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ObjectAnimation {
                return ObjectAnimation(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class PlaySoundEffect @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.id = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var id = 0

        init {
            _read()
        }

        fun id(): Int {
            return id
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): PlaySoundEffect {
                return PlaySoundEffect(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class ScreenEffectStart @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.effect = ScreenEffect.byId(this._io.readU1().toLong())
            this.loop = this._io.readBitsIntLe(1) != 0L
        }

        fun _fetchInstances() {
        }

        private var effect: ScreenEffect? = null
        private var loop = false

        init {
            _read()
        }

        fun effect(): ScreenEffect? {
            return effect
        }

        fun loop(): Boolean {
            return loop
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ScreenEffectStart {
                return ScreenEffectStart(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class SetPlayerParameterInCounter @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.parameter = PlayerParameter.byId(this._io.readU1().toLong())
            this.itemId = this._io.readU1()
            this.counterId = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var parameter: PlayerParameter? = null
        private var itemId = 0
        private var counterId = 0

        init {
            _read()
        }

        fun parameter(): PlayerParameter? {
            return parameter
        }

        fun itemId(): Int {
            return itemId
        }

        fun counterId(): Int {
            return counterId
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): SetPlayerParameterInCounter {
                return SetPlayerParameterInCounter(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class SetTimerValueInCounter @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.id = this._io.readU1()
            this.padding = this._io.readU1()
            this.destinationCounterId = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var id = 0
        private var padding = 0
        private var destinationCounterId = 0

        init {
            _read()
        }

        fun id(): Int {
            return id
        }

        fun padding(): Int {
            return padding
        }

        fun destinationCounterId(): Int {
            return destinationCounterId
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): SetTimerValueInCounter {
                return SetTimerValueInCounter(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class ShopOpen @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.shopId = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var shopId = 0

        init {
            _read()
        }

        fun shopId(): Int {
            return shopId
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ShopOpen {
                return ShopOpen(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class StartTimer @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.id = this._io.readU1()
        }

        fun _fetchInstances() {
        }

        private var id = 0

        init {
            _read()
        }

        fun id(): Int {
            return id
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): StartTimer {
                return StartTimer(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class TerminateEnemy @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.enemyId = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var enemyId = 0

        init {
            _read()
        }

        fun enemyId(): Int {
            return enemyId
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): TerminateEnemy {
                return TerminateEnemy(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class TerminateNpc @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.npcId = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var npcId = 0

        init {
            _read()
        }

        fun npcId(): Int {
            return npcId
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): TerminateNpc {
                return TerminateNpc(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class WarpEnemy @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.enemyId = this._io.readU2le()
            this.x = this._io.readU1()
            this.z = this._io.readU1()
            this.direction = this._io.readU2le()
            this.padding = this._io.readU2le()
            this.fineX = this._io.readF4le()
            this.fineY = this._io.readF4le()
            this.fineZ = this._io.readF4le()
        }

        fun _fetchInstances() {
        }

        private var enemyId = 0
        private var x = 0
        private var z = 0
        private var direction = 0
        private var padding = 0
        private var fineX = 0f
        private var fineY = 0f
        private var fineZ = 0f

        init {
            _read()
        }

        fun enemyId(): Int {
            return enemyId
        }

        fun x(): Int {
            return x
        }

        fun z(): Int {
            return z
        }

        fun direction(): Int {
            return direction
        }

        fun padding(): Int {
            return padding
        }

        fun fineX(): Float {
            return fineX
        }

        fun fineY(): Float {
            return fineY
        }

        fun fineZ(): Float {
            return fineZ
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): WarpEnemy {
                return WarpEnemy(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class WarpNpc @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.npcId = this._io.readU2le()
            this.x = this._io.readU1()
            this.z = this._io.readU1()
            this.direction = this._io.readU2le()
            this.padding = this._io.readU2le()
            this.fineX = this._io.readF4le()
            this.fineY = this._io.readF4le()
            this.fineZ = this._io.readF4le()
        }

        fun _fetchInstances() {
        }

        private var npcId = 0
        private var x = 0
        private var z = 0
        private var direction = 0
        private var padding = 0
        private var fineX = 0f
        private var fineY = 0f
        private var fineZ = 0f

        init {
            _read()
        }

        fun npcId(): Int {
            return npcId
        }

        /**
         * X coordinate on current map
         * Minimum: 1
         * Maximum: 99
         */
        fun x(): Int {
            return x
        }

        /**
         * Z coordinate on current map
         * Minimum: 1
         * Maximum: 99
         */
        fun z(): Int {
            return z
        }

        /**
         * Direction the NPC will face after warping
         * Minimum: 1
         * Maximum: 360
         */
        fun direction(): Int {
            return direction
        }

        fun padding(): Int {
            return padding
        }

        fun fineX(): Float {
            return fineX
        }

        fun fineY(): Float {
            return fineY
        }

        fun fineZ(): Float {
            return fineZ
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): WarpNpc {
                return WarpNpc(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class WarpPlayerBasic @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.x = this._io.readU1()
            this.z = this._io.readU1()
            this.direction = this._io.readU2le()
            this.fineX = this._io.readF4le()
            this.fineY = this._io.readF4le()
            this.fineZ = this._io.readF4le()
            this.setDirection = this._io.readBitsIntLe(1) != 0L
            this.setFineX = this._io.readBitsIntLe(1) != 0L
            this.setFineY = this._io.readBitsIntLe(1) != 0L
            this.setFineZ = this._io.readBitsIntLe(1) != 0L
        }

        fun _fetchInstances() {
        }

        private var x = 0
        private var z = 0
        private var direction = 0
        private var fineX = 0f
        private var fineY = 0f
        private var fineZ = 0f
        private var setDirection = false
        private var setFineX = false
        private var setFineY = false
        private var setFineZ = false

        init {
            _read()
        }

        fun x(): Int {
            return x
        }

        fun z(): Int {
            return z
        }

        fun direction(): Int {
            return direction
        }

        fun fineX(): Float {
            return fineX
        }

        fun fineY(): Float {
            return fineY
        }

        fun fineZ(): Float {
            return fineZ
        }

        fun setDirection(): Boolean {
            return setDirection
        }

        fun setFineX(): Boolean {
            return setFineX
        }

        fun setFineY(): Boolean {
            return setFineY
        }

        fun setFineZ(): Boolean {
            return setFineZ
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): WarpPlayerBasic {
                return WarpPlayerBasic(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class WarpPlayerDetailed @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: EvtOperation? = null,
        private val _root: Evt? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.mapId = this._io.readU1()
            this.defaultStartPointFlag = this._io.readU1()
            this.screenEffectOnLeave = ScreenEffect.byId(this._io.readU1().toLong())
            this.screenEffectOnEnter = ScreenEffect.byId(this._io.readU1().toLong())
            this.x = this._io.readU1()
            this.z = this._io.readU1()
            this.direction = this._io.readU2le()
            this.fineX = this._io.readF4le()
            this.fineY = this._io.readF4le()
            this.fineZ = this._io.readF4le()
            this.setDirection = this._io.readBitsIntLe(1) != 0L
            this.setFineX = this._io.readBitsIntLe(1) != 0L
            this.setFineY = this._io.readBitsIntLe(1) != 0L
            this.setFineZ = this._io.readBitsIntLe(1) != 0L
        }

        fun _fetchInstances() {
        }

        private var useDefaultStartPoint: Boolean? = null
        fun useDefaultStartPoint(): Boolean {
            if (this.useDefaultStartPoint != null) return this.useDefaultStartPoint!!
            this.useDefaultStartPoint = defaultStartPointFlag() != 0
            return this.useDefaultStartPoint!!
        }

        private var mapId = 0
        private var defaultStartPointFlag = 0
        private var screenEffectOnLeave: ScreenEffect? = null
        private var screenEffectOnEnter: ScreenEffect? = null
        private var x = 0
        private var z = 0
        private var direction = 0
        private var fineX = 0f
        private var fineY = 0f
        private var fineZ = 0f
        private var setDirection = false
        private var setFineX = false
        private var setFineY = false
        private var setFineZ = false

        init {
            _read()
        }

        /**
         * Map to warp player to.
         * 00-63
         */
        fun mapId(): Int {
            return mapId
        }

        /**
         * When set, ignores positions defined below and disable editing them
         * in the event editor. Uses default player spawn position for the
         * destination map.
         */
        fun defaultStartPointFlag(): Int {
            return defaultStartPointFlag
        }

        /**
         * Screen effect to use when warp starts.
         * Valid values are:
         * NONE(0xFFu),
         * BLACK FADES OFF(0x00u),
         * BLACK FADES ON(0x01u),
         * WHITE FADES OFF(0x02u),
         * WHITE FADES ON(0x03u)
         * Used screen effect event enum for convencience.
         */
        fun screenEffectOnLeave(): ScreenEffect? {
            return screenEffectOnLeave
        }

        /**
         * Screen effect to use when warp ends.
         * Valid values are:
         * NONE(0xFFu),
         * BLACK FADES OFF(0x00u),
         * BLACK FADES ON(0x01u),
         * WHITE FADES OFF(0x02u),
         * WHITE FADES ON(0x03u)
         * Used screen effect event enum for convencience.
         */
        fun screenEffectOnEnter(): ScreenEffect? {
            return screenEffectOnEnter
        }

        /**
         * X coordinate of destination map
         * Minimum: 1
         * Maximum: 99
         */
        fun x(): Int {
            return x
        }

        /**
         * Z coordinate of destination map
         * Minimum: 1
         * Maximum: 99
         */
        fun z(): Int {
            return z
        }

        /**
         * Player direction after warp in degrees.
         * Minimum: 0
         * Maximum: 360
         */
        fun direction(): Int {
            return direction
        }

        /**
         * Distance from center of tile.
         * Rounding to the first decimal place in the editor
         * Minimum: -1.0
         * Maximum: 1.0
         */
        fun fineX(): Float {
            return fineX
        }

        /**
         * Vertical position.
         * Rounding to the first decimal place in the editor
         * Minimum: -20.0
         * Maximum: 20.0
         */
        fun fineY(): Float {
            return fineY
        }

        /**
         * Distance from center of tile.
         * Rounding to the first decimal place in the editor
         * Minimum: -1.0
         * Maximum: 1.0
         */
        fun fineZ(): Float {
            return fineZ
        }

        /**
         * When true, update player direction after warp
         * When false, value of direction is ignored
         */
        fun setDirection(): Boolean {
            return setDirection
        }

        /**
         * When true, use fine x defined position
         * When false, I don't know. Uses player's fine x position before warp?
         */
        fun setFineX(): Boolean {
            return setFineX
        }

        /**
         * When true, use fine y position after warp
         * When false, I don't know. Use player's y position before warp?
         */
        fun setFineY(): Boolean {
            return setFineY
        }

        /**
         * When true, use fine z defined position
         * When false, I don't know. Uses player's fine z position before warp?
         */
        fun setFineZ(): Boolean {
            return setFineZ
        }

        fun _root(): Evt? {
            return _root
        }

        override fun _parent(): EvtOperation? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): WarpPlayerDetailed {
                return WarpPlayerDetailed(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    private var magic: ByteArray = ByteArray(0)
    private var definitions: MutableList<EvtDefinition?>? = null
    private val _root: Evt?

    init {
        this._root = if (_root == null) this else _root
        _read()
    }

    fun magic(): ByteArray {
        return magic
    }

    fun definitions(): MutableList<EvtDefinition?> {
        return definitions!!
    }

    fun _root(): Evt? {
        return _root
    }

    override fun _parent(): KaitaiStruct? {
        return _parent
    }

    companion object {
        @Throws(IOException::class)
        fun fromFile(fileName: String?): Evt {
            return Evt(ByteBufferKaitaiStream(fileName))
        }
    }
}
