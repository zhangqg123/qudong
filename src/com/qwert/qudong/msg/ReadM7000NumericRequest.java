/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.qwert.qudong.msg;

import com.qwert.qudong.ProcessImage;
import com.qwert.qudong.Qwert;
import com.qwert.qudong.base.QwertAsciiUtils;
import com.qwert.qudong.base.QwertUtils;
import com.qwert.qudong.exception.QudongTransportException;
import com.qwert.qudong.msg.QwertRequest;
import com.qwert.qudong.sero.util.queue.ByteQueue;

import java.io.UnsupportedEncodingException;

/**
 * <p>Abstract ReadDianzongNumericRequest class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
abstract public class ReadM7000NumericRequest extends QwertRequest {
	private int channel;

    /**
     * <p>Constructor for ReadDianzongNumericRequest.</p>
     *
     * @param slaveId a int.
     * @throws com.qwert.qudong.exception.QudongTransportException if any.
     */
    public ReadM7000NumericRequest(int slaveId, int channel) throws QudongTransportException {
        super(slaveId);
        this.channel = channel;
    }


    /** {@inheritDoc} */
    @Override
    public void validate(Qwert modbus) throws QudongTransportException {
        QwertUtils.validateOffset(slaveId);
    }

    ReadM7000NumericRequest(int slaveId) throws QudongTransportException {
        super(slaveId);
    }

    @Override
    protected void writeRequest(ByteQueue queue) {
        queue.push(QwertAsciiUtils.M7000_START);
        String hexString = String.format("%02X", slaveId);
        hexString+=channel;
        byte[] hexByte = new byte[3];
        try {
            hexByte = hexString.getBytes("ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        queue.push(hexByte);
        queue.push(QwertAsciiUtils.END);
    }

    /** {@inheritDoc} */
    @Override
    protected void readRequest(ByteQueue queue) {
    	queue.pop();
    	queue.pop();
        channel = queue.popInt(queue.pop());
    }

    /**
     * <p>getData.</p>
     *
     * @param processImage a {@link com.qwert.qudong.ProcessImage} object.
     * @return an array of {@link byte} objects.
     * @throws com.qwert.qudong.exception.QudongTransportException if any.
     */
    protected byte[] getData(ProcessImage processImage) throws QudongTransportException {
        short[] data = new short[12];

        // Get the data from the process image.
        for (int i = 0; i < 12; i++)
            data[i] = getNumeric(processImage, i );

        return convertToBytes(data);
    }

    /**
     * <p>getNumeric.</p>
     *
     * @param processImage a {@link com.qwert.qudong.ProcessImage} object.
     * @param index a int.
     * @return a short.
     * @throws com.qwert.qudong.exception.QudongTransportException if any.
     */
    abstract protected short getNumeric(ProcessImage processImage, int index) throws QudongTransportException;

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "ReadKstarNumericRequest [cid1=" + channel  +"]";
    }
}
