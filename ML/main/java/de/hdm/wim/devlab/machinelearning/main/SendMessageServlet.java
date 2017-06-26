/*
 * Copyright (c) 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package de.hdm.wim.devlab.machinelearning.main;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.PublishRequest;
import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.common.collect.ImmutableList;

import de.hdm.wim.devlab.machinelearning.util.PubsubUtils;

/**
 * Nachrichten-Eingaben auf der Web-Oberfläche werden versendet.
 * Dieses Servlet wurde rein für die Entwicklung genutzt und wird im produktiven System nicht mehr eingesetzt.
 */
@SuppressWarnings("serial")
public class SendMessageServlet extends HttpServlet {

    @Override
    public final void doPost(final HttpServletRequest req,
                             final HttpServletResponse resp)
            throws IOException {
        Pubsub client = PubsubUtils.getClient();
        String message = req.getParameter("message");
        if (!"".equals(message)) {
            String fullTopicName = String.format("projects/%s/topics/%s",
                    PubsubUtils.getProjectId(),
                    PubsubUtils.getAppTopicName());
            PubsubMessage pubsubMessage = new PubsubMessage();
            pubsubMessage.encodeData(message.getBytes("UTF-8"));
            PublishRequest publishRequest = new PublishRequest();
            publishRequest.setMessages(ImmutableList.of(pubsubMessage));

            client.projects().topics()
                    .publish(fullTopicName, publishRequest)
                    .execute();
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
       
        
        
        resp.getWriter().close();
        
        
        
        
    }
    
    
}
