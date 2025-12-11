package com.neotreks.accuterra.mapboxdemo

import android.content.Context
import com.neotreks.accuterra.mobile.sdk.ServiceFactory
import com.neotreks.accuterra.mobile.sdk.model.QueryLimitBuilder
import com.neotreks.accuterra.mobile.sdk.trail.model.DistanceSearchCriteriaBuilder
import com.neotreks.accuterra.mobile.sdk.trail.model.MapLocation
import com.neotreks.accuterra.mobile.sdk.trail.model.TrailBasicInfo
import com.neotreks.accuterra.mobile.sdk.trail.model.TrailMapSearchCriteria

class SimpleTrailsSearch(
    context: Context
) {
    private val trailService = ServiceFactory.getTrailService(context.applicationContext)

    suspend fun findTrailsByCenter(
        latitude: Double,
        longitude: Double,
        distance: Int
    ): List<TrailBasicInfo> {
        val filter = TrailMapSearchCriteria(
            mapCenter = MapLocation(latitude, longitude),
            distanceRadius = DistanceSearchCriteriaBuilder.build(distance),
            limit = QueryLimitBuilder.build(1000)
        )

        return trailService.findTrails(filter)
    }
}