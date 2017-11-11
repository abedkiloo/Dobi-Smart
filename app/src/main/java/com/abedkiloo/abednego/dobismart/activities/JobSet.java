package com.abedkiloo.abednego.dobismart.activities;

/**
 * Created by abedkiloo on 11/11/17.
 */

public class JobSet {
    String jobName;
    String jobContent;
    String jobLocation;

    public JobSet() {
    }

    public JobSet(String jName, String jContent, String jLocation) {
        this.jobName=jName;
        this.jobContent=jContent;
        this.jobLocation=jLocation;

    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

}
