package com.ksnk.tiktokdownloader.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class FileEntity(
    @SerializedName("msg")
    val status: String?,
    @SerializedName("data")
    val data: FileDataEntity?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(FileDataEntity::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<FileEntity> {
        override fun createFromParcel(parcel: Parcel): FileEntity =
            FileEntity(parcel)

        override fun newArray(size: Int): Array<FileEntity?> =
            arrayOfNulls(size)
    }
}
