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
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 * Abstract base class for basic JMS exercises.
 */
public abstract class AbstractBrokerSupport {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final Logger LOGGER = Logger.getLogger(AbstractBrokerSupport.class);

    private final ConnectionFactory connectionFactory;

    public AbstractBrokerSupport() {
        super();
        this.connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
    }

    protected ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * Stop and close the JMS connection
     *
     * @param connection
     */
    protected void closeConnection(Connection connection) {
        try {
            connection.stop();
            connection.close();
        } catch (JMSException e) {
            LOGGER.warn("Error closing JMS connection");
        }
    }
}
