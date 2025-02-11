package mouse.old.api;

import mouse.old.support.Flow;
import mouse.old.util.Pair;

/**
 * SpeedManager controls how long does it take to complete a mouse movement and within that
 * time how slow or fast the cursor is moving at a particular moment, the flow.
 * Flow controls how jagged or smooth, accelerating or decelerating, the movement is.
 */
public interface InterfaceSpeedManager {

    /**
     * Get the SpeedFlow object, which contains Flow and planned time for mouse movement in ms.
     *
     * @param distance the distance from where the cursor is now to the destination point   *
     * @return the SpeedFlow object, which details are a SpeedManager implementation decision.
     */
    // TODO: Change to origin, destination? Distance is currently not used
    // Also maybe get rid of this entirely? Add util method to recalculate time based on flow?
    // or merge flow and speedManager? speed is dependant on flow&time since speed is distance over time
    Pair<Flow, Long> getFlowWithTime(double distance);
}