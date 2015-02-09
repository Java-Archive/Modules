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

package junit.org.rapidpm.module.iot.persistence.bitcasa;

//import com.bitcasa.javalib.BitcasaClient;
//import com.bitcasa.javalib.dao.BitcasaFile;
//import com.bitcasa.javalib.dao.BitcasaFolder;
//import com.bitcasa.javalib.dao.BitcasaItem;
//import com.bitcasa.javalib.exception.BitcasaException;
//import com.bitcasa.javalib.http.Downloader;
//import com.bitcasa.javalib.http.ProgressTracker;
//import com.bitcasa.javalib.http.Uploader;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.List;
//import java.util.Scanner;

/**
 * Created by Sven Ruppert on 03.08.2014.
 */
public class BitcasaMain {
  public static final String CLIENT_ID 				= "04534653";
  public static final String CLIENT_SECRET			= "f1500c6866e96d4a81a3143d2a600981";
  public static final String ACCESS_TOKEN 			= "YOUR_ACCESS_TOKEN";

//  static BitcasaClient bitcasaClient;
//
//  static List<BitcasaItem> sItems;

//  public static void main(String[] args) {
//
//    bitcasaClient = new BitcasaClient(CLIENT_ID, CLIENT_SECRET);
//    try {
//      final String authenticateUrl = bitcasaClient.getAuthenticateUrl();
//      System.out.println("authenticateUrl = " + authenticateUrl);
//      final String accessToken = bitcasaClient.requestForAccessToken(authenticateUrl);
//      System.out.println("accessToken = " + accessToken);
//      System.out.println("Your access token is: " + bitcasaClient.getAccessToken() + "\n\n");
//      final List<BitcasaItem> result =  bitcasaClient.createFolder(null, "iot-example");
//
//      result.forEach(System.out::println);
//
//
//
//    } catch (IOException | BitcasaException  e) {
//      e.printStackTrace();
//    } catch (URISyntaxException e) {
//      e.printStackTrace();
//    }


//		bitcasaClient = new BitcasaClient(ACCESS_TOKEN);
//    try {
//      String input = null;
//      Scanner scanner = new Scanner(System.in);
//      while(bitcasaClient.getAccessToken() == null) {
//        try {
//          System.out.println("Please go to " + bitcasaClient.getAuthenticateUrl() + " and get the authorization code or type \"exit\" to exit");
//          System.out.println("authorization code: ");
//          input = scanner.next();
//          if (input.equals("exit"))
//            System.exit(0);
//          bitcasaClient.requestForAccessToken(input);
//        } catch (Exception e) {}
//      };
//
//      System.out.println("Your access token is: " + bitcasaClient.getAccessToken() + "\n\n");
//
//      List<BitcasaItem> files = getItemsInFolder(null);
//      String itemType = "File";
//      for (int i = 0; i < files.size(); i++) {
//        BitcasaItem item = files.get(i);
//        if (item.getType() == BitcasaItem.Type.FILE)
//          itemType = "File";
//        else
//          itemType = "Folder";
//        System.out.println("[" + i + "]\t" + itemType + " name: " + item.getName() + ", path: " + item.getPath());
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }

//  public static List<BitcasaItem> getItemsInFolder(BitcasaFolder folder) throws IOException, BitcasaException {
//    return bitcasaClient.getItemsInFolder(null);
//  }
//
//  public static List<BitcasaItem> createFolder(BitcasaFolder folder, String fileName) throws IOException, BitcasaException {
//    return bitcasaClient.createFolder(folder, fileName);
//  }
//
//  public static void downloadFile (BitcasaItem item, String destinationFolder) throws IOException, BitcasaException {
//    Downloader downloader = new Downloader(((BitcasaFile)item), destinationFolder);
//    downloader.setProgressTracker(new ProgressTracker() {
//      @Override
//      public void progressUpdate(int percentage) {
//      }
//
//      @Override
//      public void progressComplete(BitcasaItem item) {
//        System.out.println("Download complete");
//      }
//    });
//    bitcasaClient.downloadFile(downloader);
//  }
//
//  public static void uploadFile(String filePath, BitcasaFolder destinationFolder) throws BitcasaException, IOException {
//    Uploader uploader = new Uploader(destinationFolder, filePath);
//    uploader.setProgressTracker(new ProgressTracker() {
//
//      @Override
//      public void progressUpdate(int percentage) {
//      }
//
//      @Override
//      public void progressComplete(BitcasaItem item) {
//        System.out.println("Upload complete");
//      }
//
//    });
//    bitcasaClient.uploadFile(uploader);
//  }

}
