package `in`.mandeep_singh.walkmethrough.walk_me_through.components

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children

/**
 * A base class for `ConstraintLayout` that supports saving and restoring
 * instance state for its child views.
 *
 * This class overrides methods to handle the state of child views, ensuring
 * that the state is preserved across configuration changes or other
 * instance state changes.
 *
 * @param context The context in which the view is running.
 * @param attributeSet Optional attribute set for custom XML attributes.
 * @param defStyleAttr Optional default style attributes.
 */
open class BaseConstraintLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    companion object {
        // Keys for saving and restoring instance state
        private const val SPARSE_STATE_KEY = "SPARSE_STATE_KEY"
        private const val SUPER_STATE_KEY = "SUPER_STATE_KEY"
    }

    /**
     * Saves the instance state of this view and its children.
     *
     * @param container The container to which the state should be saved.
     */
    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        dispatchFreezeSelfOnly(container)
    }

    /**
     * Restores the instance state of this view and its children.
     *
     * @param container The container from which the state should be restored.
     */
    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
        dispatchThawSelfOnly(container)
    }

    /**
     * Creates a `Parcelable` representation of the instance state of this view
     * and its children.
     *
     * @return A `Parcelable` object representing the instance state.
     */
    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            // Save the super state and child view states into the bundle
            putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState())
            putSparseParcelableArray(SPARSE_STATE_KEY, saveChildViewStates())
        }
    }

    /**
     * Saves the state of child views into a `SparseArray`.
     *
     * @return A `SparseArray` containing the state of child views.
     */
    private fun ViewGroup.saveChildViewStates(): SparseArray<Parcelable> {
        val childViewStates = SparseArray<Parcelable>()
        // Iterate over children and save their states
        children.forEach { child -> child.saveHierarchyState(childViewStates) }
        return childViewStates
    }

    /**
     * Restores the instance state from the provided `Parcelable` object.
     *
     * @param state The `Parcelable` object representing the saved state.
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        var stateCopy = state
        if (stateCopy is Bundle) {
            // Restore child view states and then the super state
            val childrenState = stateCopy.getSparseParcelableArray<Parcelable>(SPARSE_STATE_KEY)
            childrenState?.let { restoreChildViewStates(it) }
            stateCopy = stateCopy.getParcelable(SUPER_STATE_KEY)
        }
        super.onRestoreInstanceState(stateCopy)
    }

    /**
     * Restores the state of child views from the provided `SparseArray`.
     *
     * @param childViewStates The `SparseArray` containing the state of child views.
     */
    private fun ViewGroup.restoreChildViewStates(childViewStates: SparseArray<Parcelable>) {
        // Iterate over children and restore their states
        children.forEach { child -> child.restoreHierarchyState(childViewStates) }
    }
}
