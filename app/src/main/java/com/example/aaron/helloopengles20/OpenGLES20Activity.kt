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

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle

class OpenGLES20Activity : Activity() {

  private var mGLView: GLSurfaceView? = null

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Create a GLSurfaceView instance and set it
    // as the ContentView for this Activity
    mGLView = MyGLSurfaceView(this)
    setContentView(mGLView)
  }

  override fun onPause() {
    super.onPause()
    // The following call pauses the rendering thread.
    // If your OpenGL application is memory intensive,
    // you should consider de-allocating objects that
    // consume significant memory here.
    mGLView!!.onPause()
  }

  override fun onResume() {
    super.onResume()
    // The following call resumes a paused rendering thread.
    // If you de-allocated graphic objects for onPause()
    // this is a good place to re-allocate them.
    mGLView!!.onResume()
  }
}