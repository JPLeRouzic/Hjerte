
package Misc.Tools;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

// Referenced classes of package Misc.Tools:
//            GeneralMethods

public class StringMethods
{

    public StringMethods()
    {
    }

    public static String convertFilePathToFileName(String file_path)
    {
        return file_path.substring(file_path.lastIndexOf(File.separator) + 1, file_path.length());
    }

    public static String getDirectoryName(String file_path)
        throws Exception
    {
        int index_of_last_separator = file_path.lastIndexOf(File.separatorChar);
        if(index_of_last_separator == -1)
            throw new Exception((new StringBuilder()).append(file_path).append(" does not contain a valid directory separator.").toString());
        else
            return new String((new StringBuilder()).append(file_path.substring(0, file_path.lastIndexOf(File.separator))).append(File.separator).toString());
    }

    public static String removeExtension(String filename)
    {
        if(filename.length() < 5)
            return null;
        if(filename.charAt(filename.length() - 4) != '.')
        {
            if(filename.charAt(filename.length() - 5) == '.')
                return filename.substring(0, filename.length() - 5);
            if(filename.charAt(filename.length() - 3) == '.')
                return filename.substring(0, filename.length() - 3);
            else
                return null;
        } else
        {
            return filename.substring(0, filename.length() - 4);
        }
    }

    public static String getExtension(String filename)
    {
        if(filename.length() < 5)
            return null;
        if(filename.charAt(filename.length() - 4) != '.')
        {
            if(filename.charAt(filename.length() - 5) == '.')
                return filename.substring(filename.length() - 5, filename.length());
            if(filename.charAt(filename.length() - 3) == '.')
                return filename.substring(filename.length() - 3, filename.length());
            else
                return null;
        } else
        {
            return filename.substring(filename.length() - 4, filename.length());
        }
    }

    public static String getBeginningOfString(String string_to_shorten, int number_characters)
    {
        String copy = new String(string_to_shorten);
        if(string_to_shorten.length() < number_characters)
        {
            int difference = number_characters - string_to_shorten.length();
            for(int i = 0; i < difference; i++)
                copy = (new StringBuilder()).append(copy).append(" ").toString();

            return copy;
        }
        if(string_to_shorten.length() > number_characters)
            return string_to_shorten.substring(0, number_characters);
        else
            return copy;
    }

    public static String getBeginningOfStringWithHyphenFiller(String string_to_shorten, int number_characters)
    {
        String copy = new String(string_to_shorten);
        if(string_to_shorten.length() < number_characters)
        {
            int difference = number_characters - string_to_shorten.length();
            for(int i = 0; i < difference; i++)
                if(i == 0 || i == 1 || i == difference - 2 || i == difference - 1)
                    copy = (new StringBuilder()).append(copy).append(" ").toString();
                else
                    copy = (new StringBuilder()).append(copy).append("-").toString();

            return copy;
        }
        if(string_to_shorten.length() > number_characters)
            return string_to_shorten.substring(0, number_characters);
        else
            return copy;
    }

    public static String getDoubleInScientificNotation(float number_to_round, int significant_digits)
    {
        if(Double.isNaN(number_to_round))
            return new String("NaN");
        if(Double.isInfinite(number_to_round))
            return new String("Infinity");
        String format_pattern = "0.";
        for(int i = 0; i < significant_digits - 1; i++)
            format_pattern = (new StringBuilder()).append(format_pattern).append("#").toString();

        format_pattern = (new StringBuilder()).append(format_pattern).append("E0").toString();
        NumberFormat formatter = NumberFormat.getInstance(Locale.ENGLISH);
        DecimalFormat decimal_formatter = (DecimalFormat)formatter;
        decimal_formatter.applyPattern(format_pattern);
        return decimal_formatter.format(number_to_round);
    }

    public static String getRoundedDouble(float number_to_round, int decimal_places)
    {
        if(Double.isNaN(number_to_round))
            return new String("NaN");
        if((double)number_to_round == (-1.0D / 0.0D))
            return new String("-Infinity");
        if((double)number_to_round == (1.0D / 0.0D))
            return new String("Infinity");
        String format_pattern = "#0.";
        for(int i = 0; i < decimal_places; i++)
            format_pattern = (new StringBuilder()).append(format_pattern).append("#").toString();

        DecimalFormat formatter = new DecimalFormat(format_pattern);
        return formatter.format(number_to_round);
    }

    public static int getIndexOfString(String given_name, String possible_names[])
        throws Exception
    {
        for(int i = 0; i < possible_names.length; i++)
            if(given_name.equals(possible_names[i]))
                return i;

        throw new Exception((new StringBuilder()).append("Unable to find ").append(given_name).append(".").toString());
    }

    public static String[] removeDoubles(String strings[])
    {
        String editable_strings[] = new String[strings.length];
        for(int i = 0; i < editable_strings.length; i++)
            editable_strings[i] = strings[i];

        for(int i = 0; i < editable_strings.length - 1; i++)
        {
            for(int j = i + 1; j < editable_strings.length; j++)
                if(editable_strings[i] != null && editable_strings[j] != null && editable_strings[i].equals(editable_strings[j]))
                    editable_strings[j] = null;

        }

        Object cleaned_obj[] = GeneralMethods.removeNullEntriesFromArray(editable_strings);
        String cleaned_strings[] = new String[cleaned_obj.length];
        for(int i = 0; i < cleaned_strings.length; i++)
            cleaned_strings[i] = (String)cleaned_obj[i];

        return cleaned_strings;
    }
}
