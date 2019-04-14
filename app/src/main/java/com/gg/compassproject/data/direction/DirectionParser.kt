package com.gg.compassproject.data.direction

import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.location.Location

class DirectionParser {
    var sensorEvent: SensorEvent? = null
    var location: Location? = null
    var destination: Location? = null

    fun getDirection(): DirectionModel {
        if (sensorEvent == null && location == null) return DirectionModel()
        val northDirectionRotation = 360f - rotationToNorth()
        if (destination == null || location == null) return DirectionModel(northPoleDirectionRotation = northDirectionRotation)

        var rotationToTarget = calculateDestinationBearing() + northDirectionRotation
        if (rotationToTarget < 0) rotationToTarget = 360.0 - Math.abs(rotationToTarget)
        else if (rotationToTarget > 360) rotationToTarget -= 360.0

        return DirectionModel(
            northPoleDirectionRotation = northDirectionRotation,
            selectedPointDirectionRotation = rotationToTarget
        )
    }

    fun calculateDestinationBearing(): Double {
        var bearing = location!!.bearingTo(destination).toDouble()
        if (bearing < 0) bearing = 360.0 - Math.abs(bearing)
        return bearing
    }

    private fun rotationToNorth(): Double {
        val azimuth = getAzimuth()
        return azimuthToDegrees(azimuth).toDouble()
    }

    private fun getAzimuth(): Float {
        val rotationMatrix = FloatArray(9)
        SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent?.values)
        val orientationValues = FloatArray(3)
        SensorManager.getOrientation(rotationMatrix, orientationValues)
        return orientationValues[0]
    }

    private fun azimuthToDegrees(azimuth: Float): Float {
        return ((Math.toDegrees(azimuth.toDouble()) + 360) % 360).toFloat()
    }
}