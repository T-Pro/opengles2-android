/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.aaron.helloopengles20

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

  private val mRenderer: MyGLRenderer

  private val TOUCH_SCALE_FACTOR = 180.0f / 320
  private var mPreviousX: Float = 0.toFloat()
  private var mPreviousY: Float = 0.toFloat()

  init {

    // Create an OpenGL ES 2.0 context.
    setEGLContextClientVersion(2)

    // Set the Renderer for drawing on the GLSurfaceView
    mRenderer = MyGLRenderer()
    setRenderer(mRenderer)

    // Render the view only when there is a change in the drawing data
    renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
  }

  override fun onTouchEvent(e: MotionEvent): Boolean {
    // MotionEvent reports input details from the touch screen
    // and other input controls. In this case, you are only
    // interested in events where the touch position changed.

    val x = e.x
    val y = e.y

    when (e.action) {
      MotionEvent.ACTION_MOVE -> {

        var dx = x - mPreviousX
        var dy = y - mPreviousY

        // reverse direction of rotation above the mid-line
        if (y > height / 2) {
          dx = dx * -1
        }

        // reverse direction of rotation to left of the mid-line
        if (x < width / 2) {
          dy = dy * -1
        }

        mRenderer.angle = mRenderer.angle + (dx + dy) * TOUCH_SCALE_FACTOR  // = 180.0f / 320
        requestRender()
      }
    }

    mPreviousX = x
    mPreviousY = y
    return true
  }

}
