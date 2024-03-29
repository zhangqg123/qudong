package com.qwert.qudong.ip.encap;

import com.qwert.qudong.sero.messaging.IncomingResponseMessage;
import com.qwert.qudong.sero.messaging.OutgoingRequestMessage;
import com.qwert.qudong.sero.messaging.WaitingRoomKey;
import com.qwert.qudong.sero.messaging.WaitingRoomKeyFactory;

/**
 * <p>SerialWaitingRoomKeyFactory class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public class EncapWaitingRoomKeyFactory implements WaitingRoomKeyFactory {
    private static final Sync sync = new Sync();

    /** {@inheritDoc} */
    @Override
    public WaitingRoomKey createWaitingRoomKey(OutgoingRequestMessage request) {
        return sync;
    }

    /** {@inheritDoc} */
    @Override
    public WaitingRoomKey createWaitingRoomKey(IncomingResponseMessage response) {
        return sync;
    }

    static class Sync implements WaitingRoomKey {
        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            return true;
        }
    }
}
