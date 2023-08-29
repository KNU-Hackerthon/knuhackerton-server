package dev.jombi.knuserver.util

import kotlin.math.*

object GEOCoding {

    fun measureDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthDistance = 6378.137
        val dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180
        val dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180
        val a = sin(dLat / 2) * sin(dLat / 2) + cos(lat1 * Math.PI / 180) * cos(lat2 * Math.PI / 180) * sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val d = earthDistance * c
        return d * 1000.0
    }
}