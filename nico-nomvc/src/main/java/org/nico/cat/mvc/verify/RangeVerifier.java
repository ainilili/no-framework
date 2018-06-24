package org.nico.cat.mvc.verify;

import org.nico.cat.mvc.scan.annotations.Range;
import org.nico.cat.mvc.exception.VerifyException;

public class RangeVerifier extends Verifier<Range>{

	@Override
	public boolean verify(Range verifier, Object value) {
		if(value == null) {
			return false;
		}
		if(! ( value instanceof Number )) {
			return false;
		}
		Number target = (Number) value;
		
		double[] range = verifier.range();
		
		if(range == null || range.length == 0) {
			return true;
		}
		
		if(range.length == 1) {
			if(target.doubleValue() < range[0]) {
				return false;
			}
		}
		
		if(range.length == 2) {
			if(target.doubleValue() < range[0] || target.doubleValue() > range[1]) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	void throwError(Range verifier) {
		throw new VerifyException(verifier.message());
	}

}
