package org.nico.cat.mvc.verify;

/**
 * Json field Verifier
 * 
 * @author nico
 * @time 2018-05-26 11:32
 */
public abstract class Verifier<T> {
	
	/**
	 * start verify
	 * 
	 * @param verifier 
	 * 			Type is annotation, must be decorate {@link NV}
	 * @param value
	 * 			Be verfiy value
	 * @return
	 * 			true / false
	 */
	abstract boolean verify(T verifier, Object value);
	
	/**
	 * Throw error
	 */
	abstract void throwError(T verifier);
}
