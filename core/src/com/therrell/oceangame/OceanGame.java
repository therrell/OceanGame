package com.therrell.oceangame;
import com.badlogic.gdx.Game;
import com.therrell.oceangame.com.therrell.oceangames.screens.Menu;


public class OceanGame extends Game {

	public void create() {
		this.setScreen(new Menu(this));
	}



/*	//sprites and textures
	SpriteBatch batch;
	Texture fishPic, crabPic, sHorsePic, ocean;
	Sprite fish, crab, seahorse;
	//camera
	OrthographicCamera cam;
	//variables
	float x1 = 250, y1 = 25, x2 = 250, y2 = 150, x3 = 250, y3 = 275;
	int dl = 0, numP = 3;
	ArrayList<Sprite> alive;
	boolean first = true;
	
	@Override
	public void create () {

		//assign sprites
		batch = new SpriteBatch();
		fishPic = new Texture("fish.png");
		crabPic = new Texture("crab.png");
		sHorsePic = new Texture("seahorse.png");
		ocean = new Texture("ocean.jpg");

		fish = new Sprite(fishPic);
		crab = new Sprite(crabPic);
		seahorse = new Sprite(sHorsePic);

		fish.setSize(75, 50);
		crab.setSize(75, 50);
		seahorse.setSize(75, 50);

		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1000, 800);

		alive = new ArrayList<Sprite>();

	}

	@Override
	public void render () {
	}
	*/
}
