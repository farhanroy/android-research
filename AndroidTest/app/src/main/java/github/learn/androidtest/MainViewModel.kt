package github.learn.androidtest

class MainViewModel(private val mainModel: MainModel) {

    fun getCircumference() = mainModel.getCircumference()

    fun getSurfaceArea() = mainModel.getSurfaceArea()

    fun getVolume() = mainModel.getVolume()

    fun save(w: Double, l: Double, h: Double) {
        mainModel.save(w, l, h)
    }
}