/*
 * (c) 2010, anova r&d bvba.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.anova.courses.activemq;

import java.util.concurrent.CountDownLatch;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncTopicSubscriber extends AbstractBrokerSupport {

    private static final String TOPIC_NAME = "news.it.software";
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncTopicSubscriber.class);

    private final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        AsyncTopicSubscriber subscriber = new AsyncTopicSubscriber();
        subscriber.run();
    }

    public void run() {
    	//TODO Create Message consumer which will listen to 
    	//     incoming messages from Topic news.it.sofware 
    }

    private void handle(TextMessage message) {
        try {
            if ("done".equals(message.getText().toLowerCase())) {
                latch.countDown();
                LOGGER.info("Done processing exchanges");
            } else {
                LOGGER.info("Received: " + message.getText());
            }
        } catch (JMSException e) {
            LOGGER.error("Error while reading message", e);
        }
    }
}