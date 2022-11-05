package com.iitms.bfr.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "UserInfo")
class UserInfo(): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @SerializedName("user")
    var listToJson : String? = null

}
