package no.kobler.kobly.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class UrlShortenerTest {
    @Test
    fun testUrlIsShortened() {
        val urlShortener = UrlShortener()
        val url = "http://this.is.a.long.url"
        assertThat(urlShortener.shorten(url).length).isLessThan(url.length)
    }

    @Test
    fun testShortenedIdCanBeUsedToRetrieveOriginalUrl() {
        val urlShortener = UrlShortener()
        val url = "http://this.is.a.long.url"
        val shortened = urlShortener.shorten(url)
        val result = urlShortener.findUrl(shortened)
        assertThat(result).isPresent
        assertThat(result.get()).isEqualTo(url)
    }

    @Test
    fun testSameUrlGivesDifferentShortenedLinks() {
        val urlShortener = UrlShortener()
        val url = "http://this.is.a.long.url"
        val shortened1 = urlShortener.shorten(url)
        val shortened2 = urlShortener.shorten(url)
        assertThat(urlShortener.findUrl(shortened1).get()).isEqualTo(url)
        assertThat(urlShortener.findUrl(shortened2).get()).isEqualTo(url)
        assertThat(shortened1).isNotEqualTo(shortened2)
    }
}
