package org.nico.cat.server.stream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Provides support for byte stream operations.
 * 
 * @author nico
 * @date 2018年2月24日
 */
public class ByteBuffer extends InputStream{
	
	/**
	 * New Line
	 */
	public static final byte LF = 10;
	
	/**
	 * Return
	 */
	public static final byte CR = 13;
	
	/**
	 * Pos
	 */
	private int pos;
	
	/**
	 * Wrapper inputStream
	 */
	private BufferedInputStream inputBuffer;
	
	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public ByteBuffer(InputStream inputStream){
		this.inputBuffer = new BufferedInputStream(inputStream);
	}

	@Override
	public int available() throws IOException {
		return inputBuffer.available();
	}

	@Override
	public void close() throws IOException {
		inputBuffer.close();
	}

	@Override
	public synchronized void mark(int readlimit) {
		inputBuffer.mark(readlimit);
	}

	@Override
	public boolean markSupported() {
		return inputBuffer.markSupported();
	}

	@Override
	public int read() throws IOException {
		return inputBuffer.read();
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return inputBuffer.read(b, off, len);
	}

	@Override
	public int read(byte[] b) throws IOException {
		return inputBuffer.read(b);
	}

	@Override
	public synchronized void reset() throws IOException {
		inputBuffer.reset();
	}

	@Override
	public long skip(long n) throws IOException {
		return inputBuffer.skip(n);
	}
	
	/**
	 * Read break when read a LF from The stream, and then, return a byte array
	 * of what are you read~
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] readLine() throws IOException{
		ByteLink bl = new ByteLink();
		int c = -1;
		while((c = read()) != -1){
			pos ++;
			bl.add((byte)c);
			if(c == LF){
				break;
			}
		}
		return bl.toByteArray();
	}
	
	/**
	 * Byte byte chain to save memory.
	 * 
	 * @author nico
	 */
	public class ByteLink{
		
		private ByteNode start;
		
		private ByteNode current;
		
		private int size;
		
		public void add(byte b){
			if(start == null){
				start = new ByteNode(b);
				current = start;
			}else{
				ByteNode node = new ByteNode(b);
				current.setNext(node);
				current = node;
			}
			size ++;
		}
		
		public byte[] toByteArray(){
			if(size == 0){
				return null;
			}
			byte[] bytes = new byte[size];
			int index = 0;
			ByteNode s = start.clone();
			while(s != null){
				bytes[index ++] = s.getB();
				s = s.getNext();
			}
			return bytes;
		}
		
	}
	
	/**
	 * Storge byte
	 * 
	 * @author nico
	 */
	public class ByteNode{
		
		private byte b;
		
		private ByteNode next;
		
		public ByteNode(byte b) {
			this.b = b;
		}
		
		public ByteNode(byte b, ByteNode next) {
			this.b = b;
			this.next = next;
		}
		
		protected ByteNode clone(){
			return new ByteNode(b, next);
		}

		public byte getB() {
			return b;
		}

		public void setB(byte b) {
			this.b = b;
		}

		public ByteNode getNext() {
			return next;
		}

		public void setNext(ByteNode next) {
			this.next = next;
		}
		
	}
	
}
