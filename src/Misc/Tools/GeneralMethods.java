
package Misc.Tools;


public class GeneralMethods
{

    public GeneralMethods()
    {
    }

    public static Object[] removeNullEntriesFromArray(Object array[])
    {
        if(array == null) {
            return null;
        }
        int number_null_entries = 0;
        for(int i = 0; i < array.length; i++) {
            if(array[i] == null) {
                number_null_entries++;
            }
        }

        int number_valid_entries = array.length - number_null_entries;
        if(number_valid_entries == 0) {
            return null;
        }
        Object new_array[] = new Object[number_valid_entries];
        int current_index = 0;
        for(int i = 0; i < array.length; i++) {
            if(array[i] != null)
            {
                new_array[current_index] = array[i];
                current_index++;
            }
        }

        return new_array;
    }

    public static Object[] getCopyOfArray(Object given_array[])
    {
        Object new_array[] = new Object[given_array.length];
        for(int i = 0; i < new_array.length; i++)
            new_array[i] = given_array[i];

        return new_array;
    }

    public static Object[] concatenateArray(Object array_1[], Object array_2[])
    {
        int length_1 = array_1.length;
        int length_2 = array_2.length;
        Object new_array[] = new Object[length_1 + length_2];
        for(int i = 0; i < length_1; i++)
            new_array[i] = array_1[i];

        for(int j = 0; j < length_2; j++)
            new_array[length_1 + j] = array_2[j];

        return new_array;
    }
}
