package org.fpalacios.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.function.Consumer;

import org.fpalacios.engine.gobjects.GameObject;
import org.fpalacios.engine.gobjects.GraphicsObject;
import org.fpalacios.engine.gobjects.PhisicsObject;
import org.fpalacios.flibs.util.FlinkedList;

public final class Engine implements Runnable, KeyListener {

	private static Engine instance;

	public class Key {
		public char keyCode;
		public boolean pressed;

		public Key(char keyCode, boolean pressed) {
			this.keyCode = keyCode;
			this.pressed = pressed;
		}

		public String toString() {
			return "Key "+keyCode+": "+pressed;
		}
	}

	private GraphicsEngine graphicsEngine;
	private PhisicsEngine  phisicsEngine;

	private Object lock = new Object();
	private ArrayList<Key> keys = new ArrayList<>();

	private FlinkedList<GameObject> gobjects = new FlinkedList<>();

	public int width, height;

	private int fpsTarget = 60;
	private int apsTarget = 100;
	private boolean keepPlaying = true;

	private Thread thread = new Thread(this);

	public int fps = 0;
	public int aps = 0;

	private Engine() {}

	public void setup(int resW, int resH, Consumer<BufferedImage> paintFuction) {
		this.width = resW;
		this.height = resH;
		graphicsEngine = new GraphicsEngine(resW, resH, paintFuction);
		phisicsEngine  = PhisicsEngine.getInstance();
	}

	public void start() {
		thread.start();
	}

	private void pollInputs() {
		synchronized (lock) {
			for (GameObject gobject : gobjects)
				for (Key key : keys)
					gobject.pollinput(key);

			keys.clear();
		}
	}

	private void update(long delta) {
		int milidelta = (int) (delta / 1000000);
		phisicsEngine.update(delta);
		for (GameObject gobject : gobjects) {
			gobject.update(milidelta);
			gobject.update(delta);
		}
	}

	private void render() {
		graphicsEngine.render();
	}

	public void run() {
		long last = System.nanoTime();
		long now;
		long delta;
		long deltaFps = 0;
		long deltaAps = 0;
		long deltaSecond = 0;

		long fpsTimer = 1000000000 / fpsTarget ;
		long apsTimer = 1000000000 / apsTarget;
		long secondTimer = 1000000000;

		int aps = 0;
		int fps = 0;

		while(keepPlaying) {
			now = System.nanoTime();
			delta = now - last;
			last = now;

			deltaFps += delta;
			if (deltaFps >= fpsTimer) {
				render();
				deltaFps = 0;
				fps++;
			}

			deltaAps += delta;
			if (deltaAps >= apsTimer) {
				pollInputs();
				update(deltaAps);
				deltaAps = 0;
				aps++;
			}

			deltaSecond += delta;
			if (deltaSecond >= secondTimer) {
				this.fps = fps;
				this.aps = aps;
				System.out.println("FPS: "+fps);
				System.out.println("APS: "+aps);
				deltaSecond = 0;
				fps = 0;
				aps = 0;
			}
		}
	}

	public void addObject(Object object) {
		if (object instanceof GameObject)
			this.gobjects.add((GameObject) object);
		if (object instanceof GraphicsObject)
			graphicsEngine.add((GraphicsObject) object);
		if (object instanceof PhisicsObject)
			phisicsEngine.add((PhisicsObject)object);

	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		synchronized (lock) {
			keys.add( new Key(e.getKeyChar(), true) );
		}
	}

	public void keyReleased(KeyEvent e) {
		synchronized (lock) {
			keys.add( new Key(e.getKeyChar(), false) );
		}
	}

	public static Engine getInstance() {
		if (instance == null) instance = new Engine();
		return instance;
	}
}
