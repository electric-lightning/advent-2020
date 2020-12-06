package uk.co.electriclightning.day4;

import java.util.Arrays;

public enum PassportFieldEnum
{
    BIRTH_YEAR( "byr", "birthYear", true, "(19[23456789]\\d|2000|2001|2002)" ),
    ISSUE_YEAR( "iyr", "issueYear", true, "(20[1]\\d|2020)"  ),
    EXPIRY_YEAR( "eyr", "expiryYear", true, "(20[2]\\d|2030)" ),
    HEIGHT( "hgt", "height",true, "^(1[5678]\\d|19[0123])cm|(59|6[0-9]|7[0-6])in$" ),
    HAIR_COLOUR( "hcl", "hairColour",true, "^#([a-f0-9]{6})$" ),
    EYE_COLOUR( "ecl", "eyeColour",true, "^(amb|blu|brn|gry|grn|hzl|oth)$" ),
    PASSPORT_ID( "pid", "passportId",true, "^\\d{9}$" ),
    COUNTRY_ID( "cid", "countryId",false, ".*" );

    private String fieldCode;
    private String fieldName;
    private boolean mandatory;
    private String validation;

    PassportFieldEnum( final String fieldCode, final String fieldName, final boolean mandatory, final String validation )
    {
        this.fieldCode = fieldCode;
        this.fieldName = fieldName;
        this.mandatory = mandatory;
        this.validation = validation;
    }

    public static PassportFieldEnum findByCode( final String code )
    {
        return Arrays.stream( PassportFieldEnum.values() )
                .filter( field -> field.fieldCode.equals( code ) )
                .findFirst()
                .orElse( null );
    }

    public boolean isMandatory()
    {
        return mandatory;
    }

    public String getValidation()
    {
        return validation;
    }

    public String getFieldName()
    {
        return fieldName;
    }
}
