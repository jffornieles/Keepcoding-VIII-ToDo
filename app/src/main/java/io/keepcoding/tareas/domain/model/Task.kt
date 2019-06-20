package io.keepcoding.tareas.domain.model

import android.os.Parcel
import android.os.Parcelable
import org.threeten.bp.Instant
import java.util.*

data class Task(
    val id: Long,
    val content: String,
    val createdAt: Instant,
    val isHighPriority: Boolean,
    val isFinished: Boolean
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        Instant.ofEpochMilli(parcel.readLong()),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(content)
        parcel.writeLong(createdAt.toEpochMilli())
        parcel.writeByte(if (isHighPriority) 1 else 0)
        parcel.writeByte(if (isFinished) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }


}