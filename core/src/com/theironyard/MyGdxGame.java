package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion img;
	float x, y, xv, yv;
	TextureRegion down;
	TextureRegion up;
	TextureRegion right;
	TextureRegion left;
	float time;



	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final float MAX_VELOCITY = 100;
	static final float ACCELERATION = 2.00f;
	static final float DECELERATION = 0.95f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, WIDTH, HEIGHT);
		img = grid[6][0];
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);

	}

	@Override
	public void render () {
		move();
		time += Gdx.graphics.getDeltaTime();

		TextureRegion img;
		if (xv > 0) {
			img = right;
		}
		else if (xv < 0) {
			img = left;
		}
		else if (yv > 0) {
			img = up;
		}
		else if (yv < 0) {
			img = down;
		}
		else {
			img = down;
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y, WIDTH * 3, HEIGHT * 3);
		batch.end();
	}

	public void move() {
		int adjustment = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			adjustment = 100;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			yv = MAX_VELOCITY + adjustment;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = -MAX_VELOCITY - adjustment;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY + adjustment;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = -MAX_VELOCITY - adjustment;
		}

		float delta = Gdx.graphics.getDeltaTime();
		y += yv * delta;
		x += xv * delta;

		yv = decelerate(yv);
		xv = decelerate(xv);


	}

	public float decelerate(float velocity) {
		velocity =  velocity * DECELERATION;
		if(Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}

}
