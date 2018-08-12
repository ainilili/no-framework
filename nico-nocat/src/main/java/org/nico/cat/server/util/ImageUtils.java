package org.nico.cat.server.util;

import java.util.HashSet;
import java.util.Set;

/** 
 * 
 * @author nico
 */

public class ImageUtils {

	public static Set<String> imageSuffixSet = new HashSet<String>(){
		private static final long serialVersionUID = 6602568251164836619L;
		{
			add("Webp");
			add("BMP");
			add("PCX");
			add("TIFF");
			add("GIF");
			add("JPEG");
			add("JPG");
			add("JPGX");
			add("TGA");
			add("EXIF");
			add("FPX");
			add("SVG");
			add("PSD");
			add("CDR");
			add("PCD");
			add("DXF");
			add("UFO");
			add("EPS");
			add("AI");
			add("PNG");
			add("HDRI");
			add("RAW");
			add("WMF");
			add("FLIC");
			add("EMF");
			add("ICO");
		}
	};
	
	public static boolean isImage(String suffix){
		return imageSuffixSet.contains(suffix.toUpperCase().intern());
	}
}
