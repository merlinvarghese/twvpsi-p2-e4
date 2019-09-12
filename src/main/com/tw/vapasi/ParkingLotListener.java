package com.tw.vapasi;

interface ParkingLotListener {
    void notifyParkingFull(ParkingLot parkingLot);

    void notifyParkingAvailable(ParkingLot parkingLot);
}
