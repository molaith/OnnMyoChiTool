package com.molaith.omyochitools.domin;

import java.util.List;

/**
 * Created by molaith on 2017/12/20.
 */

public class YoukaiChallenge {
    public String name="";
    public List<Step> stepList;

    public static class Step {
        public int id = 1;
        public List<Shikikami> shikikamiList = null;
    }
}
