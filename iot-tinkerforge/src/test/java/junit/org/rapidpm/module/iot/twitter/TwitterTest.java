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

package junit.org.rapidpm.module.iot.twitter;


import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Sven Ruppert on 12.04.2014.
 *
 * log in into Twiter
 * https://apps.twitter.com/
 * create new App
 *
 *
 */
public class TwitterTest {
  public static void main(String[] args) throws TwitterException, IOException {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey("DI0UZvexwirEJ1wl5sw9Vfqx3")
        .setOAuthConsumerSecret("NQ0Zumw7Li1XbuwzrNHOKquznBgmAJo1sPAoqiNMShqgFufqip")
        .setOAuthAccessToken("2440296835-4dmuWPOLYUgLjn9ddcXpuloOgDAPO1W0Y5NZSg7")
        .setOAuthAccessTokenSecret("X9QYzlDKURIxGZbFWoZ7NdvhcFurBfEUVHMTLxtBSrU2x");




    TwitterFactory tf = new TwitterFactory(cb.build());
    Twitter twitter = tf.getInstance();
    for(int i=0; i<100; i++){
      final Status status = twitter.updateStatus("IoT Tweets comming soon.. " + i + " / 100");
      System.out.println("status.getText() = " + status.getText());
    }





//    List<Status> statuses = twitter.getHomeTimeline();
//    System.out.println("Showing home timeline.");
//    for (Status status : statuses) {
//      System.out.println(status.getUser().getName() + ":" +status.getText());
//    }


//    final Status status = twitter.updateStatus("IoT RapidPM in online soon..");
//    System.out.println("status.getText() = " + status.getText());
//    IDs ids;
//    long cursor = -1;
//    do {
//      if (0 < args.length) {
//        ids = twitter.getFollowersIDs(args[0], cursor);
//      } else {
//        ids = twitter.getFollowersIDs(cursor);
//      }
//      for (long id : ids.getIDs()) {
//        System.out.println(id);
//        User user = twitter.showUser(id);
//        System.out.println("FollowerCount " + (user.getFollowersCount()));
//        System.out.println("ScreenName " + (user.getScreenName()));
//      }
//    } while ((cursor = ids.getNextCursor()) != 0);



//    DirectMessage message = twitter.sendDirectMessage(531448379, "Hi Sven , How are you?");
//    System.out.println("Sent: " +message.getText() + " to @" + message.getRecipientScreenName());


//    ResponseList<DirectMessage> directMessages = twitter.getDirectMessages();
//    directMessages.forEach(m-> System.out.println("m = " + m.getSenderScreenName() + " " + m.getText() ));



//    RequestToken requestToken = twitter.getOAuthRequestToken();
//    System.out.println("Got request token.");
//    System.out.println("Request token: " + requestToken.getToken());
//    System.out.println("Request token secret: " + requestToken.getTokenSecret());
//
//    AccessToken accessToken = null;
//    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//    while (null == accessToken) {
//      System.out.println("Open the following URL and grant access to your account:");
//      System.out.println(requestToken.getAuthorizationURL());
//      System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
//      String pin = br.readLine();
//      try{
//        if(pin.length() > 0){
//          accessToken = twitter.getOAuthAccessToken(requestToken, pin);
//        }else{
//          accessToken = twitter.getOAuthAccessToken();
//        }
//      } catch (TwitterException te) {
//        if(401 == te.getStatusCode()){
//          System.out.println("Unable to get the access token.");
//        }else{
//          te.printStackTrace();
//        }
//      }
//    }
//    //persist to the accessToken for future reference.
//    storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
//    Status status = twitter.updateStatus(args[0]);
//    System.out.println("Successfully updated the status to [" + status.getText() + "].");
//    System.exit(0);
  }
//
//  private static void storeAccessToken(long id, AccessToken accessToken) {
//    System.out.println("id = " + id);
//    System.out.println("accessToken = " + accessToken);
//    System.out.println("accessToken = " + accessToken.getToken());
//    System.out.println("accessTokenSecret = " + accessToken.getTokenSecret());
//  }


}
