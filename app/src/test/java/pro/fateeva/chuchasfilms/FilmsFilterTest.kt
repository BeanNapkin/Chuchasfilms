package pro.fateeva.chuchasfilms

import junit.framework.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test
import pro.fateeva.chuchasfilms.ui.main.Film
import pro.fateeva.chuchasfilms.ui.main.GenreEnum

class FilmsFilterTest {
    private fun getTestList(): List<Film> = listOf(
        Film(0, "Bob", true, "2022", "8.0", listOf(GenreEnum.ACTION.value, GenreEnum.COMEDY.value)),
        Film(1, "Bob", false, "2022", "8.0", listOf(GenreEnum.DOCUMENTARY.value))
    )

    @Test
    fun `Filter 18+ returns all films if isAdult true`() {
        assertEquals(2, FilmsUseCase.filterOnIsAdult(getTestList(), includeAdult = true).size)
    }

    @Test
    fun `Filter 18+ returns non-adult films if isAdult false`() {
        val actual = FilmsUseCase.filterOnIsAdult(getTestList(), includeAdult = false)
        assertEquals(1, actual.size)
        assertSame(getTestList()[1], actual.first())
    }

    @Test
    fun `Genre filter returns one documentary film`() {
        val actual = FilmsUseCase.filterByGenre(getTestList(),GenreEnum.DOCUMENTARY)
        assertEquals(1, actual.size)
        assertEquals(GenreEnum.DOCUMENTARY.value, actual.first().genres?.first())
    }
}