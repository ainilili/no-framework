package org.nico.cat.mvc.verify;

import org.nico.cat.mvc.scan.annotations.Length;
import org.nico.cat.mvc.exception.VerifyException;

public class LengthVerifier extends Verifier<Length>{

	@Override
	public boolean verify(Length verifier, Object value) {
		if(value == null) {
			return false;
		}
		
		int length = String.valueOf(value).length();
		
		int[] range = verifier.range();
		
		if(range == null || range.length == 0) {
			return true;
		}
		
		if(range.length == 1) {
			if(length < range[0]) {
				return false;
			}
		}
		
		if(range.length == 2) {
			if(length < range[0] || length > range[1]) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	void throwError(Length verifier) {
		throw new VerifyException(verifier.message());
	}

}
