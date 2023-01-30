package com.magistor8.pixabaywallpapers.data.retrofit.entires

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ImagesData (

  @SerializedName("total"     ) var total     : Int?            = null,
  @SerializedName("totalHits" ) var totalHits : Int?            = null,
  @SerializedName("category"  ) var category  : String?         = null,
  @SerializedName("hits"      ) var images    : ArrayList<Image> = arrayListOf()

) : Parcelable