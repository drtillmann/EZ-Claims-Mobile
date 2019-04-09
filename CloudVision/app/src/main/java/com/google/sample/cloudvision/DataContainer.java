package com.google.sample.cloudvision;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataContainer {

    private static List<GoogleVisionItemInfo> container;
    private static DataContainer dataContainer;

    private DataContainer(){
        container = new ArrayList<>();
    }

    public static DataContainer instance(){
        if(dataContainer == null){
            return dataContainer = new DataContainer();
        }else{
            return dataContainer;
        }
    }

    public void addData(GoogleVisionItemInfo info){
        container.add(info);
    }

    public Iterator getData(){
        return container.iterator();
    }

}
