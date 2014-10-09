/*
 * Copyright [2014] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package junit.org.rapidpm.module.iot.tinkerforge;

import com.tinkerforge.BrickletNFCRFID;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by Sven Ruppert on 14.09.2014.
 */
public class NFCTest {

  private static final String host = "localhost";
  private static final int port = 4223;
  private static final String UID = "oEP"; // Change to your UID

  public static void main(String args[]) throws Exception {
    IPConnection ipcon = new IPConnection();
    final BrickletNFCRFID nfc = new BrickletNFCRFID(UID, ipcon);
    ipcon.connect(host, port);
    nfc.addStateChangedListener((state, idle) -> {
      try {
        if(state == BrickletNFCRFID.STATE_REQUEST_TAG_ID_READY) {
          System.out.println("Tag found");
          // Write 16 byte to pages 5-8
          short[] fourPagesArray = new short[16];  //immer 4x4
          Arrays.fill(fourPagesArray, (byte)0);
          final byte[] data2Write = "Hallo".getBytes();
          System.arraycopy(data2Write, 0, fourPagesArray, 0, data2Write.length);
          nfc.writePage(5, fourPagesArray);
          System.out.println("Writing data...");
        } else if(state == BrickletNFCRFID.STATE_WRITE_PAGE_READY) {
          // Request pages 5-8
          nfc.requestPage(5);
          System.out.println("Requesting data...");
        } else if(state == BrickletNFCRFID.STATE_REQUEST_PAGE_READY) {
          // Get and print pages
          short[] data = nfc.getPage();
          String s = "Read data: [" ;
          for (final short i1 : data) { s += String.valueOf((char) i1); }
          s = s.trim();
          s += "]";
          System.out.println(s);
        } else if((state & (1 << 6)) == (1 << 6)) {
          // All errors have bit 6 set
          System.out.println("Error: " + state);
        }
      } catch(Exception e) {
        System.out.println(e);
      }
    });

    final Thread thread = new Thread(() -> {
      // Select NFC Forum Type 2 tag
      while (true) {
        try {
          nfc.requestTagID(BrickletNFCRFID.TAG_TYPE_TYPE2);
          Thread.sleep(1000);
        } catch (TimeoutException | InterruptedException | NotConnectedException e) {
          e.printStackTrace();
        }
      }
    });
    thread.run();
    WaitForQ.waitForQ();
    ipcon.disconnect();
  }
  public static class WaitForQ {
    public static void waitForQ() {
      final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

      final Thread t = new Thread(() -> {
        System.out.println("press Q THEN ENTER to terminate");
        int quit=0;
        while(true){
          try {
            Thread.sleep(10000);
            String msg = null;
            while(true){
              try{
                msg=in.readLine();
              }catch(Exception e){}
              if(msg != null && msg.equals("Q")) { quit = 1; }
              if(quit==1) break;
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          break;
        }
      });
      t.start();
    }
  }
}
