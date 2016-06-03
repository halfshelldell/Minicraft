package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion img;
	float x, y, xv, yv;
	TextureRegion down, up, right, left;
	float time;
	boolean faceDown = true, faceUp, faceRight, faceLeft;
	Animation walk, walkUp, walkDown;

	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final float MAX_VELOCITY = 100;
	static final float DECELERATION = .05f;

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
		walk = new Animation(0.2f, grid[6][2], grid[6][3]);
		TextureRegion up2 = new TextureRegion(up);
		up2.flip(true, false);
		walkUp = new Animation(0.2f, up, up2);
		TextureRegion down2 = new TextureRegion(down);
		down2.flip(true, false);
		walkDown = new Animation(0.2f, down, down2);
	}

	@Override
	public void render () {
		move();
		time += Gdx.graphics.getDeltaTime();


		TextureRegion img;

		Gdx.gl.glClearColor(60/255.0f, 181/255.0f, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (faceUp) {
			if (yv != 0) {
				img = walkUp.getKeyFrame(time, true);
			}
			else {
				img = up;
			}
		}
		else if (faceDown) {
			if (yv != 0) {
				img = walkDown.getKeyFrame(time, true);
			}
			else {
				img = down;
			}
		}
		else if (faceRight) {
			if (xv != 0) {
				img = walk.getKeyFrame(time, true);
			}
			else {
				img = right;
			}
		}
		else if (faceLeft) {
			if (xv != 0) {
				img = walk.getKeyFrame(time, true);
				img = new TextureRegion(img);
				img.flip(true, false);
			}
			else {
				img = left;
			}
		}
		else {
			img = down;
		}
		batch.draw(img, x, y, WIDTH * 3, HEIGHT * 3);
		batch.end();
	}

	public void move() {
		int adjustment = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			adjustment = 600;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			faceUp();
			yv = MAX_VELOCITY + adjustment;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			faceDown();
			yv = -MAX_VELOCITY - adjustment;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			faceRight();
			xv = MAX_VELOCITY + adjustment;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			faceLeft();
			xv = -MAX_VELOCITY - adjustment;
		}

		if (x > Gdx.graphics.getWidth()) {
			x = 0;
		}
		if (x < 0) {
			x = Gdx.graphics.getWidth();
		}
		if (y > Gdx.graphics.getHeight()) {
			y = 0;
		}
		if (y < 0) {
			y = Gdx.graphics.getHeight();
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

	public void faceUp() {
		faceUp = true;
		faceDown = false;
		faceRight = false;
		faceLeft = false;
	}
	public void faceDown() {
		faceUp = false;
		faceDown = true;
		faceRight = false;
		faceLeft = false;
	}
	public void faceRight() {
		faceUp = false;
		faceDown = false;
		faceRight = true;
		faceLeft = false;
	}
	public void faceLeft() {
		faceUp = false;
		faceDown = false;
		faceRight = false;
		faceLeft = true;
	}

}
