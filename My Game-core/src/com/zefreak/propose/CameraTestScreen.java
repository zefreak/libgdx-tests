package com.zefreak.propose;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CameraTestScreen extends ScreenAdapter{

	private static final String TAG = "CameraTestScreen";
	
	private final MyGame myGame;
	private static final float MIN_SCENE_WIDTH = 800.0f;
	private static final float MIN_SCENE_HEIGHT = 600.0f;
	private static final float MAX_SCENE_WIDTH = 1280.0f;
	private static final float MAX_SCENE_HEIGHT = 720.0f;
	
	private ArrayMap<String, Viewport> viewports;
	private int currentViewport;
	
	private Stage stage;
	private Texture background;
	
	public CameraTestScreen(MyGame game) {
		myGame = game;
		stage = new Stage();
		background = new Texture(Gdx.files.internal("splash.png"));
		createViewports();
		selectNextViewport();
		
		Gdx.input.setInputProcessor(stage);
		
		final Image backgroundImage = new Image(background);
    	backgroundImage.addListener(new ClickListener(){
    		@Override 
            public void clicked(InputEvent event, float x, float y){
                selectNextViewport();
            }
    	});
    	stage.addActor(backgroundImage);
	}
	
	private void createViewports() {
		viewports = new ArrayMap<String, Viewport>();
		viewports.put("StretchViewport", new StretchViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, stage.getCamera()));
		viewports.put("FitViewport", new FitViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, stage.getCamera()));
		viewports.put("FillViewport", new FillViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, stage.getCamera()));
		viewports.put("ScreenViewport", new ScreenViewport());
		viewports.put("ExtendViewport (no max)", new ExtendViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, stage.getCamera()));
		viewports.put("ExtendViewport (max)", new ExtendViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, MAX_SCENE_HEIGHT, MAX_SCENE_WIDTH, stage.getCamera()));
		
		currentViewport = -1;
	}
	
	@Override
    public void resize(int width, int height) {
    	stage.getViewport().update(width, height, true);
    	
    }
	
	private void selectNextViewport() {
		currentViewport = (currentViewport + 1) % viewports.size;
		stage.setViewport(viewports.getValueAt(currentViewport));
		Gdx.app.log(TAG, "selected " + viewports.getKeyAt(currentViewport));
	}
	
	@Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
	
	@Override
    public void dispose() {
		background.dispose();
        stage.dispose();
    }
}
