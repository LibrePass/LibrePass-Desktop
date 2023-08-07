package dev.medzik.librepass.desktop.style

import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import kotlin.collections.ArrayList

object StyleManager {
    private lateinit var style: Style

    private var trackedScenes = ArrayList<Scene>()
    private var trackedStages = ArrayList<Stage>()

    fun init() {
        setStyle(Style.LIGHT)
    }

    fun setStyle(style: Style) {
        StyleManager.style = style
    }

    fun loadStyle(scene: Scene) {
        scene.stylesheets.clear()
        for (stylesheet in style.styles) scene.stylesheets.add(
            StyleManager::class.java.getResource(stylesheet)!!.toExternalForm()
        )
    }

    fun loadStyle(pane: Parent) {
        pane.stylesheets.clear()
        for (stylesheet in style.styles) pane.stylesheets.add(
            StyleManager::class.java.getResource(stylesheet)!!.toExternalForm()
        )
    }

    fun trackScene(scene: Scene) {
        trackedScenes.add(scene)
    }

    fun trackStage(stage: Stage) {
        trackedStages.add(stage)
    }

    fun reloadStyle() {
        for (scene in trackedScenes) loadStyle(scene)
        for (stage in trackedStages) style.invokeHook(stage)
    }
}
