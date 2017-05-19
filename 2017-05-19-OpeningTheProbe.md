# Opening a feotal doppler

## First comments

* The board is marked __jpd-100S6__ - a generic name for several types of baby dopplers
* Powered by 2 1.5V batteries
* Screen is backlighted screen. Pins connectors on the back, easily removable
* Sound volume control
* Output to a jack plug for earphones
* USB connector to the probe, I guess only to get the signal back and to the transducer. No proper usb per se.


## Electronics:

* Couldn't risk separating the board from the plastic without hurting it
* Two [LM324](http://www.ti.com/general/docs/lit/getliterature.tsp?genericPartNumber=LM2902-N&fileType=pdf
) to the back of the board 
* No brands on the microcontroller
* A _71Y8 4871_ (U9) seems to be a [LM4871](http://www.ti.com/product/LM4871) is the Boomer Series: 3W Audio Amplifier
* U6  is not connected
* Y1 seems a fat crystal. @todo measure it
* Y2, marked B8000D seems another crystall. @todo measure it
* D2 and D3 seem clipping diodes.. for the signal from the transducer?
     * The two tracks coming from them may be the signal to the transducer. @todo: measure it

## Comparing to some DIY doppler probe

* That should be doable, compared to [this article](/pdf/DIY-doppler.pdf)

## Images!

![](/images/2017-05-19/20170519_183909.jpg)

![](/images/2017-05-19/20170519_183916.jpg)

![](/images/2017-05-19/20170519_183924.jpg)

![](/images/2017-05-19/20170519_183934.jpg)

![](/images/2017-05-19/20170519_183954.jpg)
