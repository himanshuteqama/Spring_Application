/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import javax.xml.bind.annotation.XmlAttribute;

public class Link {

	private String rel;

	private String href;

	@XmlAttribute(name = "rel")
	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	@XmlAttribute(name = "href")
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public String toString() {
		return "Link [rel=" + rel + ", href=" + href + "]";
	}

}