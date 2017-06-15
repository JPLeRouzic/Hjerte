package Misc.Gui.Main;

import ML.Classify.PDefFeats;
import java.util.Collection;
import ML.featureDetection.TrainOne;
import Misc.AudioFeatures.FromFileToAudio;
import java.io.File;
import ML.featureDetection.NormalizeBeat;
import java.util.ArrayList;
import Misc.AudioFeatures.RecordingInfo;

public final class ExtractFeatures
{
    public RecordingInfo[] recordingInfo;
    
    public ExtractFeatures(final RecordingInfo[] ri) {
        this.recordingInfo = ri;
    }
    
    public ArrayList extractAllFiles(final RecordingInfo[] info, final ExtractFeatures exFeat) throws Exception, Throwable {
        final ArrayList obs = new ArrayList();
        final RecordingInfo[] arecordinginfo = new RecordingInfo[info.length];
        System.arraycopy(info, 0, arecordinginfo, 0, info.length);
        this.recordingInfo = info;
        if (this.recordingInfo == null) {
            throw new Exception("No recordings available to extract features from.");
        }
        final NormalizeBeat norm = new NormalizeBeat();
        for (int i = 0; i < this.recordingInfo.length; ++i) {
            final File load_file = new File(this.recordingInfo[i].file_path);
            final PDefFeats predefFeatures = this.predefinedFeatures(load_file);
            final FromFileToAudio e = new FromFileToAudio(load_file);
            final float[] samples = e.getAudioSamples().getSamplesMixedDown();
            final TrainOne plugin = new TrainOne();
            final ArrayList obs2 = plugin.extractFeature(samples, e.getAudioSamples().getSamplingRate(), predefFeatures, norm);
            System.out.println("File: " + load_file.getName() + ", beat rate= " + obs2.size());
            obs.addAll(obs2);
        }
        EntryPoint.controller.filesList.fillTable(arecordinginfo);
        EntryPoint.controller.filesList.fireTableDataChanged();
        EntryPoint.outer_frame.mainPanel.heart_sound_files_table.repaint();
        EntryPoint.outer_frame.mainPanel.repaint();
        EntryPoint.outer_frame.mainPanel.setVisible(true);
        return obs;
    }
    
    public void validateFile(final String definitions, final String values) throws Exception {
        final File feature_values_save_file = new File(values);
        final File feature_definitions_save_file = new File(definitions);
        if (feature_values_save_file.exists() && !feature_values_save_file.canWrite()) {
            throw new Exception("Cannot write to " + values + ".");
        }
        if (feature_definitions_save_file.exists() && !feature_definitions_save_file.canWrite()) {
            throw new Exception("Cannot write to " + definitions + ".");
        }
    }
    
    private PDefFeats predefinedFeatures(final File load_file) {
        final PDefFeats featrs = new PDefFeats();
        final String name = load_file.getName();
        final String delims = "[_]+";
        final String[] tokens = name.split(delims);
        for (int i = tokens.length - 2; i > -1; --i) {
            if (tokens[i].contentEquals("plop")) {
                featrs.plop = true;
            }
            else if (tokens[i].contentEquals("speech")) {
                featrs.speech = true;
            }
            else if (tokens[i].contentEquals("noise")) {
                featrs.noise = true;
            }
            else if (tokens[i].contentEquals("respiration")) {
                featrs.respiration = true;
            }
            else if (tokens[i].contentEquals("variant")) {
                featrs.variant = true;
            }
            else if (tokens[i].contentEquals("hum")) {
                featrs.hum = true;
            }
            else if (tokens[i].contentEquals("saturation")) {
                featrs.saturation = true;
            }
            else {
                final String numberOfBeats = tokens[i];
                if (numberOfBeats.contains("beats")) {
                    final String[] toktok = numberOfBeats.split("b");
                    final Integer beats = Integer.valueOf(toktok[0]);
                    featrs.nbBeats = beats;
                }
                else if (numberOfBeats.contains("sec")) {
                    final String[] duration = numberOfBeats.split("s");
                    featrs.duration = Integer.valueOf(duration[0]);
                }
            }
        }
        return featrs;
    }
}
