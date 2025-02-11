package mouse.old.support;

import mouse.old.VirtualMouse;

import java.awt.*;
import java.util.Random;

public class DefaultMouseMotionNature extends MouseMotionNature {
    public static final int TIME_TO_STEPS_DIVIDER = 8;
    public static final int MIN_STEPS = 10;
    public static final int EFFECT_FADE_STEPS = 15;
    public static final int REACTION_TIME_BASE_MS = 20;
    public static final int REACTION_TIME_VARIATION_MS = 120;

    public DefaultMouseMotionNature() {
        setDeviationProvider(new DeviationProvider());
        setNoiseProvider(new NoiseProvider());
        setSpeedManager(new DefaultSpeedManager());
        setOvershootManager(new DefaultOvershootManager(new Random()));
        setEffectFadeSteps(EFFECT_FADE_STEPS);
        setMinSteps(MIN_STEPS);
        setReactionTimeBaseMs(REACTION_TIME_BASE_MS);
        setReactionTimeVariationMs(REACTION_TIME_VARIATION_MS);
        setTimeToStepsDivider(TIME_TO_STEPS_DIVIDER);
    }
}