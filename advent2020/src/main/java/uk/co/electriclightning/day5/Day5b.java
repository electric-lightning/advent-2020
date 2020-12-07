package uk.co.electriclightning.day5;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * # Day 5b
 * <p>
 * Ding! The "fasten seat belt" signs have turned on. Time to find your seat.
 * <p>
 * It's a completely full flight, so your seat should be the only missing boarding pass in your list. However,
 * there's a catch: some of the seats at the very front and back of the plane don't exist on this aircraft, so
 * they'll be missing from your list as well.
 * <p>
 * Your seat wasn't at the very front or back, though; the seats with IDs +1 and -1 from yours will be in your list.
 * <p>
 * What is the ID of your seat?
 */
public class Day5b
{
    private static final List<Integer> ROWS = IntStream.rangeClosed( 0, 127 ).boxed().collect( Collectors.toList() );
    private static final List<Integer> AISLES = IntStream.rangeClosed( 0, 7 ).boxed().collect( Collectors.toList() );
    private final List<PassengerData> values = loadData();
    private int rowOffset;
    private int aisleOffset;

    public static void main( final String[] args )
    {
        new Day5b().solve();
    }

    private void solve()
    {
        final List<Integer> seatIds = new ArrayList<>();

        values.forEach( passengerData -> {

            rowOffset = aisleOffset = 0;
            final int row = determineRow( passengerData.rowData, new ArrayList<>( ROWS ) );
            final int aisle = determineAisle( passengerData.aisleData, new ArrayList<>( AISLES ) );

            seatIds.add( ( row * 8 ) + aisle );
        } );

        // Discover the min/max seatId from the sorted list
        final int min = seatIds.stream().min( Comparator.naturalOrder() ).get();
        final int max = seatIds.stream().max( Comparator.naturalOrder() ).get();

        // Create a set containing all Ids between min and max (a complete list of seat Ids). Then use the set
        // 'remove all' functionality to remove the seat ids we know about leaving my seat.
        final Set<Integer> allSeatIds = IntStream.rangeClosed( min, max ).boxed().collect( Collectors.toSet() );
        allSeatIds.removeAll( seatIds );


        allSeatIds.stream().findFirst()
                .ifPresent( seatId -> System.out.printf( "My seatId: %d%n", seatId ) );
    }

    private int determineAisle( final char[] aisleData, final List<Integer> seats )
    {
        if ( seats.size() == 1 )
        {
            return seats.get( 0 );
        }
        if ( 'L' == aisleData[ aisleOffset++ ] )
        {
            return determineAisle( aisleData, seats.subList( 0, seats.size() / 2 ) );
        }
        else
        {
            return determineAisle( aisleData, seats.subList( seats.size() / 2, seats.size() ) );
        }
    }

    private int determineRow( final char[] rowData, final List<Integer> remaining )
    {
        if ( remaining.size() == 1 )
        {
            return remaining.get( 0 );
        }

        if ( 'F' == rowData[ rowOffset++ ] )
        {
            return determineRow( rowData, remaining.subList( 0, remaining.size() / 2 ) );
        }
        else
        {
            return determineRow( rowData, remaining.subList( remaining.size() / 2, remaining.size() ) );
        }
    }

    private List<PassengerData> loadData()
    {
        final List<PassengerData> passengerData = new ArrayList<>();

        final InputStream is = getClass().getClassLoader().getResourceAsStream( "day5" );
        new BufferedReader( new InputStreamReader( is, StandardCharsets.UTF_8 ) )
                .lines()
                .collect( Collectors.toList() )
                .forEach( data ->
                        passengerData.add(
                                new PassengerData(
                                        data.substring( 0, 7 ).toCharArray(),
                                        data.substring( 7 ).toCharArray() ) ) );

        return passengerData;
    }
}

