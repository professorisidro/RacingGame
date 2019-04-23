package br.com.professorisidro.racinggame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.assets.loaders.ModelLoader.ModelParameters;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.UBJsonReader;

import br.com.professorisidro.racinggame.core.Commands;
import br.com.professorisidro.racinggame.screen.AbstractScreen;
import br.com.professorisidro.racinggame.screen.GameScreen;
import br.com.professorisidro.racinggame.screen.SelectCarScreen;

public class MyGame extends Game implements InputProcessor {
	private AbstractScreen currentScreen;
	public static ModelBuilder modelBuilder = new ModelBuilder();
	public static ModelLoader<ModelParameters> loader;
	
	public static String player1Name;
	public static String player2Name;
	
	
	@Override
	public void create () {
//		modelBuilder = new ModelBuilder();
		Gdx.input.setInputProcessor(this);
		loader = new G3dModelLoader(new UBJsonReader());
		currentScreen = new SelectCarScreen("SELECT");
		setScreen(currentScreen);
	}

	@Override
	public void render () {
		currentScreen.render(Gdx.graphics.getDeltaTime());
		if (currentScreen.isDone()) {
			if (currentScreen.getId().equals("SELECT")) {
				currentScreen = new GameScreen("GAME");
			}
		}
	}
	
	@Override
	public void dispose () {
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Keys.UP) {
			Commands.set[Commands.ACCELERATE] = true;
			Commands.log("1");
			return true;
		}
		if (keycode == Keys.DOWN) {
			Commands.set[Commands.BREAK] = true;
			Commands.log("2");
			return true;
		}
		if (keycode == Keys.LEFT) {
			Commands.set[Commands.ROTATE_L] = true;
			Commands.log("3");
			return true;
		}
		if (keycode == Keys.RIGHT) {
			Commands.set[Commands.ROTATE_R] = true;
			Commands.log("4");
			return true;
		}
		if (keycode == Keys.SPACE) {
			Commands.set[Commands.ZOOM] = !Commands.set[Commands.ZOOM];
			return true;
		}
			
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Keys.UP) {
			Commands.set[Commands.ACCELERATE] = false;
			Commands.log("-1");
			return true;
		}
		if (keycode == Keys.DOWN) {
			Commands.set[Commands.BREAK] = false;
			Commands.log("-2");
			return true;
		}
		if (keycode == Keys.LEFT) {
			Commands.set[Commands.ROTATE_L] = false;
			Commands.log("-3");
			return true;
		}
		if (keycode == Keys.RIGHT) {
			Commands.set[Commands.ROTATE_R] = false;
			Commands.log("-4");
			return true;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		if (character == 's') {
			Commands.startLog();
			Commands.log("START");
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
