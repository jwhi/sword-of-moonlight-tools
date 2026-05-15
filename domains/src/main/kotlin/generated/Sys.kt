package com.jwhi.som.domains.generated

import io.kaitai.struct.ByteBufferKaitaiStream
import io.kaitai.struct.KaitaiStream
import io.kaitai.struct.KaitaiStruct
import java.io.IOException
import java.nio.charset.Charset

/**
 * Sword of Moonlight SYS.dat file.
 * Stores game specific information and counter names used in map events.
 * @see [Source](https://doc.swordofmoonlight.com/editor/ff/param-system-file-formats/)
 */
class Sys @JvmOverloads constructor(_io: KaitaiStream?, private val _parent: KaitaiStruct? = null, _root: Sys? = null) :
    KaitaiStruct(_io) {
    enum class LevelingType(private val id: Long) {
        KINGS_FIELD_1(0),
        KINGS_FIELD_2(1),
        BALANCED(2),
        SOLDIER(3),
        MAGICIAN(4);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, LevelingType?> = HashMap<Long?, LevelingType?>(5)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): LevelingType? {
                return byId.get(id)
            }
        }
    }

    enum class SequenceMode(private val id: Long) {
        NONE(0),
        VIDEO(1),
        SLIDESHOW(2);

        fun id(): Long {
            return id
        }

        companion object {
            private val byId: MutableMap<Long?, SequenceMode?> = HashMap<Long?, SequenceMode?>(3)

            init {
                for (e in entries) byId.put(e.id(), e)
            }

            fun byId(id: Long): SequenceMode? {
                return byId.get(id)
            }
        }
    }

    private fun _read() {
        this.sequenceSettings = SequenceSettings(this._io, this, _root)
        this.dashEnabledFlag = this._io.readU2le()
        this.playerSpeed = PlayerSpeed(this._io, this, _root)
        this.levelingType = LevelingType.byId(this._io.readU1().toLong())
        this.classData = ClassData(this._io, this, _root)
        this.magicTable = MagicTable(this._io, this, _root)
        this.menuConfiguration = MenuConfiguration(this._io, this, _root)
        this.messages = Messages(this._io, this, _root)
        this.systemMessages = SystemMessages(this._io, this, _root)
        this.currencyUnit =
            String(KaitaiStream.bytesTerminate(this._io.readBytes(3), 0.toByte(), false), Charset.forName("Shift_JIS"))
        this.playerConfig = PlayerConfig(this._io, this, _root)
        this.playerConfigTestPlay = PlayerConfig(this._io, this, _root)
        this.startingMap = this._io.readU1()
        this.counterNames = ArrayList<String?>()
        for (i in 0..1023) {
            this.counterNames!!.add(
                String(
                    KaitaiStream.bytesTerminate(this._io.readBytes(31), 0.toByte(), false),
                    Charset.forName("Shift_JIS")
                )
            )
        }
        this.unknown = ArrayList<Int?>()
        for (i in 0..0) {
            this.unknown!!.add(this._io.readU1())
        }
        this.sounds = ArrayList<Int?>()
        for (i in 0..15) {
            this.sounds!!.add(this._io.readU2le())
        }
        this.menuBackgroundFilename =
            String(KaitaiStream.bytesTerminate(this._io.readBytes(38), 0.toByte(), false), Charset.forName("Shift_JIS"))
        this.messagesAdditional = MessagesAdditional(this._io, this, _root)
        this.menuSoundEffect = this._io.readU1()
        this.endingPadding = ArrayList<Int?>()
        run {
            var i = 0
            while (!this._io.isEof()) {
                this.endingPadding!!.add(this._io.readU1())
                i++
            }
        }
    }

    fun _fetchInstances() {
        this.sequenceSettings!!._fetchInstances()
        this.playerSpeed!!._fetchInstances()
        this.classData!!._fetchInstances()
        this.magicTable!!._fetchInstances()
        this.menuConfiguration!!._fetchInstances()
        this.messages!!._fetchInstances()
        this.systemMessages!!._fetchInstances()
        this.playerConfig!!._fetchInstances()
        this.playerConfigTestPlay!!._fetchInstances()
        for (i in this.counterNames!!.indices) {
        }
        for (i in this.unknown!!.indices) {
        }
        for (i in this.sounds!!.indices) {
        }
        this.messagesAdditional!!._fetchInstances()
        for (i in this.endingPadding!!.indices) {
        }
    }

    class ClassData @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: Sys? = null,
        private val _root: Sys? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.names = ArrayList<String?>()
            for (i in 0..24) {
                this.names!!.add(
                    String(
                        KaitaiStream.bytesTerminate(this._io.readBytes(15), 0.toByte(), false),
                        Charset.forName("Shift_JIS")
                    )
                )
            }
            this.strengthTiers = ArrayList<Int?>()
            for (i in 0..3) {
                this.strengthTiers!!.add(this._io.readU2le())
            }
            this.magicTiers = ArrayList<Int?>()
            for (i in 0..3) {
                this.magicTiers!!.add(this._io.readU2le())
            }
        }

        fun _fetchInstances() {
            for (i in this.names!!.indices) {
            }
            for (i in this.strengthTiers!!.indices) {
            }
            for (i in this.magicTiers!!.indices) {
            }
        }

        private var names: MutableList<String?>? = null
        private var strengthTiers: MutableList<Int?>? = null
        private var magicTiers: MutableList<Int?>? = null

        init {
            _read()
        }

        fun names(): MutableList<String?> {
            return names!!
        }

        fun strengthTiers(): MutableList<Int?> {
            return strengthTiers!!
        }

        fun magicTiers(): MutableList<Int?> {
            return magicTiers!!
        }

        fun _root(): Sys? {
            return _root
        }

        override fun _parent(): Sys? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): ClassData {
                return ClassData(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    /**
     * Takes an array index between 0 and 250
     * Max item id is 249 but space is allocated to 250
     * in player config.
     * Corresponds to item id
     */
    class InventoryItem(
        _io: KaitaiStream?,
        private val _parent: PlayerConfig?,
        private val _root: Sys?,
        private val itemId: Int
    ) : KaitaiStruct(_io) {
        constructor(_io: KaitaiStream?, itemId: Int) : this(_io, null, null, itemId)

        constructor(_io: KaitaiStream?, _parent: PlayerConfig?, itemId: Int) : this(_io, _parent, null, itemId)

        private fun _read() {
        }

        fun _fetchInstances() {
        }

        private var quantity: Int? = null
        fun quantity(): Int {
            if (this.quantity != null) return this.quantity!!
            this.quantity = ((_parent()!!.inventoryCount().get(((itemId()) as Number).toInt())) as Number).toInt()
            return this.quantity!!
        }

        init {
            _read()
        }

        fun itemId(): Int {
            return itemId
        }

        fun _root(): Sys? {
            return _root
        }

        override fun _parent(): PlayerConfig? {
            return _parent
        }
    }

    /**
     * Parses the two seperate arrays into a single
     * object to represent each row of the magic table.
     */
    class Magic(_io: KaitaiStream?, private val _parent: MagicTable?, private val _root: Sys?, private val i: Int) :
        KaitaiStruct(_io) {
        constructor(_io: KaitaiStream?, i: Int) : this(_io, null, null, i)

        constructor(_io: KaitaiStream?, _parent: MagicTable?, i: Int) : this(_io, _parent, null, i)

        private fun _read() {
        }

        fun _fetchInstances() {
        }

        private var id: Int? = null
        fun id(): Int {
            if (this.id != null) return this.id!!
            this.id = ((_parent()!!.ids().get(((i()) as Number).toInt())) as Number).toInt()
            return this.id!!
        }

        private var learnByEvent: Boolean? = null
        fun learnByEvent(): Boolean {
            if (this.learnByEvent != null) return this.learnByEvent!!
            this.learnByEvent = _parent()!!.levels().get(((i()) as Number).toInt())!! > 100
            return this.learnByEvent!!
        }

        private var levelRequirement: Int? = null
        fun levelRequirement(): Int {
            if (this.levelRequirement != null) return this.levelRequirement!!
            this.levelRequirement =
                ((KaitaiStream.mod(_parent()!!.levels().get(((i()) as Number).toInt())!!, 100)) as Number).toInt()
            return this.levelRequirement!!
        }

        init {
            _read()
        }

        fun i(): Int {
            return i
        }

        fun _root(): Sys? {
            return _root
        }

        override fun _parent(): MagicTable? {
            return _parent
        }
    }

    class MagicTable @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: Sys? = null,
        private val _root: Sys? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.ids = ArrayList<Int?>()
            for (i in 0..31) {
                this.ids!!.add(this._io.readU1())
            }
            this.levels = ArrayList<Int?>()
            for (i in 0..31) {
                this.levels!!.add(this._io.readU1())
            }
        }

        fun _fetchInstances() {
            for (i in this.ids!!.indices) {
            }
            for (i in this.levels!!.indices) {
            }
            magic()
            if (this.magic != null) {
                for (i in this.magic!!.indices) {
                    this.magic!!.get(((i) as Number).toInt())!!._fetchInstances()
                }
            }
        }

        private var magic: MutableList<Magic?>? = null
        fun magic(): MutableList<Magic?>? {
            if (this.magic != null) return this.magic
            this.magic = ArrayList<Magic?>()
            run {
                var _it: Magic?
                var i = 0
                do {
                    _it = Magic(this._io, this, _root, i)
                    this.magic!!.add(_it)
                    i++
                } while (_it.id() != 255)
            }
            return this.magic
        }

        private var ids: MutableList<Int?>? = null
        private var levels: MutableList<Int?>? = null

        init {
            _read()
        }

        fun ids(): MutableList<Int?> {
            return ids!!
        }

        fun levels(): MutableList<Int?> {
            return levels!!
        }

        fun _root(): Sys? {
            return _root
        }

        override fun _parent(): Sys? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): MagicTable {
                return MagicTable(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class MenuConfiguration @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: Sys? = null,
        private val _root: Sys? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.allowSaveInMenuFlag = this._io.readU1()
            this.enableEquipmentWeightLimitFlag = this._io.readU1()
            this.compass = this._io.readU1()
            this.gauge = this._io.readU1()
            this.padding = this._io.readU1()
            this.menuStyle = this._io.readU1()
        }

        fun _fetchInstances() {
        }

        private var allowSaveInMenu: Boolean? = null
        fun allowSaveInMenu(): Boolean {
            if (this.allowSaveInMenu != null) return this.allowSaveInMenu!!
            this.allowSaveInMenu = allowSaveInMenuFlag() != 0
            return this.allowSaveInMenu!!
        }

        private var allowSaveInMenuFlag = 0
        private var enableEquipmentWeightLimitFlag = 0
        private var compass = 0
        private var gauge = 0
        private var padding = 0
        private var menuStyle = 0

        init {
            _read()
        }

        fun allowSaveInMenuFlag(): Int {
            return allowSaveInMenuFlag
        }

        fun enableEquipmentWeightLimitFlag(): Int {
            return enableEquipmentWeightLimitFlag
        }

        /**
         * Compass 0 is NONE.
         * Maximum: 4
         */
        fun compass(): Int {
            return compass
        }

        /**
         * Guage 0 is NONE.
         * Maximum: 4
         */
        fun gauge(): Int {
            return gauge
        }

        fun padding(): Int {
            return padding
        }

        /**
         * Menu Style 0 is NONE.
         * Maxmimum: 4
         */
        fun menuStyle(): Int {
            return menuStyle
        }

        fun _root(): Sys? {
            return _root
        }

        override fun _parent(): Sys? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): MenuConfiguration {
                return MenuConfiguration(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    /**
     * Messages tab is split between here and additional messages field.
     */
    class Messages @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: Sys? = null,
        private val _root: Sys? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.sealed = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.locked = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.wrongKey = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.nothingHappens = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.notEnoughMp = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.levelIncreased = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.magicLearned = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.strengthIncreased = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.magicIncreased = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
        }

        fun _fetchInstances() {
        }

        private var sealed: String? = null
        private var locked: String? = null
        private var wrongKey: String? = null
        private var nothingHappens: String? = null
        private var notEnoughMp: String? = null
        private var levelIncreased: String? = null
        private var magicLearned: String? = null
        private var strengthIncreased: String? = null
        private var magicIncreased: String? = null

        init {
            _read()
        }

        fun sealed(): String? {
            return sealed
        }

        fun locked(): String? {
            return locked
        }

        fun wrongKey(): String? {
            return wrongKey
        }

        fun nothingHappens(): String? {
            return nothingHappens
        }

        fun notEnoughMp(): String? {
            return notEnoughMp
        }

        fun levelIncreased(): String? {
            return levelIncreased
        }

        fun magicLearned(): String? {
            return magicLearned
        }

        fun strengthIncreased(): String? {
            return strengthIncreased
        }

        fun magicIncreased(): String? {
            return magicIncreased
        }

        fun _root(): Sys? {
            return _root
        }

        override fun _parent(): Sys? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): Messages {
                return Messages(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class MessagesAdditional @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: Sys? = null,
        private val _root: Sys? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.nothingInside = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.seemsToBeDead = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.unlockedWithKey = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
        }

        fun _fetchInstances() {
        }

        private var nothingInside: String? = null
        private var seemsToBeDead: String? = null
        private var unlockedWithKey: String? = null

        init {
            _read()
        }

        fun nothingInside(): String? {
            return nothingInside
        }

        fun seemsToBeDead(): String? {
            return seemsToBeDead
        }

        fun unlockedWithKey(): String? {
            return unlockedWithKey
        }

        fun _root(): Sys? {
            return _root
        }

        override fun _parent(): Sys? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): MessagesAdditional {
                return MessagesAdditional(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class PlayerConfig @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: Sys? = null,
        private val _root: Sys? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.initialStrength = this._io.readU2le()
            this.initialMagic = this._io.readU2le()
            this.initialHp = this._io.readU2le()
            this.initialMp = this._io.readU2le()
            this.initialGold = this._io.readU4le()
            this.initialExperience = this._io.readU4le()
            this.initialLevel = this._io.readU1()
            this.weapon = this._io.readU1()
            this.headArmor = this._io.readU1()
            this.chestArmor = this._io.readU1()
            this.handsArmor = this._io.readU1()
            this.feetArmor = this._io.readU1()
            this.shield = this._io.readU1()
            this.accessory = this._io.readU1()
            this.magic = this._io.readU1()
            this.inventoryCount = ArrayList<Int?>()
            for (i in 0..250) {
                this.inventoryCount!!.add(this._io.readU1())
            }
        }

        fun _fetchInstances() {
            for (i in this.inventoryCount!!.indices) {
            }
            inventory()
            if (this.inventory != null) {
                for (i in this.inventory!!.indices) {
                    this.inventory!!.get(((i) as Number).toInt())!!._fetchInstances()
                }
            }
        }

        private var inventory: MutableList<InventoryItem?>? = null
        fun inventory(): MutableList<InventoryItem?>? {
            if (this.inventory != null) return this.inventory
            this.inventory = ArrayList<InventoryItem?>()
            for (i in 0..250) {
                this.inventory!!.add(InventoryItem(this._io, this, _root, i))
            }
            return this.inventory
        }

        private var initialStrength = 0
        private var initialMagic = 0
        private var initialHp = 0
        private var initialMp = 0
        private var initialGold: Long = 0
        private var initialExperience: Long = 0
        private var initialLevel = 0
        private var weapon = 0
        private var headArmor = 0
        private var chestArmor = 0
        private var handsArmor = 0
        private var feetArmor = 0
        private var shield = 0
        private var accessory = 0
        private var magic = 0
        private var inventoryCount: MutableList<Int?>? = null

        init {
            _read()
        }

        fun initialStrength(): Int {
            return initialStrength
        }

        fun initialMagic(): Int {
            return initialMagic
        }

        fun initialHp(): Int {
            return initialHp
        }

        fun initialMp(): Int {
            return initialMp
        }

        fun initialGold(): Long {
            return initialGold
        }

        fun initialExperience(): Long {
            return initialExperience
        }

        fun initialLevel(): Int {
            return initialLevel
        }

        fun weapon(): Int {
            return weapon
        }

        fun headArmor(): Int {
            return headArmor
        }

        fun chestArmor(): Int {
            return chestArmor
        }

        fun handsArmor(): Int {
            return handsArmor
        }

        fun feetArmor(): Int {
            return feetArmor
        }

        fun shield(): Int {
            return shield
        }

        fun accessory(): Int {
            return accessory
        }

        fun magic(): Int {
            return magic
        }

        fun inventoryCount(): MutableList<Int?> {
            return inventoryCount!!
        }

        fun _root(): Sys? {
            return _root
        }

        override fun _parent(): Sys? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): PlayerConfig {
                return PlayerConfig(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class PlayerSpeed @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: Sys? = null,
        private val _root: Sys? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.walk = this._io.readF4le()
            this.dash = this._io.readF4le()
            this.turnSpeed = this._io.readU2le()
        }

        fun _fetchInstances() {
        }

        private var walk = 0f
        private var dash = 0f
        private var turnSpeed = 0

        init {
            _read()
        }

        /**
         * Rounded to first decimal place in UI
         * Minimum: 0.1
         * Maximum: 10.0
         */
        fun walk(): Float {
            return walk
        }

        /**
         * Rounded to first decimal place in UI
         * Minimum: 0.1
         * Maximum: 10.0
         */
        fun dash(): Float {
            return dash
        }

        /**
         * Turn speed in degrees per second
         * Minimum: 1
         * Maximum: 360
         */
        fun turnSpeed(): Int {
            return turnSpeed
        }

        fun _root(): Sys? {
            return _root
        }

        override fun _parent(): Sys? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): PlayerSpeed {
                return PlayerSpeed(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    class SequenceSettings @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: Sys? = null,
        private val _root: Sys? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.titleSequenceMode = SequenceMode.byId(this._io.readU1().toLong())
            this.titleSequenceFilename = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(31), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.titleImage = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(31), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.openingSequenceMode = SequenceMode.byId(this._io.readU1().toLong())
            this.openingSequenceFilename = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(31), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.ending1SequenceMode = SequenceMode.byId(this._io.readU1().toLong())
            this.ending1SequenceFilename = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(31), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.ending2SequenceMode = SequenceMode.byId(this._io.readU1().toLong())
            this.ending2Filename = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(31), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.ending3Mode = SequenceMode.byId(this._io.readU1().toLong())
            this.ending3Filename = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(31), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.creditsMode = SequenceMode.byId(this._io.readU1().toLong())
            this.creditsFilename = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(31), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.creditsFinalImageFilename = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(31), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
        }

        fun _fetchInstances() {
        }

        private var titleSequenceMode: SequenceMode? = null
        private var titleSequenceFilename: String? = null
        private var titleImage: String? = null
        private var openingSequenceMode: SequenceMode? = null
        private var openingSequenceFilename: String? = null
        private var ending1SequenceMode: SequenceMode? = null
        private var ending1SequenceFilename: String? = null
        private var ending2SequenceMode: SequenceMode? = null
        private var ending2Filename: String? = null
        private var ending3Mode: SequenceMode? = null
        private var ending3Filename: String? = null
        private var creditsMode: SequenceMode? = null
        private var creditsFilename: String? = null
        private var creditsFinalImageFilename: String? = null

        init {
            _read()
        }

        fun titleSequenceMode(): SequenceMode? {
            return titleSequenceMode
        }

        fun titleSequenceFilename(): String? {
            return titleSequenceFilename
        }

        fun titleImage(): String? {
            return titleImage
        }

        fun openingSequenceMode(): SequenceMode? {
            return openingSequenceMode
        }

        fun openingSequenceFilename(): String? {
            return openingSequenceFilename
        }

        fun ending1SequenceMode(): SequenceMode? {
            return ending1SequenceMode
        }

        fun ending1SequenceFilename(): String? {
            return ending1SequenceFilename
        }

        fun ending2SequenceMode(): SequenceMode? {
            return ending2SequenceMode
        }

        fun ending2Filename(): String? {
            return ending2Filename
        }

        fun ending3Mode(): SequenceMode? {
            return ending3Mode
        }

        fun ending3Filename(): String? {
            return ending3Filename
        }

        fun creditsMode(): SequenceMode? {
            return creditsMode
        }

        fun creditsFilename(): String? {
            return creditsFilename
        }

        fun creditsFinalImageFilename(): String? {
            return creditsFinalImageFilename
        }

        fun _root(): Sys? {
            return _root
        }

        override fun _parent(): Sys? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): SequenceSettings {
                return SequenceSettings(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    /**
     * Defined in example project, but don't seem to be defined in new projects
     * for the english translation patch 1.2.
     * Provided example values and translation for each field.
     */
    class SystemMessages @JvmOverloads constructor(
        _io: KaitaiStream?,
        private val _parent: Sys? = null,
        private val _root: Sys? = null
    ) : KaitaiStruct(_io) {
        private fun _read() {
            this.saving = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.saveComplete = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.loading = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
            this.loadComplete = String(
                KaitaiStream.bytesTerminate(this._io.readBytes(41), 0.toByte(), false),
                Charset.forName("Shift_JIS")
            )
        }

        fun _fetchInstances() {
        }

        private var saving: String? = null
        private var saveComplete: String? = null
        private var loading: String? = null
        private var loadComplete: String? = null

        init {
            _read()
        }

        /**
         * セーブ中
         * Saving...
         */
        fun saving(): String? {
            return saving
        }

        /**
         * セーブ完了
         * Save Complete
         */
        fun saveComplete(): String? {
            return saveComplete
        }

        /**
         * ロード中
         * Loading...
         */
        fun loading(): String? {
            return loading
        }

        /**
         * ロード完了
         * Loading Complete
         */
        fun loadComplete(): String? {
            return loadComplete
        }

        fun _root(): Sys? {
            return _root
        }

        override fun _parent(): Sys? {
            return _parent
        }

        companion object {
            @Throws(IOException::class)
            fun fromFile(fileName: String?): SystemMessages {
                return SystemMessages(ByteBufferKaitaiStream(fileName))
            }
        }
    }

    private var sequenceSettings: SequenceSettings? = null
    private var dashEnabledFlag = 0
    private var playerSpeed: PlayerSpeed? = null
    private var levelingType: LevelingType? = null
    private var classData: ClassData? = null
    private var magicTable: MagicTable? = null
    private var menuConfiguration: MenuConfiguration? = null
    private var messages: Messages? = null
    private var systemMessages: SystemMessages? = null
    private var currencyUnit: String? = null
    private var playerConfig: PlayerConfig? = null
    private var playerConfigTestPlay: PlayerConfig? = null
    private var startingMap = 0
    private var counterNames: MutableList<String?>? = null
    private var unknown: MutableList<Int?>? = null
    private var sounds: MutableList<Int?>? = null
    private var menuBackgroundFilename: String? = null
    private var messagesAdditional: MessagesAdditional? = null
    private var menuSoundEffect = 0
    private var endingPadding: MutableList<Int?>? = null
    private val _root: Sys?

    init {
        this._root = if (_root == null) this else _root
        _read()
    }

    fun sequenceSettings(): SequenceSettings {
        return sequenceSettings!!
    }

    fun dashEnabledFlag(): Int {
        return dashEnabledFlag
    }

    fun playerSpeed(): PlayerSpeed {
        return playerSpeed!!
    }

    fun levelingType(): LevelingType? {
        return levelingType
    }

    fun classData(): ClassData {
        return classData!!
    }

    fun magicTable(): MagicTable {
        return magicTable!!
    }

    fun menuConfiguration(): MenuConfiguration {
        return menuConfiguration!!
    }

    fun messages(): Messages {
        return messages!!
    }

    fun systemMessages(): SystemMessages {
        return systemMessages!!
    }

    fun currencyUnit(): String? {
        return currencyUnit
    }

    fun playerConfig(): PlayerConfig {
        return playerConfig!!
    }

    fun playerConfigTestPlay(): PlayerConfig {
        return playerConfigTestPlay!!
    }

    fun startingMap(): Int {
        return startingMap
    }

    fun counterNames(): MutableList<String?> {
        return counterNames!!
    }

    fun unknown(): MutableList<Int?> {
        return unknown!!
    }

    /**
     * 0 padded filename of sound effect in Sword of Moonlight's se folder
     * (Sword of Moonlight\data\sound\se).
     * Max value 0xFFFF (65535) is NONE (no sound effect set).
     *
     * Example:
     * Sound 539 (0x21B) is 0539.snd (Wind sound effect)
     * @see [Source](https://doc.swordofmoonlight.com/editor/contentauthoring/auth-sounds/)
     *
     * @see [Source](https://doc.swordofmoonlight.com/editor/ff/snd-file-format/)
     */
    fun sounds(): MutableList<Int?> {
        return sounds!!
    }

    fun menuBackgroundFilename(): String? {
        return menuBackgroundFilename
    }

    fun messagesAdditional(): MessagesAdditional {
        return messagesAdditional!!
    }

    /**
     * Default menu sound effect.
     * 0 = NONE
     * 1-4 = Menu sound effect
     */
    fun menuSoundEffect(): Int {
        return menuSoundEffect
    }

    fun endingPadding(): MutableList<Int?> {
        return endingPadding!!
    }

    fun _root(): Sys? {
        return _root
    }

    override fun _parent(): KaitaiStruct? {
        return _parent
    }

    companion object {
        @Throws(IOException::class)
        fun fromFile(fileName: String?): Sys {
            return Sys(ByteBufferKaitaiStream(fileName))
        }
    }
}
