package shane.opengles2.Triangle;

import android.opengl.GLES20;
import android.util.Log;

public class Shader {

    private final static String vertcode =
            "attribute vec4 a_pos;" +
            "attribute vec4 a_color;" +
            "varying vec4 v_color;" +
            "void main() {" +
            "gl_Position = a_pos;" +
            "v_color = a_color;" +
            "}";

    private final static String fragcode =
            "varying lowp vec4 v_color;" +
            "void main() {" +
            "gl_FragColor = v_color;" +
            "}";

    public static int program;
    public static int positionhandle;
    public static int colorhandle;

    public static void makeprogram() {
        int vertexshader = loadshader(GLES20.GL_VERTEX_SHADER, vertcode);
        int fragmentshader = loadshader(GLES20.GL_FRAGMENT_SHADER, fragcode);

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexshader);
        GLES20.glAttachShader(program, fragmentshader);
        GLES20.glLinkProgram(program);
        Log.e("same2", GLES20.glGetProgramInfoLog(program));

        positionhandle = GLES20.glGetAttribLocation(program, "a_pos");
        colorhandle = GLES20.glGetAttribLocation(program, "a_color");

        GLES20.glUseProgram(program);
    }

    private static int loadshader(int type, String shadertext) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shadertext);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
