/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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

import org.apache.log4j.Logger;

public class VirtualTopicConsumer extends AbstractBrokerSupport {

    public static String brokerURL = "tcp://localhost:61616";
	private static Logger LOGGER = Logger
			.getLogger(VirtualTopicConsumer.class);

	private final static CountDownLatch latch = new CountDownLatch(1);

	private String QUEUE_NAME;

	public VirtualTopicConsumer(String queue) {
		QUEUE_NAME = queue;
	}

    public static void main(String[] args) {

		try {
			VirtualTopicConsumer consumer1 = new VirtualTopicConsumer(
					"Consumer.Editor.VirtualTopic.News");
			consumer1.run();

			VirtualTopicConsumer consumer2 = new VirtualTopicConsumer(
					"Consumer.Typeset.VirtualTopic.News");
			consumer2.run();

			VirtualTopicConsumer consumer3 = new VirtualTopicConsumer(
					"Consumer.Archive.VirtualTopic.News");
			consumer3.run();

			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		Connection connection = null;
		try {
			connection = getConnectionFactory().createConnection();

			final Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

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
