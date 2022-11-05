package com.iitms.bfr.ui.listener

import com.iitms.bfr.data.model.Place

interface LocationListener {
    fun onLocationClick(location: Place)
    fun onDeleteLocation(location: Place)
    fun onEditLocation(location: Place)
}