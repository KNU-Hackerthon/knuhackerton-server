@file:Suppress("unused")

package dev.jombi.knuserver

object DisabilityFacility {
    const val RAMP_SAVED = 1 shl 0
    const val ELEVATOR_SAVED = 1 shl 1
    const val HANDICAPPED_PARKING_LOT_SAVED = 1 shl 2
    const val HANDICAPPED_TOILET_SAVED = 1 shl 3
    const val BRAILLE_BLOCK_SAVED = 1 shl 4
    const val BRAILLE_MAP_SAVED = 1 shl 5

    fun has(facility: Int, has: Int): Boolean {
        return facility and has != 0
    }

    fun add(facility: Int, has: Int): Int {
        return facility or has
    }

    fun remove(facility: Int, has: Int): Int {
        return facility and has.inv()
    }
}