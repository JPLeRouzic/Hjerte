
package Misc.XML;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

// Referenced classes of package Misc.XML:
//            ParseSaveSettings, ParseFileHandler

public class XMLDocumentParser
{

    public XMLDocumentParser()
    {
    }

    public static Object parseXMLDocument(String file_path, String document_type)
        throws Exception
    {
        File test_file = new File(file_path);
        if(!test_file.exists())
            throw new Exception((new StringBuilder()).append("The specified path ").append(file_path).append(" does not refer to an existing file.").toString());
        if(test_file.isDirectory())
            throw new Exception((new StringBuilder()).append("The specified path ").append(file_path).append(" refers to a directory, not to a file.").toString());
        SAXParser reader = SAXParserFactory.newInstance().newSAXParser();
        ParseFileHandler handler;
        if(document_type.equals("save_settings"))
            handler = new ParseSaveSettings();
        else
            throw new Exception((new StringBuilder()).append("Invalid type of XML file specified. The XML file type ").append(document_type).append(" is not known.").toString());
        try
        {
            reader.parse(file_path, handler);
        }
        catch(SAXParseException e)
        {
            throw new Exception((new StringBuilder()).append("The ").append(file_path).append(" file is not a valid XML file.\n\nDetails of the problem: ").append(e.getMessage()).append("\n\nThis error is likely in the region of line ").append(e.getLineNumber()).append(".").toString());
        }
        catch(SAXException e)
        {
            throw new Exception((new StringBuilder()).append("The ").append(file_path).append(" file must be of type ").append(document_type).append(". ").append(e.getMessage()).toString());
        }
        catch(IOException e)
        {
            throw new Exception((new StringBuilder()).append("The ").append(file_path).append(" file is not formatted properly.\n\nDetails of the problem: ").append(e.getMessage()).toString());
        }
        return ((Object) (handler.parsed_file_contents));
    }
}
