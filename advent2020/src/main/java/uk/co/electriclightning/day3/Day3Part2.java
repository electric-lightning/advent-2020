package uk.co.electriclightning.day3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * # Day 3, part 2
 *
 * Time to check the rest of the slopes - you need to minimize the probability of a sudden arboreal stop, after all.
 *
 * Determine the number of trees you would encounter if, for each of the following slopes, you start at the top-left corner and traverse the map all the way to the bottom:
 *
 *     Right 1, down 1.
 *     Right 3, down 1. (This is the slope you already checked.)
 *     Right 5, down 1.
 *     Right 7, down 1.
 *     Right 1, down 2.
 *
 * In the above example, these slopes would find 2, 7, 3, 4, and 2 tree(s) respectively; multiplied together, these produce the answer 336.
 *
 * What do you get if you multiply together the number of trees encountered on each of the listed slopes?
 */
public class Day3Part2
{
    private final List<String> values = loadData();

    public static void main( final String[] args )
    {
        new Day3Part2().solve();
    }

    private void solve()
    {
        int[][] slopes = { {1,1}, {3,1}, {5,1}, {7,1}, {1,2} };

        int answer = 0;
        for ( int[] slope : slopes )
        {
            int treeCount = getTreeCountForSlope( slope[0], slope[1] );
            answer = answer == 0 ? treeCount : answer * treeCount;
            System.out.printf( "Trees encountered on slope %s: %d\n", Arrays.toString( slope ), treeCount );
        }

        System.out.printf( "Trees encountered on slopes multiplied together: %d", answer );
    }

    private int getTreeCountForSlope(final int colIncrement, final int rowIncrement )
    {
        int width = values.get(0).length();
        int height = values.size();

        char[][] grid = new char[height][width];
        int row = 0;
        for ( String line : values )
        {
            grid[row++] = line.toCharArray();
        }

        int currentRow = 0;
        int currentCol = 0;
        int treeCount = 0;
        while ( currentRow < height )
        {
            if ( grid[currentRow][currentCol] == '#' )
            {
                treeCount++;
            }

            currentRow += rowIncrement;
            currentCol += colIncrement;

            // Look out for array boundary and recalculate column
            if ( currentCol >= width )
            {
                currentCol = currentCol - width;
            }
        }

        return treeCount;
    }

    private List<String> loadData()
    {
        final InputStream is = getClass().getClassLoader().getResourceAsStream( "day3" );
        return new BufferedReader( new InputStreamReader( is, StandardCharsets.UTF_8 ) )
                .lines()
                .collect(Collectors.toList() );
    }
}

