package org.fpalacios.flibs.util;

@FunctionalInterface
public interface Valueator<T> {
	public double value(T obj);
}
