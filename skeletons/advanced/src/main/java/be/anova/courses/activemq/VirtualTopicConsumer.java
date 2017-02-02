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

public class VirtualTopicConsumer extends AbstractBrokerSupport {

    public static String brokerURL = "tcp://localhost:61616";
	private static Logger LOGGER = LoggerFactory.getLogger(VirtualTopicConsumer.class);

	private final static CountDownLatch latch = new CountDownLatch(1);

	private String QUEUE_NAME;

	public VirtualTopicConsumer(String queue) {
		QUEUE_NAME = queue;
	}

    public static void main(String[] args) {
        //TODO Create consumers for queues:
		//     Consumer.Editor.VirtualTopic.News, 
		//     Consumer.Typeset.VirtualTopic.News
		//     Consumer.Archive.VirtualTopic.News
	}

	public void run() {
		Connection connection = null;
		try {
			connection = getConnectionFactory().createConnection();

			final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createQueue(QUEUE_NAME);
			MessageConsumer consumer = session.createConsumer(destination);

			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message message) {
					handle((TextMessage) message);
				}
			});
			connection.start();
		} catch (Exception e) {
			LOGGER.error("Error occurred while receiving JMS messages", e);
		}
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
