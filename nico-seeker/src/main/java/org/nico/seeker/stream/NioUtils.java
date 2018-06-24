package org.nico.seeker.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class NioUtils {
	
	@SuppressWarnings("resource")
	public static String readFileToString(File beRead) throws IOException{
		StringBuffer 	cache 	= new StringBuffer();
		FileInputStream fis	 	= new FileInputStream(beRead);
		FileChannel 	fc 		= fis.getChannel();
		ByteBuffer 		bb 		= ByteBuffer.allocate(1024);
		CharBuffer charBuffer = null;  
		Charset charset = Charset.forName("UTF-8");  
		while (true) {  
            // clear方法重设缓冲区，使它可以接受读入的数据  
            bb.clear();  
            // 从输入通道中将数据读到缓冲区  
            int r = fc.read(bb);  
            if (r == -1) {  
                break;  
            }  
            // flip方法让缓冲区可以将新读入的数据写入另一个通道    
            bb.flip();
            
            charBuffer =  charset.decode(bb);  
			cache.append(charBuffer.toString());
        }
		return cache.toString();
	}
}
