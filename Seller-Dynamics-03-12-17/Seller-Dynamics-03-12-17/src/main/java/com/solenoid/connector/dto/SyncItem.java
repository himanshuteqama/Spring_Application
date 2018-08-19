/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.dto;
/**
 * DTO for SyncItem, to hold date for perform sync operation.
 *
 */
public class SyncItem {

	private String sdToExact;

    private String exactToSd;

    public String getSdToExact ()
    {
        return sdToExact;
    }

    public void setSdToExact (String sdToExact)
    {
        this.sdToExact = sdToExact;
    }

    public String getExactToSd ()
    {
        return exactToSd;
    }

    public void setExactToSd (String exactToSd)
    {
        this.exactToSd = exactToSd;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sdToExact = "+sdToExact+", exactToSd = "+exactToSd+"]";
    }
}
