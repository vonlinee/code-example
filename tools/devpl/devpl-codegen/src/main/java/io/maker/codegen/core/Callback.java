package io.maker.codegen.core;

@FunctionalInterface
public interface Callback<P, R> {
	/**
	 * The <code>call</code> method is called when required, and is given a single
	 * argument of type P, with a requirement that an object of type R is returned.
	 * 
	 * @param param The single argument upon which the returned value should be
	 *              determined.
	 * @return An object of type R that may be determined based on the provided
	 *         parameter value.
	 */
	R call(P param);
}
