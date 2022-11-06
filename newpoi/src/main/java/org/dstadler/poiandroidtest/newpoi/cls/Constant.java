package org.dstadler.poiandroidtest.newpoi.cls;

import org.dstadler.poiandroidtest.newpoi.main.RecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constant {

    public static String[] fileExtensions = {".docx", ".doc"};

    public static ArrayList<File> allFileList = new ArrayList<>();
    public static ArrayList<String> allFileNameList = new ArrayList<>();
    public static ArrayList<String> allAbsolutePathList = new ArrayList<>();
    public static ArrayList<String> allParentPathList = new ArrayList<>();
    public static List<RecyclerViewAdapter.FileLayoutHolder> list = new ArrayList<>();

    public static HashMap<String, String> scanInfo= new HashMap<>();

}
