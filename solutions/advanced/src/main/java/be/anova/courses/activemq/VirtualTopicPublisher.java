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
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.log4j.Logger;

public class VirtualTopicPublisher extends AbstractBrokerSupport {
   
    private static final String TOPIC_NAME = "VirtualTopic.News";
    private static final Logger LOGGER = Logger.getLogger(VirtualTopicPublisher.class);

    public static void main(String[] args) {
    	VirtualTopicPublisher publisher = new VirtualTopicPublisher();
        publisher.run();
    }

    public void run() {
        Connection connection = null;
        try {
            connection = getConnectionFactory().createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createTopic(TOPIC_NAME);
            MessageProducer producer = session.createProducer(destination);

                Message message = session.createTextMessage("Helo o newyddion TG");
                message.setStringProperty("Language", "cy");
                producer.send(message);

                message = session.createTextMessage("Dia duit � IT nuachta");
                message.setStringProperty("Language", "ga");
                producer.send(message);

                message = session.createTextMessage("Hello from IT News!");
                message.setStringProperty("Language", "en");
                producer.send(message);

        } catch (JMSException e) {
            LOGGER.error("Error occured while sending JMS messages", e);
        } finally {
            closeConnection(connection);
        }
    }

}
