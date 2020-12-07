/*
 * Copyright 2020 Applied Card Technologies Ltd
 */
package uk.co.electriclightning.day5;

/**
 * @author chrisk
 */
public class PassengerData
{
    char[] rowData;
    char[] aisleData;

    public PassengerData( final char[] rowData, final char[] aisleData )
    {
        this.rowData = rowData;
        this.aisleData = aisleData;
    }
}
