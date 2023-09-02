package com.qwert.qudong.test;

import java.util.Arrays;

import com.qwert.qudong.QwertFactory;
import com.qwert.qudong.QwertMaster;
import com.qwert.qudong.code.ProtocalCode;
import com.qwert.qudong.exception.QudongTransportException;
import com.qwert.qudong.ip.IpParameters;
import com.qwert.qudong.msg.*;
import com.qwert.qudong.msg.ReadM7000Request;
import com.qwert.qudong.msg.ReadM7000Response;
import com.qwert.qudong.msg.delta.ReadDeltaRequest;
import com.qwert.qudong.msg.delta.ReadDeltaResponse;
import com.qwert.qudong.msg.kstar.ReadKstarRequest;
import com.qwert.qudong.msg.kstar.ReadKstarResponse;
import com.qwert.qudong.msg.protocal.ReadProtocalResponse;
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
            float ver=2.0f;
            int cid1 = 60;
            int cid2 = 42;
            int lenid = 0;
//            readDianzongTest(master, ver,slaveId, cid1,cid2,lenid);
           read7000Test(master,1,6);
//              readkstarTest(master,1,51,1);//"~00P003STB"
//            readDeltaTest(master,1,"~00P003STB");

 /*           BatchRead<String> batch = new BatchRead<String>();
			batch.addLocator("10",	BaseLocator.holdingRegister(slaveId, 10, DataType.TWO_BYTE_INT_SIGNED));
			batch.addLocator("21",	BaseLocator.holdingRegister(slaveId, 21, DataType.FOUR_BYTE_FLOAT));
			BatchResults<String> results = master.send(batch);
			System.out.println("::"+results);
			String res = results.toString();*/
            
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
            ReadDianzongResponse response = (ReadDianzongResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(""+Arrays.toString(response.getShortData()));
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

    public static void readkstarTest(QwertMaster master,int slaveId, int cid1,int cid2) {
        try {
            ReadKstarRequest request = new ReadKstarRequest(slaveId, cid1,cid2);
            ReadKstarResponse response = (ReadKstarResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(response.getMessage());
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
