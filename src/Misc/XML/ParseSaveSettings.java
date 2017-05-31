
package Misc.XML;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

// Referenced classes of package Misc.XML:
//            ParseFileHandler

public class ParseSaveSettings extends ParseFileHandler
{

    public ParseSaveSettings()
    {
        tagType = -1;
        inFeature = false;
        count = 0;
        outputType = "";
        t = Pattern.compile(".*true.*");
        f = Pattern.compile(".*false.*");
    }

    public void startDocument()
        throws SAXException
    {
        checkedMap_ = new HashMap();
        tmpAttributes_ = new LinkedList();
        attributeMap_ = new HashMap();
        aggregatorNames = new LinkedList();
        aggregatorFeatures = new LinkedList();
        aggregatorParameters = new LinkedList();
        tmpAggregatorFeature_ = new LinkedList();
        tmpAggregatorParameters_ = new LinkedList();
        count = 0;
        tagType = -1;
    }

    public void characters(char ch[], int start, int length)
        throws SAXException
    {
        String tmp = new String(ch, start, length);
        tmp = tmp.substring(start, start + length);
        switch(tagType)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 12: // '\f'
            break;

        case 2: // '\002'
            windowLength = tmp;
            break;

        case 3: // '\003'
            windowOverlap = tmp;
            break;

        case 4: // '\004'
            try
            {
                sampleRate = Float.parseFloat(tmp);
            }
            catch(NumberFormatException e)
            {
                throw new SAXException("sampleRate must be a float");
            }
            break;

        case 5: // '\005'
            tm = t.matcher(tmp);
            fm = f.matcher(tmp);
            if(tm.matches())
            {
                savePerWindow = true;
                break;
            }
            if(fm.matches())
                savePerWindow = false;
            else
                throw new SAXException((new StringBuilder()).append("Valid states are true or false, not ").append(tmp).toString());
            break;

        case 6: // '\006'
            tm = t.matcher(tmp);
            fm = f.matcher(tmp);
            if(tm.matches())
            {
                saveOverall = true;
                break;
            }
            if(fm.matches())
                saveOverall = false;
            else
                throw new SAXException((new StringBuilder()).append("Valid states are true or false, not ").append(tmp).toString());
            break;

        case 7: // '\007'
            tm = t.matcher(tmp);
            fm = f.matcher(tmp);
            if(tm.matches())
            {
                normalise = true;
                break;
            }
            if(fm.matches())
                normalise = false;
            else
                throw new SAXException((new StringBuilder()).append("Valid states are true or false, not ").append(tmp).toString());
            break;

        case 8: // '\b'
            outputType = tmp;
            break;

        case 9: // '\t'
            tm = t.matcher(tmp);
            fm = f.matcher(tmp);
            if(tm.matches())
            {
                checkedMap_.put(name, Boolean.valueOf(true));
                break;
            }
            if(fm.matches())
                checkedMap_.put(name, Boolean.valueOf(false));
            else
                throw new SAXException((new StringBuilder()).append("Valid states are true or false, not ").append(tmp).toString());
            break;

        case 10: // '\n'
            tmpAttributes_.add(tmp);
            break;

        case 11: // '\013'
            name = tmp;
            break;

        case 13: // '\r'
            aggregatorNames.add(tmp);
            break;

        case 14: // '\016'
            tmpAggregatorFeature_.add(tmp);
            break;

        case 15: // '\017'
            tmpAggregatorParameters_.add(tmp);
            break;

        default:
            throw new SAXException((new StringBuilder()).append("Unknwon Tag Type ").append(tagType).append("in characters").toString());
        }
    }

    public void endDocument()
        throws SAXException
    {
        parsed_file_contents = new Object[12];
        int i = 0;
        parsed_file_contents[i++] = windowLength;
        parsed_file_contents[i++] = windowOverlap;
        parsed_file_contents[i++] = new Double(sampleRate);
        parsed_file_contents[i++] = new Boolean(normalise);
        parsed_file_contents[i++] = new Boolean(savePerWindow);
        parsed_file_contents[i++] = new Boolean(saveOverall);
        parsed_file_contents[i++] = outputType;
        parsed_file_contents[i++] = checkedMap_;
        parsed_file_contents[i++] = attributeMap_;
        parsed_file_contents[i++] = aggregatorNames;
        parsed_file_contents[i++] = aggregatorFeatures;
        parsed_file_contents[i] = aggregatorParameters;
        count = -1;
    }

    public void endElement(String uri, String localName, String qName)
        throws SAXException
    {
        if(localName.equals("name") || qName.equals("name"))
            tagType = 1;
        else
        if(localName.equals("active") || qName.equals("active"))
            tagType = 1;
        else
        if(localName.equals("attribute") || qName.equals("attribute"))
            tagType = 1;
        else
        if(localName.equals("feature") || qName.equals("feature"))
        {
            attributeMap_.put(name, ((Object) (tmpAttributes_.toArray(new String[0]))));
            tmpAttributes_.clear();
            tagType = 0;
        } else
        if(localName.equals("aggregatorName") || qName.equals("aggregatorName"))
            tagType = 12;
        else
        if(localName.equals("aggregatorFeature") || qName.equals("aggregatorFeature"))
            tagType = 12;
        else
        if(localName.equals("aggregatorAttribute") || qName.equals("aggregatorAttribute"))
            tagType = 12;
        else
        if(localName.equals("aggregator") || qName.equals("aggregator"))
        {
            aggregatorFeatures.add(((Object) (tmpAggregatorFeature_.toArray(new String[0]))));
            aggregatorParameters.add(((Object) (tmpAggregatorParameters_.toArray(new String[0]))));
            tmpAggregatorFeature_.clear();
            tmpAggregatorParameters_.clear();
            tagType = 0;
        } else
        {
            tagType = 0;
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException
    {
        if(count == 0 && !localName.equals("save_settings") && !qName.equals("name"))
            throw new SAXException((new StringBuilder()).append("\n\nIt is in reality of the type [").append(localName).append("].").toString());
        count++;
        if(localName.equals("feature") || qName.equals("feature"))
            tagType = 1;
        else
        if(localName.equals("windowSize") || qName.equals("windowSize"))
            tagType = 2;
        else
        if(localName.equals("windowOverlap") || qName.equals("windowOverlap"))
            tagType = 3;
        else
        if(localName.equals("samplingRate") || qName.equals("samplingRate"))
            tagType = 4;
        else
        if(localName.equals("perWindowStats") || qName.equals("perWindowStats"))
            tagType = 5;
        else
        if(localName.equals("overallStats") || qName.equals("overallStats"))
            tagType = 6;
        else
        if(localName.equals("normalise") || qName.equals("normalise"))
            tagType = 7;
        else
        if(localName.equals("outputType") || qName.equals("outputType"))
            tagType = 8;
        else
        if(localName.equals("active") || qName.equals("active"))
            tagType = 9;
        else
        if(localName.equals("attribute") || qName.equals("attribute"))
            tagType = 10;
        else
        if(localName.equals("name") || qName.equals("name"))
            tagType = 11;
        else
        if(localName.equals("aggregator") || qName.equals("aggregator"))
            tagType = 12;
        else
        if(localName.equals("aggregatorName") || qName.equals("aggregatorName"))
            tagType = 13;
        else
        if(localName.equals("aggregatorFeature") || qName.equals("aggregatorFeature"))
            tagType = 14;
        else
        if(localName.equals("aggregatorAttribute") || qName.equals("aggregatorAttribute"))
            tagType = 15;
    }

    private HashMap checkedMap_;
    private HashMap attributeMap_;
    private LinkedList aggregatorNames;
    private LinkedList aggregatorFeatures;
    private LinkedList aggregatorParameters;
    private LinkedList tmpAggregatorFeature_;
    private LinkedList tmpAggregatorParameters_;
    private LinkedList tmpAttributes_;
    private String name;
    private String windowLength;
    private String windowOverlap;
    private float sampleRate;
    private boolean savePerWindow;
    private boolean saveOverall;
    private boolean normalise;
    private int tagType;
    private boolean inFeature;
    private int count;
    private String outputType;
    Pattern t;
    Pattern f;
    Matcher tm;
    Matcher fm;
}
