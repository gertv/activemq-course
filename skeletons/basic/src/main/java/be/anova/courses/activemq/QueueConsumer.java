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
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class QueueConsumer extends AbstractBrokerSupport {

    private static final String QUEUE_NAME = "private.msgs.anova";
    private static final Logger LOGGER = Logger.getLogger(AsyncQueueConsumer.class);

    private final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        QueueConsumer consumer = new QueueConsumer();
        consumer.run();
    }

    public void run() {
        //TODO Create your own loop to receive messages from the queue
        //     Invoke the handle method for every message you receive
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
