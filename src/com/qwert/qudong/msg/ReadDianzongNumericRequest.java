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
import com.qwert.qudong.sero.util.queue.ByteQueue;

import java.io.UnsupportedEncodingException;

/**
 * <p>Abstract ReadDianzongNumericRequest class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
abstract public class ReadDianzongNumericRequest extends QwertRequest {
    private float ver;
	private int cid1;
    private int cid2;
    private int lenid;

    /**
     * <p>Constructor for ReadDianzongNumericRequest.</p>
     *
     * @param slaveId a int.
     * @param startOffset a int.
     * @param numberOfRegisters a int.
     * @throws com.qwert.qudong.exception.QudongTransportException if any.
     */
    public ReadDianzongNumericRequest(float ver,int slaveId, int cid1, int cid2,int lenid) throws QudongTransportException {
        super(slaveId);
        this.ver=ver;
        this.cid1 = cid1;
        this.cid2 = cid2;
        this.lenid=lenid;
    }

    /** {@inheritDoc} */
    @Override
    public void validate(Qwert modbus) throws QudongTransportException {
        QwertUtils.validateOffset(cid1);
        modbus.validateNumberOfRegisters(cid2);
        QwertUtils.validateEndOffset(cid1 + cid2 - 1);
    }

    ReadDianzongNumericRequest(int slaveId) throws QudongTransportException {
        super(slaveId);
    }

    @Override
    protected void writeRequest(ByteQueue queue) {
        int tmpVer = (int) (ver * 10);
        String strQueue = "";
        strQueue+=""+tmpVer;
        strQueue+=(""+slaveId).length()>1?""+slaveId:"0"+slaveId;
        strQueue+=cid1;
        strQueue+=cid2;
        strQueue+="0000";
        try {
            queue.push(strQueue.getBytes("ASCII"));
            int drc = QwertAsciiUtils.calculateDRC(queue);
            QwertAsciiUtils.writeAscii2(queue, drc);
            byte[] tmpQueue = queue.popAll();
            queue.push(QwertAsciiUtils.START);
            queue.push(tmpQueue);
            queue.push(QwertAsciiUtils.END);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void readRequest(ByteQueue queue) {
    	String tmpver = Integer.toHexString(queue.pop() & 0xFF);
    	ver=Float.parseFloat(tmpver.substring(0,1)+"."+tmpver.substring(1));
    	queue.pop();
        cid1 = queue.popInt(queue.pop());
        cid2 = queue.popInt(queue.pop());
        lenid = queue.popInt(queue.pop());
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
        return "ReadDianzongNumericRequest [ver=" + ver + ",cid1=" + cid1 + ", cid2=" + cid2 + ", lenid=" + lenid + "]";
    }
}
