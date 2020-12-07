package uk.co.electriclightning.day6;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomsForm
{
    private final Map<String, Integer> questionsAnsweredYesByAll = new HashMap<>();
    private int lineCount = 0;

    public CustomsForm fromSpaceSeparatedData( final String[] fieldValues )
    {
        lineCount = fieldValues.length;

        Arrays.stream( fieldValues ).forEach( line -> {
            Arrays.asList( line.split( "" ) ).stream().forEach( letter -> {

                final Integer total = questionsAnsweredYesByAll.putIfAbsent( letter, 1 );
                if ( total != null )
                {
                    questionsAnsweredYesByAll.put( letter, total + 1 );
                }
            } );
        } );
        return this;
    }

    public int getCountOfQuestionsEveryoneAnsweredYes()
    {
        int total = 0;

        for ( final String letter : questionsAnsweredYesByAll.keySet() )
        {
           if ( questionsAnsweredYesByAll.get( letter ) == lineCount )
           {
                total++;
           }
        }

        return total;
    }
}
