package addonovan.robosim.desktop;

import addonovan.robosim.RobotSimulator;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.lwjgl.opengl.Display;

/**
 * @author addonovan
 * @since 11/10/16
 */
public class DesktopLauncher
{

    public static void main( String[] args )
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = config.height = 350;
        config.x = config.y = 150;
        config.title = "Robot Simulator";
        RobotSimulator simulator = new RobotSimulator();
        new LwjglApplication( simulator, config );

        EditorFrame frame = new EditorFrame();
        frame.setLocation( Display.getX() + 380, Display.getY() + 25 );
        frame.setVisible( true );
    }

}
