package com.zefreak.propose;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen extends ScreenAdapter {

	final private MyGame game;
    private Stage stage;
    private Texture splashTexture;

    public SplashScreen(MyGame myGame) {     
    	game = myGame;
        stage = new Stage();
        splashTexture = new Texture( "splash.png" );
        splashTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );

        Gdx.input.setInputProcessor(stage);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    
    @Override
    public void resize(
        int width,
        int height )
    {
        super.resize( width, height );

        // let's make sure the stage is clear
        stage.clear();

        // in the image atlas, our splash image begins at (0,0) of the
        // upper-left corner and has a dimension of 512x301
        TextureRegion splashRegion = new TextureRegion(splashTexture);

        // here we create the splash image actor and set its size
        Image splashImage = new Image( splashRegion );
        splashImage.setWidth(width);
        splashImage.setHeight(height);
        splashImage.setColor(splashImage.getColor().r, splashImage.getColor().g, splashImage.getColor().b, 0f);
        splashImage.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenuScreen(game));
            }
        });
        splashImage.addAction(sequence(fadeIn(2), delay(2), fadeOut(2), run(new Runnable() {
            public void run () {
            	game.setScreen(new MainMenuScreen(game));
            }
        })));
        stage.addActor( splashImage );
    }

    @Override
    public void dispose() {
        stage.dispose();
        splashTexture.dispose();
    }
}
