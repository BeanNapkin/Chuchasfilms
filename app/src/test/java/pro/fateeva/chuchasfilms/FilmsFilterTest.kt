package pro.fateeva.chuchasfilms

import android.content.Context
import com.nhaarman.mockito_kotlin.isA
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import pro.fateeva.chuchasfilms.data.FilmRepository
import pro.fateeva.chuchasfilms.data.PreferencesRepository
import pro.fateeva.chuchasfilms.ui.main.Film
import pro.fateeva.chuchasfilms.ui.main.GenreEnum

class FilmsFilterTest {
    private fun getTestList(): List<Film> = listOf(
        Film(0, "Bob", true, "2022", "8.0", listOf(GenreEnum.ACTION.value, GenreEnum.COMEDY.value)),
        Film(1, "Bob", false, "2022", "8.0", listOf(GenreEnum.DOCUMENTARY.value))
    )

    private val filmRepo: FilmRepository = mock()
    private val preferencesRepo: PreferencesRepository = mock()

    private val useCase = FilmsUseCase(filmRepo, preferencesRepo)

    @Before
    fun init() {
        whenever(preferencesRepo.getAdultFilmsPreference(isA())).thenReturn(true)
        whenever(filmRepo.getFilmsFromServer(isA())).thenReturn(getTestList())
    }

    @Test
    fun `Filter 18+ returns all films if isAdult true`() {
        assertEquals(2, useCase.getDataFromRemoteSource(MovieTopList.NOW_PLAYING, Mockito.mock(Context::class.java), GenreEnum.ALL).size)
    }

    @Test
    fun `Filter 18+ returns non-adult films if isAdult false`() {
        val expectedObj = getTestList()[1]
        val actual = useCase.getDataFromRemoteSource(MovieTopList.NOW_PLAYING, Mockito.mock(Context::class.java), GenreEnum.ALL)
        assertEquals(1, actual.size)
        assertEquals(expectedObj, actual.first())
    }

    @Test
    fun `Genre filter returns one documentary film`() {
        val actual = useCase.getDataFromRemoteSource(MovieTopList.NOW_PLAYING, Mockito.mock(Context::class.java),GenreEnum.DOCUMENTARY)
        assertEquals(1, actual.size)
        assertEquals(GenreEnum.DOCUMENTARY.value, actual.first().genres?.first())
    }
}