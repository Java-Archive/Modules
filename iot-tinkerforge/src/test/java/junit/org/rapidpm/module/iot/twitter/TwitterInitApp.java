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

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Sven Ruppert on 12.04.2014.
 *
 * Init the Twitter app
 *
 * 1 create new App inside your Twitter Account
 * 2 get API Key and API Secret, after you switched AcessLevel to  Read, write, and direct messages
 * 3 create my Access Token, and wait until Twitter is ready
 *
 *
 */
public class TwitterInitApp {
  public static void main(String[] args) throws TwitterException {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey("DI0UZvexwirEJ1wl5sw9Vfqx3")
        .setOAuthConsumerSecret("NQ0Zumw7Li1XbuwzrNHOKquznBgmAJo1sPAoqiNMShqgFufqip")
        .setOAuthAccessToken("2440296835-4dmuWPOLYUgLjn9ddcXpuloOgDAPO1W0Y5NZSg7")
        .setOAuthAccessTokenSecret("X9QYzlDKURIxGZbFWoZ7NdvhcFurBfEUVHMTLxtBSrU2x");

    TwitterFactory tf = new TwitterFactory(cb.build());
    Twitter twitter = tf.getInstance();


    twitter.getHomeTimeline().forEach(s-> System.out.println("s.getText() = " + s.getText()));

  }
}
