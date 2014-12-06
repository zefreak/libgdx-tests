package com.zefreak.propose;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen extends ScreenAdapter {
	
	final private MyGame game;
    private Skin skin;
    private Stage stage;

    public MainMenuScreen(MyGame myGame) {     
    	game = myGame;
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
    	stage.getViewport().update(width, height, true);
    	final TextButton startButton = new TextButton("Start Game", skin, "default");
        startButton.setWidth(200f);
        startButton.setHeight(20f);
        startButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 + 10f);
        
        startButton.addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        		game.setScreen(new MainMenuScreen(game));
        	}
        });
        
        final TextButton button = new TextButton("Click me", skin, "toggle");
        
        button.setWidth(200f);
        button.setHeight(20f);
        button.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 10f);
        
        button.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                button.setText("You clicked the button");
            }
        });
        
        Table table = new Table();
        table.columnDefaults(0).width(200);
        table.setFillParent(true);
        table.add(startButton);
        table.row();
        table.add(button);
        
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

}
