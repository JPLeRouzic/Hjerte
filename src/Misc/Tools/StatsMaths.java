
package Misc.Tools;


public class StatsMaths
{

    public StatsMaths()
    {
    }

    public static float getAverage(float data[])
    {
        if(data.length < 1)
            return 0.0F;
        float sum = 0.0F;
        for(int i = 0; i < data.length; i++)
            sum += data[i];

        return sum / (float)data.length;
    }

    public static float getAverage(int data[])
    {
        if(data.length < 1)
            return 0.0F;
        float sum = 0.0F;
        for(int i = 0; i < data.length; i++)
            sum += data[i];

        return sum / (float)data.length;
    }

    public static float[] getDiff(float data[])
        throws Exception
    {
        float datb[] = new float[data.length - 1];
        if(data.length < 2)
            throw new IllegalArgumentException("Wrong size, should be greater than 2");
        for(int i = 1; i < data.length; i++)
        {
            float tmp = data[i] - data[i - 1];
            datb[i - 1] = tmp;
        }

        return datb;
    }

    public static float[][] reshape(float A[][], int m, int n)
    {
        int origM = A.length;
        int origN = A[0].length;
        if(origM * origN != m * n)
            throw new IllegalArgumentException("New matrix must be of same area as matix A");
        float B[][] = new float[m][n];
        float A1D[] = new float[A.length * A[0].length];
        int index = 0;
        for(int i = 0; i < A.length; i++)
        {
            for(int j = 0; j < A[0].length; j++)
                A1D[index++] = A[i][j];

        }

        index = 0;
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++)
                B[j][i] = A1D[index++];

        }

        return B;
    }

    public static float getStandardDeviation(float data[])
    {
        if(data.length < 2)
            return 0.0F;
        float average = getAverage(data);
        float sum = 0.0F;
        for(int i = 0; i < data.length; i++)
        {
            float diff = data[i] - average;
            sum += diff * diff;
        }

        return (float)Math.sqrt(sum / (float)(data.length - 1));
    }

    public static float getStandardDeviation(int data[])
    {
        if(data.length < 2)
            return 0.0F;
        float average = getAverage(data);
        float sum = 0.0F;
        for(int i = 0; i < data.length; i++)
        {
            float diff = (float)data[i] - average;
            sum += diff * diff;
        }

        return (float)Math.sqrt(sum / (float)(data.length - 1));
    }

    public static boolean isFactorOrMultiple(int x, int y, int z[])
    {
        boolean is_factor_or_multiple = false;
        if(y > x)
        {
            for(int i = 0; i < z.length; i++)
                if(x * z[i] == y)
                {
                    is_factor_or_multiple = true;
                    i = z.length + 1;
                }

        } else
        {
            for(int i = 0; i < z.length; i++)
                if(y * z[i] == x)
                {
                    is_factor_or_multiple = true;
                    i = z.length + 1;
                }

        }
        return is_factor_or_multiple;
    }

    public static int getIndexOfSmallest(float values[])
    {
        int min_index = 0;
        for(int i = 0; i < values.length; i++)
            if(values[i] < values[min_index])
                min_index = i;

        return min_index;
    }

    public static int getIndexOfLargest(float values[])
    {
        int max_index = 0;
        for(int i = 0; i < values.length; i++)
            if(values[i] > values[max_index])
                max_index = i;

        return max_index;
    }

    public static int getIndexOfLargest(int values[])
    {
        int max_index = 0;
        for(int i = 0; i < values.length; i++)
            if(values[i] > values[max_index])
                max_index = i;

        return max_index;
    }

    public static float calculateEuclideanDistance(float x[], float y[])
        throws Exception
    {
        if(x.length != y.length)
            throw new Exception("The two given arrays have different sizes.");
        float total = 0.0F;
        for(int dim = 0; dim < x.length; dim++)
            total = (float)((double)total + Math.pow(x[dim] - y[dim], 2D));

        return (float)Math.sqrt(total);
    }

    public static int generateRandomNumber(int max)
    {
        int random_number = (int)(2147483648D * Math.random());
        return random_number % max;
    }

    public static int[] getRandomOrdering(int number_entries)
    {
        float random_values[] = new float[number_entries];
        for(int i = 0; i < random_values.length; i++)
            random_values[i] = (float)Math.random();

        int scrambled_values[] = new int[number_entries];
        for(int i = 0; i < scrambled_values.length; i++)
        {
            int largest_index = getIndexOfLargest(random_values);
            scrambled_values[i] = largest_index;
            random_values[largest_index] = -1F;
        }

        return scrambled_values;
    }

    public static float getArraySum(float to_sum[])
    {
        float sum = 0.0F;
        for(int i = 0; i < to_sum.length; i++)
            sum += to_sum[i];

        return sum;
    }

    public static float[] normalize(float to_normalize[])
    {
        float normalized[] = new float[to_normalize.length];
        for(int i = 0; i < normalized.length; i++)
            normalized[i] = to_normalize[i];

        float sum = getArraySum(normalized);
        for(int i = 0; i < normalized.length; i++)
            normalized[i] = normalized[i] / sum;

        return normalized;
    }

    public static float[][] normalize(float to_normalize[][])
    {
        float normalized[][] = new float[to_normalize.length][];
        for(int i = 0; i < normalized.length; i++)
        {
            normalized[i] = new float[to_normalize[i].length];
            for(int j = 0; j < normalized[i].length; j++)
                normalized[i][j] = to_normalize[i][j];

        }

        float totals[] = new float[normalized.length];
        for(int i = 0; i < normalized.length; i++)
        {
            totals[i] = 0.0F;
            for(int j = 0; j < normalized[i].length; j++)
                totals[i] += normalized[i][j];

        }

        for(int i = 0; i < normalized.length; i++)
        {
            for(int j = 0; j < normalized[i].length; j++)
                normalized[i][j] = normalized[i][j] / totals[i];

        }

        return normalized;
    }

    public static int pow(int a, int b)
    {
        int result = a;
        for(int i = 1; i < b; i++)
            result *= a;

        return result;
    }

    public static float logBaseN(float x, float n)
    {
        return (float)(Math.log10(x) / Math.log10(n));
    }

    public static int ensureIsPowerOfN(int x, int n)
    {
        float log_value = logBaseN(x, n);
        int log_int = (int)log_value;
        int valid_size = pow(n, log_int);
        if(valid_size != x)
            valid_size = pow(n, log_int + 1);
        return valid_size;
    }
}
