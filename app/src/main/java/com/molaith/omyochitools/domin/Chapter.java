package com.molaith.omyochitools.domin;

import java.util.List;

/**
 * Created by molaith on 2017/12/20.
 */

public class Chapter {
    public String index;
    public List<YouKai> youKais;
    public List<Shikikami> challengeList;

    public static class YouKai {
        public String name;
        public List<Shikikami> shikikamiList = null;

        public YouKai() {
        }
    }
}
