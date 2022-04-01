package com.progressoft.jipfive;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ImportReport {

    private String jobName;
    private LocalDateTime queuingTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long totalTime;
    private String message;
    private Status status;

    public static enum Status {
        COMPLETED, RUNNING, WATING, FAILD
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public LocalDateTime getQueuingTime() {
        return queuingTime;
    }

    public void setQueuingTime(LocalDateTime queuingTime) {
        this.queuingTime = queuingTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime() {
        this.totalTime = ChronoUnit.SECONDS.between( queuingTime, endTime );
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getJobName() + "|" + getStatus() + "|" + getQueuingTime() + "|" +
                getStartTime() + "|" + getEndTime() + "|" + getTotalTime() +
                "|" + getMessage();
    }
}
