package com.redescooter.ses.web.ros.enums.assign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum YearEnum {

    YEAR_1980("1980", "A"),
    YEAR_1981("1981", "B"),
    YEAR_1982("1982", "C"),
    YEAR_1983("1983", "D"),
    YEAR_1984("1984", "E"),
    YEAR_1985("1985", "F"),
    YEAR_1986("1986", "G"),
    YEAR_1987("1987", "H"),
    YEAR_1988("1988", "J"),
    YEAR_1989("1989", "K"),
    YEAR_1990("1990", "L"),
    YEAR_1991("1991", "M"),
    YEAR_1992("1992", "N"),
    YEAR_1993("1993", "P"),
    YEAR_1994("1994", "R"),
    YEAR_1995("1995", "S"),
    YEAR_1996("1996", "T"),
    YEAR_1997("1997", "V"),
    YEAR_1998("1998", "W"),
    YEAR_1999("1999", "X"),
    YEAR_2000("2000", "Y"),
    YEAR_2001("2001", "1"),
    YEAR_2002("2002", "2"),
    YEAR_2003("2003", "3"),
    YEAR_2004("2004", "4"),
    YEAR_2005("2005", "5"),
    YEAR_2006("2006", "6"),
    YEAR_2007("2007", "7"),
    YEAR_2008("2008", "8"),
    YEAR_2009("2009", "9"),
    YEAR_2010("2010", "A"),
    YEAR_2011("2011", "B"),
    YEAR_2012("2012", "C"),
    YEAR_2013("2013", "D"),
    YEAR_2014("2014", "E"),
    YEAR_2015("2015", "F"),
    YEAR_2016("2016", "G"),
    YEAR_2017("2017", "H"),
    YEAR_2018("2018", "J"),
    YEAR_2019("2019", "K"),
    YEAR_2020("2020", "L"),
    YEAR_2021("2021", "M"),
    YEAR_2022("2022", "N"),
    YEAR_2023("2023", "P"),
    YEAR_2024("2024", "R"),
    YEAR_2025("2025", "S"),
    YEAR_2026("2026", "T"),
    YEAR_2027("2027", "V"),
    YEAR_2028("2028", "W"),
    YEAR_2029("2029", "X"),
    YEAR_2030("2030", "Y"),
    YEAR_2031("2031", "1"),
    YEAR_2032("2032", "2"),
    YEAR_2033("2033", "3"),
    YEAR_2034("2034", "4"),
    YEAR_2035("2035", "5"),
    YEAR_2036("2036", "6"),
    YEAR_2037("2037", "7"),
    YEAR_2038("2038", "8"),
    YEAR_2039("2039", "9"),
    YEAR_2040("2040", "A");

    private String key;

    private String value;

    public static String showValue(String key) {
        for (YearEnum o : YearEnum.values()) {
            if (o.getKey().equals(key)) {
                return o.getValue();
            }
        }
        return null;
    }

}
