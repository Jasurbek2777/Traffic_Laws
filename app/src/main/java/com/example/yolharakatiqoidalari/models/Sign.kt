package com.example.yolharakatiqoidalari.models

class Sign {
    var sign_id: Int? = null
    var sign_name: String? = null
    var sign_desc: String? = null
    var sign_type: String? = null
    var sign_image: ByteArray? = null
    var sign_likable: Int? = null

    constructor(
        sign_id: Int?,
        sign_name: String?,
        sign_desc: String?,
        sign_type: String?,
        sign_image: ByteArray?,
        sign_likeble: Int?
    ) {
        this.sign_id = sign_id
        this.sign_name = sign_name
        this.sign_desc = sign_desc
        this.sign_type = sign_type
        this.sign_image = sign_image
        this.sign_likable = sign_likeble
    }

    constructor()
    constructor(
        sign_name: String?,
        sign_desc: String?,
        sign_type: String?,
        sign_image: ByteArray?,
        sign_likeble: Int?
    ) {
        this.sign_name = sign_name
        this.sign_desc = sign_desc
        this.sign_type = sign_type
        this.sign_image = sign_image
        this.sign_likable = sign_likeble
    }
}