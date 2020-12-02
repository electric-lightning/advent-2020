package uk.co.electriclightning.day2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * # Day 2, Part 2
 *
 * The shopkeeper suddenly realizes that he just accidentally explained the password policy rules from his old job at the sled rental place down the street! The Official Toboggan Corporate Policy actually works a little differently.
 *
 * Each policy actually describes two positions in the password, where 1 means the first character, 2 means the second character, and so on. (Be careful; Toboggan Corporate Policies have no concept of "index zero"!) Exactly one of these positions must contain the given letter. Other occurrences of the letter are irrelevant for the purposes of policy enforcement.
 *
 * Given the same example list from above:
 *
 *     1-3 a: abcde is valid: position 1 contains a and position 3 does not.
 *     1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
 *     2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
 *
 * How many passwords are valid according to the new interpretation of the policies?
 */
public class Day2Part2
{
    public static final String SPACE = " ";
    public static final String HYPHEN = "-";
    public static final String COLON = ":";
    private final List<String> values = loadData();

    public static void main( final String[] args )
    {
        new Day2Part2().solve();
    }

    private void solve()
    {
        final AtomicInteger goodPasswordCount = new AtomicInteger();

        values.forEach( input -> {

            final String[] split = input.split( SPACE );

            final String[] policyOffsets = split[0].split( HYPHEN );
            final int policyOffsetA = Integer.parseInt( policyOffsets[0] ) -1;
            final int policyOffsetB = Integer.parseInt( policyOffsets[1] ) -1;

            final String policyAlpha = split[1].split( COLON )[0];

            final String originalPassword = split[2];
            final String alphaA = originalPassword.substring( policyOffsetA, policyOffsetA + 1 );
            final String alphaB = originalPassword.substring( policyOffsetB, policyOffsetB + 1 );
            final String test = alphaA + alphaB;

            if ( test.replaceAll( policyAlpha, "" ).length() == 1 )
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

