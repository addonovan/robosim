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

package addonovan.robosim;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * An object in the simulation that has physics.
 *
 * @author addonovan
 * @since 11/11/16
 */
public abstract class Entity implements Renderable
{

    //
    // Actions
    //

    public float getMass()
    {
        return getBody().getMass();
    }

    public float getX()
    {
        return getBody().getPosition().x;
    }

    public float getY()
    {
        return getBody().getPosition().y;
    }

    public float getAngle()
    {
        return getBody().getAngle();
    }

    //
    // Abstract
    //

    /**
     * @return The body used by this entity in the simulation.
     */
    abstract Body getBody();

}
