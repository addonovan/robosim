/*
 * Copyright (c) 2016 Austin D. Donovan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package addonovan.robosim.desktop;

import addonovan.robosim.Motor;
import addonovan.robosim.Simulation;

import javax.swing.*;
import java.awt.GridLayout;
import java.util.List;

/**
 * Displays the debug information for all the motors.
 *
 * @author addonovan
 * @since 11/21/2016
 */
public class MotorDebugForm
{

    //
    // Components
    //

    private JPanel rootPanel;

    //
    // Creation
    //

    private void createUIComponents()
    {
        rootPanel = new JPanel();

        // look: it's javascript!
        SwingUtilities.invokeLater( () ->
        {
            Simulation.running.attach( running ->
            {
                List< Motor > motorList = Simulation.robot.getMotors();

                rootPanel.removeAll();
                rootPanel.setLayout( new GridLayout( motorList.size(), 2 ) );

                for ( Motor motor : Simulation.robot.getMotors() )
                {
                    JLabel label = new JLabel( motor.name );
                    JLabel value = new JLabel( "" );

                    // attach a listener for power changes
                    motor.power.attach( power ->
                    {
                        value.setText( String.format( "%.3f%%", power * 100 ) );
                    } );

                    rootPanel.add( label );
                    rootPanel.add( value );
                }

            } );
        } );
    }
}
