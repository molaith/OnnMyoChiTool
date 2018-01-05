package com.molaith.omyochitools.domin;

import android.text.TextUtils;

/**
 * Created by molaith on 2017/12/20.
 */

public class Shikikami {
    public String name = "";
    public int count = 0;
    public String model = "";
    public String from="";
    public String yokihuinnName = "";
    public String karumaHaraNoHonoName = "";
    public String yamataNoOrochiStage = "";
    public String secretYokaiName="";
    public String secretYokaiStage="";
    public String chapterIndex = "";
    public boolean isChallenge = false;

    public boolean isYokihuinn() {
        return !TextUtils.isEmpty(yokihuinnName);
    }

    public boolean isInKarumaHaraNoHonoName() {
        return !TextUtils.isEmpty(karumaHaraNoHonoName);
    }

    public boolean isInyamataNoOrochi() {
        return !TextUtils.isEmpty(yamataNoOrochiStage);
    }

    public boolean isInSecretYokai(){
        return !TextUtils.isEmpty(secretYokaiName);
    }
}
