package com.qwert.qudong.test;

import java.util.Arrays;

import com.qwert.qudong.QwertFactory;
import com.qwert.qudong.QwertMaster;
import com.qwert.qudong.exception.QudongTransportException;
import com.qwert.qudong.ip.IpParameters;
import com.qwert.qudong.msg.*;
import com.qwert.qudong.msg.ReadM7000Request;
import com.qwert.qudong.msg.ReadM7000Response;
import com.qwert.qudong.msg.delta.ReadDeltaRequest;
import com.qwert.qudong.msg.delta.ReadDeltaResponse;
import com.qwert.qudong.msg.kstar.ReadKstarRequest;
import com.qwert.qudong.msg.kstar.ReadKstarResponse;
import com.qwert.qudong.serial.QwertSerialPortWrapper;

public class QwertTest {
    public static void main(String[] args) throws Exception {

    	String commPortId = "COM1";
    	int baudRate = 9600;
    	int flowControlIn = 0;
		int flowControlOut = 0; 
		int dataBits = 8;
		int stopBits = 1;
		int parity = 0;
		
        IpParameters ipParameters = new IpParameters();
        ipParameters.setHost("127.0.0.1");
        ipParameters.setPort(502);
        ipParameters.setEncapsulated(true);
//        ipParameters.setProtocal(ProtocalCode.DELTA);
//        ipParameters.setProtocal(ProtocalCode.DELTA);

   	
    	QwertSerialPortWrapper wrapper = new QwertSerialPortWrapper(commPortId, baudRate, flowControlIn, flowControlOut, dataBits, stopBits, parity);
        QwertFactory QwertFactory = new QwertFactory();
  //      QwertMaster master = QwertFactory.createDianzongMaster(wrapper);
        QwertMaster master = QwertFactory.createTcpMaster(ipParameters, false);
 
        try {
            master.init();
            int slaveId = 3;
            float ver=2.1f;
            int cid1 = 60;
            int cid2 = 47;
            int lenid = 0;
            readDianzongTest(master, ver,slaveId, cid1,cid2,lenid);
//           read7000Test(master,1,6);
//              readkstarTest(master,1,"Q1");
//            readDeltaTest(master,1,"~00P003STB");

        }
        finally {
            master.destroy();
        }
    }

    public static void readDeltaTest(QwertMaster master,int slaveId, String command) {
        try {
            ReadDeltaRequest request = new ReadDeltaRequest(slaveId, command);
//            ReadDeltaResponse response = (ReadDeltaResponse) master.send(request);
            ReadResponse response = (ReadResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else{
                byte[] tmpData = response.getData();
                String strDelta = new String(tmpData);
                System.out.println("delta data:"+strDelta);
            }
        }
        catch (QudongTransportException e) {
            e.printStackTrace();
        }
    }


    public static void readDianzongTest(QwertMaster master,float ver, int slaveId, int cid1, int cid2,int lenid) {
        try {
            ReadDianzongRequest request = new ReadDianzongRequest(ver,slaveId, cid1, cid2,lenid);
            ReadResponse response = (ReadResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else{
                byte[] tmpData = response.getData();
                String strDelta = new String(tmpData);
                System.out.println("dianzong data:"+strDelta);
            }
        }
        catch (QudongTransportException e) {
            e.printStackTrace();
        }
    }

    public static void read7000Test(QwertMaster master,int slaveId, int channel) {
        try {
            ReadM7000Request request = new ReadM7000Request(slaveId, channel);
            ReadResponse response = (ReadResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else{
                byte[] tmpData = response.getData();
                String strDelta = new String(tmpData);
                System.out.println("M7000 data:"+strDelta);
            }
        }
        catch (QudongTransportException e) {
            e.printStackTrace();
        }
    }

    public static void readkstarTest(QwertMaster master,int slaveId, String cmd) {
        try {
            ReadKstarRequest request = new ReadKstarRequest(slaveId, cmd);
       //     ReadKstarResponse response = (ReadKstarResponse) master.send(request);
            ReadResponse response = (ReadResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else{
                byte[] tmpData = response.getData();
                String strDelta = new String(tmpData);
                System.out.println("M7000 data:"+strDelta);
            }
        }
        catch (QudongTransportException e) {
            e.printStackTrace();
        }
    }

    public static void reportSlaveIdTest(QwertMaster master, int slaveId) {
        try {
            ReportSlaveIdRequest request = new ReportSlaveIdRequest(slaveId);
            ReportSlaveIdResponse response = (ReportSlaveIdResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(Arrays.toString(response.getData()));
        }
        catch (QudongTransportException e) {
            e.printStackTrace();
        }
    }

}
