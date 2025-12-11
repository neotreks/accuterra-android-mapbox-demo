package com.neotreks.accuterra.mapboxdemo

import android.content.Context
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.neotreks.accuterra.mobile.sdk.ServiceFactory
import com.neotreks.accuterra.mobile.sdk.trail.model.TrailBasicInfo
import com.neotreks.accuterra.mobile.sdk.trail.model.TrailPath
import com.neotreks.accuterra.mobile.sdk.trail.service.TrailLoadFilter

class TrailFeatureBuilder(
    context: Context
) {

    private val trailService = ServiceFactory.getTrailService(context)

    suspend fun buildFeatureCollection(
        trails: List<TrailBasicInfo>
    ) : FeatureCollection {
        val trailsById = trails.associateBy { trail -> trail.id }

        val filter = TrailLoadFilter(trailsById.keys)
        val trailsPaths = trailService.getTrailsPaths(filter)
        val trailsFeatures = trailsPaths.map { trailPath ->
            return@map parseTrailFeature(
                trailPath,
                trailsById.getValue(trailPath.trailId)
            )
        }

        return FeatureCollection.fromFeatures(trailsFeatures)
    }

    private fun parseTrailFeature(
        trailPath: TrailPath,
        trail: TrailBasicInfo
    ) : Feature {
        val feature = Feature.fromJson(trailPath.geojson)
            ?: throw IllegalStateException("Cannot parse trail Feature from GeoJson: ${trailPath.geojson}")
        feature.addStringProperty(DIFFICULTY_LEVEL_KEY, trail.techRatingHigh.code)
        return feature
    }

    companion object {
        const val DIFFICULTY_LEVEL_KEY = "difficulty-level"
    }
}