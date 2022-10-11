package org.dstadler.poiandroidtest.newpoi.cls;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Constant {
//    private File file;
//    private String absolutePath;
//    private String parentPath;
//
//
//
//    public Constant(File file, String absolutePath, String parentPath){
//        this.file = file;
//        this.absolutePath = absolutePath;
//        this.parentPath = parentPath;
//    }

    public static List<Constant> fileInfos = new ArrayList<>();

    public static String[] fileExtensions = {".docx", ".doc"};
//
    public static ArrayList<File> allFileList = new ArrayList<>();
    public static ArrayList<String> allAbsolutePathList = new ArrayList<>();
    public static ArrayList<String> allParentPathList = new ArrayList<>();

    //FileAbsolutePath,
    public static ArrayList<ArrayList<String>> allFileInfo = new ArrayList<ArrayList<String>>();

}
