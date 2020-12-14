/*
 * Copyright 2020 Applied Card Technologies Ltd
 */
package uk.co.electriclightning.day7;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chrisk
 */
public class Bag
{
    private String colour;
    private int quantity;

    private Set<Bag> bags = new HashSet<>();

    public Bag( final String colour, final int quantity )
    {
        this.colour = colour;
        this.quantity = quantity;
    }

    /**
     * @return The colour.
     */
    public String getColour()
    {
        return colour;
    }

    /**
     * @return The quantity.
     */
    public int getQuantity()
    {
        return quantity;
    }

    /**
     * @return The bags.
     */
    public Set<Bag> getBags()
    {
        return bags;
    }

    /**
     * @param bags The bags to set.
     */
    public void setBags( final Set<Bag> bags )
    {
        this.bags = bags;
    }

    public Bag add( final String colour, final int quantity )
    {
        bags.add( new Bag( colour, quantity ) );
        return this;
    }

    public boolean containsBagColour( final String bagColour )
    {
        return bags.stream().anyMatch( bag -> bagColour.equals( bag.getColour() ) );
    }

    public boolean containsColour( final String bagColour )
    {
        return this.getColour().equals( bagColour );
    }
}
