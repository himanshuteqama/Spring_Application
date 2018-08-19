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
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="encryptedLogin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="retailerId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="orderItemIds" type="{https://my.sellerdynamics.com/}ArrayOfOrderUpdateItemWithTracking" minOccurs="0"/&gt;
 *         &lt;element name="updateType" type="{https://my.sellerdynamics.com/}OrderUpdateType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "encryptedLogin", "retailerId",
		"orderItemIds", "updateType" })
@XmlRootElement(name = "UpdateOrdersWithTracking")
public class UpdateOrdersWithTracking {

	protected String encryptedLogin;
	@XmlElement(required = true)
	protected String retailerId;
	protected ArrayOfOrderUpdateItemWithTracking orderItemIds;
	@XmlElement(required = true)
	@XmlSchemaType(name = "string")
	protected OrderUpdateType updateType;

	/**
	 * Gets the value of the encryptedLogin property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEncryptedLogin() {
		return encryptedLogin;
	}

	/**
	 * Sets the value of the encryptedLogin property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEncryptedLogin(String value) {
		this.encryptedLogin = value;
	}

	/**
	 * Gets the value of the retailerId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRetailerId() {
		return retailerId;
	}

	/**
	 * Sets the value of the retailerId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRetailerId(String value) {
		this.retailerId = value;
	}

	/**
	 * Gets the value of the orderItemIds property.
	 * 
	 * @return possible object is {@link ArrayOfOrderUpdateItemWithTracking }
	 * 
	 */
	public ArrayOfOrderUpdateItemWithTracking getOrderItemIds() {
		return orderItemIds;
	}

	/**
	 * Sets the value of the orderItemIds property.
	 * 
	 * @param value
	 *            allowed object is {@link ArrayOfOrderUpdateItemWithTracking }
	 * 
	 */
	public void setOrderItemIds(ArrayOfOrderUpdateItemWithTracking value) {
		this.orderItemIds = value;
	}

	/**
	 * Gets the value of the updateType property.
	 * 
	 * @return possible object is {@link OrderUpdateType }
	 * 
	 */
	public OrderUpdateType getUpdateType() {
		return updateType;
	}

	/**
	 * Sets the value of the updateType property.
	 * 
	 * @param value
	 *            allowed object is {@link OrderUpdateType }
	 * 
	 */
	public void setUpdateType(OrderUpdateType value) {
		this.updateType = value;
	}

}
