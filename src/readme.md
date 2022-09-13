### Overview


# Design


## Audio Persistence
first thoughts are to write a record for each phrase/audio/timestamp pair
so there is an exact replica of everything that happened

An individual record of audio data can be sent to the speaker on demand

Further records can sew the pieces together, ie by associating them with some entity
like a note or a question

Later on sewn together audio data can be saved to a wav file if needed

## material design icons
go there for the list of material design icons to use in fxml

use the lower case id's

[icons](https://kordamp.org/ikonli/cheat-sheet-materialdesign2.html#_a_materialdesigna)

example
````
    <Button onAction="#onHelloButtonClick">
        <graphic>
            <FontIcon iconLiteral="mdi2a-alarm-check" iconSize="64"/>
        </graphic>
    </Button>
````


### Audio visualizations sample code 
[link](https://stackoverflow.com/questions/49097889/how-to-calculate-the-decibel-of-audio-signal-and-record-the-audio-in-java)
````
public static short[] encodeToSample(byte[] srcBuffer, int numBytes) {
    byte[] tempBuffer = new byte[2];
    int nSamples = numBytes / 2;        
    short[] samples = new short[nSamples];  // 16-bit signed value

    for (int i = 0; i < nSamples; i++) {
        tempBuffer[0] = srcBuffer[2 * i];
        tempBuffer[1] = srcBuffer[2 * i + 1];
        samples[i] = bytesToShort(tempBuffer);
    }

    return samples;
}

public static short bytesToShort(byte [] buffer) {
    ByteBuffer bb = ByteBuffer.allocate(2);
    bb.order(ByteOrder.BIG_ENDIAN);
    bb.put(buffer[0]);
    bb.put(buffer[1]);
    return bb.getShort(0);
}

public static void calculatePeakAndRms(short [] samples) {
    double sumOfSampleSq = 0.0;    // sum of square of normalized samples.
    double peakSample = 0.0;     // peak sample.

    for (short sample : samples) {
        double normSample = (double) sample / 32767;  // normalized the sample with maximum value.
        sumOfSampleSq += (normSample * normSample);
        if (Math.abs(sample) > peakSample) {
            peakSample = Math.abs(sample);
        }
    }

    double rms = 10*Math.log10(sumOfSampleSq / samples.length);
    double peak = 20*Math.log10(peakSample / 32767);
}
````