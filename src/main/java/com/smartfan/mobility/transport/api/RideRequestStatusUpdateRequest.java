package com.smartfan.mobility.transport.api;

public class RideRequestStatusUpdateRequest {
    private String driverProfileId;
    private String driverDisplayName;
    private String driverPhone;
    private String driverVehiclePlate;
    private String driverVehicleLabel;
    private String driverVehicleImageUrl;
    private Double driverLatitude;
    private Double driverLongitude;
    private Double driverDistanceKm;
    private Integer driverEtaMinutes;
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

    public String getDriverVehiclePlate() {
        return driverVehiclePlate;
    }

    public void setDriverVehiclePlate(String driverVehiclePlate) {
        this.driverVehiclePlate = driverVehiclePlate;
    }

    public String getDriverVehicleLabel() {
        return driverVehicleLabel;
    }

    public void setDriverVehicleLabel(String driverVehicleLabel) {
        this.driverVehicleLabel = driverVehicleLabel;
    }

    public String getDriverVehicleImageUrl() {
        return driverVehicleImageUrl;
    }

    public void setDriverVehicleImageUrl(String driverVehicleImageUrl) {
        this.driverVehicleImageUrl = driverVehicleImageUrl;
    }

    public Double getDriverLatitude() {
        return driverLatitude;
    }

    public void setDriverLatitude(Double driverLatitude) {
        this.driverLatitude = driverLatitude;
    }

    public Double getDriverLongitude() {
        return driverLongitude;
    }

    public void setDriverLongitude(Double driverLongitude) {
        this.driverLongitude = driverLongitude;
    }

    public Double getDriverDistanceKm() {
        return driverDistanceKm;
    }

    public void setDriverDistanceKm(Double driverDistanceKm) {
        this.driverDistanceKm = driverDistanceKm;
    }

    public Integer getDriverEtaMinutes() {
        return driverEtaMinutes;
    }

    public void setDriverEtaMinutes(Integer driverEtaMinutes) {
        this.driverEtaMinutes = driverEtaMinutes;
    }

    public String getResponseNote() {
        return responseNote;
    }

    public void setResponseNote(String responseNote) {
        this.responseNote = responseNote;
    }
}
