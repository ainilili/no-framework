package org.nico.cat.mvc.util;

import java.lang.reflect.Method;
import java.util.Map;

import org.nico.cat.mvc.container.NomvcContainer;
import org.nico.util.placeparser.PlaceParserHelper;
import org.nico.util.string.StringUtils;

/**
 * Path attribute matching
 * 
 * @author nico
 * @version createTime：2018年1月17日 下午9:40:06
 */
public class PathValidMatchUtils {

	/**
	 * String parser
	 */
	public static PlaceParserHelper parser = new PlaceParserHelper("{", "}");
	
	/**
	 * Get mathes regx
	 * @param refer Reference string
	 * @return matches regx
	 */
	public static String getMatches(String refer){
		if(StringUtils.isNotBlank(refer)){
			return refer.replaceAll("\\{(.*?)}", "(.*)");
		}
		return refer;
	}
	
	/**
	 * Matches the referenced string.
	 * @param refer Reference string
	 * @param berefer Be reference string
	 * @return is match?
	 */
	public static boolean pathValidMatcher(String refer, String berefer){
		if(StringUtils.isNotBlank(refer) && StringUtils.isNotBlank(berefer)){
			return berefer.matches(getMatches(refer));
		}else{
			return false;
		}
	}

	/**
	 * Gets the matching property.
	 * @param refer Reference string
	 * @param berefer Be reference string
	 * @return The attributes that match.
	 */
	public static Map<String, Object> parsePathValid(String refer, String berefer){
		return parser.parser(refer, berefer);
	}
	
//  Test	
//	public static void main(String[] args) {
//		String refer = "/v1/rest/devices/140156464";
//		String berefer = "/v1/rest/devices/140156464";
//		if(PathValidMatchUtils.pathValidMatcher(refer, berefer)){
//			System.out.println("Path perfect match");
//			System.out.println("Path valid: " + PathValidMatchUtils.parsePathValid(refer, berefer));
//		}
//	}

}
