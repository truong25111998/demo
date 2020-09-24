package com.t3h.firstserver.controller

import com.t3h.firstserver.service.SongService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SongApi {
    @Autowired
    private lateinit var service:SongService
    @GetMapping("/api/test")
    fun test(): Any {
        return "Ahihi"
    }

    @GetMapping("/api/getSong")
    fun getSong(
            @RequestParam("songName", required = false)
            songName: String?
    ): Any {
        return service.getSong(songName)
    }

    @GetMapping("/api/linkMusic")
    fun getLinkMusic(
            @RequestParam(value = "linkSong") linkSong: String?
    ): Any? {
        return service.getLinkMusic(linkSong)
    }
}