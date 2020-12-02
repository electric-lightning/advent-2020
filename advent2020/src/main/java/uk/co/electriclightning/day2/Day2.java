package uk.co.electriclightning.day2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * # Day 2
 *
 * 1-3 a: abcde
 * 1-3 b: cdefg
 * 2-9 c: ccccccccc
 *
 * Each line gives the password policy and then the password.
 * The password policy indicates the lowest and highest number of times a given letter must appear for the password
 * to be valid. For example, 1-3 a means that the password must contain a at least 1 time and at most 3 times.
 *
 * In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b, but
 * needs at least 1. The first and third passwords are valid: they contain one a or nine c, both within the limits
 * of their respective policies.
 *
 * How many passwords are valid according to their policies?
 *
 * 5-11 t: glhbttzvzttkdx
 */
public class Day2
{
    public static final String SPACE = " ";
    public static final String HYPHEN = "-";
    public static final String COLON = ":";
    private final List<String> values = loadData();

    public static void main( final String[] args )
    {
        new Day2().solve();
    }

    private void solve()
    {
        final AtomicInteger goodPasswordCount = new AtomicInteger();

        values.forEach( input -> {

            final String[] split = input.split( SPACE );

            final String[] policyOccurrences = split[0].split( HYPHEN );
            final String policyOccurrenceMin = policyOccurrences[0];
            final String policyOccurrenceMax = policyOccurrences[1];

            final String policyAlpha = split[1].split( COLON )[0];

            // Get pw and remove all characters other than the character of interest
            final String password = split[2].replaceAll( "[^" + policyAlpha + "]", "" );

            final String regex = String.format( "(%s{%s,%s})", policyAlpha, policyOccurrenceMin, policyOccurrenceMax );
            if ( password.matches( regex ) )
            {
                goodPasswordCount.getAndIncrement();
            }
        });

        System.out.println( String.format( "Passwords matching policy %d/%d", goodPasswordCount.get(), values.size() ) );
    }

    private List<String> loadData()
    {
        final InputStream is = getClass().getClassLoader().getResourceAsStream( "day2" );
        return new BufferedReader( new InputStreamReader( is, StandardCharsets.UTF_8 ) )
                .lines()
                .collect( Collectors.toList() );
    }
}

