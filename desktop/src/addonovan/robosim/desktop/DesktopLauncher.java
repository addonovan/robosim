package addonovan.robosim.desktop;

import addonovan.robosim.RobotSimulator;
import addonovan.robosim.Units;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.lwjgl.opengl.Display;

import javax.swing.*;

/**
 * This launches the desktop application.
 *
 * @author addonovan
 * @since 11/10/16
 */
public class DesktopLauncher
{

    public static void main( String[] args )
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = config.height = ( int ) Units.inToPx( 160f );

        config.x = config.y = 150;
        config.title = "Robot Simulator";
        RobotSimulator simulator = new RobotSimulator();
        new LwjglApplication( simulator, config );

        EditorFrame frame = new EditorFrame();
        frame.setSize( config.width * 3 / 2, config.height - 25 );
        frame.setLocation( Display.getX() + config.width + 25, Display.getY() + 25 );
    }

}
