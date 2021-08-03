package com.example.yolharakatiqoidalari.db

import com.example.yolharakatiqoidalari.models.Sign

interface MyDbService {
    fun addSign(sign: Sign)
    fun editSign(sign: Sign)
    fun deleteSign(sign: Sign)
    fun getAll(): ArrayList<Sign>
    fun getLikedSigns(): ArrayList<Sign>
    fun getbyId(id:Int): Sign
}