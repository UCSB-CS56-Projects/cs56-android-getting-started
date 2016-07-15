package shane.opengles2.Triangle;

/**
 * Created by hannavigil on 7/15/16.
 */

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {
    private GLSurfaceView surface;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        surface = new GLSurfaceView(this);
        surface.setEGLContextClientVersion(2);
        surface.setRenderer(new Renderer());
        setContentView(surface);
    }

    public void onPause() {
        super.onPause();
        surface.onPause();
    }

    public void onResume() {
        super.onResume();
        surface.onResume();
    }
}
