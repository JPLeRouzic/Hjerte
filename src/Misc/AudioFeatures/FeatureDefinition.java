
package Misc.AudioFeatures;

import Misc.XML.XMLDocumentParser;
import java.io.*;
import java.util.Vector;

public class FeatureDefinition
    implements Serializable
{

    public FeatureDefinition()
    {
        name = "Undefined Feature";
        description = new String("");
        is_sequential = false;
        dimensions = 1;
        attributes = new String[0];
    }

    public FeatureDefinition(String name, String description, boolean is_sequential, int dimensions)
    {
        this.name = name;
        this.description = description;
        this.is_sequential = is_sequential;
        this.dimensions = dimensions;
        attributes = new String[0];
    }

    public FeatureDefinition(String name, String description, boolean is_sequential, int dimensions, String attributes[])
    {
        this.name = name;
        this.description = description;
        this.is_sequential = true;
        this.dimensions = dimensions;
        this.attributes = attributes;
    }

    public String getFeatureDescription()
    {
        String info = (new StringBuilder()).append("NAME: ").append(name).append("\n").toString();
        info = (new StringBuilder()).append(info).append("DESCRIPTION: ").append(description).append("\n").toString();
        info = (new StringBuilder()).append(info).append("IS SEQUENTIAL: ").append(is_sequential).append("\n").toString();
        info = (new StringBuilder()).append(info).append("DIMENSIONS: ").append(dimensions).append("\n\n").toString();
        return info;
    }

    public static String getFeatureDescriptions(FeatureDefinition definitions[])
    {
        String combined_descriptions = new String();
        for(int i = 0; i < definitions.length; i++)
            combined_descriptions = (new StringBuilder()).append(combined_descriptions).append(definitions[i].getFeatureDescription()).toString();

        return combined_descriptions;
    }

    public static FeatureDefinition[] parseFeatureDefinitionsFile(String feature_key_file_path)
        throws Exception
    {
        Object results[] = (Object[])(Object[])XMLDocumentParser.parseXMLDocument(feature_key_file_path, "feature_key_file");
        FeatureDefinition parse_results[] = new FeatureDefinition[results.length];
        for(int i = 0; i < parse_results.length; i++)
            parse_results[i] = (FeatureDefinition)results[i];

        String duplicates = verifyFeatureNameUniqueness(parse_results);
        if(duplicates != null)
            throw new Exception((new StringBuilder()).append("Could not parse because there are multiple\noccurences of the following feature names:\n").append(duplicates).toString());
        else
            return parse_results;
    }

    public static void saveFeatureDefinitions(FeatureDefinition definitions[], File to_save_to, String comments)
        throws Exception
    {
        String duplicates = verifyFeatureNameUniqueness(definitions);
        if(duplicates != null)
            throw new Exception((new StringBuilder()).append("Could not save because there are multiple\noccurences of the following feature names:\n").append(duplicates).toString());
        FileOutputStream to = new FileOutputStream(to_save_to);
        DataOutputStream writer = new DataOutputStream(to);
        String pre_tree_part = new String((new StringBuilder()).append("<?xml version=\"1.0\"?>\n<!DOCTYPE feature_key_file [\n   <!ELEMENT feature_key_file (comments, feature+)>\n   <!ELEMENT comments (#PCDATA)>\n   <!ELEMENT feature (name, description?, is_sequential, parallel_dimensions)>\n   <!ELEMENT name (#PCDATA)>\n   <!ELEMENT description (#PCDATA)>\n   <!ELEMENT is_sequential (#PCDATA)>\n   <!ELEMENT parallel_dimensions (#PCDATA)>\n]>\n\n<feature_key_file>\n\n   <comments>").append(comments).append("</comments>\n\n").toString());
        writer.writeBytes(pre_tree_part);
        for(int feat = 0; feat < definitions.length; feat++)
        {
            writer.writeBytes("   <feature>\n");
            writer.writeBytes((new StringBuilder()).append("      <name>").append(definitions[feat].name).append("</name>\n").toString());
            if(!definitions[feat].description.equals(""))
                writer.writeBytes((new StringBuilder()).append("      <description>").append(definitions[feat].description).append("</description>\n").toString());
            writer.writeBytes((new StringBuilder()).append("      <is_sequential>").append(definitions[feat].is_sequential).append("</is_sequential>\n").toString());
            writer.writeBytes((new StringBuilder()).append("      <parallel_dimensions>").append(definitions[feat].dimensions).append("</parallel_dimensions>\n").toString());
            writer.writeBytes("   </feature>\n\n");
        }

        writer.writeBytes("</feature_key_file>");
        writer.close();
    }

    public static String verifyFeatureNameUniqueness(FeatureDefinition definitions[])
    {
        boolean found_duplicate = false;
        Vector duplicates = new Vector();
        for(int i = 0; i < definitions.length - 1; i++)
        {
            for(int j = i + 1; j < definitions.length; j++)
                if(definitions[i].name.equals(definitions[j].name))
                {
                    found_duplicate = true;
                    duplicates.add(definitions[i].name);
                    j = definitions.length;
                }

        }

        if(found_duplicate)
        {
            Object duplicated_names_obj[] = (Object[])duplicates.toArray();
            String duplicated_names[] = new String[duplicated_names_obj.length];
            for(int i = 0; i < duplicated_names.length; i++)
                duplicated_names[i] = (String)duplicated_names_obj[i];

            String duplicates_formatted = new String();
            for(int i = 0; i < duplicated_names.length; i++)
            {
                duplicates_formatted = (new StringBuilder()).append(duplicates_formatted).append(duplicated_names[i]).toString();
                if(i < duplicated_names.length - 1)
                    duplicates_formatted = (new StringBuilder()).append(duplicates_formatted).append(", ").toString();
            }

            return duplicates_formatted;
        } else
        {
            return null;
        }
    }

    public String name;
    public String description;
    public boolean is_sequential;
    public int dimensions;
    private static final long serialVersionUID = 2L;
    public String attributes[];
}
