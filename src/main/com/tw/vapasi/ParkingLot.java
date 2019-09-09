package com.tw.vapasi;

import java.util.HashSet;

// Understands space available to station a parkable automobile
public class ParkingLot {
    private final long availableSpace;
    private HashSet<Parkable> parkedVehicles;
    private ParkingLotOwner owner;

    public ParkingLot(long maxSpace) {
        this.availableSpace = maxSpace;
        this.parkedVehicles = new HashSet<>();
    }

    public ParkingLot(long maxSpace, ParkingLotOwner owner) {
        this.availableSpace = maxSpace;
        this.parkedVehicles = new HashSet<>();
        this.owner = owner;
    }

    private boolean isParkingFull() {
        return parkedVehicles.size() >= availableSpace;
    }

    public void park(Parkable vehicle) throws SpaceNotAvailableException, VehicleAlreadyParkedException {
        if (isParkingFull()) {
            throw new SpaceNotAvailableException("Parking full");
        }
        boolean isParked = parkedVehicles.add(vehicle);
        if (!isParked) {
            throw new VehicleAlreadyParkedException();
        }
        if(isParkingFull()) {
            owner.notifyParkingFull();
        }
    }

    public void unPark(Parkable vehicle) throws VehicleNotParkedException {
        boolean status = parkedVehicles.remove(vehicle);
        if (!status) {
            throw new VehicleNotParkedException("Vehicle Not Parked");
        }
    }

    public boolean isCarParked(Parkable car) {
        return parkedVehicles.contains(car);
    }
}
