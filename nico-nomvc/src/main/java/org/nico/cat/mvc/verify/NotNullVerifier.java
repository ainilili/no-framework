package org.nico.cat.mvc.verify;

import org.nico.cat.mvc.scan.annotations.NotNull;
import org.nico.cat.mvc.exception.VerifyException;

public class NotNullVerifier extends Verifier<NotNull>{

	@Override
	public boolean verify(NotNull verifier, Object value) {
		if(value == null) {
			return false;
		}
		return true;
	}

	@Override
	void throwError(NotNull verifier) {
		throw new VerifyException(verifier.message());
	}

}
