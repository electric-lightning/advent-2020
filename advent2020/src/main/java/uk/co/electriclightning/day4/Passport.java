package uk.co.electriclightning.day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Passport
{
    private String birthYear;
    private String issueYear;
    private String expiryYear;
    private String height;
    private String hairColour;
    private String eyeColour;
    private String passportId;
    private String countryId;

    public Passport fromSpaceSeparatedData(String[] fieldValues )
    {
        for ( String fieldValue : fieldValues )
        {
            final String[] fieldValueSplit = fieldValue.split(":" );
            final String field = fieldValueSplit[0];
            final String value = fieldValueSplit[1];

            final PassportFieldEnum passportField = PassportFieldEnum.findByCode( field );
            try
            {
                Passport.class.getDeclaredField( passportField.getFieldName() ).set( this, value );
            }
            catch (IllegalAccessException | NoSuchFieldException e)
            {
                throw new RuntimeException( "Unexpected error setting field: " + passportField.name(), e );
            }
        }

        return this;
    }

    public boolean isValid()
    {
        final List<String> errors = new ArrayList<>();

        Arrays.stream(PassportFieldEnum.values())
                .filter(PassportFieldEnum::isMandatory)
                .forEach( field -> {
                    try
                    {
                        final String val = ( String ) Passport.class.getDeclaredField(field.getFieldName()).get(this);
                        if ( val == null )
                        {
                            errors.add( field.name() );
                        }
                        else if ( !val.matches( field.getValidation() ) )
                        {
                            errors.add( field.name() );
                        }
                    }
                    catch ( final NoSuchFieldException | IllegalAccessException e)
                    {
                        throw new RuntimeException( "Unexpected error getting field: " + field.name(), e );
                    }
                });

        return errors.isEmpty();
    }
}
