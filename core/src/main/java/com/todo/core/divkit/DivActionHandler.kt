package com.todo.core.divkit

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class DivActionHandler(
    private val actionController: ActionController,
) : DivActionHandler() {

    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver,
    ): Boolean {

        val url =
            action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        return if (handleSampleAction(url, view.view.context)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }
    }

    private fun handleSampleAction(action: Uri, context: Context): Boolean {
        return when (action.host) {
            "toast" -> {
                Toast.makeText(context, action.query, Toast.LENGTH_SHORT).show()
                true
            }

            "navigate_up" -> {
                actionController.action(DivKitActions.NavigateUp)
                true
            }

            else -> false
        }
    }
}
