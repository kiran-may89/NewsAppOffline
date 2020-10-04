package com.test.newsappoffline.models.news

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class Article(@PrimaryKey  val primaryKey: Int? = null, val author: String, val content: String, val description: String,
    val publishedAt: String, val source: Source, val title: String, val url: String, val urlToImage: String) : Parcelable {
    constructor(data: Parcel) : this(
        data.readInt(),
        data.readString()!!,
        data.readString()!!,
        data.readString()!!,
        data.readString()!!,
        data.readParcelable<Source>(Source::class.java.classLoader)!!,
        data.readString()!!,
        data.readString()!!,
        data.readString()!!
    )

    override fun describeContents(): Int = 0
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeInt(primaryKey?:0)
            it.writeString(author)
            it.writeString(content)
            it.writeString(description)
            it.writeString(publishedAt)
            it.writeParcelable(source, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
            it.writeString(title)
            it.writeString(url)
            it.writeString(urlToImage)


        }
    }


    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }


}

