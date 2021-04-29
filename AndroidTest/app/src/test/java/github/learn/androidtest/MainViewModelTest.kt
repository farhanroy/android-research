package github.learn.androidtest

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito.mock

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var mainModel: MainModel

    private val dummyLength = 12.0
    private val dummyWidth = 7.0
    private val dummyHeight = 6.0

    private val dummyVolume = 504.0

    @Before
    fun before() {
        mainModel = mock(MainModel::class.java)
        viewModel = MainViewModel(mainModel)
    }

    @Test
    fun getCircumference() {
    }

    @Test
    fun getSurfaceArea() {
    }

    @Test
    fun getVolume() {
        mainModel = MainModel()
        viewModel = MainViewModel(mainModel)
        viewModel.save(dummyWidth, dummyLength, dummyHeight)
        val volume = viewModel.getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }

    @Test
    fun save() {
    }
}