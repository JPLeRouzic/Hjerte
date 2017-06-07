package Misc.Gui.Main;

import ML.Classify.PDefFeats;
import Misc.AudioFeatures.RecordingInfo;
import ML.featureDetection.NormalizeBeat;
import ML.featureDetection.TrainOne;
import Misc.AudioFeatures.Cancel;
import Misc.AudioFeatures.FromFileToAudio;
import java.io.File;
import java.util.ArrayList;

public final class ExtractFeatures {

    public Cancel cancel_;
    public RecordingInfo recordingInfo[];

    public ExtractFeatures(RecordingInfo[] ri) {
        recordingInfo = ri;
    }

    public ArrayList extractAllFiles(
            RecordingInfo info[],
            ExtractFeatures exFeat)
            throws Exception, Throwable {
        ArrayList obs = new ArrayList();

        RecordingInfo arecordinginfo[] = new RecordingInfo[info.length];
        System.arraycopy(info, 0, arecordinginfo, 0, info.length);
        recordingInfo = info;
        if (recordingInfo == null) {
            throw new Exception("No recordings available to extract features from.");
        }
        NormalizeBeat norm = new NormalizeBeat();
        for (int i = 0; i < recordingInfo.length; i++) {
            File load_file = new File(recordingInfo[i].file_path);
            PDefFeats predefFeatures = predefinedFeatures(load_file);
            FromFileToAudio e = new FromFileToAudio(load_file);
            float samples[] = e.getAudioSamples().getSamplesMixedDown();
            TrainOne plugin = new TrainOne();
            // obs is an ArrayList of ArrayList of Observation
            ArrayList obs1 = plugin.extractFeature(
                    samples, 
                    e.getAudioSamples().getSamplingRate(), 
                    predefFeatures,
                    norm);
System.out.println("File: " + load_file.getName() + ", beat rate= " + obs1.size());

            obs.addAll(obs1);
        }
        EntryPoint.controller.filesList.fillTable(arecordinginfo);
        EntryPoint.controller.filesList.fireTableDataChanged();
        // FIXME the rate does not appear
        EntryPoint.outer_frame.mainPanel.heart_sound_files_table.repaint();
        EntryPoint.outer_frame.mainPanel.repaint();
        EntryPoint.outer_frame.mainPanel.setVisible(true);
        return obs;
    }

    public void validateFile(String definitions, String values)
            throws Exception {
        File feature_values_save_file = new File(values);
        File feature_definitions_save_file = new File(definitions);
        if (feature_values_save_file.exists() && !feature_values_save_file.canWrite()) {
            throw new Exception((new StringBuilder()).append("Cannot write to ").append(values).append(".").toString());
        }
        if (feature_definitions_save_file.exists() && !feature_definitions_save_file.canWrite()) {
            throw new Exception((new StringBuilder()).append("Cannot write to ").append(definitions).append(".").toString());
        } else {
            return;
        }
    }

    private PDefFeats predefinedFeatures(File load_file) {
        PDefFeats featrs = new PDefFeats();
        String name = load_file.getName();
        String delims = "[_]+";
        
        String[] tokens = name.split(delims);

        int i = tokens.length - 2;
        while(i > -1) {
        if(tokens[i].contentEquals("plop")) {
            featrs.plop = true ;
        } else 
        if(tokens[i].contentEquals("speech")) {
            featrs.speech = true ;
        } else 
        if(tokens[i].contentEquals("noise")) {
            featrs.noise = true ;
        } else 
        if(tokens[i].contentEquals("respiration")) {
            featrs.respiration = true ;
        } else 
        if(tokens[i].contentEquals("variant")) {
            featrs.variant = true ;
        } else 
        if(tokens[i].contentEquals("hum")) {
            featrs.hum = true ;
        } else 
        if(tokens[i].contentEquals("saturation")) {
            featrs.saturation = true ;
        } else 
        {
            String numberOfBeats = tokens[i] ;
            if(numberOfBeats.contains("beats")) {
                String[] toktok = numberOfBeats.split("b");
                Integer beats = Integer.valueOf(toktok[0]) ;
                featrs.nbBeats = beats ;
            } else
            if(numberOfBeats.contains("sec")) {
                String[] duration = numberOfBeats.split("s");
                featrs.duration = Integer.valueOf(duration[0]) ;
        } else {
            System.out.print("problem in predefinedFeatures");
        }
        }
            i-- ;
        }
        return featrs ;
    }
}
