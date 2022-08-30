package com.example.clothesstore.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class SwipeToDeleteHelper(
    context: Context?,
    private val recyclerView: RecyclerView
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    companion object {
        const val BUTTON_WIDTH = 200
    }

    interface UnderlayButtonClickListener {
        fun onClick(pos: Int)
    }

    private var buttons: MutableList<UnderlayButton>? = null
    private val buttonsBuffer: MutableMap<Int, MutableList<UnderlayButton>?>
    private var gestureDetector: GestureDetector? = null
    private var swipedPos = -1
    private var swipeThreshold = 0.5f
    private var recoverQueue: Queue<Int>? = null
    private val gestureListener: GestureDetector.SimpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            buttons?.let {
                for (button in it) {
                    if (button.onClick(e.x, e.y)) break
                }
            }
            return true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private val onTouchListener = OnTouchListener { view, e ->
        gestureDetector?.onTouchEvent(e)
        false
    }

    init {
        buttons = ArrayList()
        gestureDetector = GestureDetector(context, gestureListener)
        recyclerView.setOnTouchListener(onTouchListener)
        buttonsBuffer = HashMap()
        recoverQueue = object : LinkedList<Int>() {
            override fun add(o: Int): Boolean {
                return if (contains(o)) false else super.add(o)
            }
        }
        attachSwipe()
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        if (swipedPos != pos) recoverQueue?.add(swipedPos)
        swipedPos = pos
        if (buttonsBuffer.containsKey(swipedPos))
            buttons = buttonsBuffer[swipedPos] else buttons!!.clear()
        buttonsBuffer.clear()
        swipeThreshold =
            0.5f * buttons!!.size * BUTTON_WIDTH
        recoverSwipedItem()
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return swipeThreshold
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f * defaultValue
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 5.0f * defaultValue
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView
        if (pos < 0 || pos > recyclerView.adapter!!.itemCount.minus(1)) {
            swipedPos = pos
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                // 'buffer' variable contains a list of UnderlayButton, which is set in WishlistFragment, with the help
                // of abstract method instantiateUnderlayButton()
                var buffer: MutableList<UnderlayButton>? =
                    ArrayList()
                if (!buttonsBuffer.containsKey(pos)) {
                    instantiateUnderlayButton(viewHolder, buffer)
                    buttonsBuffer[pos] = buffer
                } else {
                    //OnChildDraw() gets called the 2nd time immediately, when its fully swiped open,
                    // in this case buffer variable is set by buttonsBuffer map using position of the swiped element.
                    // This 'buffer' is then used for drawing the button
                    buffer = buttonsBuffer[pos]
                }
                translationX =
                    dX * buffer!!.size * BUTTON_WIDTH / itemView.width
                drawButtons(c, itemView, buffer, pos, translationX)
            }
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            translationX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    @Synchronized
    private fun recoverSwipedItem() {
        while (recoverQueue?.isEmpty()?.not() == true) {
            val pos = recoverQueue?.poll()
            pos?.let {
                if (it > -1) {
                    recyclerView.adapter?.notifyItemChanged(it)
                }
            }
        }
    }

    private fun drawButtons(
        c: Canvas,
        itemView: View,
        buffer: List<UnderlayButton>?,
        pos: Int,
        dX: Float
    ) {
        // 'right' variable holds the value of the right most edge of the screen,
        // this value is used, so that the underlying button can be drawn from this point onwards.
        var right = itemView.right.toFloat()
        val dButtonWidth = -1 * dX / buffer!!.size
        for (button in buffer) {
            //Similar to 'right' variable, but to get the left most area until which the underlying button has to be rendered
            val left = right - dButtonWidth
            button.onDraw(
                c,
                //Area of the underlying button
                RectF(
                    left,
                    itemView.top.toFloat(),
                    right,
                    itemView.bottom.toFloat()
                ),
                pos
            )
            //The value of 'left' variable is assigned to 'right' variable, because if there is another underlying button,
            //eg: apart from delete button, say if there was edit button, the rendering of edit buttons right edge will be done
            // to the immediate left of delete button.
            right = left
        }
    }

    private fun attachSwipe() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    abstract fun instantiateUnderlayButton(
        viewHolder: RecyclerView.ViewHolder?,
        underlayButtons: MutableList<UnderlayButton>?
    )

    class UnderlayButton(
        private val context: Context,
        private val imageResId: Int,
        private val color: Int,
        private val clickListener: UnderlayButtonClickListener
    ) {
        private var pos = 0
        private var clickRegion: RectF? = null

        fun onClick(x: Float, y: Float): Boolean {
            if (clickRegion != null && clickRegion!!.contains(x, y)) {
                clickListener.onClick(pos)
                return true
            }
            return false
        }

        fun onDraw(
            c: Canvas,
            rect: RectF,
            pos: Int
        ) {
            val p = Paint()
            p.color = color
            c.drawRect(rect, p)
            drawText("Remove", c, rect, p)
            clickRegion = rect
            this.pos = pos
        }

        private fun drawText(text: String, canvas: Canvas, rect: RectF, paint: Paint) {
            var textSize = 60F
            paint.apply {
                color = Color.WHITE
                isAntiAlias = true
                textSize = textSize
            }
            val textWidth = paint.measureText(text)
            canvas.drawText(text, rect.centerX() - (textWidth/2), rect.centerY()+(textSize/2), paint)
        }
    }

}