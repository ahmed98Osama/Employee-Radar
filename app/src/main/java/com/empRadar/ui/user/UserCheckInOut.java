package com.empRadar.ui.user;

public class UserCheckInOut {


    private String id;
    private String name;
    private String available;
    private Double timein;
    private Double timeout;
    private Double timeintimeout;
    private Double totaltime;
    private String timeinSt;
    private String timeinoutSt;
    private String timeoutSt;

    public UserCheckInOut() {
    }

    public UserCheckInOut(String id, String name, String available, Double timein, Double timeout, Double timeintimeout, Double totaltime, String timeinSt, String timeinoutSt, String timeoutSt) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.timein = timein;
        this.timeout = timeout;
        this.timeintimeout = timeintimeout;
        this.totaltime = totaltime;
        this.timeinSt = timeinSt;
        this.timeinoutSt = timeinoutSt;
        this.timeoutSt = timeoutSt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Double getTimein() {
        return timein;
    }

    public void setTimein(Double timein) {
        this.timein = timein;
    }

    public Double getTimeout() {
        return timeout;
    }

    public void setTimeout(Double timeout) {
        this.timeout = timeout;
    }

    public Double getTimeintimeout() {
        return timeintimeout;
    }

    public void setTimeintimeout(Double timeintimeout) {
        this.timeintimeout = timeintimeout;
    }

    public Double getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(Double totaltime) {
        this.totaltime = totaltime;
    }

    public String getTimeinSt() {
        return timeinSt;
    }

    public void setTimeinSt(String timeinSt) {
        this.timeinSt = timeinSt;
    }

    public String getTimeinoutSt() {
        return timeinoutSt;
    }

    public void setTimeinoutSt(String timeinoutSt) {
        this.timeinoutSt = timeinoutSt;
    }

    public String getTimeoutSt() {
        return timeoutSt;
    }

    public void setTimeoutSt(String timeoutSt) {
        this.timeoutSt = timeoutSt;
    }
}