package CRCTest;

import java.io.*;

public class ConvertUtils {
	/**
	 * int32转换为二进制（4个字节）
	 * 
	 * @param i 待转换的整数
	 * @return 返回4字节二进制数
	 */
	public byte[] int2byte(int i) {
		byte[] res = new byte[4];
		res[3] = (byte) i;
		res[2] = (byte) (i >>> 8);
		res[1] = (byte) (i >>> 16);
		res[0] = (byte) (i >>> 24);
		return res;
	}

	/**
	 * 4字节二进制数转换为int32的整数
	 * 
	 * @param bytes 4字节的二进制数
	 * @return int32整数
	 */
	public int byte2int(byte[] bytes) {
		int res = (((bytes[0] << 24) >>> 24) << 24) | (((bytes[1] << 24) >>> 24) << 16)
				| (((bytes[2] << 24) >>> 24) << 8) | ((bytes[3] << 24) >>> 24);
		return res;
	}

	/**
	 * int16整数转换二2字节的二进制数
	 * 
	 * @param i int16整数
	 * @return 2字节的二进制数
	 */
	public byte[] int16Tobyte(int i) {
		byte[] res = new byte[2];
		res[1] = (byte) i;
		res[0] = (byte) (i >>> 8);
		return res;
	}

	/**
	 * 2字节的二进制数转换为int16整数
	 * 
	 * @param bytes 2字节的二进制数
	 * @return int16整数
	 */
	public int byteToint16(byte[] bytes) {
		int res = ((bytes[0] << 8) | ((bytes[1] << 24) >>> 24));
		return res;
	}

	/**
	 * 长整型long转换为8字节的二进制数
	 * 
	 * @param l 长整型long
	 * @return 8字节的二进制数
	 */
	public byte[] long2byte(long l) {
		byte[] res = new byte[8];
		res[7] = (byte) l;
		res[6] = (byte) (l >>> 8);
		res[5] = (byte) (l >>> 16);
		res[4] = (byte) (l >>> 24);
		res[3] = (byte) (l >>> 32);
		res[2] = (byte) (l >>> 40);
		res[1] = (byte) (l >>> 48);
		res[0] = (byte) (l >>> 56);
		return res;
	}

	/**
	 * 8字节的二进制数转换为长整型long
	 * 
	 * @param bytes 8字节的二进制数
	 * @return 长整型long
	 */
	public long byte2long(byte[] bytes) {
		long l0 = bytes[0];
		long l1 = bytes[1];
		long l2 = bytes[2];
		long l3 = bytes[3];
		long l4 = bytes[4];
		long l5 = bytes[5];
		long l6 = bytes[6];
		long l7 = bytes[7];
		long res = (l0 << 56) | (((l1 << 56) >>> 56) << 48) | (((l2 << 56) >>> 56) << 40) | (((l3 << 56) >>> 56) << 32)
				| (((l4 << 56) >>> 56) << 24) | (((l5 << 56) >>> 56) << 16) | (((l6 << 56) >>> 56) << 8)
				| ((l7 << 56) >>> 56);
		return res;
	}

	/**
	 * 浮点型float转换为4字节的二进制数
	 * 
	 * @param f 浮点数float
	 * @return 4字节的二进制数
	 */
	public byte[] float2byte(float f) {
		byte[] res = new byte[4];
		int l = Float.floatToIntBits(f);
		for (int i = 3; i >= 0; i--) {
			res[i] = new Integer(l).byteValue();
			l >>= 8;
		}
		return res;
	}

	/**
	 * 4字节的二进制数转换为浮点数float
	 * 
	 * @param bytes 4字节的二进制数
	 * @return 浮点数float
	 */
	public float byte2float(byte[] bytes) {
		int l = byte2int(bytes);
		float res = Float.intBitsToFloat(l);
		return res;
	}

	/**
	 * 双浮点数转换为8字节的二进制数
	 * 
	 * @param d 双浮点数double
	 * @return 8字节的二进制数
	 */
	public byte[] double2byte(double d) {
		long l = Double.doubleToLongBits(d);
		byte[] res = long2byte(l);
		return res;
	}

	/**
	 * 8字节的二进制数转换为双浮点数
	 * 
	 * @param bytes 8字节的二进制数
	 * @return 双浮点数double
	 */
	public double byte2double(byte[] bytes) {
		long l = byte2long(bytes);
		double res = Double.longBitsToDouble(l);
		return res;
	}

	public void test() throws Exception {
		FileOutputStream fos = new FileOutputStream("d:/data.dat");
		java.io.DataOutputStream dos = new DataOutputStream(fos);
		double d = 233344.233444;
		dos.writeDouble(d);
		dos.close();
		fos.close();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("begin write a int type to file:'c:/data.dat'");
		new ConvertUtils().test();
		System.out.println("end write..");
	}
}
