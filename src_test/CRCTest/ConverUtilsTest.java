package CRCTest;

import junit.framework.Assert;

import org.junit.Test;

public class ConverUtilsTest {
	private ConvertUtils test = new ConvertUtils();

	@Test
	public void int2byteTest() {
		int i = -10101;
		byte[] bytes = test.int2byte(i);
		int j = test.byte2int(bytes);

		Assert.assertEquals(i, j);
		i = 10101;
		bytes = test.int2byte(i);
		j = test.byte2int(bytes);
		Assert.assertEquals(i, j);
		i = -0x1f1f1f;
		bytes = test.int2byte(i);
		j = test.byte2int(bytes);
		Assert.assertEquals(i, j);
		i = 0x1f1f1f;
		bytes = test.int2byte(i);
		j = test.byte2int(bytes);
		Assert.assertEquals(i, j);
		i = 0x1f1f1f1f;
		bytes = test.int2byte(i);
		j = test.byte2int(bytes);
		Assert.assertEquals(i, j);
		i = -0x1f1f1f1f;
		bytes = test.int2byte(i);
		j = test.byte2int(bytes);
		Assert.assertEquals(i, j);
	}

	@Test
	public void int16TobyteTest() {
		int i = -0x01;
		byte[] bytes = test.int16Tobyte(i);
		int j = test.byteToint16(bytes);
		Assert.assertEquals("-0x01", i, j);
		i = 0x01;
		bytes = test.int16Tobyte(i);
		j = test.byteToint16(bytes);
		Assert.assertEquals("0x01", i, j);
		i = -0x0101;
		bytes = test.int16Tobyte(i);
		j = test.byteToint16(bytes);
		Assert.assertEquals("-0x0101", i, j);
		i = 0x0101;
		bytes = test.int16Tobyte(i);
		j = test.byteToint16(bytes);
		Assert.assertEquals("0x0101", i, j);
	}

	@Test
	public void long2byteTest() {
		long l = -0x01;
		byte[] bytes = test.long2byte(l);
		long j = test.byte2long(bytes);
		Assert.assertEquals("-0x01", l, j);
		l = 0x01;
		bytes = test.long2byte(l);
		j = test.byte2long(bytes);
		Assert.assertEquals("0x01", l, j);
		l = -0x0101;
		bytes = test.long2byte(l);
		j = test.byte2long(bytes);
		Assert.assertEquals("-0x0101", l, j);
		l = -0x0101;
		bytes = test.long2byte(l);
		j = test.byte2long(bytes);
		Assert.assertEquals("-0x0101", l, j);
	}

	@Test
	public void float2byteTest() {
		float f = 233344.233444f;
		byte[] bytes = test.float2byte(f);
		float j = test.byte2float(bytes);
		Assert.assertEquals(f, j);
	}

	@Test
	public void double2byteTest() {
		double d = 233344.233444;
		byte[] bytes = test.double2byte(d);
		double j = test.byte2double(bytes);
		Assert.assertEquals(d, j);
	}
}