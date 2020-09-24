package com.t3h.firstserver.service

import com.t3h.firstserver.model.GetLinkMusic
import com.t3h.firstserver.model.ItemMusicOnline
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.*


@Service
class SongServiceImpl :SongService{
    override fun getSong(name: String?): Any {
        if (name == null){
            return getListFirstSong()
        }
        var songName = name
        val onlines: MutableList<ItemMusicOnline> = ArrayList()
        try {
            val c: Document = Jsoup.connect(("https://chiasenhac.vn/tim-kiem?q="
                    + songName.replace(" ", "+")) +
                    "&page_music=" + "1" + "&filter=all").get()
            val els: Elements = c.select("div.tab-content").first().select("ul.list-unstyled")
            for (i in 0..els.size - 1) {
                val e: Element = els.get(i)
                val childEls: Elements = e.select("li.media")
                for (child in childEls) {
                    try {
                        val linkSong: String = child.select("a").first().attr("href")
                        val linkImg: String = child.select("a").first().select("img").attr("src")
                        val title: String = child.select("a").first().attr("title")
                        val singer: String = child.select("div.author").text()
                        onlines.add(ItemMusicOnline(linkSong, linkImg, title, singer, linkSong))
                    } catch (e1: Exception) {
                        e1.printStackTrace()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        return onlines
    }

    private fun getListFirstSong(): Any {
        val onlines: MutableList<ItemMusicOnline> = ArrayList()
        try {
            val doc = Jsoup.connect("https://chiasenhac.vn/bang-xep-hang/tuan.html").get()
            val els = doc.select("div.tab-content").select("ul.list-unstyled")
            val childEls = els.select("li.media")
            for (child in childEls) {
                val linkSong = "https://chiasenhac.vn" + child.select("a").first().attr("href")
                val linkImage = child.select("a").first().select("img").attr("src")
                val nameSong = child.select("a").first().attr("title")
                val nameSinger = child.select("div.author").text()
                onlines.add(ItemMusicOnline(linkSong, linkImage, nameSong, nameSinger, linkSong))
            }
        } catch (e: IOException) {
        }
        return onlines
    }

    override fun getLinkMusic(linkSong: String?): Any {
        try {
            val c = Jsoup.connect(linkSong).get()
            val els = c.select("div.tab-content").first().select("a.download_item")
            return if (els.size >= 2) {
                GetLinkMusic(els[1].attr("href"))
            } else {
                GetLinkMusic(els[0].attr("href"))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return  GetLinkMusic(null)
    }
}