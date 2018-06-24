package org.nico.cat.mvc.verify;

import org.nico.cat.mvc.scan.annotations.Match;
import org.nico.cat.mvc.exception.VerifyException;

public class MatchVerifier extends Verifier<Match>{

	
	@Override
	public boolean verify(Match verifier, Object value) {
		if(value == null) {
			return false;
		}
		
		String regex = verifier.regex();
		
		if(regex == null) {
			return true;
		}
		
		String target = String.valueOf(value);
		
		if(! target.matches(regex)) {
			return false;
		}
		
		return true;
	}

	@Override
	void throwError(Match verifier) {
		throw new VerifyException(verifier.message());
	}

}
