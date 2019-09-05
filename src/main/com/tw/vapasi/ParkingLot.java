package com.tw.vapasi;

import java.util.HashSet;

// Understands space available to station a parkable automobile
public class ParkingLot {
    private final long availableSpace;
    private HashSet<Vehicle> parkedVehicles;

    public ParkingLot(long maxSpace) {
        this.availableSpace = maxSpace;
        parkedVehicles = new HashSet<>();
    }

    private boolean isParkingSpaceAvailable() {
        return parkedVehicles.size() < availableSpace;
    }

    public void park(Vehicle vehicle) throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        if (!isParkingSpaceAvailable()) {
            throw new SpaceNotAvailableException("Parking full");
        }
        boolean status = parkedVehicles.add(vehicle);
        if (!status) {
            throw new VehicleAlreadyParkedException();
        }
    }

    public void unPark(Vehicle vehicle) throws VehicleNotParkedException {
        boolean status = parkedVehicles.remove(vehicle);
        if (!status) {
            throw new VehicleNotParkedException("Vehicle Not Parked");
        }
    }
}
