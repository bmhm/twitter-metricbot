/*
 *  Copyright 2018 The twittermetricbot contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.github.bmhm.twitter.metricbot.factory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.bmhm.twitter.metricbot.twitter.TwitterConfig;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.AsyncTwitterFactory;
import twitter4j.Twitter;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Produces instances of Twitter clients.
 *
 * <p>Called this factory 'producer', to avoid name clash with {@link twitter4j.TwitterFactory},
 * and because in CDI you'd call this a producer method anyway.</p>
 */
@Factory
public class TwitterProducer {

  private static final Logger LOG = LoggerFactory.getLogger(TwitterProducer.class);

  @Inject
  private TwitterConfig twitterAttributes;

  @Bean
  @Singleton
  public Twitter getTwitter() {
    if (this.twitterAttributes == null || this.twitterAttributes.getConsumerKey() == null
        || this.twitterAttributes.getAccessToken() == null) {
      throw new IllegalStateException("not configured");
    }

    final ConfigurationBuilder cb = new ConfigurationBuilder()
        .setDebugEnabled(this.twitterAttributes.isDebug())
        .setOAuthConsumerKey(this.twitterAttributes.getConsumerKey())
        .setOAuthConsumerSecret(this.twitterAttributes.getConsumerSecret())
        .setOAuthAccessToken(this.twitterAttributes.getAccessToken())
        .setOAuthAccessTokenSecret(this.twitterAttributes.getAccessTokenSecret());
    final twitter4j.TwitterFactory tf = new twitter4j.TwitterFactory(cb.build());

    return tf.getInstance();
  }

  @Bean
  @Singleton
  public AsyncTwitterFactory getAsyncTwitter() {
    if (this.twitterAttributes == null || this.twitterAttributes.getConsumerKey() == null
        || this.twitterAttributes.getAccessToken() == null) {
      throw new IllegalStateException("not configured");
    }

    final ConfigurationBuilder cb = new ConfigurationBuilder()
        .setDebugEnabled(this.twitterAttributes.isDebug())
        .setOAuthConsumerKey(this.twitterAttributes.getConsumerKey())
        .setOAuthConsumerSecret(this.twitterAttributes.getConsumerSecret())
        .setOAuthAccessToken(this.twitterAttributes.getAccessToken())
        .setOAuthAccessTokenSecret(this.twitterAttributes.getAccessTokenSecret());

    return new AsyncTwitterFactory(cb.build());
  }

  @Singleton
  @Named("recentMatches")
  public Map<Long, Instant> recentMatches() {
    return new ConcurrentHashMap<>();
  }

}
