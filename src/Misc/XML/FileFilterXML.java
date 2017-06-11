
package Misc.XML;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileFilterXML extends FileFilter
{

    public FileFilterXML()
    {
    }

    public boolean accept(File f)
    {
        return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
    }

    public String getDescription()
    {
        return "XML File";
    }
}
