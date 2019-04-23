package br.com.professorisidro.racinggame.model;

import java.io.FileInputStream;
import java.io.FileReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import br.com.professorisidro.racinggame.MyGame;
import br.com.professorisidro.racinggame.core.GameObject;

public class Carro extends AbstractModel {

	private boolean autonomous = false;
	private Array<Actions> actions;
	private GameObject modelCarro;
	private static final int SLIDE = 0;
	private static final int FRONT = 1;
	private static final int BREAK = 3;
	private static final int ROTATER = 1;
	private static final int ROTATEL = -1;
	private static final int NOROTATE = 0;
	private float currentSpeed = 0.0f;
	private float aceleracao = 1.5f;
	private float atrito = 1.5f;
	private float freio = 2.5f;
	private float maxSpeed = 4.0f;
	private float minSpeed = 0.0f;
	private float angle = 45.0f;
	private int mode;
	private int direction;
	private float time = 0.0f;
	private boolean enabled = false;

	public Carro(String name) {
		super(true, true);
		modelCarro = new GameObject(MyGame.loader.loadModel(Gdx.files.internal("cars/"+name+"/"+name+".g3db")));
		for (Material m : modelCarro.materials) {
			m.remove(ColorAttribute.Emissive);
		}
		
	}

	public Carro(String name, boolean autonomous) {
		this(name);
		this.autonomous = autonomous;
		this.actions = new Array<Actions>();
		String str = Gdx.files.internal("cars/actions.txt").readString();
		String[] lines = str.split("\n");
		for (String s : lines) {
			String act[] = s.split(";");
			actions.add(new Actions(Float.parseFloat(act[0]), Integer.parseInt(act[1].trim())));
		}
	}

	public String getCurrentSpeed() {
		return (int) (currentSpeed * 100) + ".0";
	}

	public void doAction(float delta) {
		
		time += delta;
		if (actions.size > 0) {
			Actions ac = actions.first();

			if (time >= ac.getTime()) {

				switch (ac.getAction()) {
				case 1:
					acelerar();
					break;
				case 2:
					frear();
					break;
				case 3:
					virarEsquerda();
					break;
				case 4:
					virarDireita();
					break;
				case -1:
				case -2:
					desacelerar();
					break;
				case -3:
				case -4:
					naoVirar();
					break;
				}
				actions.removeIndex(0);
			}
		} else {
			desacelerar();
		}

	}

	@Override
	public void update(float delta) {

		if (enabled) {
			if (autonomous)
				doAction(delta);
			// TODO Auto-generated method stub
			if (mode == FRONT) {
				currentSpeed += aceleracao * delta;
				if (currentSpeed >= maxSpeed) {
					currentSpeed = maxSpeed;
				}

				this.getGameObject().transform.translate(0, 0, -currentSpeed);
			}
			if (mode == SLIDE) {
				currentSpeed -= atrito * delta;
				if (currentSpeed <= minSpeed) {
					currentSpeed = minSpeed;
				}
				this.getGameObject().transform.translate(0, 0, -currentSpeed);
			}

			if (mode == BREAK) {
				currentSpeed -= freio * delta;
				if (currentSpeed <= 0) {
					currentSpeed = 0;
				}
				this.getGameObject().transform.translate(0, 0, -currentSpeed);
			}

			if (direction == ROTATER) {
				if (currentSpeed > 0) {
					this.getGameObject().setAngle(-angle * delta);
					this.getGameObject().transform.rotate(Vector3.Y, -angle * delta);
				}
			}
			if (direction == ROTATEL) {
				if (currentSpeed > 0) {
					this.getGameObject().setAngle(angle * delta);
					this.getGameObject().transform.rotate(Vector3.Y, angle * delta);
				}
			}
		}

	}

	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		//time = 0;
	}

	@Override
	public GameObject getGameObject() {
		// TODO Auto-generated method stub
		return modelCarro;
	}

	public void acelerar() {
		mode = FRONT;
	}

	public void frear() {
		mode = BREAK;
	}

	public void desacelerar() {
		mode = SLIDE;
	}

	public void virarDireita() {
		direction = ROTATER;
	}

	public void virarEsquerda() {
		direction = ROTATEL;
	}

	public void naoVirar() {
		direction = NOROTATE;
	}

	
}
