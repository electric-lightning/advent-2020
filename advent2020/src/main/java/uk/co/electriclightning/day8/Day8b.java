package uk.co.electriclightning.day8;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * --- Part Two ---
 * <p>
 * After some careful analysis, you believe that exactly one instruction is corrupted.
 * <p>
 * Somewhere in the program, either a jmp is supposed to be a nop, or a nop is supposed to be a jmp. (No acc
 * instructions were harmed in the corruption of this boot code.)
 * <p>
 * The program is supposed to terminate by attempting to execute an instruction immediately after the last
 * instruction in the file. By changing exactly one jmp or nop, you can repair the boot code and make it terminate
 * correctly.
 * <p>
 * For example, consider the same program from above:
 * <p>
 * nop +0
 * acc +1
 * jmp +4
 * acc +3
 * jmp -3
 * acc -99
 * acc +1
 * jmp -4
 * acc +6
 * <p>
 * If you change the first instruction from nop +0 to jmp +0, it would create a single-instruction infinite loop,
 * never leaving that instruction. If you change almost any of the jmp instructions, the program will still
 * eventually find another jmp instruction and loop forever.
 * <p>
 * However, if you change the second-to-last instruction (from jmp -4 to nop -4), the program terminates! The
 * instructions are visited in this order:
 * <p>
 * nop +0  | 1
 * acc +1  | 2
 * jmp +4  | 3
 * acc +3  |
 * jmp -3  |
 * acc -99 |
 * acc +1  | 4
 * nop -4  | 5
 * acc +6  | 6
 * <p>
 * After the last instruction (acc +6), the program terminates by attempting to run the instruction below the last
 * instruction in the file. With this change, after the program terminates, the accumulator contains the value 8 (acc
 * +1, acc +1, acc +6).
 * <p>
 * Fix the program so that it terminates normally by changing exactly one jmp (to nop) or nop (to jmp). What is the
 * value of the accumulator after the program terminates?
 */
public class Day8b
{
    private final List<String> values = loadData();

    public static void main( final String[] args )
    {
        new Day8b().solve();
    }

    private void solve()
    {
        final Computer computer = new Computer();
        for ( int offset = 0; offset < values.size(); offset++ )
        {
            if ( computer.runProgram( values, offset ) )
            {
                System.out.println( "Program executed to completion." );
                break;
            }
        }
        System.out.println( "Accumulator value: " + computer.getAccumulatorValue() );
    }

    private List<String> loadData()
    {
        final InputStream is = getClass().getClassLoader().getResourceAsStream( "day8" );
        return new BufferedReader( new InputStreamReader( is, StandardCharsets.UTF_8 ) )
                .lines()
                .collect( Collectors.toList() );
    }
}

