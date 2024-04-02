package com.ksnk.tiktokdownloader.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class FileDataEntity(
    val id: String?,
    val title: String?,
    val cover: String?,
    @SerializedName("play")
    val playUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(cover)
        parcel.writeString(playUrl)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<FileDataEntity> {
        override fun createFromParcel(parcel: Parcel): FileDataEntity =
            FileDataEntity(parcel)

        override fun newArray(size: Int): Array<FileDataEntity?> =
            arrayOfNulls(size)
    }
}
