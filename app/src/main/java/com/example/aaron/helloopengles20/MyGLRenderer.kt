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

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 *
 *  * [android.opengl.GLSurfaceView.Renderer.onSurfaceCreated]
 *  * [android.opengl.GLSurfaceView.Renderer.onDrawFrame]
 *  * [android.opengl.GLSurfaceView.Renderer.onSurfaceChanged]
 *
 */
class MyGLRenderer : GLSurfaceView.Renderer {
  private var mTriangle: Triangle? = null
  private var mSquare: Square? = null

  // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
  private val mMVPMatrix = FloatArray(16)
  private val mProjectionMatrix = FloatArray(16)
  private val mViewMatrix = FloatArray(16)
  private val mRotationMatrix = FloatArray(16)
  private val mTranslateMatrix = FloatArray(16)

  /**
   * Returns the rotation angle of the triangle shape (mTriangle).
   *
   * @return - A float representing the rotation angle.
   */
  /**
   * Sets the rotation angle of the triangle shape (mTriangle).
   */
  var angle: Float = 0.toFloat()

  override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {

    // Set the background frame color
    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

    mTriangle = Triangle()
    mSquare = Square()
  }

  override fun onDrawFrame(unused: GL10) {
    val scratch = FloatArray(16)
    val scratch2 = FloatArray(16)

    // Draw background color
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

    // Set the camera position (View matrix)
    Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)

    // Calculate the projection and view transformation
    Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)


    // Create a rotation for the triangle

    // Use the following code to generate constant rotation.
    // Leave this code out when using TouchEvents.
    // long time = SystemClock.uptimeMillis() % 4000L;
    // float angle = 0.090f * ((int) time);
//    Matrix.translateM(mTranslateMatrix, 0, 1f, 1f, 1f)

    Matrix.setRotateM(mTranslateMatrix, 0, angle, 0f, 0f, 1.0f)

    // Combine the rotation matrix with the projection and camera view
    // Note that the mMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
    Matrix.multiplyMM(scratch2, 0, mMVPMatrix, 0, mTranslateMatrix, 0)

    // Draw square
    mSquare!!.draw(scratch2)

    // Create a rotation for the triangle

    // Use the following code to generate constant rotation.
    // Leave this code out when using TouchEvents.
    // long time = SystemClock.uptimeMillis() % 4000L;
    // float angle = 0.090f * ((int) time);

    Matrix.setRotateM(mRotationMatrix, 0, angle, 0f, 0f, 1.0f)

    // Combine the rotation matrix with the projection and camera view
    // Note that the mMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
    Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0)

    // Draw triangle
    mTriangle!!.draw(scratch)
  }

  override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
    // Adjust the viewport based on geometry changes,
    // such as screen rotation
    GLES20.glViewport(0, 0, width, height)

    val ratio = width.toFloat() / height

    // this projection matrix is applied to object coordinates
    // in the onDrawFrame() method
    Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)

  }

  companion object {

    private val TAG = "MyGLRenderer"

    /**
     * Utility method for compiling a OpenGL shader.
     *
     *
     * **Note:** When developing shaders, use the checkGlError()
     * method to debug shader coding errors.
     *
     * @param type - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    fun loadShader(type: Int, shaderCode: String): Int {

      // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
      // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
      val shader = GLES20.glCreateShader(type)

      // add the source code to the shader and compile it
      GLES20.glShaderSource(shader, shaderCode)
      GLES20.glCompileShader(shader)

      return shader
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    fun checkGlError(glOperation: String) {
      var error: Int = 0
      while ({ error = GLES20.glGetError(); error }() != GLES20.GL_NO_ERROR) {
        Log.e(TAG, "$glOperation: glError $error")
        throw RuntimeException("$glOperation: glError $error")
      }
    }
  }

}