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
package com.qwert.qudong.ip.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qwert.qudong.QwertMaster;
import com.qwert.qudong.base.BaseMessageParser;
import com.qwert.qudong.exception.QudongTransportException;
import com.qwert.qudong.exception.ModbusInitException;
import com.qwert.qudong.ip.IpMessageResponse;
import com.qwert.qudong.ip.IpParameters;
import com.qwert.qudong.ip.encap.EncapMessageParser;
import com.qwert.qudong.ip.encap.EncapMessageRequest;
import com.qwert.qudong.ip.encap.EncapWaitingRoomKeyFactory;
import com.qwert.qudong.msg.QwertRequest;
import com.qwert.qudong.msg.QwertResponse;
import com.qwert.qudong.sero.messaging.EpollStreamTransport;
import com.qwert.qudong.sero.messaging.MessageControl;
import com.qwert.qudong.sero.messaging.OutgoingRequestMessage;
import com.qwert.qudong.sero.messaging.StreamTransport;
import com.qwert.qudong.sero.messaging.Transport;
import com.qwert.qudong.sero.messaging.WaitingRoomKeyFactory;

/**
 * <p>TcpMaster class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public class TcpMaster extends QwertMaster {
    private static final int RETRY_PAUSE_START = 50;
    private static final int RETRY_PAUSE_MAX = 1000;

    // Configuration fields.
    private final Log LOG = LogFactory.getLog(TcpMaster.class);
    private short nextTransactionId = 0;
    private final IpParameters ipParameters;
    private final boolean keepAlive;
    private final boolean autoIncrementTransactionId;

    // Runtime fields.
    private Socket socket;
    private Transport transport;
    private MessageControl conn;

    /**
     * <p>Constructor for TcpMaster.</p>
     *
     * @param params
     * @param keepAlive
     * @param autoIncrementTransactionId
     * @param validateResponse - confirm that requested slave id is the same in the response
     */
    public TcpMaster(IpParameters params, boolean keepAlive, boolean autoIncrementTransactionId, boolean validateResponse) {
        this.ipParameters = params;
        this.keepAlive = keepAlive;
        this.autoIncrementTransactionId = autoIncrementTransactionId;
    }

    /**
     * <p>Constructor for TcpMaster.</p>
     * Default to not validating the slave id in responses
     *
     * @param params a {@link com.qwert.qudong.ip.IpParameters} object.
     * @param keepAlive a boolean.
     * @param autoIncrementTransactionId a boolean.
     */
    public TcpMaster(IpParameters params, boolean keepAlive, boolean autoIncrementTransactionId) {
        this(params, keepAlive, autoIncrementTransactionId, false);
    }


    /**
     * <p>Constructor for TcpMaster.</p>
     *
     * Default to auto increment transaction id
     * Default to not validating the slave id in responses
     *
     * @param params a {@link com.qwert.qudong.ip.IpParameters} object.
     * @param keepAlive a boolean.
     */
    public TcpMaster(IpParameters params, boolean keepAlive) {
        this(params, keepAlive, true, false);
    }

    /**
     * <p>Setter for the field <code>nextTransactionId</code>.</p>
     *
     * @param id a short.
     */
    public void setNextTransactionId(short id) {
        this.nextTransactionId = id;
    }

    /**
     * <p>Getter for the field <code>nextTransactionId</code>.</p>
     *
     * @return a short.
     */
    protected short getNextTransactionId() {
        return nextTransactionId;
    }

    /** {@inheritDoc} */
    @Override
    synchronized public void init() throws ModbusInitException {
        try {
            if (keepAlive)
                openConnection();
        }
        catch (Exception e) {
            throw new ModbusInitException(e);
        }
        initialized = true;
    }

    /** {@inheritDoc} */
    @Override
    synchronized public void destroy() {
        closeConnection();
        initialized = false;
    }

    /** {@inheritDoc} */
    @Override
    synchronized public QwertResponse sendImpl(QwertRequest request) throws QudongTransportException {
        try {
            // Check if we need to open the connection.
            if (!keepAlive)
                openConnection();

            if(conn == null){
                LOG.debug("Connection null: " +  ipParameters.getPort());
            }

        }
        catch (Exception e) {
            closeConnection();
            throw new QudongTransportException(e, request.getSlaveId());
        }

        // Wrap the modbus request in a ip request.
        OutgoingRequestMessage ipRequest = null;
        if (ipParameters.isEncapsulated())
            ipRequest = new EncapMessageRequest(request);
        else {
//            if(autoIncrementTransactionId)
//                this.nextTransactionId++;
//            ipRequest = new XaMessageRequest(request, getNextTransactionId());
        }

        if(LOG.isDebugEnabled()){
            StringBuilder sb = new StringBuilder();
            for (byte b : Arrays.copyOfRange(ipRequest.getMessageData(),0,ipRequest.getMessageData().length)) {
                sb.append(String.format("%02X ", b));
            }
            LOG.debug("Encap Request: " + sb.toString());
        }

        // Send the request to get the response.
        IpMessageResponse ipResponse;
        LOG.debug("Sending on port: " +  ipParameters.getPort());
        try {
            if(conn == null){
                LOG.debug("Connection null: " +  ipParameters.getPort());
            }
            ipResponse = (IpMessageResponse) conn.send(ipRequest);
            if (ipResponse == null)
                return null;

            if(LOG.isDebugEnabled()){
                StringBuilder sb = new StringBuilder();
                for (byte b : Arrays.copyOfRange(ipResponse.getMessageData(),0,ipResponse.getMessageData().length)) {
                    sb.append(String.format("%02X ", b));
                }
                LOG.debug("Response: " + sb.toString());
            }
            return ipResponse.getQwertResponse();
        }
        catch (Exception e) {
            LOG.debug("Exception: " + e.getMessage() + " " + e.getLocalizedMessage());
            if (keepAlive) {
                LOG.debug("KeepAlive - reconnect!");
                // The connection may have been reset, so try to reopen it and attempt the message again.
                try {
                    LOG.debug("Modbus4J: Keep-alive connection may have been reset. Attempting to re-open.");
                    openConnection();
                    ipResponse = (IpMessageResponse) conn.send(ipRequest);
                    if (ipResponse == null)
                        return null;
                    if(LOG.isDebugEnabled()){
                        StringBuilder sb = new StringBuilder();
                        for (byte b : Arrays.copyOfRange(ipResponse.getMessageData(),0,ipResponse.getMessageData().length)) {
                            sb.append(String.format("%02X ", b));
                        }
                        LOG.debug("Response: " + sb.toString());
                    }
                    return ipResponse.getQwertResponse();
                }
                catch (Exception e2) {
                    closeConnection();
                    LOG.debug("Exception: " + e2.getMessage() + " " + e2.getLocalizedMessage());
                    throw new QudongTransportException(e2, request.getSlaveId());
                }
            }

            throw new QudongTransportException(e, request.getSlaveId());
        }
        finally {
            // Check if we should close the connection.
            if (!keepAlive)
                closeConnection();
        }
    }

    //
    //
    // Private methods
    //
    private void openConnection() throws IOException {
        // Make sure any existing connection is closed.
        closeConnection();

        // Try 'retries' times to get the socket open.
        int retries = getRetries();
        int retryPause = RETRY_PAUSE_START;
        while (true) {
            try {
                socket = new Socket();
                socket.setSoTimeout(getTimeout());
                socket.connect(new InetSocketAddress(ipParameters.getHost(), ipParameters.getPort()), getTimeout());
                if (getePoll() != null)
                    transport = new EpollStreamTransport(socket.getInputStream(), socket.getOutputStream(), getePoll());
                else
                    transport = new StreamTransport(socket.getInputStream(), socket.getOutputStream());
                break;
            }
            catch (IOException e) {
                closeConnection();

                if (retries <= 0)
                    throw e;

                retries--;

                // Pause for a bit.
                try {
                    Thread.sleep(retryPause);
                }
                catch (InterruptedException e1) {
                    // ignore
                }
                retryPause *= 2;
                if (retryPause > RETRY_PAUSE_MAX)
                    retryPause = RETRY_PAUSE_MAX;
            }
        }

        BaseMessageParser ipMessageParser = null;
        WaitingRoomKeyFactory waitingRoomKeyFactory=null;
        if (ipParameters.isEncapsulated()) {
            byte protocal = ipParameters.getProtocal();
            ipMessageParser = new EncapMessageParser(true);
            waitingRoomKeyFactory = new EncapWaitingRoomKeyFactory();
        }
        else {
//            ipMessageParser = new XaMessageParser(true);
//            waitingRoomKeyFactory = new XaWaitingRoomKeyFactory();
        }

        conn = getMessageControl();
        conn.start(transport, ipMessageParser, null, waitingRoomKeyFactory);
        if (getePoll() == null)
            ((StreamTransport) transport).start("Modbus4J TcpMaster");
    }

    private void closeConnection() {
        closeMessageControl(conn);
        try {
            if (socket != null)
                socket.close();
        }
        catch (IOException e) {
            getExceptionHandler().receivedException(e);
        }

        conn = null;
        socket = null;
    }
}
