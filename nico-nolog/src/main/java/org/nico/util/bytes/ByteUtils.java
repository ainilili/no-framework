package org.nico.util.bytes;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.nico.util.string.StringUtils;

/** 
 * 
 * @author nico
 * @version createTime：2018年2月19日 下午12:18:22
 */

public class ByteUtils {

	/**
	 * Byte[] to hex
	 * 
	 * @param bytes Bytes arrays
	 * @return String
	 */
	public static String bytesToHexFun(byte[] bytes) {
		return bytesToHexFunWithSplit(bytes, "");
	}

	/**
	 * Byte[] to hex
	 * 
	 * @param bytes Bytes arrays
	 * @param split Split mark
	 * @return String
	 */
	public static String bytesToHexFunWithSplit(byte[] bytes, String split) {
		StringBuilder buf = new StringBuilder(bytes.length * 2);
		for(int index = 0; index < bytes.length; index ++) {
			if(index == bytes.length - 1){
				split = "";
			}
			byte b = bytes[index];
			buf.append(String.format("%02x", new Integer(b & 0xff)) + split);
		}
		return buf.toString();
	}

	/**
	 * Split byte array by sub bytes
	 * 
	 * @param mains be split byte array
	 * @param subs split byte array
	 * @return byte array list
	 */
	public static List<byte[]> split(byte[] mains, byte[] subs){
		return split(mains, subs, -1);
	}

	/**
	 * Split byte array by sub bytes with num, 
	 * But it's not suitable for large arrays. It's very slow.
	 * 
	 * @param mains be split byte array
	 * @param subs split byte array
	 * @param num split array size
	 * @return byte array list
	 */
	public static List<byte[]> split(byte[] mains, byte[] subs, int num){
		if(mains == null || mains.length == 0 || subs == null || subs.length == 0) return null;
		List<byte[]> resultList = new ArrayList<byte[]>();
		LinkedList<Byte> tempStorge = new LinkedList<Byte>();
		int cursor = 0;
		for(int index = 0; index < mains.length; index ++){
			System.out.println("---" + index);
			boolean c = true;
			if(num != -1 && num <= resultList.size() + 1 ){
				c = false;	
			}
			if(c){
				if(subs[0] != mains[index]){
					c = false;
				}else{
					for(cursor = 0; cursor < subs.length; cursor ++){
						if(index + cursor >= mains.length) break;
						if(subs[cursor] != mains[index + cursor]){
							c = false;
							while(cursor-- > 0){
								tempStorge.removeLast();
							}
							break;
						}
						tempStorge.add(mains[index + cursor]);
					}
				}
			}
			if(c){
				index += cursor - 1;
				while(cursor-- > 0){
					tempStorge.removeLast();
				}
				byte[] bytes = new byte[tempStorge.size()];
				int count = 0;
				for(Byte b: tempStorge){
					bytes[count ++] = b;
				}
				resultList.add(bytes);
				tempStorge.clear();
			}else{
				tempStorge.add(mains[index]);
			}
			if(index == mains.length - 1 && tempStorge.size() > 0){
				byte[] bytes = new byte[tempStorge.size()];
				int count = 0;
				for(Byte b: tempStorge){
					bytes[count ++] = b;
				}
				resultList.add(bytes);
			}
		}
		return resultList;
	}

	/**
	 * Splice arrays
	 * 
	 * @param a1 array1
	 * @param a2 array2
	 * @return result array
	 */
	public static byte[] splice(byte[] a1, byte[] a2){
		byte[] bs = new byte[a1.length + a2.length];
		for(int index = 0; index < bs.length; index ++){
			if(index < a1.length){
				bs[index] = a1[index];
			}else{
				bs[index] = a2[index - a1.length];
			}
		}
		return bs;
	}

	/**
	 * Split the str to byte arrays by blockSize
	 * 
	 * @param str	
	 * 			The target be split
	 * @param blockSize		
	 * 			Max block size
	 * @param charset
	 * 			The charset about byte array encoding
	 * @return 
	 * 			Byte array list
	 * @throws UnsupportedEncodingException 
	 */
	public static List<byte[]> block(String str, int blockSize, String charset) throws UnsupportedEncodingException{
		List<byte[]> bytes = null;
		if(StringUtils.isNotBlank(str) && blockSize > 0){
			bytes = new ArrayList<byte[]>();
			String temp = null;
			for(int index = 0; index < str.length(); index += blockSize){
				if(str.length() - index <= blockSize){
					temp = str.substring(index);
				}else{
					temp = str.substring(index, index + blockSize);
				}
				byte[] bs = temp.getBytes(charset);
				bytes.add(bs);
			}
		}
		return bytes;
	}

}
