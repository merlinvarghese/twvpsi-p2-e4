package com.tw.vapasi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// Understands space available to station a parkable automobile
class ParkingLot {
    private final long capacity;
    private HashSet<Parkable> parkedVehicles;
    private List<ParkingLotListener> listeners;

    ParkingLot(long maxSpace) {
        this.capacity = maxSpace;
        this.parkedVehicles = new HashSet<>();
        this.listeners = new ArrayList<>();
    }

    void registerListener(ParkingLotListener listener){
        this.listeners.add(listener);
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
        sendParkingAvailableNotificationToListeners();
    }

    private void sendParkingAvailableNotificationToListeners() {
        for (ParkingLotListener listener : listeners) {
            if ((capacity - 1) == parkedVehicles.size())
                listener.notifyParkingAvailable(this);
        }
    }

    boolean isCarParked(Parkable car) {
        return parkedVehicles.contains(car);
    }

    private void sendParkingFullNotification() {
        for (ParkingLotListener listener : listeners) {
            if (isParkingFull())
                listener.notifyParkingFull(this);
        }
    }

    boolean isParkingSpaceAvailable() {
        return !isParkingFull();
    }
}
