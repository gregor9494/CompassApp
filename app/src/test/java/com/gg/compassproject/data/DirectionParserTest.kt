package com.gg.compassproject.data

import com.gg.compassproject.AndroidTest
import com.gg.compassproject.data.direction.DirectionParser
import com.gg.compassproject.platform.location.LocationCreator
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Created by ggaworowski on 13.04.2019.
 */
class DirectionParserTest : AndroidTest() {
    lateinit var directionParser: DirectionParser

    @Before
    fun setup() {
        directionParser = DirectionParser()
    }

    @Test
    fun `test if target direction rotation is correct`() {
        directionParser.location = LocationCreator.create(49.616198, 21.535804)
        directionParser.destination = LocationCreator.create(49.7438066, 21.470801)

        val correctBearingRange = 340.0..342.0
        val resultBearing = directionParser.calculateDestinationBearing()

        assertTrue { resultBearing in correctBearingRange }
    }

}