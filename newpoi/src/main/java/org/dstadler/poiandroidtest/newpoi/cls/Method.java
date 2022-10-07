package org.dstadler.poiandroidtest.newpoi.cls;

import java.io.File;
import java.util.Locale;

public class Method {

    public static void load_Directory_Files(File directory){

        File[] fileList = directory.listFiles();
        if(fileList != null && fileList.length > 0){
            for (int i = 0; i< fileList.length; i++){
                if(fileList[i].isDirectory()){
                    load_Directory_Files(fileList[i]);
                }
                else {
                    String name = fileList[i].getName().toLowerCase();
                    for (String extension: Constant.wordExtensions){
                        //check the type of file and if allWordList doesn't contain now file, add the file in allWordList
                        if(name.endsWith(extension) && !Constant.allWordList.contains(fileList[i])) {
                            Constant.allWordList.add(fileList[i]);
                            //when we found file
                            break;
                        }
                    }
                }

            }
        }
    }
}
