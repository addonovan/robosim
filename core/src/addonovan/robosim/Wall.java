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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.sun.istack.internal.NotNull;

/**
 * Wall.
 *
 * @author addonovan
 * @since 11/13/16
 */
public class Wall extends Entity
{

    //
    // Fields
    //

    /** The body of this object. */
    @NotNull private Body body;

    private final float width;

    private final float height;

    //
    // Constructors
    //

    /**
     * Creates a new wall.
     *
     * @param width
     *          The width of the wall [in].
     * @param height
     *          The height of the wall [in].
     * @param x
     *          The x position of the wall [in].
     * @param y
     *          The y position of the wall [in].
     */
    public Wall( float width, float height, float x, float y )
    {
        this.width = Units.inToPx( width );
        this.height = Units.inToPx( height );
        body = makeBody();

        body.setTransform( Units.inToM( x + width / 2 ), Units.inToM( y + height / 2 ), body.getAngle() );
    }

    //
    // Body
    //

    /**
     * @return The newly created body for this wall.
     */
    private Body makeBody()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Body body = Simulation.getWorld().createBody( bodyDef );

        PolygonShape shape = new PolygonShape();
        shape.setAsBox( Units.pxToM( width / 2 ), Units.pxToM( height / 2 ) );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 1.5f;
        fixtureDef.restitution = 0.0f;

        body.createFixture( fixtureDef );
        shape.dispose();

        return body;
    }

    @Override
    Body getBody()
    {
        return body;
    }

    //
    // Rendering
    //

    @Override
    public void render()
    {
        Simulation.renderShape( ShapeRenderer.ShapeType.Filled, sr ->
        {
            sr.setColor( Color.DARK_GRAY );

            float x = Units.mToPx( body.getPosition().x ) - width / 2;
            float y = Units.mToPx( body.getPosition().y ) - height / 2;

            sr.rect( x, y, width / 2f, height / 2f, width, height, 1, 1, body.getAngle() );

        } );
    }

}
