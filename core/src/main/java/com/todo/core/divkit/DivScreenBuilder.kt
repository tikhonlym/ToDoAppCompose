package com.todo.core.divkit

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.todo.core.R
import com.todo.core.util.AssetReader
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.picasso.PicassoDivImageLoader


class DivScreenBuilder(
    private val activity: Activity,
    private val lifecycleOwner: LifecycleOwner,
    private val actionController: ActionController,
) {

    private val assetReader = AssetReader(activity)

    fun provideScreenByJsonName(screenName: String): View {
        val screenJson = assetReader.read(screenName)

        val templatesJson = screenJson.optJSONObject(TEMPLATES)
        val cardJson = screenJson.getJSONObject(CARD)

        val divContext = Div2Context(
            baseContext = activity,
            configuration = createDivConfiguration(),
            lifecycleOwner = lifecycleOwner
        )

        val inflater: LayoutInflater = activity.layoutInflater
        val baseContainer: ViewGroup =
            inflater.inflate(R.layout.layout_div_kit_base, null) as ViewGroup
        baseContainer.addView(Div2ViewFactory(divContext, templatesJson).createView(cardJson))

        return baseContainer
    }

    private fun createDivConfiguration(): DivConfiguration {
        return DivConfiguration.Builder(PicassoDivImageLoader(activity.baseContext))
            .actionHandler(DivActionHandler(actionController))
            .visualErrorsEnabled(true)
            .build()
    }

    companion object {
        private const val TEMPLATES = "templates"
        private const val CARD = "card"
    }
}