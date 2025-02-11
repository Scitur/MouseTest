package mouse.old.support;

import lombok.Getter;
import lombok.Setter;
import mouse.old.api.InterfaceSpeedManager;

@Getter
@Setter
public abstract class MouseMotionNature {
    private double timeToStepsDivider;

    private int minSteps;
    private int effectFadeSteps;
    private int reactionTimeBaseMs;
    private int reactionTimeVariationMs;

    private DeviationProvider deviationProvider;
    private NoiseProvider noiseProvider;
    private DefaultOvershootManager overshootManager;
    private InterfaceSpeedManager speedManager;
}
