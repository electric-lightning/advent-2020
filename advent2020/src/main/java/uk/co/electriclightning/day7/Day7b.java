package uk.co.electriclightning.day7;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * --- Day 7b
 * --- Part Two ---
 *
 * It's getting pretty expensive to fly these days - not because of ticket prices, but because of the ridiculous
 * number of bags you need to buy!
 *
 * Consider again your shiny gold bag and the rules from the above example:
 *
 *     faded blue bags contain 0 other bags.
 *     dotted black bags contain 0 other bags.
 *     vibrant plum bags contain 11 other bags: 5 faded blue bags and 6 dotted black bags.
 *     dark olive bags contain 7 other bags: 3 faded blue bags and 4 dotted black bags.
 *
 * So, a single shiny gold bag must contain 1 dark olive bag (and the 7 bags within it) plus 2 vibrant plum bags (and
 * the 11 bags within each of those): 1 + 1*7 + 2 + 2*11 = 32 bags!
 *
 * Of course, the actual rules have a small chance of going several levels deeper than this example; be sure to count
 * all of the bags, even if the nesting becomes topologically impractical!
 *
 * Here's another example:
 *
 * shiny gold bags contain 2 dark red bags.
 * dark red bags contain 2 dark orange bags.
 * dark orange bags contain 2 dark yellow bags.
 * dark yellow bags contain 2 dark green bags.
 * dark green bags contain 2 dark blue bags.
 * dark blue bags contain 2 dark violet bags.
 * dark violet bags contain no other bags.
 *
 * In this example, a single shiny gold bag must contain 126 other bags.
 *
 * How many individual bags are required inside your single shiny gold bag?
 */
public class Day7b
{
    private final List<Bag> values = loadData();
    private Set<String> bagsContainingGold = new HashSet<>();

    public static void main( final String[] args )
    {
        new Day7b().solve();
    }

    private void solve()
    {
        values.stream()
                .filter( bag -> bag.getColour().equals( "shiny gold" ) )
                .findFirst()
                .ifPresent( bag -> {

                    System.out.printf( "Number of bags in a gold bag: %d%n", checkForBagByColour( bag.getBags() ) );
                } );
    }

    private int checkForBagByColour( final Collection<Bag> bags )
    {
        int total = 0;
        for ( final Bag bag : bags )
        {
            if ( bag.getBags().isEmpty() )
            {
                values.stream()
                        .filter( thisBag -> thisBag.getColour().equals( bag.getColour() ) )
                        .findFirst().ifPresent( thisBag -> bag.setBags( thisBag.getBags() ) );
            }

            final int bagQuantity = bag.getQuantity();
            total += bagQuantity + ( bagQuantity * checkForBagByColour( bag.getBags() ) );
        }
        return total;
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

