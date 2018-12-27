package com.reafor.jiamixiu.bean;

import java.util.ArrayList;

public class ProvinceCityBean {
    public ArrayList<ProvinceData> data;
    public static class ProvinceData{
        public String areaId;
        public String areaName;
        public ArrayList<CityData> cities;
    }
    public static class CountyData{
        public String areaId;
        public String areaName;
    }

    public static class CityData{
        public String areaId;
        public String areaName;
        public ArrayList<CountyData> counties;
    }
}
