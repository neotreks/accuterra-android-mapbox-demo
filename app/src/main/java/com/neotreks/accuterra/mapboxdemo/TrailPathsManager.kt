package com.neotreks.accuterra.mapboxdemo

import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.maps.MapView
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.neotreks.accuterra.mapboxdemo.TrailFeatureBuilder.Companion.DIFFICULTY_LEVEL_KEY
import com.neotreks.accuterra.mobile.sdk.ServiceFactory
import com.neotreks.accuterra.mobile.sdk.trail.model.TrailBasicInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TRAIL_PATH_SOURCE_ID = "trails"
private const val TRAIL_PATH_LAYER_ID = "trail-paths"

class TrailPathsManager(
    private val mapView: MapView,
) {
    private val context = mapView.context.applicationContext

    suspend fun addToMap() {
        val source = GeoJsonSource.Builder(TRAIL_PATH_SOURCE_ID)
            .featureCollection(
                FeatureCollection.fromFeatures(emptyList<Feature>()),
            )
            .build()


        val trailsLayer = LineLayer(TRAIL_PATH_LAYER_ID, TRAIL_PATH_SOURCE_ID)
            .lineColor(buildLineColorByDifficultyExpression())
            .lineWidth(2.0)

        val map = mapView.mapboxMap
        if (map.isValid()) {
            map.getStyle { style ->
                if (style.isValid()) {
                    style.addSource(source)
                    style.addLayer(trailsLayer)
                }
            }
        }
    }

    private suspend fun buildLineColorByDifficultyExpression() : Expression {
        val difficultyColors = listOf(
            Expression.rgba(255.0, 0.0, 255.0, 1.0),
            Expression.rgba(0.0, 0.0, 255.0, 1.0),
            Expression.rgba(255.0, 165.0, 0.0, 1.0),
            Expression.rgba(255.0, 0.0, 0.0, 1.0),
            Expression.rgba(0.0, 0.0, 0.0, 1.0),
        )

        val fallbackColor = Expression.rgba(100.0, 100.0, 100.0, 1.0)

        val difficultyColorMappings = ServiceFactory.getEnumService(context).getTechRatings()
            .sortedBy { rating -> rating.level }
            .flatMapIndexed { index, rating ->
                if (index <= difficultyColors.lastIndex) {
                    listOf(
                        Expression.literal(rating.code),
                        difficultyColors[index]
                    )
                } else {
                    emptyList()
                }
            }
            .toTypedArray()

        return Expression.match(
            Expression.get(DIFFICULTY_LEVEL_KEY),
            *difficultyColorMappings,
            fallbackColor
        )
    }

    suspend fun showTrailsFromFeatures(trails: List<TrailBasicInfo>) {
        val featureCollection = TrailFeatureBuilder(context)
            .buildFeatureCollection(trails)

        withContext(Dispatchers.Main) {
            val map = mapView.mapboxMap
            if (map.isValid()) {
                mapView.mapboxMap.getStyle { style ->
                    if (style.isValid()) {
                        style.getSourceAs<GeoJsonSource>(TRAIL_PATH_SOURCE_ID)
                            ?.featureCollection(featureCollection)
                    }
                }
            }

        }
    }

    fun removeFromMap() {
        val map = mapView.mapboxMap
        if (map.isValid()) {
            map.getStyle { style ->
                if (style.isValid()) {
                    style.removeStyleLayer(TRAIL_PATH_LAYER_ID)
                    style.removeStyleSource(TRAIL_PATH_SOURCE_ID)
                }
            }
        }
    }
}