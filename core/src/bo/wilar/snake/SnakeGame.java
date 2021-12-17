package bo.wilar.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class SnakeGame extends ApplicationAdapter {

    ShapeRenderer shapeRenderer;
    Float xAxis;
    Float yAxis;

    @Override
    public void create() {

        shapeRenderer = new ShapeRenderer();
        xAxis = 200f;
        yAxis = 200f;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.5f, 0.5f, 0, 1);
        verifyKeys();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 1);
        shapeRenderer.rect(xAxis, yAxis, 100, 50);
        shapeRenderer.end();


    }


    @Override
    public void dispose() {

    }

    private void verifyKeys() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            yAxis += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yAxis += -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xAxis += -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xAxis += 1;
        }

    }
}
