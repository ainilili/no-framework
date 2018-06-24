package org.nico.cat.mvc.util;

import org.nico.cat.mvc.container.entity.LobbyEntity;
import org.nico.util.string.StringUtils;

public class UriUtils {

	/**
	 * Tidy uri format
	 * <br>"/" -> "/"
	 * <br>"get" -> "/get"
	 * <br>"get/" -> "/get"
	 * <br>"/get/" 	-> "/get"
	 * 
	 * @param uri uri
	 * @return after tidy uri
	 */
	public static String tidyUri(String uri){
		if(StringUtils.isNotBlank(uri)){
			if(! uri.startsWith("/")){
				uri = "/" + uri;
			}
			if(uri.endsWith("/")){
				uri = uri.substring(0, uri.length() - 1);
			}
		}else{
			return "";
		}
		return uri;
	}
	
	public static String parseUri(String uri){
		System.out.println(uri);
		if(uri == null){
			throw new NullPointerException();
		}
		if(uri.contains("?")){
			return uri.substring(0, uri.indexOf("?"));
		}else{
			return uri;
		}
	}
	
	
	public static String assemblyUri(LobbyEntity rootLobby, LobbyEntity nextLobby){
		String uri = "";
		if(rootLobby != null){
			if(rootLobby.getLobby() != null){
				uri = UriUtils.tidyUri(rootLobby.getLobby().mapping());
			}
		}
		if(nextLobby != null){
			if(nextLobby.getLobby() != null){
				uri += UriUtils.tidyUri(nextLobby.getLobby().mapping());
			}
		}
		return uri.equals("") ? "/" : uri;
	}
}
