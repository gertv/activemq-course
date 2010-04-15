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
import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import junit.framework.TestCase;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;

public class BrokerTest extends TestCase implements MessageListener {

    private static final int COUNT = 10000;
    private static final String BROKER_URL = "vm://localhost";

    private ConnectionFactory factory;

    private int sent = 0;
    private CountDownLatch received = new CountDownLatch(COUNT);

    private static Logger LOG = Logger.getLogger(BrokerTest.class);

    private BrokerService broker;

    protected void setUp() throws Exception {
        super.setUp();

        broker = new BrokerService();
        broker.setPersistent(false);
        broker.setUseJmx(false);
        broker.start();

        factory = new ActiveMQConnectionFactory(BROKER_URL);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        broker.stop();
    }

    public void test() throws Exception {
        sendMessages();
        startReceiver();

        assertEquals(COUNT, sent);

        assertTrue("Should have received " + COUNT + " messages, got " + (COUNT - received.getCount()),
                   received.await(15, TimeUnit.SECONDS));

    }

    private void sendMessages() throws JMSException {
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("test");
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < COUNT; i++) {
            Message message = session.createTextMessage("Message " + (i + 1) + " of " + COUNT);
            producer.send(message);
            sent++;
        }

        connection.close();

    }

    private void startReceiver() throws JMSException {
        Connection connection = factory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("test");
        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(this);
        connection.start();

    }

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage txtMessage = (TextMessage) message;
                LOG.info(txtMessage.getText() + " received");
                received.countDown();
            } else {
                LOG.debug("Invalid message received.");
            }
        } catch (JMSException e) {
            System.out.println("Caught:" + e);
            e.printStackTrace();
        }
    }
}
