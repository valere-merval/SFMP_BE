package com.smartfan.mobility.transport.api;

public class RideRequestStatusUpdateRequest {
    private String driverProfileId;
    private String driverDisplayName;
    private String driverPhone;
    private String responseNote;

    public String getDriverProfileId() {
        return driverProfileId;
    }

    public void setDriverProfileId(String driverProfileId) {
        this.driverProfileId = driverProfileId;
    }

    public String getDriverDisplayName() {
        return driverDisplayName;
    }

    public void setDriverDisplayName(String driverDisplayName) {
        this.driverDisplayName = driverDisplayName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getResponseNote() {
        return responseNote;
    }

    public void setResponseNote(String responseNote) {
        this.responseNote = responseNote;
    }
}
