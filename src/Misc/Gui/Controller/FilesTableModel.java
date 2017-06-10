
package Misc.Gui.Controller;

import Misc.AudioFeatures.RecordingInfo;
import java.awt.TextArea;
import javax.swing.table.DefaultTableModel;

public class FilesTableModel extends DefaultTableModel
{

    public FilesTableModel(Object columnNames[], int rows, int heartRate)
    {
        super(columnNames, rows);
//        addColumn("heartRate") ;
    }

    public void fillTable(RecordingInfo recording_list[])
    {
        clearTable();
        if(recording_list != null)
        {
            RecordingInfo arecordinginfo[] = recording_list;
            int i = arecordinginfo.length;
            for(int j = 0; j < i; j++)
            {
                RecordingInfo recording_list1 = arecordinginfo[j];
                Object row_contents[] = new Object[3];
                row_contents[0] = recording_list1.identifier;
                row_contents[1] = recording_list1.file_path;
                row_contents[2] = "" ; // To be done FIXME
                addRow(row_contents);
            }

        }
    }

    public void clearTable()
    {
        for(; getRowCount() != 0; removeRow(0));
    }

    public boolean isCellEditable(int row, int column)
    {
        return false;
    }
}
