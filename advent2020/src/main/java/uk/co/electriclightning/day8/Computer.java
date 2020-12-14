/*
 * Copyright 2020 Applied Card Technologies Ltd
 */
package uk.co.electriclightning.day8;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chrisk
 */
public class Computer
{
    private int accumulator = 0;
    private final Map<Integer, Integer> instructionCount = new LinkedHashMap<>();
    final List<String> executed = new ArrayList<>();

    public boolean runProgram( final List<String> instructions, final Integer swapOffset )
    {
        accumulator = 0;

        for( int index = 0; index < instructions.size(); index++ )
        {
            instructionCount.put( index, 0 );
        }

        Integer instructionOffset = 0;

        while ( instructionOffset < instructions.size() )
        {
            final String instruction = instructions.get( instructionOffset );
            final Integer count = instructionCount.get( instructionOffset );
            if ( count == 1 )
            {
                executed.forEach( System.out::println );
                return false;
            }

            instructionCount.put( instructionOffset, count + 1 );
            executed.add( String.format( instruction + "(%d)", instructionOffset ) );

            final String[] op = instruction.split( " " );
            if ( instructionOffset.equals( swapOffset ) )
            {
                switch ( op[ 0 ] )
                {
                    case "jmp":
                        op[ 0 ] = "nop";
                        break;
                    case "nop":
                        op[ 0 ] = "jmp";
                    default:
                }
            }

            switch( op[0] )
            {
                case "jmp":
                    instructionOffset += Integer.parseInt( op[1] );
                    break;
                case "acc":
                    accumulator += Integer.parseInt( op[ 1 ] );
                case "nop":
                    instructionOffset++;
                    break;
                default:
                    return true;
            }
        }

        return true;
    }

    public int getAccumulatorValue()
    {
        return accumulator;
    }
}
