package br.com.professorisidro.racinggame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;

import br.com.professorisidro.racinggame.MyGame;
import br.com.professorisidro.racinggame.core.GameObject;

public class Pista extends AbstractModel{
	private GameObject modelPista;
	
	public Pista() {
		super(true, false);
		modelPista = new GameObject(
				MyGame.loader.loadModel(Gdx.files.internal("pista/pista.g3db")));
		for (Material m: modelPista.materials) {
			m.remove(ColorAttribute.Emissive);
		}
	}
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public GameObject getGameObject() {
		// TODO Auto-generated method stub
		return modelPista;
	}
	

}
