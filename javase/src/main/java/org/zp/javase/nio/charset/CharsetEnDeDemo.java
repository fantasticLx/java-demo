package org.zp.javase.nio.charset;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharsetDecoder;
import java.nio.CharBuffer;
import java.nio.ByteBuffer;
import java.util.SortedMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 */
public class CharsetEnDeDemo{
	public static void main(String args[]) throws Exception {
		Charset charset = Charset.forName("ISO-8859-1");	// 只能表示的英文字符
		CharsetEncoder encoder = charset.newEncoder();	// 得到编码器
		CharsetDecoder decoder = charset.newDecoder();	// 得到解码器
		// 通过CharBuffer类中的
		CharBuffer cb = CharBuffer.wrap("梦里花落知多少");
		ByteBuffer buf = encoder.encode(cb);	// 进行编码操作
		System.out.println(decoder.decode(buf));	// 进行解码操作
	}
}