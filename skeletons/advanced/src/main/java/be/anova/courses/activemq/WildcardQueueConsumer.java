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

import org.apache.log4j.Logger;

public class WildcardQueueConsumer extends AbstractBrokerSupport implements MessageListener {

	private static Logger LOG = Logger.getLogger(WildcardQueueConsumer.class);

    private final CountDownLatch done = new CountDownLatch(1);

	public static void main(String[] args) {
		WildcardQueueConsumer mc = new WildcardQueueConsumer();
		mc.run();
	}

	public void run() {
        Connection connection = null;
		try {
			// TODO: connect to the broker and listen for messages using a wildcard 

            done.await();
		} catch (Exception e) {
			System.out.println("Caught:" + e);
			e.printStackTrace();
		} finally {
            closeConnection(connection);
        }
	}

	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage text = (TextMessage) message;
				System.out.println("Message received from " + text.getJMSDestination());

                if (text.getText().contains("done")) {
                    done.countDown();
                }
            } else {
				System.out.println("Invalid message received.");
			}
		} catch (JMSException e) {
			LOG.warn("Error handling message", e);
		}
	}
}
