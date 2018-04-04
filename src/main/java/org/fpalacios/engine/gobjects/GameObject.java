package org.fpalacios.engine.gobjects;

import org.fpalacios.engine.Engine.Key;

public interface GameObject {
	public void update(long delta);
	public void update(int delta);
	public void pollinput(Key e);
}
