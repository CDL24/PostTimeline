package interfaces;

import java.util.ArrayList;

import modal.Timeline;

/**
 * Created by C.limbachiya on 8/9/2016.
 */
public interface TimelineUpdateListener {
    void onDataAvailable(ArrayList<Timeline> newDataList);
}
