package com.simpure.expires.model

import com.google.gson.annotations.SerializedName

/**
 * @SerializedName to skip proguard models used by retrofit2
 * https://stackoverflow.com/questions/44473673/how-to-skip-proguard-models-used-by-retrofit2-that-is-on-the-base-package?source=post_page-----333a4422a890----------------------
 */
data class SignIn(
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("tick_type") val tick_type: Int,  //1 出席；2 不出席；3 代表出席
    @SerializedName("representative_name") val representative_name: String? = null
) {
    override fun toString(): String {
        return "name: $name, phone: $phone, tick_type: $tick_type, representative_name: $representative_name"
    }
}