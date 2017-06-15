package Misc.Gui.Actions;

import Misc.Tools.GeneralMethods;
import javax.swing.JOptionPane;
import Misc.sampled.AudioSamples;
import Misc.Gui.Main.ExtractFeatures;
import Misc.AudioFeatures.RecordingInfo;
import java.awt.Component;
import javax.swing.filechooser.FileFilter;
import Audio.FileFilterAudio;
import java.io.File;
import java.awt.event.ActionEvent;
import Misc.Gui.Controller.Control;
import javax.swing.JFileChooser;
import javax.swing.AbstractAction;

public class AddRecordingAction extends AbstractAction
{
    static final long serialVersionUID = 1L;
    private JFileChooser load_recording_chooser;
    private Control controller;
    
    public AddRecordingAction() {
        super("Add Recording...");
        this.load_recording_chooser = null;
    }
    
    public void setModel(final Control c) {
        this.controller = c;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (this.load_recording_chooser == null) {
            (this.load_recording_chooser = new JFileChooser()).setCurrentDirectory(new File("."));
            this.load_recording_chooser.setFileFilter(new FileFilterAudio());
            this.load_recording_chooser.setFileSelectionMode(0);
            this.load_recording_chooser.setMultiSelectionEnabled(true);
        }
        final int dialog_result = this.load_recording_chooser.showOpenDialog(null);
        if (dialog_result == 0) {
            final File[] load_files = this.load_recording_chooser.getSelectedFiles();
            final RecordingInfo[] ri = new RecordingInfo[load_files.length];
            for (int s = 0; s < load_files.length; ++s) {
                ri[s] = new RecordingInfo(load_files[s].getAbsolutePath());
            }
            this.controller.exfeat = new ExtractFeatures(ri);
            try {
                this.addRecording(load_files);
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    public void addFile() {
        if (this.load_recording_chooser == null) {
            (this.load_recording_chooser = new JFileChooser()).setCurrentDirectory(new File("."));
            this.load_recording_chooser.setFileFilter(new FileFilterAudio());
            this.load_recording_chooser.setFileSelectionMode(0);
            this.load_recording_chooser.setMultiSelectionEnabled(true);
        }
        final int dialog_result = this.load_recording_chooser.showOpenDialog(null);
        if (dialog_result == 0) {
            final File[] load_files = this.load_recording_chooser.getSelectedFiles();
            final RecordingInfo[] ri = new RecordingInfo[load_files.length];
            for (int s = 0; s < load_files.length; ++s) {
                ri[s] = new RecordingInfo(load_files[s].getAbsolutePath());
            }
            this.controller.exfeat = new ExtractFeatures(ri);
            try {
                this.addRecording(load_files);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    public void addRecording(final File[] toBeAdded) throws Exception {
        final RecordingInfo[] recording_info = new RecordingInfo[toBeAdded.length];
        for (int i = 0; i < toBeAdded.length; ++i) {
            recording_info[i] = null;
            if (toBeAdded[i].exists()) {
                try {
                    AudioSamples audio_samples = null;
                    audio_samples = new AudioSamples(toBeAdded[i], toBeAdded[i].getPath(), false);
                    audio_samples = null;
                    recording_info[i] = new RecordingInfo(toBeAdded[i].getName(), toBeAdded[i].getPath(), audio_samples);
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", 0);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "The selected file " + toBeAdded[i].getName() + " does not exist.", "ERROR", 0);
            }
        }
        int number_old_recordings = 0;
        if (this.controller.exfeat.recordingInfo != null) {
            number_old_recordings = this.controller.exfeat.recordingInfo.length;
        }
        int number_new_recordings = 0;
        if (recording_info != null) {
            number_new_recordings = recording_info.length;
        }
        final RecordingInfo[] temp_recording_list = new RecordingInfo[number_old_recordings + number_new_recordings];
        for (int j = 0; j < number_old_recordings; ++j) {
            temp_recording_list[j] = this.controller.exfeat.recordingInfo[j];
        }
        for (int j = 0; j < number_new_recordings; ++j) {
            temp_recording_list[j + number_old_recordings] = recording_info[j];
        }
        for (int j = 0; j < temp_recording_list.length - 1; ++j) {
            if (temp_recording_list[j] != null) {
                final String current_path = temp_recording_list[j].file_path;
                for (int k = j + 1; k < temp_recording_list.length; ++k) {
                    if (temp_recording_list[k] != null && current_path.equals(temp_recording_list[k].file_path)) {
                        temp_recording_list[k] = null;
                    }
                }
            }
        }
        final Object[] results = GeneralMethods.removeNullEntriesFromArray(temp_recording_list);
        if (results != null) {
            this.controller.exfeat.recordingInfo = new RecordingInfo[results.length];
            for (int l = 0; l < results.length; ++l) {
                this.controller.exfeat.recordingInfo[l] = (RecordingInfo)results[l];
            }
        }
        this.controller.filesList.fillTable(this.controller.exfeat.recordingInfo);
        this.controller.filesList.fireTableDataChanged();
    }
}
