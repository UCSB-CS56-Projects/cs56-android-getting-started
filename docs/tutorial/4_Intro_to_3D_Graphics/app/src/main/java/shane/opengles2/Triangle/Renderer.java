package shane.opengles2.Triangle;

/**
 * Created by hannavigil on 7/15/16.
 */

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderer implements GLSurfaceView.Renderer {

    private int vertexBufferObject;
    private int colorBufferObject;

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        int[] bufferIDs = new int [2];
        GLES20.glGenBuffers(2, bufferIDs, 0);
        vertexBufferObject = bufferIDs[0];
        colorBufferObject = bufferIDs[1];
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferObject);

        Shader.makeprogram();

        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        GLES20.glEnableVertexAttribArray(Shader.positionhandle);
        GLES20.glEnableVertexAttribArray(Shader.colorhandle);

        float[] verts = {
                -1.0f, -1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 0.0f, 1.0f,
                1.0f, -1.0f, 0.0f, 1.0f
        };

        float[] colors = {
                1.0f, 0.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f
        };

        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, 48, FloatBuffer.wrap(verts), GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, colorBufferObject);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, 48, FloatBuffer.wrap(colors), GLES20.GL_STATIC_DRAW);

    }

    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public void onDrawFrame(GL10 gl10) {
        gl10.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vertexBufferObject);
        GLES20.glVertexAttribPointer(Shader.positionhandle, 4, GLES20.GL_FLOAT, false, 0, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, colorBufferObject);
        GLES20.glVertexAttribPointer(Shader.colorhandle, 4, GLES20.GL_FLOAT, false, 0, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }
}
