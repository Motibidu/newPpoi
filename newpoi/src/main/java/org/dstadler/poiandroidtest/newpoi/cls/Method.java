package org.dstadler.poiandroidtest.newpoi.cls;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Method {
    public static boolean loadDirectoryComplete = false;


    public static void load_Directory_Files(File directory){

        File[] fileList = directory.listFiles();


//        List<Constant> fileInfos = new ArrayList<>();
        if(fileList != null && fileList.length > 0){
            for (int i = 0; i< fileList.length; i++){
                if(fileList[i].isDirectory()){
                    load_Directory_Files(fileList[i]);
                }
                else {
                    String name = fileList[i].getName().toLowerCase();
                    for (String extension: Constant.fileExtensions){
                        //check the type of file
                        //except file which is from ".Trash"
                        //except duplicate
                        if(name.endsWith(extension) && !fileList[i].getAbsolutePath().contains(".Trash") && !Constant.allFileList.contains(fileList[i])){
                            Constant.allFileList.add(fileList[i]);
                            Constant.allFileNameList.add(fileList[i].getName());
                            Constant.allAbsolutePathList.add(fileList[i].getAbsolutePath());
                            Constant.allParentPathList.add(fileList[i].getParent());
                            //when we found file
                            break;
                        }
                    }
                }
            }
            loadDirectoryComplete = true;
        }

    }

    public static void loadInfoFromPref(Context mContext){
        ArrayList<String> pref_allFilNameList = new ArrayList(PreferenceManager.getStringArrayPref(mContext, "pref_allFilNameList"));
        ArrayList<String> allFileNameList = new ArrayList(PreferenceManager.getStringArrayPref(mContext, "pref_allFileNameList"));

    }
}
