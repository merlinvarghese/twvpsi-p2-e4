package com.tw.vapasi;

import java.util.HashSet;

// Understands space available to station a parkable automobile
public class ParkingLot {
    long availableSpace;
    HashSet<Vehicle> parkedVehicles;

    public ParkingLot(long maxSpace) {
        this.availableSpace = maxSpace;
        parkedVehicles = new HashSet<Vehicle>();
    }

    private boolean isParkingSpaceAvailable() {
        if (parkedVehicles.size() < availableSpace) {
            return true;
        }
        return false;
    }

    public void park(Vehicle vehicle) throws SpaceNotAvailableException {
        if (!isParkingSpaceAvailable()) {
            throw new SpaceNotAvailableException("Parking full");
        }
        parkedVehicles.add(vehicle);
    }

    public void unPark(Vehicle vehicle) throws VehicleNotParkedException {
        boolean status = parkedVehicles.remove(vehicle);
        if (!status) {
            throw new VehicleNotParkedException("Vehicle Not Parked");
        }
    }
}
