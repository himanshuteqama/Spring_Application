/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.29 at 02:53:44 PM IST 
//

package com.solenoid.connector.sd.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetSettlementDataResult" type="{https://my.sellerdynamics.com/}SettlementData" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getSettlementDataResult" })
@XmlRootElement(name = "GetSettlementDataResponse")
public class GetSettlementDataResponse {

	@XmlElement(name = "GetSettlementDataResult")
	protected SettlementData getSettlementDataResult;

	/**
	 * Gets the value of the getSettlementDataResult property.
	 * 
	 * @return possible object is {@link SettlementData }
	 * 
	 */
	public SettlementData getGetSettlementDataResult() {
		return getSettlementDataResult;
	}

	/**
	 * Sets the value of the getSettlementDataResult property.
	 * 
	 * @param value
	 *            allowed object is {@link SettlementData }
	 * 
	 */
	public void setGetSettlementDataResult(SettlementData value) {
		this.getSettlementDataResult = value;
	}

}
