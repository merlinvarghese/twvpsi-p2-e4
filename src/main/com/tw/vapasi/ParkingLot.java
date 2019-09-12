package com.tw.vapasi;

import java.util.HashSet;

// Understands space available to station a parkable automobile
class ParkingLot {
    private final long capacity;
    private HashSet<Parkable> parkedVehicles;
    private ParkingLotOwner owner;

    ParkingLot(long maxSpace) {
        this.capacity = maxSpace;
        this.parkedVehicles = new HashSet<>();
    }

    ParkingLot(long maxSpace, ParkingLotOwner owner) {
        this.capacity = maxSpace;
        this.parkedVehicles = new HashSet<>();
        this.owner = owner;
    }

    private boolean isParkingFull() {
        return capacity == parkedVehicles.size();
    }

    void park(Parkable vehicle) throws SpaceNotAvailableException,
            VehicleAlreadyParkedException {
        if (isParkingFull()) {
            throw new SpaceNotAvailableException("Parking full");
        }
        boolean isParked = parkedVehicles.add(vehicle);
        if (!isParked) {
            throw new VehicleAlreadyParkedException();
        }
        sendParkingFullNotification();
    }

    void unPark(Parkable vehicle) throws VehicleNotParkedException {
        boolean isUnParked = parkedVehicles.remove(vehicle);
        if (!isUnParked) {
            throw new VehicleNotParkedException("Vehicle Not Parked");
        }
        sendParkingAvailableNotificationToOwner();
    }

    private void sendParkingAvailableNotificationToOwner() {
        if (owner == null) {
            return;
        }
        if ((capacity - 1) == parkedVehicles.size())
            owner.notifyParkingAvailable();
    }

    boolean isCarParked(Parkable car) {
        return parkedVehicles.contains(car);
    }

    private void sendParkingFullNotification() {
        if (owner != null && isParkingFull()) {
            owner.notifyParkingFull();
        }
    }

    boolean isParkingSpaceAvailable() {
        return !isParkingFull();
    }
}
