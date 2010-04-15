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
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class QueueProducerMultipleDestinations {
	
    public static String brokerURL = "tcp://localhost:61616";
	
    private static Logger LOG = Logger.getLogger(QueueProducerMultipleDestinations.class);
    private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    
    public static void main(String[] args) throws JMSException {

    	QueueProducerMultipleDestinations producer = new QueueProducerMultipleDestinations();
    	producer.run();
    	producer.close();

    }

    public void run() throws JMSException
    {
        // Setup the connection to ActiveMQ
        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
    
        connection = factory.createConnection();
        connection.start();
    
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    
        Destination destination = session.createQueue("private.msgs.anova,private.msgs.abis");
        MessageProducer producer = session.createProducer(destination);
        
        for (int i = 0; i < 100; i++)
        {
            Message message = session.createTextMessage("Hello World!");
            producer.send(message);
            LOG.debug("Message " + i + " created: " + message);
        }
        
        session.commit();
    }
 
    public void close() throws JMSException
    {
        if (connection != null) {
            connection.close();
        }
    }

	
	

}
