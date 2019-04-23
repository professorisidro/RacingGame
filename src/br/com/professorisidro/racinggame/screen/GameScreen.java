package br.com.professorisidro.racinggame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import br.com.professorisidro.racinggame.MyGame;
import br.com.professorisidro.racinggame.core.ChasingCamera;
import br.com.professorisidro.racinggame.core.Commands;
import br.com.professorisidro.racinggame.model.Carro;
import br.com.professorisidro.racinggame.model.Pista;

public class GameScreen extends AbstractScreen {
	private Carro npc;
	private Carro carro;
	private Pista pista;
	private Environment environment;
	private ChasingCamera camera;
	private ModelBatch modelBatch;
	private Matrix4 viewMatrix;
	private Matrix4 tranMatrix;
	private SpriteBatch spriteBatch;
	private BitmapFont font;
	private Texture    fundo;
	private BitmapFont countdown;
	private float        countdownTimer;
	
	private Vector3 positon = new Vector3();

	public GameScreen(String id) {
		super(id);
		carro = new Carro(MyGame.player1Name);
		npc   = new Carro(MyGame.player2Name,true);
		
		carro.getGameObject().transform.translate(-92,0,-74);
		npc.getGameObject().transform.translate(-84, 0, -74);
		
		fundo = new Texture(Gdx.files.internal("pista/fundo.jpg"));
		pista = new Pista();
		countdownTimer = 5;
		viewMatrix = new Matrix4();
		tranMatrix = new Matrix4();
		spriteBatch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("fonts/digital_final.fnt"));
		countdown = new BitmapFont(Gdx.files.internal("fonts/digital_final.fnt"));
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.6f, 0.6f, 0.6f, 1));
		environment.add(new DirectionalLight().set(Color.WHITE, -1f, -0.8f, -0.2f));
		camera = new ChasingCamera(45.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 5, 15);
		camera.setObjectToFollow(carro.getGameObject());
		camera.near = 0.01f;
		camera.far = 400;
		camera.update();
		countdown.getData().scale(2);
		
		
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta) {
		
		if (Commands.set[Commands.ACCELERATE]) {
			carro.acelerar();
		}
		if (Commands.set[Commands.BREAK]) {
			carro.frear();
		}
		if (Commands.noMovment()) {
			carro.desacelerar();
		}
		if (Commands.set[Commands.ROTATE_R]) {
			carro.virarDireita();
		}
		if (Commands.set[Commands.ROTATE_L]) {
			carro.virarEsquerda();
		}
		if (Commands.noRotation()) {
			carro.naoVirar();
		}
		countdownTimer -= delta;
		if (countdownTimer <1) {
			carro.setEnabled(true);
			npc.setEnabled(true);
		}
		
		carro.update(delta);
		npc.update(delta);
		camera.update();
		
		// só pra saber o instante de tempo
		Commands.globalTime += delta;

	}

	@Override
	public void draw(float delta) {
		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(102f/255,204f/255,1,1);
		viewMatrix.setToOrtho2D(0, 0, 1024, 768);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.setTransformMatrix(tranMatrix);
		spriteBatch.begin();
		spriteBatch.draw(fundo, 0,0);
		spriteBatch.end();

		modelBatch.begin(camera);
		modelBatch.render(pista.getGameObject(), environment);
		modelBatch.render(carro.getGameObject(), environment);
		modelBatch.render(npc.getGameObject(), environment);
		modelBatch.end();
		camera.update();

		spriteBatch.begin();
		carro.getGameObject().transform.getTranslation(positon);
		//font.draw(spriteBatch, "POSITION: "+positon, 20, 700);
		if (countdownTimer >=0 ) {
			countdown.draw(spriteBatch," " +(int)countdownTimer , 480, 350);
		}
		font.draw(spriteBatch, "SPEED: " + carro.getCurrentSpeed(), 20, 600);
		spriteBatch.end();

	}

}
