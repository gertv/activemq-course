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


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: gert
 * Date: Apr 9, 2010
 * Time: 11:02:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageReceiver implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(MessageReceiver.class);

    private boolean echo = true;

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                if (text.contains("echo")) {
                    onEchoCommand(text);
                } else {
                    doEcho(text);
                }
            }
        } catch (JMSException e) {
            LOGGER.warn("Error receiving JMS message", e);
        }
    }

    private void onEchoCommand(String text) {
        echo = text.contains("on");
        LOGGER.info(String.format("Echo is now %s", echo ? "on" : "off"));
    }

    private void doEcho(String text) {
        if (echo) {
            LOGGER.info(text);
        }
    }
}
