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
package com.qwert.qudong;

import com.qwert.qudong.base.QwertUtils;
import com.qwert.qudong.code.RegisterRange;
import com.qwert.qudong.exception.QudongTransportException;
import com.qwert.qudong.exception.QwertIdException;
import com.qwert.qudong.ip.IpParameters;
import com.qwert.qudong.ip.tcp.TcpMaster;
import com.qwert.qudong.ip.tcp.TcpSlave;
import com.qwert.qudong.msg.QwertRequest;
import com.qwert.qudong.msg.ReadDianzongRequest;
import com.qwert.qudong.serial.SerialPortWrapper;
import com.qwert.qudong.serial.dianzong.DianzongMaster;
import com.qwert.qudong.serial.dianzong.DianzongSlave;

/**
 * <p>QwertFactory class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public class QwertFactory {
    
    /**
     * <p>createDianzongMaster.</p>
     *
     * @param wrapper a {@link com.qwert.qudong.serial.SerialPortWrapper} object.
     * @return a {@link com.qwert.qudong.QwertMaster} object.
     */
    public QwertMaster createDianzongMaster(SerialPortWrapper wrapper) {
        return new DianzongMaster(wrapper);
    }

     /**
     * <p>createDianzongSlave.</p>
     *
     * @param wrapper a {@link com.qwert.qudong.serial.SerialPortWrapper} object.
     * @return a {@link com.qwert.qudong.QwertSlaveSet} object.
     */
    public QwertSlaveSet createDianzongSlave(SerialPortWrapper wrapper) {
        return new DianzongSlave(wrapper);
    }
    /**
     * <p>createTcpMaster.</p>
     *
     * @param params a {@link com.serotonin.modbus4j.ip.IpParameters} object.
     * @param keepAlive a boolean.
     * @return a {@link com.serotonin.modbus4j.ModbusMaster} object.
     */
    public QwertMaster createTcpMaster(IpParameters params, boolean keepAlive) {
        return new TcpMaster(params, keepAlive);
    }
    
    public QwertSlaveSet createTcpSlave(boolean encapsulated) {
        return new TcpSlave(encapsulated);
    }

    /**
     * <p>createReadRequest.</p>
     *
     * @param slaveId a int.
     * @param range a int.
     * @param offset a int.
     * @param length a int.
     * @return a {@link com.qwert.dianzong.msg.QwertRequest} object.
     * @throws com.qwert.dianzong.exception.QudongTransportException if any.
     * @throws com.qwert.dianzong.exception.QwertIdException if any.
     */
/*    public QwertRequest createReadRequest(int slaveId, int range, int offset, int length)
            throws QudongTransportException, QwertIdException {
        QwertUtils.validateRegisterRange(range);

        if (range == RegisterRange.INPUT_REGISTER)
            return new ReadInputRegistersRequest(slaveId, offset, length);

        return new ReadDianzongRequest(slaveId, offset, length);
    } */
}
