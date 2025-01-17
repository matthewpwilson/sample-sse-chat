// ******************************************************************************
//  Copyright (c) 2019 IBM Corporation and others.
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  which accompanies this distribution, and is available at
//  http://www.eclipse.org/legal/epl-v10.html
//
//  Contributors:
//  IBM Corporation - initial API and implementation
// ******************************************************************************
package io.openliberty.sample.sse.chat;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("sse")
public class ChatApp extends Application {

    @Override
    public Set<Object> getSingletons() {
        new Thread(new ChatAgent("http://localhost:" + System.getProperty("default.http.port") + "/SseChatSample/sse/chat")).start();
        return Collections.singleton(new ChatResource());
    }
}