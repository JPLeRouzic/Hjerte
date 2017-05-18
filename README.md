# Early and low cost detection of Heart Failure

_Heart Failure is a debilitating condition that most old people encounters. A PoC uses coded signals, Doppler and a sound ML classifier._

See on [HackADay Page](https://hackaday.io/project/19685-early-and-low-cost-detection-of-heart-failure)

# Description

Heart failure (HF) is a complex heart syndrome. It prevents the heart from fulfilling the circulatory demands of the body. A patient is characterized by breathlessness, ankle swelling and fatigue. Medicine doctors formulated several criteria to determine the presence of HF, however there is no remedy.

We propose to use a point of care device, composed of a low cost Doppler device with a trained classifier ,to detect suspicion of HF in primary care.

In order to detect HF with a ML classifier, we train it to recognize features in sounds from the Physionet Cinc or similar competitions.

In our case, signals acquired with a low cost fetal Doppler, are connected to a Linux box hosting the classifier and user GUI. It records heart beats without needs of any gel. We will later code the Doppler signal to obtain more features, specially about fibrous tissues. In a further iteration we will implement the ML classifier on a low cost controller.

# Details

### Short list of the features:

* This device can measure the acoustic impedance of heart and lung tissues __even in non medical environment__, no need to move to a medical center.

* This device measures the acoustic impedance of tissues by a home carer, without need for medical staff.

* This device mitigate issues due to unanticipated skin colors, sweating or medical conditions, as it can "phone home" if it found anomalous results. Conversely it receives updates automatically, so what is learn from a single case, is send to every devices at their next update.

* It is very low cost: Certainly less than $150 in its final version.

* It can be used for a long time without creating skin problems, as it uses no gel and is easy to clean.

Actually ultrasound specialists also use such artifacts to detect cysts, tumors, calcifications. B-line is an artifact that is used in ultrasound imaging (or lack of!) to infer medical conditions.

### Acoustic impedance:

It is well known that the density of degenerated tissues is lower than those of normal tissues. This is due both to intracellular and extracellular damages.

While using machine learning to detect heart failure is not new, using ML features symptomatic of fibrous tissues is entirely new.

Based on the acoustic impedance the tissue could be classified as: normal, degenerated, granulated and fibrous. Each category indicates specific problems mostly in connective tissues.

Changes in tissues acoustic impedance alone, do not mean in themselves that the medical condition changed. What makes it accurate is that this device can recognize signatures of degenerated tissue thanks to modern statistical technologies such as Hidden Markov Chains.
Sourcing the Transducer

A critical component of this system is the ultrasound transducer. A piezoelectric transducer element transmits energy into the body and receive the resulting reflections.

While we use in our PoC a commercial 3 MHz ultrasound probe, it is possible to build one. The DGH 6000 Scanmate A transducer consists of a single piezo - ceramic element that runs at a center frequency of 10.0 MHz nominal.

Please have a look at [this excellent project for suggestions of bill of materials](https://hackaday.io/project/9281-murgen-open-source-ultrasound-imaging)

### High-Voltage Transmitters (Pulser)

We need to transmit two frequencies for “0” and “1”, because we code our transmission with pulse’s ID, time’ ID and ECC (maybe Golay code).
We transmit those information in order to gather impedance information.

High-voltage pulsers quickly switch the transducer element to the appropriate programmable high-voltage supplies to generate the transmit waveform.

To generate a simple bipolar transmit waveform, a transmit pulser alternately connects the element to a positive and negative transmit supply voltage 
