package mouse.old.support;

import mouse.old.api.InterfaceSpeedManager;
import mouse.old.templates.FlowTemplates;
import mouse.old.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultSpeedManager implements InterfaceSpeedManager {
    private static final double SMALL_DELTA = 10e-6;
    private final List<Flow> flows;
    private long mouseMovementTimeMs = 500;

    public DefaultSpeedManager(List<Flow> flows) {
        this.flows = flows;
    }

    public DefaultSpeedManager(Collection<Flow> flows) {
        this(new ArrayList<>());
        this.flows.addAll(flows);
    }

    public DefaultSpeedManager() {
        this(Arrays.stream(FlowTemplates.values())
                .map(FlowTemplates::getFlow)
                .collect(Collectors.toList()));
    }

    @Override
    public Pair<Flow,Long> getFlowWithTime(double distance) {
        double time = mouseMovementTimeMs + (long) (Math.random() * mouseMovementTimeMs);
        Flow flow = flows.get((int) (Math.random() * flows.size()));

        // Let's ignore waiting time, e.g 0's in flow, by increasing the total time
        // by the amount of 0's there are in the flow multiplied by the time each bucket represents.
        final long nSmallDelta = Arrays.stream(flow.getFlowCharacteristics())
                .filter(bucket -> Math.abs(bucket - 0) < SMALL_DELTA).count();

        if (nSmallDelta == flow.getFlowCharacteristics().length) throw new IllegalArgumentException();

        final double timePerBucket = time / (double) (flow.getFlowCharacteristics().length-nSmallDelta);
        time += timePerBucket * nSmallDelta;

        return new Pair<>(flow,(long) time);
    }

    public void setMouseMovementBaseTimeMs(long mouseMovementSpeedMs) {
        this.mouseMovementTimeMs = mouseMovementSpeedMs;
    }
}