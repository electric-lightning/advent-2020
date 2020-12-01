package uk.co.electriclightning.day1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * # Day 1
 *
 * Before you leave, the Elves in accounting just need you to fix your expense report (your puzzle input); apparently, something isn't quite adding up.
 *
 * Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.
 *
 * For example, suppose your expense report contained the following:
 *
 * 1721
 * 979
 * 366
 * 299
 * 675
 * 1456
 *
 * In this list, the two entries that sum to 2020 are 1721 and 299. Multiplying them together produces 1721 * 299 = 514579, so the correct answer is 514579.
 */
public class Day1
{
    private static final int MAGIC_NUMBER = 2020;
    private final List<Integer> values = loadData();
    private int offset = 0;

    public static void main( final String[] args )
    {
        new Day1().solve();
    }

    private void solve()
    {
        final int x = values.get( offset++ );
        final int y = MAGIC_NUMBER - x;

        values.stream()
                .filter( number -> number == y )
                .findAny()
                .ifPresentOrElse( number -> System.out.println( x + "*" + y + "=" + ( x * y ) ), this::solve );
    }

    private List<Integer> loadData()
    {
        final InputStream is = getClass().getClassLoader().getResourceAsStream( "input" );
        return new BufferedReader( new InputStreamReader( is, StandardCharsets.UTF_8 ) )
                .lines()
                .map( Integer::parseInt )
                .collect( Collectors.toList() );
    }
}

