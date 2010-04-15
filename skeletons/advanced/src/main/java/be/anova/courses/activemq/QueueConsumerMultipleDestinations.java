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

public class QueueConsumerMultipleDestinations extends AbstractBrokerSupport implements MessageListener {

	private static final Logger LOG = Logger.getLogger(QueueConsumerMultipleDestinations.class);

	public static void main(String[] args) {
		QueueConsumerMultipleDestinations mc = new QueueConsumerMultipleDestinations();
		mc.run();
	}

	public void run() {
		//TODO Use wildcards to messages from multiple queues 
		//     private.msgs.anova & private.msgs.abis
	}

	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage txtMessage = (TextMessage) message;
				System.out.println("Message received: " + txtMessage.getText());
			} else {
				LOG.warn("Unexpected message type: " + message.getClass());
			}
		} catch (JMSException e) {
			LOG.warn("Error occurred while receiving message", e);
		}
	}
}
