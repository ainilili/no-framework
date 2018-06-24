package org.nico.cat.server.stream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/** 
 * Segment byte array storage.
 * 
 * @author nico
 * @version createTime：2018年2月25日 下午5:05:34
 */

public class ChunkedByteArrayInputStream extends InputStream{

	/**
	 * Byte array max length
	 */
	protected int bufMaxLength = 99999999;

	/**
	 * Stroge byte arrays
	 */
	protected List<byte[]> bufs;

	/**
	 * The index of the next character to read from the input stream buffer.
	 */
	protected long pos = 0l;

	/**
	 * The size of <code>[bufs]</code> total length
	 */
	protected long length = 0l;

	/**
	 * Mark
	 */
	protected long mark = 0l;

	/**
	 * Segement pos
	 */
	protected int segPos = 0;

	/**
	 * byte array pos 
	 */
	protected int bufPos = 0;

	/**
	 * Instance this object with some parameter who need be init
	 * 
	 * @param bufs	bufs
	 * @param bufMaxLength bufMaxLength
	 */
	public ChunkedByteArrayInputStream(List<byte[]> bufs, int bufMaxLength){
		if(bufMaxLength > 0){
			this.bufMaxLength = bufMaxLength;
		}
		this.bufs = bufs;
		if(bufs != null && bufs.size() > 0){
			for(byte[] bs: bufs){
				if(bs != null){
					length += bs.length;
				}
			}
		}
	}

	public ChunkedByteArrayInputStream(List<byte[]> bufs){
		this(bufs, -1);
	}

	@Override
	public synchronized int read() throws IOException {
		if(pos < length && segPos < bufs.size()){
			if(bufPos < bufs.get(segPos).length){
				pos ++;
				return bufs.get(segPos)[bufPos ++] & 0xFF;
			}else{
				bufPos = 0;
				if(segPos + 1 < bufs.size()){
					pos ++;
					return bufs.get(++ segPos)[bufPos ++] & 0xFF;
				}
			}
		}
		return -1;
	}

	@Override
	public synchronized int read(byte[] b, int off, int len) throws IOException {
		if (b == null) {
			throw new NullPointerException();
		} else if (off < 0 || len < 0 || len > b.length - off) {
			throw new IndexOutOfBoundsException();
		}
		int available = 0;
		int need = len - off;
		byte[] buf = bufs.get(segPos);
		while(need > 0 && pos <= length){
			off = available;
			len = need;
			if(buf != null){
				if(len > buf.length - bufPos){
					len = buf.length - bufPos;
				}
				System.arraycopy(buf, bufPos, b, off, len);
				int def = 0;
				if((def = buf.length - bufPos) >= need){
					available = need;
					bufPos += need;
					pos += need;
					break;
				}else{
					need -= def;
					bufPos = 0;
					available += def;
					pos += def;
					if(segPos +1 < bufs.size()){
						segPos ++;
					}else{
						buf = null;
					}
				}

			}else{
				break;
			}
		}
		return available == 0 ? -1 : available;
	}



	@Override
	public synchronized void reset() throws IOException {
		skip(mark);
	}

	public long bigAvailable(){
		return length - pos;
	}

	@Override
	public synchronized void mark(int readlimit) {
		mark = readlimit < length ? readlimit : length;
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}
	
	@Override
	public int available() throws IOException {
		return super.available();
	}

	@Override
	public long skip(long n) throws IOException {
		pos = n;
		segPos = 0;
		bufPos = 0;
		long temp = pos;
		if(bufs != null){
			for(int index = 0; index < bufs.size(); index ++){
				if(bufs.get(index) != null){
					int len = bufs.get(index).length;
					if(temp > len){
						segPos ++;
						temp -= len;
					}else{
						bufPos = (int) (temp > 0 ? temp - 1 : 0);
					}
				}
			}
		}
		return n;
	}

	@Override
	public void close() throws IOException {
		super.close();
	}

	public static void main(String[] args) throws IOException {
		List<byte[]> bufs = new ArrayList<byte[]>();
		byte[] bs1 = new byte[]{1,2,3,4,5,6,7,8};
		byte[] bs2 = new byte[]{1,2,3,4,5,6,7,8};
		byte[] bs3 = new byte[]{1,2,3,4,5,6,7,8};
		byte[] bs4 = new byte[]{1,2,3,4,5,6,7,8};
		bufs.add(bs1);
		bufs.add(bs2);
		bufs.add(bs3);
		bufs.add(bs4);
		InputStream is = new ChunkedByteArrayInputStream(bufs);
		
		
		System.out.println("read 测试");
		int i = -1;
		while((i = is.read()) != -1){
			System.out.print(i + " ");
		}
		System.out.println();
		
		
		System.out.println("read(byte[], int, int) 测试");
		is.reset();
		byte[] bytes = new byte[20];
		is.read(bytes, 0, bytes.length);
		for(byte b: bytes){
			System.out.print(b + " ");
		}
		System.out.println();
		
		
		System.out.println("mark 测试");
		is.mark(8);
		is.reset();
		bytes = new byte[20];
		is.read(bytes, 0, bytes.length);
		for(byte b: bytes){
			System.out.print(b + " ");
		}
		System.out.println();
		
		
		System.out.println("skip 测试");
		is.skip(2);
		bytes = new byte[20];
		is.read(bytes, 0, bytes.length);
		for(byte b: bytes){
			System.out.print(b + " ");
		}

	}



}
