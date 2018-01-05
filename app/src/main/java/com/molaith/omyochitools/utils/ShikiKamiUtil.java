package com.molaith.omyochitools.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.litesuits.common.io.IOUtils;
import com.molaith.omyochitools.domin.Chapter;
import com.molaith.omyochitools.domin.Shikikami;
import com.molaith.omyochitools.domin.YokiHuinn;
import com.molaith.omyochitools.domin.YoukaiChallenge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by molaith on 2017/12/20.
 */

public class ShikiKamiUtil {
    private static List<Shikikami> shikikamiList = null;

    public static void init(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            String shikikamis = IOUtils.toString(assetManager.open("shikikami.txt"));
            if (shikikamis.contains("seperator_fa")) {
                String[] counterparts = shikikamis.split("seperator_fa");
                shikikamiList = new ArrayList<>();
                initChapters(counterparts[0]);
                initYokiHuinn(counterparts[1]);
                initkarumaHaraNoHono(counterparts[2]);
                inityamataNoOrochi(counterparts[3]);
                inityoukaiChallengeList(counterparts[4]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initChapters(String chapters) {
        if (!chapters.contains("seperator_ch")) {
            return;
        }
        String[] chapterStrings = chapters.split("seperator_ch");
        for (String current : chapterStrings) {
            int startIndex = 0;
            Chapter chapter = new Chapter();
            current = current.trim();
            String[] lines = current.split("\n");
            chapter.index = lines[startIndex].trim();
            if (TextUtils.isEmpty(chapter.index)) {
                startIndex++;
                chapter.index = lines[startIndex].trim();
            }
            boolean challengestarted = false;
            String yokaiName = "";
            for (int i = startIndex + 1; i < lines.length; i++) {
                if ((i & 1) == 0) {
                    if (challengestarted) {
                        dealWithShikiKamiString(false, "", false, "", false, "", false, "", "",
                                true, chapter.index, yokaiName, lines[i]);
                    } else {
                        if (lines[i].contains(",")) {
                            String[] shikikamis = lines[i].split(",");
                            for (String currentSKKM : shikikamis) {
                                Shikikami shikikami = new Shikikami();
                                shikikami.chapterIndex = chapter.index;
                                shikikami.from = yokaiName;
                                shikikami.name = currentSKKM.split("×")[0].trim();
                                if (currentSKKM.split("×")[1].contains("模式")) {
                                    if (currentSKKM.split("×")[1].contains("简单模式")) {
                                        shikikami.model = "简单模式";
                                        shikikami.count = Integer.parseInt(currentSKKM.split("×")[1].split("\\(简单模式\\)")[0].trim());
                                    } else if (currentSKKM.split("×")[1].contains("困难模式")) {
                                        shikikami.model = "困难模式";
                                        shikikami.count = Integer.parseInt(currentSKKM.split("×")[1].split("\\(困难模式\\)")[0].trim());
                                    }
                                } else {
                                    shikikami.count = Integer.parseInt(currentSKKM.split("×")[1].trim());
                                }
                                shikikamiList.add(shikikami);
                            }
                        } else {
                            Shikikami shikikami = new Shikikami();
                            shikikami.chapterIndex = chapter.index;
                            shikikami.from = yokaiName;
                            shikikami.name = lines[i].split("×")[0].trim();
                            if (lines[i].split("×")[1].contains("模式")) {
                                if (lines[i].split("×")[1].contains("简单模式")) {
                                    shikikami.model = "简单模式";
                                    shikikami.count = Integer.parseInt(lines[i].split("×")[1].split("(简单模式)")[0].trim());
                                } else if (lines[i].split("×")[1].contains("困难模式")) {
                                    shikikami.model = "困难模式";
                                    shikikami.count = Integer.parseInt(lines[i].split("×")[1].split("(困难模式)")[0].trim());
                                }
                            } else {
                                shikikami.count = Integer.parseInt(lines[i].split("×")[1].trim());
                            }
                            shikikamiList.add(shikikami);
                        }
                    }
                } else if ((i & 1) == 1) {
                    if (lines[i].contains("挑战")) {
                        challengestarted = true;
                    } else {
                        if (challengestarted) {
                            dealWithShikiKamiString(false, "", false, "", false, "", false, "", "",
                                    true, chapter.index, yokaiName, lines[i]);
                        } else {
                            yokaiName = lines[i].trim();
                        }
                    }
                }
            }
        }
    }

    private static void initYokiHuinn(String yokistring) {
        if (!yokistring.contains("seperator_ch")) {
            return;
        }
        String[] yokis = yokistring.split("seperator_ch");
        for (String current : yokis) {
            current = current.trim();
            YokiHuinn huinn = new YokiHuinn();
            String[] huinnList = current.split("\n");
            huinn.name = huinnList[0].trim();
            huinn.shikikamiList = new ArrayList<>();
            for (int i = 2; i < huinnList.length; i++) {
                if ((i & 1) == 0) {
                    dealWithShikiKamiString(true, huinn.name, false, "", false, "",
                            false, "", "", false, "", "", huinnList[i]);
                }
            }
        }
    }

    private static void initkarumaHaraNoHono(String karumaHaraNoHonoString) {
        if (!karumaHaraNoHonoString.contains("seperator_ch")) {
            return;
        }
        String[] karumas = karumaHaraNoHonoString.split("seperator_ch");
        String[] chiList = karumas[0].trim().split("\n");
        for (int i = 1; i < chiList.length; i++) {
            if ((i & 1) == 0) {
                dealWithShikiKamiString(false, "", true, "痴", false, "",
                        false, "", "", false, "", "", chiList[i]);
            }
        }

        String[] chenList = karumas[1].trim().split("\n");
        for (int i = 1; i < chenList.length; i++) {
            if ((i & 1) == 0) {
                dealWithShikiKamiString(false, "", true, "嗔", false, "",
                        false, "", "", false, "", "", chiList[i]);
            }
        }

        String[] tanList = karumas[2].trim().split("\n");
        for (int i = 1; i < tanList.length; i++) {
            if ((i & 1) == 0) {
                dealWithShikiKamiString(false, "", true, "贪", false, "",
                        false, "", "", false, "", "", chiList[i]);
            }
        }
    }

    private static void inityamataNoOrochi(String yamataNoOrochiString) {

    }

    private static void inityoukaiChallengeList(String youkaiChallengeListString) {
        if (!youkaiChallengeListString.contains("seperator_ch")) {
            return;
        }
        for (String current : youkaiChallengeListString.split("seperator_ch")) {
            current = current.trim();
            YoukaiChallenge youkaiChallenge = new YoukaiChallenge();
            String[] challenges = current.split("\n");
            youkaiChallenge.name = challenges[0];
            youkaiChallenge.stepList = new ArrayList<>();
            int stepCount = 0;
            for (int i = 1; i < challenges.length; i++) {
                if (challenges[i].contains("第") && challenges[i].contains("层")) {
                    stepCount++;
                } else {
                    if (!challenges[i].contains("回合")) {
                        Log.d("molaith", "challenges:" + challenges[i]);
                        dealWithShikiKamiString(false, "", false, "", false, "",
                                true, youkaiChallenge.name, "第" + stepCount + "层", false, "", "", challenges[i]);
                    }
                }
            }
        }
    }

    private static void dealWithShikiKamiString(boolean isYokiHuinn, String huinnName, boolean isKarumaHaraNoHono, String karumaHaraNoHonoName, boolean isYamataNoOrochi, String yamataNoOrochiStage,
                                                boolean isSecretYokai, String secretName, String secretStage, boolean isChallenge, String chapterIndex, String yokaiName, String shikikamiString) {
        if (shikikamiList == null) {
            shikikamiList = new ArrayList<>();
        }
        if (isYokiHuinn) {
            if (shikikamiString.contains(",")) {
                for (String currentSKKM : shikikamiString.split(",")) {
                    Shikikami shikikami = new Shikikami();
                    shikikami.yokihuinnName = huinnName;
                    shikikami.name = currentSKKM.split("×")[0].trim();
                    shikikami.count = Integer.parseInt(currentSKKM.split("×")[1].trim());
                    shikikamiList.add(shikikami);
                }
            } else {
                Shikikami shikikami = new Shikikami();
                shikikami.yokihuinnName = huinnName;
                shikikami.name = shikikamiString.split("×")[0].trim();
                shikikami.count = Integer.parseInt(shikikamiString.split("×")[1].trim());
                shikikamiList.add(shikikami);
            }
        } else if (isKarumaHaraNoHono) {
            if (shikikamiString.contains(",")) {
                for (String currentSKKM : shikikamiString.split(",")) {
                    Shikikami shikikami = new Shikikami();
                    shikikami.karumaHaraNoHonoName = karumaHaraNoHonoName;
                    shikikami.name = currentSKKM.split("×")[0].trim();
                    shikikami.count = Integer.parseInt(currentSKKM.split("×")[1].trim());
                    shikikamiList.add(shikikami);
                }
            } else {
                Shikikami shikikami = new Shikikami();
                shikikami.karumaHaraNoHonoName = karumaHaraNoHonoName;
                shikikami.name = shikikamiString.split("×")[0].trim();
                shikikami.count = Integer.parseInt(shikikamiString.split("×")[1].trim());
                shikikamiList.add(shikikami);
            }
        } else if (isYamataNoOrochi) {
            if (shikikamiString.contains(",")) {
                for (String currentSKKM : shikikamiString.split(",")) {
                    Shikikami shikikami = new Shikikami();
                    shikikami.yamataNoOrochiStage = yamataNoOrochiStage;
                    shikikami.name = currentSKKM.split("×")[0].trim();
                    shikikami.count = Integer.parseInt(currentSKKM.split("×")[1].trim());
                    shikikamiList.add(shikikami);
                }
            } else {
                Shikikami shikikami = new Shikikami();
                shikikami.yamataNoOrochiStage = yamataNoOrochiStage;
                shikikami.name = shikikamiString.split("×")[0].trim();
                shikikami.count = Integer.parseInt(shikikamiString.split("×")[1].trim());
                shikikamiList.add(shikikami);
            }
        } else if (isSecretYokai) {
            if (shikikamiString.contains(",")) {
                for (String currentSKKM : shikikamiString.split(",")) {
                    Shikikami shikikami = new Shikikami();
                    shikikami.secretYokaiName = secretName;
                    shikikami.secretYokaiStage = secretStage;
                    shikikami.name = currentSKKM.split("×")[0].trim();
                    shikikami.count = Integer.parseInt(currentSKKM.split("×")[1].trim());
                    shikikamiList.add(shikikami);
                }
            } else {
                Shikikami shikikami = new Shikikami();
                shikikami.secretYokaiName = secretName;
                shikikami.secretYokaiStage = secretStage;
                shikikami.name = shikikamiString.split("×")[0].trim();
                shikikami.count = Integer.parseInt(shikikamiString.split("×")[1].trim());
                shikikamiList.add(shikikami);
            }
        } else {
            if (shikikamiString.contains(",")) {
                for (String currentSKKM : shikikamiString.split(",")) {
                    Shikikami shikikami = new Shikikami();
                    shikikami.from = yokaiName;
                    shikikami.chapterIndex = chapterIndex;
                    shikikami.name = currentSKKM.split("×")[0].trim();
                    shikikami.count = Integer.parseInt(currentSKKM.split("×")[1].trim());
                    shikikamiList.add(shikikami);
                }
            } else {
                Shikikami shikikami = new Shikikami();
                shikikami.from = yokaiName;
                shikikami.chapterIndex = chapterIndex;
                shikikami.isChallenge = isChallenge;
                shikikami.name = shikikamiString.split("×")[0].trim();
                shikikami.count = Integer.parseInt(shikikamiString.split("×")[1].trim());
                shikikamiList.add(shikikami);
            }
        }
    }

    public static List<Shikikami> getResult(String searchText) {
        List<Shikikami> result = new ArrayList<>();
        if (shikikamiList != null) {
            for (Shikikami shikikami : shikikamiList) {
                if (shikikami.name.contains(searchText)) {
                    result.add(shikikami);
                }
            }
        }
        return result;
    }



    public static String getCombinedResult(String searchText) {
        HashMap<String,List<Shikikami>> combine=new HashMap<>();
        if (searchText.contains(",")){
            String[] shikikamis=searchText.split(",");
            for (String current:shikikamis) {
                List<Shikikami> resultCurrent=new ArrayList<>();
                for (Shikikami shikikami:shikikamiList) {
                    if (shikikami.name.contains(current)){
                        resultCurrent.add(shikikami);
                    }
                }
                if (resultCurrent.size()>0){
                    combine.put(current,resultCurrent);
                }
            }
        }else if (searchText.contains("，")){
            String[] shikikamis=searchText.split("，");
        }
        return parseResult(combine,false,false,false);
    }

    public static String parseResult(HashMap<String,List<Shikikami>> combine,boolean sushibest, boolean timebest, boolean atleastTwo){
        Set<String> keys = combine.keySet();
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key+":\n");
            sb.append(parseResult(combine.get(key),sushibest,timebest,atleastTwo));
        }
        return sb.toString();
    }

    public static String parseResult(List<Shikikami> shikikamiResult, boolean sushibest, boolean timebest, boolean atleastTwo) {
        StringBuilder sb = new StringBuilder();
        String yokihuinn = "";
        for (Shikikami current : shikikamiResult) {
            if (atleastTwo && current.count >= 2) {
                if (current.isYokihuinn()) {
                    sb.append(current.yokihuinnName + " 妖气封印 " + current.count + "个\n");
                }
                if (current.isInKarumaHaraNoHonoName()) {
                    sb.append("业原火 " + current.karumaHaraNoHonoName + "之卷轴 " + current.count + "个\n");
                }
                if (current.isInyamataNoOrochi()) {
                    sb.append("八岐大蛇第" + current.yamataNoOrochiStage + "层 " + current.count + "个\n");
                }
                if (current.isInSecretYokai()) {
                    sb.append("妖怪秘闻 " + current.secretYokaiName + " " + current.secretYokaiStage + " " + current.count + "个\n");
                }

                if (current.isChallenge) {
                    sb.append(current.chapterIndex + " 妖怪挑战 " + current.count + "个\n");
                } else {
                    if (!TextUtils.isEmpty(current.chapterIndex)) {
                        sb.append(current.chapterIndex + " " + current.from + " " + (current.model.equals("困难模式") ? "(困难)" : "") + current.count + "个\n");
                    }
                }
            }
        }
        if (TextUtils.isEmpty(sb.toString())) {
            sb.append("没找到该妖怪哟!\n");
        }
        return sb.toString();
    }
}
