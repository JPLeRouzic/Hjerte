
package Misc.Gui.Controller;

import ML.featureDetection.NormalizeBeat;
import Misc.Gui.Actions.*;
import Misc.Gui.ExtractFeatures;
import javax.swing.JTable;

public class Control
{

    public FilesTableModel filesList;
    public ExtractFeatures exfeat;
    public ExitAction exitAction;
    public SaveAction saveAction;
    public LoadAction loadAction;
    public ViewFileInfoAction viewFileInfoAction;
    public AddRecordingAction addRecordingsAction;
    public RemoveRecordingAction removeRecordingsAction;
    public AboutAction aboutAction;
    public NormalizeBeat norm;
    JTable recordTable ;

    public Control()
        throws Exception
    {
        exitAction = new ExitAction();
        addRecordingsAction = new AddRecordingAction();
        removeRecordingsAction = new RemoveRecordingAction(recordTable);
        aboutAction = new AboutAction();
        filesList = new FilesTableModel(new Object[] {
            "Name", "Path"
        }, 0, 60);
        saveAction = new SaveAction(this);
        loadAction = new LoadAction(this);
        addRecordingsAction.setModel(this);
        viewFileInfoAction = new ViewFileInfoAction(this, recordTable);
    }
}
