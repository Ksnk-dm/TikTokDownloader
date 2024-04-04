package com.ksnk.tiktokdownloader.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class FileInfo(
    @SerializedName("msg")
    val status: String?,
    @SerializedName("data")
    val data: FileData?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(FileData::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<FileInfo> {
        override fun createFromParcel(parcel: Parcel): FileInfo =
            FileInfo(parcel)

        override fun newArray(size: Int): Array<FileInfo?> =
            arrayOfNulls(size)
    }
}
