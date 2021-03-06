//
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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfRetailerPricingProfile complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRetailerPricingProfile"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RetailerPricingProfile" type="{https://my.sellerdynamics.com/}RetailerPricingProfile" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRetailerPricingProfile", propOrder = { "retailerPricingProfile" })
public class ArrayOfRetailerPricingProfile {

	@XmlElement(name = "RetailerPricingProfile", nillable = true)
	protected List<RetailerPricingProfile> retailerPricingProfile;

	/**
	 * Gets the value of the retailerPricingProfile property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the retailerPricingProfile property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getRetailerPricingProfile().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link RetailerPricingProfile }
	 * 
	 * 
	 */
	public List<RetailerPricingProfile> getRetailerPricingProfile() {
		if (retailerPricingProfile == null) {
			retailerPricingProfile = new ArrayList<RetailerPricingProfile>();
		}
		return this.retailerPricingProfile;
	}

}
