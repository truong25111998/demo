package com.t3h.firstserver.service

import org.springframework.stereotype.Service


interface SongService {
    fun getSong(name:String?):Any
    fun getLinkMusic(name:String?):Any
}