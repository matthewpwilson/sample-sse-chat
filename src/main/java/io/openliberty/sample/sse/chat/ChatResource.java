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

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

@Path("chat")
public class ChatResource {

	@Context
	private Sse sse;
	
	private static SseBroadcaster broadcaster;
	
	private synchronized static SseBroadcaster getOrCreateBroadcaster(Sse sse) {
		if (broadcaster == null) {
			broadcaster = sse.newBroadcaster();
		}
		return broadcaster;
	}
	
	@GET
	@Path("register")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void register(@Context SseEventSink sink, @Context Sse sse) {
		SseBroadcaster b = getOrCreateBroadcaster(this.sse);
		b.register(sink);
	}
	
	@PUT
	public void broadcast(@QueryParam("user") String user, @QueryParam("message") String message) {
		SseBroadcaster b = getOrCreateBroadcaster(sse);
		ChatMessage chatMessage = new ChatMessage(user, message);
		OutboundSseEvent event = sse.newEventBuilder().data(ChatMessage.class, chatMessage)
				.id(""+chatMessage.getMsgID()).mediaType(MediaType.APPLICATION_JSON_TYPE).build();
		b.broadcast(event);
	}
}
