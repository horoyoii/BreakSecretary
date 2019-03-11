package com.beacon.breaksecretary.model;

public class Section {
    String sectionNum;
    int reserveNum;
    String totallNum;

    public Section(String sectionNum, int reserveNum, String totallNum) {
        this.sectionNum = sectionNum;
        this.reserveNum = reserveNum;
        this.totallNum = totallNum;
    }

    public String getSectionNum() {
        return sectionNum;
    }

    public int getReserveNum() {
        return reserveNum;
    }

    public String getTotallNum() {
        return totallNum;
    }

    public void setSectionNum(String sectionNum) {
        this.sectionNum = sectionNum;
    }

    public void setReserveNum(int reserveNum) {
        this.reserveNum = reserveNum;
    }

    public void setTotallNum(String totallNum) {
        this.totallNum = totallNum;
    }
}
