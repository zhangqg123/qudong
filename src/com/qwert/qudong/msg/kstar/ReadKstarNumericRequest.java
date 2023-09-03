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
package com.qwert.qudong.msg.kstar;

import com.qwert.qudong.ProcessImage;
import com.qwert.qudong.Qwert;
import com.qwert.qudong.base.QwertAsciiUtils;
import com.qwert.qudong.base.QwertUtils;
import com.qwert.qudong.exception.QudongTransportException;
import com.qwert.qudong.msg.QwertRequest;
import com.qwert.qudong.sero.util.queue.ByteQueue;

/**
 * <p>Abstract ReadDianzongNumericRequest class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
abstract public class ReadKstarNumericRequest extends QwertRequest {
	private String cmd;

    /**
     * <p>Constructor for ReadDianzongNumericRequest.</p>
     *
     * @param slaveId a int.
     * @throws QudongTransportException if any.
     */

    public ReadKstarNumericRequest(int slaveId, String cmd) throws QudongTransportException {
        super(slaveId);
        this.cmd=cmd;
    }

    /** {@inheritDoc} */
    @Override
    public void validate(Qwert modbus) throws QudongTransportException {
        QwertUtils.validateOffset(slaveId);
    }

    public ReadKstarNumericRequest(int slaveId) throws QudongTransportException {
        super(slaveId);
    }

    @Override
    protected void writeRequest(ByteQueue queue) {
        byte[] kstarByte = cmd.toUpperCase().getBytes();
        queue.push(kstarByte);
        queue.push(QwertAsciiUtils.END);
    }

    /** {@inheritDoc} */
    @Override
    protected void readRequest(ByteQueue queue) {
    }

    /**
     * <p>getData.</p>
     *
     * @param processImage a {@link ProcessImage} object.
     * @return an array of {@link byte} objects.
     * @throws QudongTransportException if any.
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
     * @param processImage a {@link ProcessImage} object.
     * @param index a int.
     * @return a short.
     * @throws QudongTransportException if any.
     */
    abstract protected short getNumeric(ProcessImage processImage, int index) throws QudongTransportException;

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "ReadKstarNumericRequest [cmd=" + cmd  + "]";
    }
}
