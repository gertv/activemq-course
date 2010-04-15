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

import java.util.Date;
import java.util.TimerTask;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * Created by IntelliJ IDEA.
 * User: gert
 * Date: Apr 9, 2010
 * Time: 9:47:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageTimerTask extends TimerTask implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(MessageTimerTask.class);

    private JmsTemplate template;

    private boolean enabled = true;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    @Override
    public void run() {
    	//TODO Use JmsTemplate to send/receive new messages
    }

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String command = ((TextMessage) message).getText();
                if (command.contains("timer")) {
                    onTimerCommand(command);
                } 
            }
        } catch (JMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void onTimerCommand(String command) {
        if (command.contains("on")) {
            LOGGER.info("Timer is now on");
            enabled = true;
        } else {
            LOGGER.info("Timer is now off");
            enabled = false;
        }
    }
}