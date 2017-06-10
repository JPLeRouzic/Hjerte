package Misc.Gui.Actions;

import Audio.FileFilterAudio;
import Misc.AudioFeatures.RecordingInfo;
import Misc.Gui.Controller.Control;
import Misc.Gui.Main.ExtractFeatures;
import Misc.Tools.GeneralMethods;
import Misc.sampled.AudioSamples;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;

public class AddRecordingAction extends AbstractAction {

    static final long serialVersionUID = 1L;
    private JFileChooser load_recording_chooser;
    private Control controller;

    public AddRecordingAction() {
        super("Add Recording...");
        load_recording_chooser = null;
    }

    public void setModel(Control c) {
        controller = c;
    }

    public void actionPerformed(ActionEvent e) {

        if (load_recording_chooser == null) {
            load_recording_chooser = new JFileChooser();
            load_recording_chooser.setCurrentDirectory(new File("."));
            load_recording_chooser.setFileFilter(new FileFilterAudio());
            load_recording_chooser.setFileSelectionMode(0);
            load_recording_chooser.setMultiSelectionEnabled(true);
        }
        int dialog_result = load_recording_chooser.showOpenDialog(null);
        if (dialog_result == 0) {
            File load_files[] = load_recording_chooser.getSelectedFiles();
            RecordingInfo[] ri = new RecordingInfo[load_files.length];
            for (int s = 0; s < load_files.length; s++) {
                ri[s] = new RecordingInfo(load_files[s].getAbsolutePath());
            }
            controller.exfeat = new ExtractFeatures(ri);
            try {
                addRecording(load_files);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void addFile() {

        if (load_recording_chooser == null) {
            load_recording_chooser = new JFileChooser();
            load_recording_chooser.setCurrentDirectory(new File("."));
            load_recording_chooser.setFileFilter(new FileFilterAudio());
            load_recording_chooser.setFileSelectionMode(0);
            load_recording_chooser.setMultiSelectionEnabled(true);
        }
        int dialog_result = load_recording_chooser.showOpenDialog(null);
        if (dialog_result == 0) {
            File load_files[] = load_recording_chooser.getSelectedFiles();
            RecordingInfo[] ri = new RecordingInfo[load_files.length];
            for (int s = 0; s < load_files.length; s++) {
                ri[s] = new RecordingInfo(load_files[s].getAbsolutePath());
            }
            controller.exfeat = new ExtractFeatures(ri);
            try {
                addRecording(load_files);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void addRecording(File toBeAdded[])
            throws Exception {
        RecordingInfo recording_info[] = new RecordingInfo[toBeAdded.length];
        for (int i = 0; i < toBeAdded.length; i++) {
            recording_info[i] = null;
            if (toBeAdded[i].exists()) {
                try {
                    AudioSamples audio_samples = null;
                    audio_samples = new AudioSamples(toBeAdded[i], toBeAdded[i].getPath(), false);
                    audio_samples = null;
                    recording_info[i] = new RecordingInfo(toBeAdded[i].getName(), toBeAdded[i].getPath(), audio_samples);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", 0);
                }
            } else {
                JOptionPane.showMessageDialog(null, (new StringBuilder()).append("The selected file ").append(toBeAdded[i].getName()).append(" does not exist.").toString(), "ERROR", 0);
            }
        }

        int number_old_recordings = 0;
        if (controller.exfeat.recordingInfo != null) {
            number_old_recordings = controller.exfeat.recordingInfo.length;
        }
        int number_new_recordings = 0;
        if (recording_info != null) {
            number_new_recordings = recording_info.length;
        }
        RecordingInfo temp_recording_list[] = new RecordingInfo[number_old_recordings + number_new_recordings];
        for (int i = 0; i < number_old_recordings; i++) {
            temp_recording_list[i] = controller.exfeat.recordingInfo[i];
        }

        for (int i = 0; i < number_new_recordings; i++) {
            temp_recording_list[i + number_old_recordings] = recording_info[i];
        }

        for (int i = 0; i < temp_recording_list.length - 1; i++) {
            if (temp_recording_list[i] == null) {
                continue;
            }
            String current_path = temp_recording_list[i].file_path;
            for (int j = i + 1; j < temp_recording_list.length; j++) {
                if (temp_recording_list[j] != null && current_path.equals(temp_recording_list[j].file_path)) {
                    temp_recording_list[j] = null;
                }
            }

        }

        Object results[] = GeneralMethods.removeNullEntriesFromArray(temp_recording_list);
        if (results != null) {
            controller.exfeat.recordingInfo = new RecordingInfo[results.length];
            for (int i = 0; i < results.length; i++) {
                controller.exfeat.recordingInfo[i] = (RecordingInfo) results[i];
            }

        }
        controller.filesList.fillTable(controller.exfeat.recordingInfo);
        controller.filesList.fireTableDataChanged();
    }
}
