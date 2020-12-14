package uk.co.electriclightning.day7;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * --- Day 7: Handy Haversacks ---
 * <p>
 * You land at the regional airport in time for your next flight. In fact, it looks like you'll even have time to
 * grab some food: all flights are currently delayed due to issues in luggage processing.
 * <p>
 * Due to recent aviation regulations, many rules (your puzzle input) are being enforced about bags and their
 * contents; bags must be color-coded and must contain specific quantities of other color-coded bags. Apparently,
 * nobody responsible for these regulations considered how long they would take to enforce!
 * <p>
 * For example, consider the following rules:
 * <p>
 * light red bags contain 1 bright white bag, 2 muted yellow bags.
 * dark orange bags contain 3 bright white bags, 4 muted yellow bags.
 * bright white bags contain 1 shiny gold bag.
 * muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
 * shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
 * dark olive bags contain 3 faded blue bags, 4 dotted black bags.
 * vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
 * faded blue bags contain no other bags.
 * dotted black bags contain no other bags.
 * <p>
 * These rules specify the required contents for 9 bag types. In this example, every faded blue bag is empty, every
 * vibrant plum bag contains 11 bags (5 faded blue and 6 dotted black), and so on.
 * <p>
 * You have a shiny gold bag. If you wanted to carry it in at least one other bag, how many different bag colors
 * would be valid for the outermost bag? (In other words: how many colors can, eventually, contain at least one shiny
 * gold bag?)
 * <p>
 * In the above rules, the following options would be available to you:
 * <p>
 * A bright white bag, which can hold your shiny gold bag directly.
 * A muted yellow bag, which can hold your shiny gold bag directly, plus some other bags.
 * A dark orange bag, which can hold bright white and muted yellow bags, either of which could then hold your
 * shiny gold bag.
 * A light red bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny
 * gold bag.
 * <p>
 * So, in this example, the number of bag colors that can eventually contain at least one shiny gold bag is 4.
 * <p>
 * How many bag colors can eventually contain at least one shiny gold bag? (The list of rules is quite long; make
 * sure you get all of it.)
 */
public class Day7
{
    private final List<Bag> values = loadData();
    private Set<String> bagsContainingGold = new HashSet<>();

    public static void main( final String[] args )
    {
        new Day7().solve();
    }

    private void solve()
    {
        checkForBagByColour( "shiny gold", values );

        System.out.printf( "Number of bags that could contain a shiny gold bag: %d/%d%n",
                bagsContainingGold.size(),
                values.size() );
    }

    private void checkForBagByColour( final String bagColour, final List<Bag> bags )
    {
        for ( final Bag bag : bags )
        {
            if ( bag.containsBagColour( bagColour ) )
            {
                bagsContainingGold.add( bag.getColour() );
                checkForBagByColour( bag.getColour(), bags );
            }
        }
    }

    private List<Bag> loadData()
    {
        final List<Bag> bags = new ArrayList<>();

        final InputStream is = getClass().getClassLoader().getResourceAsStream( "day7" );
        new BufferedReader( new InputStreamReader( is, StandardCharsets.UTF_8 ) )
                .lines()
                .collect( Collectors.toList() )
                .forEach( line -> {

                    final String[] bagBags = line.split( "contain" );

                    final Bag bag = new Bag( getBagName( bagBags[ 0 ] ), 1 );

                    for ( final String bagDesc : bagBags[ 1 ].split( "([,.])" ) )
                    {
                        if ( !"no other bags".equals( bagDesc.trim() ) )
                        {
                            bag.add( getBagName( bagDesc ), getQuantity( bagDesc ) );
                        }
                    }

                    bags.add( bag );
                } );

        return bags;
    }

    private String getBagName( final String in )
    {
        return in.replaceAll( "(\\d+|bags?)", "" ).trim();
    }

    private int getQuantity( final String in )
    {
        return Integer.parseInt( in.replaceAll( "[^\\d+]", "" ).trim() );
    }
}

