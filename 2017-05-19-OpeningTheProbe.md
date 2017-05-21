# Opening a feotal doppler

## First comments

* The board is marked __jpd-100S6__ - a generic name for several types of baby dopplers
* Powered by 2 1.5V batteries
* Screen is backlighted screen. Pins connectors on the back, easily removable
* Sound volume control
* Output to a jack plug for earphones
* USB connector to the probe, I guess only to get the signal back and to the transducer. No proper usb per se.
* Probe contains another board and bigger crystal

## Body electronics

* Couldn't risk separating the board from the plastic without hurting it
* Two [LM324](http://www.ti.com/general/docs/lit/getliterature.tsp?genericPartNumber=LM2902-N&fileType=pdf
) to the back of the board 
* No brands on the microcontroller - it seems to be [this one](http://www.abov.co.kr/en/index.php?Depth1=3&Depth2=1&Depth3=1&Depth4=1&Item=MC96F6432Q) though.
* A _71Y8 4871_ (U9) seems to be a [LM4871](http://www.ti.com/product/LM4871) is the Boomer Series: 3W Audio Amplifier
* U6  is not connected
* Y1 seems a 32kHz crystal.
* Y2, marked B8000D seems another crystal. It's a 8MHz one. Should be for the microcontroller
* D2 and D3 seem clipping diodes.. for the signal from the transducer?
     * The two tracks coming from them may be the signal to the transducer. @todo: measure it
* White cable has a connector to the speaker. It may be interesting to measure what goes inside. Or inside the headers. 

The double diods clamp yield this signal:

![](/images/2017-05-19/raw signal from probe.JPG)

## Head electronics

* It contains two piezos, each half a disc. One is the emitter, the other the receiver.
* A couple of ICs, a big fat crystal at 3MHz.
* 3 signals from the usb wire: GND, blue and red (S and V respectively - one would assume for voltage and Signal)
* Images are below 


## Comparing to some DIY doppler probe

* That should be doable, compared to [this article](/pdf/DIY-doppler.pdf)

## Images!

### In the head

![](/images/2017-05-19/20170521_170648.jpg)


![](/images/2017-05-19/20170521_170703.jpg)

### In the body

![](/images/2017-05-19/20170519_183909.jpg)

![](/images/2017-05-19/20170519_183916.jpg)

![](/images/2017-05-19/20170519_183924.jpg)

![](/images/2017-05-19/20170519_183934.jpg)

![](/images/2017-05-19/20170519_183954.jpg)
