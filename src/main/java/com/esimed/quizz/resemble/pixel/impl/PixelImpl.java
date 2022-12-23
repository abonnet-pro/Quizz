package com.esimed.quizz.resemble.pixel.impl;

import com.esimed.quizz.resemble.pixel.Pixel;
import com.esimed.quizz.resemble.pixel.PixelChannel;
import com.esimed.quizz.resemble.pixel.PixelChannelChangeListener;
import com.esimed.quizz.resemble.pixel.PixelChannelNeededListener;
import com.esimed.quizz.resemble.pixel.utils.ColorUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

import static com.esimed.quizz.resemble.pixel.utils.ColorUtils.*;

public class PixelImpl implements Pixel {

    @JsonIgnore
    private final PixelChannel[] values = new PixelChannel[PixelChannel.Channel.values().length];

    @JsonIgnore
    private final PixelChannelChangeListener<Integer> rgbChanged = (PixelChannel<Integer> source, Integer oldValue) -> {
        getMinBrightness().setAvailable(false);
        getMaxBrightness().setAvailable(false);
        getHue().setAvailable(false);
    };

    @JsonIgnore
    private final PixelChannelNeededListener<Integer> brightnessNeeded = (PixelChannel<Integer> source) -> {
        addBrightnessInfo();
    };

    @JsonIgnore
    private final PixelChannelNeededListener<Double> hsvNeeded = (PixelChannel<Double> source) -> {
        addHueInfo();
    };

    public PixelImpl(int a, int r, int g, int b) {
        initValues();
        setARGB(a, r, g, b);
    }

    private void initValues() {
        values[PixelChannel.Channel.ALPHA.ordinal()] = new BytePixelChannelImpl(PixelChannel.Channel.ALPHA);
        values[PixelChannel.Channel.RED.ordinal()] = new BytePixelChannelImpl(PixelChannel.Channel.RED).setChangedListener(rgbChanged);
        values[PixelChannel.Channel.GREEN.ordinal()] = new BytePixelChannelImpl(PixelChannel.Channel.GREEN).setChangedListener(rgbChanged);
        values[PixelChannel.Channel.BLUE.ordinal()] = new BytePixelChannelImpl(PixelChannel.Channel.BLUE).setChangedListener(rgbChanged);
        values[PixelChannel.Channel.MIN_BRIGHTNESS.ordinal()] = new BytePixelChannelImpl(PixelChannel.Channel.MIN_BRIGHTNESS).setNeededListener(brightnessNeeded);
        values[PixelChannel.Channel.MAX_BRIGHTNESS.ordinal()] = new BytePixelChannelImpl(PixelChannel.Channel.MAX_BRIGHTNESS).setNeededListener(brightnessNeeded);
        values[PixelChannel.Channel.HUE.ordinal()] = new HuePixelChannelImpl(PixelChannel.Channel.HUE).setNeededListener(hsvNeeded);
    }

    @JsonProperty
    public Map<PixelChannel.Channel, String> getValues() {
        List<Pair<PixelChannel.Channel, String>> pairs = Arrays.stream(values)
                .filter(c -> c.isAvailable())
                .map(c -> Pair.of(c.getChannel(), c.getStringValue()))
                .collect(Collectors.toList());
        Map<PixelChannel.Channel, String> retv = new HashMap(pairs.size());
        pairs.forEach(p -> retv.put(p.getKey(), p.getValue()));
        return retv;
    }

    @JsonProperty
    public void setValues(Map<PixelChannel.Channel, String> valueMap) {
        initValues();
        if (valueMap != null) {
            for (Map.Entry<PixelChannel.Channel, String> entry: valueMap.entrySet()) {
                values[entry.getKey().ordinal()].setValue(entry.getValue());
            }
        }
    }

    public PixelImpl() {
        this(0, 0, 0, 0);
    }

    private PixelImpl(BufferedImage image, int x, int y) {
        this();
        setARGB(image, x, y);
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    @Override
    public boolean isPixelBrightnessSimilar(Pixel d2, Pixel tolerance) {
        if (!getAlpha().isColorSimilar(d2.getAlpha(), tolerance)) {
            return false;
        }
        return getMinBrightness().isColorSimilar(d2.getMinBrightness(), tolerance);
    }

    @Override
    public boolean isRGBSame(Pixel d2) {
        return (getRed().isSame(d2.getRed()) &&
                getGreen().isSame(d2.getGreen()) &&
                getBlue().isSame(d2.getBlue()));
    }

    @Override
    public boolean isARGBSimilar(Pixel d2, Pixel tolerance) {
        return (getRed().isColorSimilar(d2.getRed(), tolerance) &&
                getGreen().isColorSimilar(d2.getGreen(), tolerance) &&
                getBlue().isColorSimilar(d2.getBlue(), tolerance) &&
                getAlpha().isColorSimilar(d2.getAlpha(), tolerance));
    }

    @Override
    public boolean isContrasting(Pixel d2, Pixel tolerance) {
        int thisValue = getMinBrightness().getValue();
        int d2Value = d2.getMinBrightness().getValue();
        int tolValue = tolerance.getMaxBrightness().getValue();
        return Math.abs(thisValue - d2Value) > tolValue;
    }

    @Override
    @JsonIgnore
    public PixelChannel<Integer> getAlpha() {
        return get(PixelChannel.Channel.ALPHA);
    }

    @Override
    @JsonIgnore
    public PixelChannel<Integer> getRed() {
        return get(PixelChannel.Channel.RED);
    }

    @Override
    @JsonIgnore
    public PixelChannel<Integer> getGreen() {
        return get(PixelChannel.Channel.GREEN);
    }

    @Override
    @JsonIgnore
    public PixelChannel<Integer> getBlue() {
        return get(PixelChannel.Channel.BLUE);
    }

    @Override
    @JsonIgnore
    public PixelChannel<Integer> getMinBrightness() {
        return get(PixelChannel.Channel.MIN_BRIGHTNESS);
    }

    @Override
    @JsonIgnore
    public PixelChannel<Integer> getMaxBrightness() {
        return get(PixelChannel.Channel.MAX_BRIGHTNESS);
    }

    @Override
    @JsonIgnore
    public PixelChannel<Double> getHue() {
        return get(PixelChannel.Channel.HUE);
    }

    @Override
    @JsonIgnore
    public PixelChannel get(PixelChannel.Channel channel) {
        return values[channel.ordinal()];
    }

    @Override
    @JsonIgnore
    public void setARGB(int argb) {
        //System.out.printf("setARGB: argb=%08X\n", argb);
        setARGB(getARGB_Alpha(argb), getARGB_Red(argb), getARGB_Green(argb), getARGB_Blue(argb));
    }

    @Override
    @JsonIgnore
    public final void setARGB(int a, int r, int g, int b) {
        //System.out.printf("setARGB: argb={%02X,%02X,%02X,%02X}\n", a, r, g, b);
        getAlpha().setValue(a);
        getRed().setValue(r);
        getGreen().setValue(g);
        getBlue().setValue(b);
    }

    @Override
    @JsonIgnore
    public final void setARGB(BufferedImage image, int x, int y) {
        setARGB(getARGB(image, x, y));
    }

    private void addBrightnessInfo() {
        int brightness = getBrightness(getRed().getValue(), getGreen().getValue(), getBlue().getValue());
        getMinBrightness().setValue(brightness);
        getMaxBrightness().setValue(brightness);
    }

    private void addHueInfo() {
        double hue = ColorUtils.getHue(getRed().getValue(), getGreen().getValue(), getBlue().getValue());
        getHue().setValue(hue);
    }
}
