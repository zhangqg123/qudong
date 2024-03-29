package com.qwert.qudong.test;

import com.qwert.qudong.serial.dianzong.DianzongMessageParser;
import com.qwert.qudong.sero.util.queue.ByteQueue;

public class AsciiDecodingTest {
    public static void main(String[] args) throws Exception {
//        decodeRequest(":010100000002FC\r\n");
//        decodeResponse(":01010101FC\r\n");
        decodeRequest("$016\r"); //7000d

        //	String putIn = "210360470000";
    //    ByteQueue queue = new ByteQueue(toBytes(putIn));
//        decodeRequest(":010300000008F4\r\n");
        decodeResponse(":010310009100000000000000000000000000005B\r\n");
    }

    public static void decodeRequest(String s) throws Exception {
        ByteQueue queue = new ByteQueue(toBytes(s));
        new DianzongMessageParser(false).parseMessage(queue);
    }

    public static void decodeResponse(String s) throws Exception {
        ByteQueue queue = new ByteQueue(toBytes(s));
        new DianzongMessageParser(true).parseMessage(queue);
    }

    public static byte[] toBytes(String s) throws Exception {
        return s.getBytes("ASCII");
    }
}
