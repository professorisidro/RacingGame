package br.com.professorisidro.racinggame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
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
import br.com.professorisidro.racinggame.model.Carro;

public class SelectCarScreen extends AbstractScreen {
	private Environment environment;
	private PerspectiveCamera camera;
	private ModelBatch modelBatch;
	private Matrix4 viewMatrix;
	private Matrix4 tranMatrix;
	private SpriteBatch spriteBatch;
	private BitmapFont font;
	private Texture    fundo;
	private Carro[]    carros;
	int pos=0;
	private String names[] = {"Mclaren","Lotus","Renault","RedBull"};
	
	public SelectCarScreen(String id) {
		super(id);
		// TODO Auto-generated constructor stubviewMatrix = new Matrix4();
		tranMatrix = new Matrix4();
		viewMatrix = new Matrix4();
		spriteBatch = new SpriteBatch();
		fundo = new Texture(Gdx.files.internal("pista/fundo.jpg"));
		font = new BitmapFont(Gdx.files.internal("fonts/digital_final.fnt"));
    	modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.6f, 0.6f, 0.6f, 1));
		environment.add(new DirectionalLight().set(Color.WHITE, -1f, -0.8f, -0.2f));
		camera = new PerspectiveCamera(45.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0,10,15);
		camera.lookAt(0, 0, 0);
		camera.near = 0.01f;
		camera.far = 400;
		camera.update();
		carros = new Carro[4];
		carros[0]  = new Carro("Mclaren",true);
		carros[1]  = new Carro("Lotus",true);
		carros[2]  = new Carro("Renault",true);
		carros[3]  = new Carro("RedBull",true);
		System.out.println("Cars loaded");
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			pos--;
			if (pos<0) pos=3;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			pos = (pos+1)%4;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			MyGame.player1Name=names[pos];
			MyGame.player2Name=names[((int)(Math.random()*10000))%4];
			setDone(true);
		}
		for (Carro c: carros) {
			c.getGameObject().transform.rotate(Vector3.Y, 90*delta);
		}
	}

	@Override
	public void draw(float delta) {
		
		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
		//Gdx.gl.glClearColor(102f/255,204f/255,1,1);
		Gdx.gl.glClearColor(0,0,0,0);
		viewMatrix.setToOrtho2D(0, 0, 1024, 768);
		spriteBatch.setProjectionMatrix(viewMatrix);
		spriteBatch.setTransformMatrix(tranMatrix);
		

		modelBatch.begin(camera);
		  modelBatch.render(carros[pos].getGameObject(),environment);
		modelBatch.end();
		camera.update();

		spriteBatch.begin();
		font.draw(spriteBatch, "Select your car", 450,750);
		font.draw(spriteBatch, names[pos], 450,700);
		spriteBatch.end();

		
	}

}
