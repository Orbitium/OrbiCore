package com.orbi.orbimc.database;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.util.Color;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repo {
    private static final Map<String, String> textCache = new HashMap<>();
    private static final Map<String, Object> configCache = new HashMap<>();
    private static final Map<String, String> messageCache = new HashMap<>();
    public static Map<String, List<String>> infoCache = new HashMap<>();

    public static void loadAll() {
        File textFiles = new File(OrbiCore.getInstance().getDataFolder() + "/Text/");

        for (File file : textFiles.listFiles()) {
            try {
                textCache.putAll(OrbiCore.getOB().readValue(file, Map.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loadSystemFiles();
        formatTexts();
    }

    public static void formatTexts() {
        for (Map.Entry<String, String> entry : textCache.entrySet()) {
            if (!entry.getValue().contains("${"))
                continue;
            char[] charEntry = entry.getValue().toCharArray();
            String mainText = entry.getValue();
            for (int i = 0; i < entry.getValue().length(); i++) {
                if (charEntry[i] == '$' && charEntry[i + 1] == '{') {
                    StringBuilder value = new StringBuilder();
                    for (int i1 = i + 2; i1 < entry.getValue().length(); i1++) {
                        if (charEntry[i1] != '}') {
                            value.append(charEntry[i1]); //value tamalandığında gerçek doğru değeri veriyor ama sonunda bir boşluk ile
                        } else {
                            mainText = mainText.replace("${", "").replace("}", "").replace(value.toString(), textCache.get(value.toString()));
                            break;
                        }
                    }
                }
            }
            textCache.replace(entry.getKey(), mainText);
        }
    }

    public static void loadSystemFiles() {
        File systemFiles = new File(OrbiCore.getInstance().getDataFolder() + "/Systems/");
        File configFile = new File(OrbiCore.getInstance().getDataFolder() + "/config.yml");

        try {
            configCache.putAll(OrbiCore.getOB().readValue(configFile, Map.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File msgFile = new File(OrbiCore.getInstance().getDataFolder() + "/Text/Messages.yml");

        try {
            messageCache.putAll(OrbiCore.getOB().readValue(msgFile, Map.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (File systemFile : systemFiles.listFiles()) { //Tüm sistem dosyaları
            for (File singleSystemFile : systemFile.listFiles()) { //Tek bir sisteme ait dosyalar
                switch (singleSystemFile.getName()) {
                    case "text.yml":
                        try {
                            textCache.putAll(OrbiCore.getOB().readValue(singleSystemFile, Map.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "config.yml":
                        try {
                            configCache.putAll(OrbiCore.getOB().readValue(singleSystemFile, Map.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "message.yml":
                        try {
                            messageCache.putAll(OrbiCore.getOB().readValue(singleSystemFile, Map.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "info.yml":
                        try {
                            infoCache.putAll(OrbiCore.getOB().readValue(singleSystemFile, Map.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
    }

    public static String getText(String textName) {
        if (textCache.get(textName) == null)
            throw new NullPointerException(textName + " metini bulunamadı");
        return Color.translateHex(textCache.get(textName));
    }

    public static String[] getMultipleText(String... textNames) {
        String[] returnList = new String[textNames.length];
        int i = 0;
        for (String textName : textNames) {
            if (textCache.get(textName) != null)
                returnList[i] = (Color.translateHex(textCache.get(textName)));
            else throw new NullPointerException(textName + " sunucu mesajı bulunamadı!");
            i++;
        }
        return returnList;
    }

    public static List<Object> getMultipleConfig(String... configIDs) {
        List<Object> list = new ArrayList<>();
        int i = 0;
        for (String configID : configIDs) {
            if (configCache.get(configID) != null)
                list.add(configCache.get(configID));
            else throw new NullPointerException(configID + " ayarı bulunamadı!");
            i++;
        }
        return list;
    }

    public static int getConfig(String configID) {
        if (configCache.get(configID) == null)
            throw new NullPointerException(configID + " ayarı bulunamadı");
        return (int) configCache.get(configID);
    }

    public static List<String> getInfo(String infoID) {
        if (infoCache.get(infoID) == null)
            throw new NullPointerException(infoID + " bilgi metni bulunamadı");
        return infoCache.get(infoID);
    }

    public static String getMSG(String messageID) {
        if (messageCache.get(messageID) == null)
            throw new NullPointerException(messageID + " sunucu metini bulunamadı");
        return messageCache.get(messageID);
    }

    public static void setConfig(String key, int value) {
        if (configCache.get(key) != null) {
            configCache.put(key, value);
            File configFile = new File(OrbiCore.getInstance().getDataFolder() + "/config.yml");
            try {
                OrbiCore.getOB().writeValue(configFile, Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
