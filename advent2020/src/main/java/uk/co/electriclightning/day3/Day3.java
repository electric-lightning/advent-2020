package uk.co.electriclightning.day3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * # Day 3
 *
 * The toboggan can only follow a few specific slopes (you opted for a cheaper model that prefers rational numbers); start by counting all the trees you would encounter for the slope right 3, down 1:
 *
 * From your starting position at the top-left, check the position that is right 3 and down 1. Then, check the position that is right 3 and down 1 from there, and so on until you go past the bottom of the map.
 *
 * The locations you'd check in the above example are marked here with O where there was an open square and X where there was a tree:
 *
 * ..##.........##.........##.........##.........##.........##.......  --->
 * #..O#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
 * .#....X..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
 * ..#.#...#O#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
 * .#...##..#..X...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
 * ..#.##.......#.X#.......#.##.......#.##.......#.##.......#.##.....  --->
 * .#.#.#....#.#.#.#.O..#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
 * .#........#.#........X.#........#.#........#.#........#.#........#
 * #.##...#...#.##...#...#.X#...#...#.##...#...#.##...#...#.##...#...
 * #...##....##...##....##...#X....##...##....##...##....##...##....#
 * .#..#...#.#.#..#...#.#.#..#...X.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
 *
 * In this example, traversing the map using this slope would cause you to encounter 7 trees.
 *
 * Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you encounter?
 */
public class Day3
{
    private final List<String> values = loadData();

    public static void main( final String[] args )
    {
        new Day3().solve();
    }

    private void solve()
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
            currentRow += 1;
            currentCol += 3;

            if ( currentCol >= width )
            {
                currentCol = currentCol - width;
            }
        }

        System.out.println( String.format( "Trees encountered: %d", treeCount ) );
    }

    private List<String> loadData()
    {
        final InputStream is = getClass().getClassLoader().getResourceAsStream( "day3" );
        return new BufferedReader( new InputStreamReader( is, StandardCharsets.UTF_8 ) )
                .lines()
                .collect(Collectors.toList() );
    }
}

