package uk.co.electriclightning.day9;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * --- Day 9: Encoding Error ---
 * <p>
 * With your neighbor happily enjoying their video game, you turn your attention to an open data port on the little
 * screen in the seat in front of you.
 * <p>
 * Though the port is non-standard, you manage to connect it to your computer through the clever use of several
 * paperclips. Upon connection, the port outputs a series of numbers (your puzzle input).
 * <p>
 * The data appears to be encrypted with the eXchange-Masking Addition System (XMAS) which, conveniently for you, is
 * an old cypher with an important weakness.
 * <p>
 * XMAS starts by transmitting a preamble of 25 numbers. After that, each number you receive should be the sum of any
 * two of the 25 immediately previous numbers. The two numbers will have different values, and there might be more
 * than one such pair.
 * <p>
 * For example, suppose your preamble consists of the numbers 1 through 25 in a random order. To be valid, the next
 * number must be the sum of two of those numbers:
 * <p>
 * 26 would be a valid next number, as it could be 1 plus 25 (or many other pairs, like 2 and 24).
 * 49 would be a valid next number, as it is the sum of 24 and 25.
 * 100 would not be valid; no two of the previous 25 numbers sum to 100.
 * 50 would also not be valid; although 25 appears in the previous 25 numbers, the two numbers in the pair must
 * be different.
 * <p>
 * Suppose the 26th number is 45, and the first number (no longer an option, as it is more than 25 numbers ago) was
 * 20. Now, for the next number to be valid, there needs to be some pair of numbers among 1-19, 21-25, or 45 that add
 * up to it:
 * <p>
 * 26 would still be a valid next number, as 1 and 25 are still within the previous 25 numbers.
 * 65 would not be valid, as no two of the available numbers sum to it.
 * 64 and 66 would both be valid, as they are the result of 19+45 and 21+45 respectively.
 * <p>
 * Here is a larger example which only considers the previous 5 numbers (and has a preamble of length 5):
 * <p>
 * 35
 * 20
 * 15
 * 25
 * 47
 * 40
 * 62
 * 55
 * 65
 * 95
 * 102
 * 117
 * 150
 * 182
 * 127
 * 219
 * 299
 * 277
 * 309
 * 576
 * <p>
 * In this example, after the 5-number preamble, almost every number is the sum of two of the previous 5 numbers; the
 * only number that does not follow this rule is 127.
 * <p>
 * The first step of attacking the weakness in the XMAS data is to find the first number in the list (after the
 * preamble) which is not the sum of two of the 25 numbers before it. What is the first number that does not have
 * this property?
 * <p>
 * --- Part Two ---
 * <p>
 * The final step in breaking the XMAS encryption relies on the invalid number you just found: you must find a
 * contiguous set of at least two numbers in your list which sum to the invalid number from step 1.
 * <p>
 * Again consider the above example:
 * <p>
 * 35
 * 20
 * 15
 * 25
 * 47
 * 40
 * 62
 * 55
 * 65
 * 95
 * 102
 * 117
 * 150
 * 182
 * 127
 * 219
 * 299
 * 277
 * 309
 * 576
 * <p>
 * In this list, adding up all of the numbers from 15 through 40 produces the invalid number from step 1, 127. (Of
 * course, the contiguous set of numbers in your actual list might be much longer.)
 * <p>
 * To find the encryption weakness, add together the smallest and largest number in this contiguous range; in this
 * example, these are 15 and 47, producing 62.
 * <p>
 * What is the encryption weakness in your XMAS-encrypted list of numbers?
 */
public class Day9
{
    private final List<Long> values = loadData();

    public static void main( final String[] args )
    {
        new Day9().solve();
    }

    private void solve()
    {
        int offset = 0;

        while ( canMakeTargetFromBuffer( getBuffer( offset ), getTarget( offset ) ) )
        {
            offset++;
        }

        final List<Long> numbers = getContiguousNumbersThatMakeTarget( getTarget( offset ) );
        Collections.sort( numbers );
        System.out.printf( "Contiguous numbers: %s%n", numbers.toString() );

        final Long min = numbers.stream().min( Comparator.naturalOrder() ).get();
        final Long max = numbers.stream().max( Comparator.naturalOrder() ).get();
        System.out.printf( "Smallest %d, largest %d. Added together: %d%n ", min, max, min + max );
    }

    private List<Long> getContiguousNumbersThatMakeTarget( final Long target )
    {
        int offset = 0;
        while ( offset < values.size() )
        {
            if ( addNext( values.get( offset ), offset, target ).equals( target ) )
            {
                break;
            }
            offset++;
        }

        final List<Long> contiguousNumbers = new ArrayList<>();
        while ( !contiguousNumbers.stream().reduce( 0L, Long::sum ).equals( target ) )
        {
            contiguousNumbers.add( values.get( offset++ ) );
        }

        return contiguousNumbers;
    }

    private Long addNext( final Long val, final int offset, final Long target )
    {
        final int currentOffset = offset + 1;
        final Long nextVal = values.get( currentOffset );
        if ( val.equals( target ) )
        {
            return nextVal;
        }
        else if ( val + nextVal < target )
        {
            return addNext( val + nextVal, currentOffset, target );
        }
        else
        {
            return val + nextVal;
        }
    }

    private boolean canMakeTargetFromBuffer( final List<Long> buffer, final Long target )
    {
        for ( final Long x : buffer )
        {
            final long y = target - x;
            final Optional<Long> val = buffer.stream().filter( number -> number == y ).findFirst();
            if ( val.isPresent() )
            {
                System.out.printf( "%d + %d = %d%n", x, val.get(), target );
                return true;
            }
        }

        return false;
    }

    private Long getTarget( final int offset )
    {
        return values.get( 25 + offset );
    }

    private List<Long> getBuffer( final int offset )
    {
        return values.subList( offset, 25 + offset );
    }

    private List<Long> loadData()
    {
        final InputStream is = getClass().getClassLoader().getResourceAsStream( "day9" );
        return new BufferedReader( new InputStreamReader( is, StandardCharsets.UTF_8 ) )
                .lines()
                .map( Long::valueOf )
                .collect( Collectors.toList() );
    }
}

