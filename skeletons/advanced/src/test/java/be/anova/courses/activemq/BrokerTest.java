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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokerTest extends TestCase implements MessageListener {

    public static String brokerURL = "vm://localhost";

    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private MessageConsumer consumer;

    private int msgsSend = 0;
    private CountDownLatch msgsReceived = new CountDownLatch(COUNT);
    private static Logger LOG = LoggerFactory.getLogger(BrokerTest.class);

    private BrokerService broker;

    private static int COUNT = 10000;

    protected void setUp() throws Exception {
        super.setUp();
        //TODO Setup a new broker
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        if (broker != null) {
            broker.stop();
        }
    }

    public void test() throws Exception {
        sendMessages();
//    	assertEquals(COUNT, msgsSend);

        receiveMessages();
//    	assertTrue("Should have received " + COUNT + " messages, got " + (COUNT - msgsReceived.getCount()), 
//        	       msgsReceived.await(15, TimeUnit.SECONDS));
    }

    private void sendMessages() throws JMSException {
        //TODO Send messages to a test queue
    }

    private void receiveMessages() throws JMSException {
        //TODO Listen to test queue and handle messages
    }

    public void onMessage(Message message) {
	try {
	    if (message instanceof TextMessage) {
		    TextMessage txtMessage = (TextMessage) message;
		    LOG.info("Message received: " + txtMessage.getText());
		    msgsReceived.countDown();
	    } else {
		    LOG.debug("Invalid message received.");
	    }
	} catch (JMSException e) {
	    System.out.println("Caught:" + e);
	    e.printStackTrace();
	}
    }

}
