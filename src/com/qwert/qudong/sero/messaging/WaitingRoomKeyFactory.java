package com.qwert.qudong.sero.messaging;


/**
 * <p>WaitingRoomKeyFactory interface.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public interface WaitingRoomKeyFactory {
    /**
     * <p>createWaitingRoomKey.</p>
     *
     * @param request a {@link com.qwert.qudong.sero.messaging.OutgoingRequestMessage} object.
     * @return a {@link com.qwert.qudong.sero.messaging.WaitingRoomKey} object.
     */
    WaitingRoomKey createWaitingRoomKey(OutgoingRequestMessage request);

    /**
     * <p>createWaitingRoomKey.</p>
     *
     * @param response a {@link com.qwert.qudong.sero.messaging.IncomingResponseMessage} object.
     * @return a {@link com.qwert.qudong.sero.messaging.WaitingRoomKey} object.
     */
    WaitingRoomKey createWaitingRoomKey(IncomingResponseMessage response);
}
