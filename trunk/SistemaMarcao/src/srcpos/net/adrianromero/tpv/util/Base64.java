/**
 * Implementation of MIME's Base64 encoding and decoding conversions.
 * Optimized code. (raw version taken from oreilly.jonathan.util)
 *
 * @author Anli Shundi
 *
 */

package net.adrianromero.tpv.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;


public class Base64 {

	/**
	* <p>Decode a Base64-encoded string to a byte array</p>
	*
	* @param base64 <code>String</code> encoded string (single line only !!)
	* @return Decoded data in a byte array
	*/
	public static byte[] decode(String base64) {

		//strip whitespace from anywhere in the string.  Not the most memory
		//efficient solution but elegant anyway :-)
		StringTokenizer tok = new StringTokenizer(base64, " \n\r\t", false);
		StringBuffer    buf = new StringBuffer(base64.length());

		while (tok.hasMoreElements()) {
			buf.append(tok.nextToken());
		}

		base64 = buf.toString();

		int pad = 0;

		for (int i = base64.length() - 1;
				  (i > 0) && (base64.charAt(i) == '='); i--) {
			pad++;
		}

		int    length = base64.length() / 4 * 3 - pad;
		byte[] raw = new byte[length];

		for (int i = 0, rawIndex = 0; i < base64.length();
				  i += 4, rawIndex += 3) {
			int block = (getValue(base64.charAt(i)) << 18)
							+ (getValue(base64.charAt(i + 1)) << 12)
							+ (getValue(base64.charAt(i + 2)) << 6)
							+ (getValue(base64.charAt(i + 3)));

			for (int j = 2; j >= 0; j--) {
				if (rawIndex + j < raw.length) {
					raw[rawIndex + j] = (byte) (block & 0xff);
				}

				block >>= 8;
			}
		}

		return raw;
	}

	/**
	* <p>Encode a byte array in Base64 format and return an optionally
	* wrapped line</p>
	*
	* @param raw <code>byte[]</code> data to be encoded
	* @param wrap <code>int<code> length of wrapped lines; No wrapping if less than 4.
	* @return a <code>String</code> with encoded data
	*/
	public static String encode(byte[] raw, int wrap) {

		//calculate length of encoded string
		int encLen = ((raw.length + 2) / 3) * 4;

		//adjust for newlines
		if (wrap > 3) {
			wrap -= wrap % 4;
			encLen += 2 * (encLen / wrap);
		} else {    //disable wrapping
			wrap = Integer.MAX_VALUE;
		}

		StringBuffer encoded = new StringBuffer(encLen);
		int          len3 = (raw.length / 3) * 3;
		int          outLen = 0;    //length of output line

		for (int i = 0; i < len3; i += 3, outLen += 4) {
			if (outLen + 4 > wrap) {
				encoded.append("\r\n");

				outLen = 0;
			}

			encoded.append(encodeFullBlock(raw, i));
		}

		if (outLen >= wrap) {    //this will produce an extra newline if needed !? Sun had it this way...
			encoded.append("\r\n");
		}

		if (len3 < raw.length) {
			encoded.append(encodeBlock(raw, len3));
		}

		return encoded.toString();
	}

	/**
	 * Encode a byte array and fold lines at the standard 76th character.
	 *
	 * @param raw <code>byte[]<code> to be base64 encoded
	 * @return the <code>String<code> with encoded data
	 */
	public static String encode(byte[] raw) {
		return encode(raw, 76);
	}

	/**
	 * Base64 decode the lines from the reader and return an InputStream
	 * with the bytes.
	 *
	 * @param <code>{@link BufferedReader}<code> reader holding encoded data
	 * @return InputStream with the decoded bytes
	 * @Exception IOException passes what the reader throws
	 */
	public static byte[] decode(BufferedReader reader) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String                line;

		while (null != (line = reader.readLine())) {
			byte[] bytes = decode(line);

			baos.write(bytes);
		}

		return baos.toByteArray();
	}

	protected static char[] encodeBlock(byte[] raw, int offset) {

		int block = 0;
		int slack = raw.length - offset - 1;
		int end = (slack >= 2)
					 ? 2
					 : slack;

		for (int i = 0; i < 3; i++) {
			byte b = (offset + i < raw.length)
						? raw[offset + i]
						: 0;
			int  neuter = (b < 0)
							  ? b + 256
							  : b;

			block <<= 8;
			block += neuter;
		}

		char[] base64 = new char[4];

		for (int i = 3; i >= 0; i--) {
			int sixBit = block & 0x3f;

			base64[i] = getChar(sixBit);
			block >>= 6;
		}

		if (slack < 1) {
			base64[2] = '=';
		}

		if (slack < 2) {
			base64[3] = '=';
		}

		return base64;
	}

	protected static char[] encodeFullBlock(byte[] raw, int offset) {

		int block = 0;

		for (int i = 0; i < 3; i++) {

			//byte b = raw[offset + i];
			//int neuter = (b < 0) ? b + 256 : b;
			block <<= 8;
			block += (0xff & raw[offset + i]);
		}

		block = ((raw[offset] & 0xff) << 16)
				  + ((raw[offset + 1] & 0xff) << 8) + (raw[offset + 2] & 0xff);

		char[] base64 = new char[4];

		for (int i = 3; i >= 0; i--) {
			int sixBit = block & 0x3f;

			base64[i] = getChar(sixBit);
			block >>= 6;
		}

		return base64;
	}

	protected static char getChar(int sixBit) {

		if ((sixBit >= 0) && (sixBit < 26)) {
			return (char) ('A' + sixBit);
		}

		if ((sixBit >= 26) && (sixBit < 52)) {
			return (char) ('a' + (sixBit - 26));
		}

		if ((sixBit >= 52) && (sixBit < 62)) {
			return (char) ('0' + (sixBit - 52));
		}

		if (sixBit == 62) {
			return '+';
		}

		if (sixBit == 63) {
			return '/';
		}

		return '?';
	}

	protected static int getValue(char c) {

		if ((c >= 'A') && (c <= 'Z')) {
			return c - 'A';
		}

		if ((c >= 'a') && (c <= 'z')) {
			return c - 'a' + 26;
		}

		if ((c >= '0') && (c <= '9')) {
			return c - '0' + 52;
		}

		if (c == '+') {
			return 62;
		}

		if (c == '/') {
			return 63;
		}

		if (c == '=') {
			return 0;
		}

		return -1;
	}
}