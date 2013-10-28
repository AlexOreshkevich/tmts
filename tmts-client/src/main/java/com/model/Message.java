package com.model;

import java.io.Serializable;

/**
 * @author Alex Laputski
 */
public final class Message implements Serializable {

	private static final long serialVersionUID = -817539171868695848L;
	private final String content;

	public Message(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + "]";
	}
}
