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

public class QueueProducerMultipleDestinations extends AbstractBrokerSupport {
	
    private static final Logger LOG = Logger.getLogger(QueueProducerMultipleDestinations.class);

    public static void main(String[] args) throws JMSException {
    	QueueProducerMultipleDestinations producer = new QueueProducerMultipleDestinations();
    	producer.run();
    }

    public void run() throws JMSException {
        //TODO: Send messages to multiple queues: private.msgs.anova & private.msgs.abis
    }
 
}
