package uk.co.electriclightning.day6;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * --- Day 6: Custom Customs ---
 *
 * --- Part Two ---
 *
 * As you finish the last group's customs declaration, you notice that you misread one word in the instructions:
 *
 * You don't need to identify the questions to which anyone answered "yes"; you need to identify the questions to
 * which everyone answered "yes"!
 *
 * Using the same example as above:
 *
 * abc
 *
 * a
 * b
 * c
 *
 * ab
 * ac
 *
 * a
 * a
 * a
 * a
 *
 * b
 *
 * This list represents answers from five groups:
 *
 *     In the first group, everyone (all 1 person) answered "yes" to 3 questions: a, b, and c.
 *     In the second group, there is no question to which everyone answered "yes".
 *     In the third group, everyone answered yes to only 1 question, a. Since some people did not answer "yes" to b
 *     or c, they don't count.
 *     In the fourth group, everyone answered yes to only 1 question, a.
 *     In the fifth group, everyone (all 1 person) answered "yes" to 1 question, b.
 *
 * In this example, the sum of these counts is 3 + 0 + 1 + 1 + 1 = 6.
 *
 * For each group, count the number of questions to which everyone answered "yes". What is the sum of those counts?
 */
public class Day6b
{
    private static final String SPACE = " ";
    private final List<String> values = loadData();

    public static void main( final String[] args )
    {
        new Day6b().solve();
    }

    private void solve()
    {
        int total = 0;

        for ( final String spaceDelimitedRawData : values )
        {
            total += new CustomsForm()
                    .fromSpaceSeparatedData( spaceDelimitedRawData.split( SPACE ) )
                    .getCountOfQuestionsEveryoneAnsweredYes();
        }

        System.out.println( "Total questions answered yes: " + total );
    }

    private List<String> loadData()
    {
        final InputStream is = getClass().getClassLoader().getResourceAsStream( "day6" );
        return Arrays.asList( new BufferedReader( new InputStreamReader( is, StandardCharsets.UTF_8 ) )
                .lines()
                .collect( Collectors.joining( "\n" ) )
                .replaceAll( "\n\n", "," )
                .replaceAll( "\n", " " )
                .split( "," ) );
    }
}

