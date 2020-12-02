package uk.co.electriclightning.day1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * # Day 1
 *
 * --- Part Two ---
 *
 * The Elves in accounting are thankful for your help; one of them even offers you a starfish coin they had left over from a past vacation. They offer you a second one if you can find three numbers in your expense report that meet the same criteria.
 *
 * Using the above example again, the three entries that sum to 2020 are 979, 366, and 675. Multiplying them together produces the answer, 241861950.
 */
public class Day1Part2
{
    private static final int MAGIC_NUMBER = 2020;
    private final List<Integer> values = loadData();
    private int offset = 0;
    private boolean done = false;

    public static void main( final String[] args )
    {
        new Day1Part2().solve();
    }

    private void solve()
    {
        final Iterator<Integer> iterator = values.iterator();
        while ( iterator.hasNext() && !done )
        {
            offset = 0;
            checkNumbers( iterator.next() );
        }
    }

    private void checkNumbers(final int numX )
    {
        final int numY = values.get( offset );
        final int numZ = MAGIC_NUMBER - numX - numY;

        final Optional<Integer> val = values.stream()
                .filter( number -> number == numZ )
                .findAny();
        if ( val.isEmpty() )
        {
            while( ++offset < values.size() && !done )
            {
                checkNumbers( numX );
            }
        }
        else
        {
            System.out.println( numX + "*" + numY + "*" + numZ + "=" + (numX * numY * numZ) );
            done = true;
        }
    }

    private List<Integer> loadData()
    {
        final InputStream is = getClass().getClassLoader().getResourceAsStream("day1" );
        return new BufferedReader( new InputStreamReader( is, StandardCharsets.UTF_8 ) )
                .lines()
                .map( Integer::parseInt )
                .collect( Collectors.toList() );
    }
}

