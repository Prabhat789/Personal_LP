package com.mobisys.aspr.model;

/**
 * Created by ubuntu1 on 5/4/16.
 */
public class CallTrackModel {

    int id;
    String Number;
    String callStratTime;
    String callEndTime;
    String callType;
    String callDuration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getCallStratTime() {
        return callStratTime;
    }

    public void setCallStratTime(String callStratTime) {
        this.callStratTime = callStratTime;
    }

    public String getCallEndTime() {
        return callEndTime;
    }

    public void setCallEndTime(String callEndTime) {
        this.callEndTime = callEndTime;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public CallTrackModel( String number, String callStratTime, String callEndTime, String callType, String callDuration) {
        Number = number;
        this.callStratTime = callStratTime;
        this.callEndTime = callEndTime;
        this.callType = callType;
        this.callDuration = callDuration;
    }

    public CallTrackModel() {
    }
}
