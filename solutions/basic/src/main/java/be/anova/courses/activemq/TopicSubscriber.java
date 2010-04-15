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

public class TopicSubscriber extends AbstractBrokerSupport {

    private static final String TOPIC_NAME = "news.it.software";
    private static final Logger LOGGER = Logger.getLogger(QueueConsumer.class);

    public static void main(String[] args) {
        TopicSubscriber subscriber = new TopicSubscriber();
        subscriber.run();
    }

    public void run() {
        Connection connection = null;
        try {
            connection = getConnectionFactory().createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createTopic(TOPIC_NAME);
            MessageConsumer consumer = session.createConsumer(destination);

            boolean done = false;

            while (!done) {
                TextMessage message = (TextMessage) consumer.receive();
                if ("done".equals(message.getText().toLowerCase())) {
                    LOGGER.info("Done receiving messages!");
                    done = true;
                } else {
                    LOGGER.info("Received: " + message.getText());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while receiving JMS messages", e);
        } finally {
            closeConnection(connection);
        }
    }
}
