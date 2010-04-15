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

public class QueueConsumerMultipleDestinations implements MessageListener {

	public static String brokerURL = "tcp://localhost:61616";
	private static Logger LOG = Logger
			.getLogger(QueueConsumerMultipleDestinations.class);

	private ConnectionFactory factory;
	private Connection connection;
	private Session session;
    private MessageConsumer consumer;

	public static void main(String[] args) {
		QueueConsumerMultipleDestinations mc = new QueueConsumerMultipleDestinations();
		mc.run();
	}

	public void run() {
		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
			connection = factory.createConnection();
			connection.start();

			session = connection
					.createSession(true, Session.CLIENT_ACKNOWLEDGE);

			Destination destination = session
					.createQueue("private.msgs.anova,private.msgs.abis");
			consumer = session.createConsumer(destination);
			consumer.setMessageListener(this);
		} catch (Exception e) {
			System.out.println("Caught:" + e);
			e.printStackTrace();
		}
	}

	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			if (message instanceof TextMessage) {
				Thread.sleep(10000);
				TextMessage txtMessage = (TextMessage) message;
				System.out.println("Message received: " + txtMessage.getText());
			} else {
				System.out.println("Invalid message received.");
			}
		} catch (JMSException e) {
			System.out.println("Caught:" + e);
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
