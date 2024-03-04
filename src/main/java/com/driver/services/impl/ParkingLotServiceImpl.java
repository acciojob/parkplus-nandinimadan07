package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

//    @Override
//    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
//        Reservation reservation = reservationRepository2.findById(reservationId).get();
//
//        Payment payment = new Payment();
//        Spot spot = reservation.getSpot();
//
//        if(mode.equals("CASH")){   //doubt : how will it work for cAsh or cash or Cash
//            payment.setPaymentMode(PaymentMode.CASH);
//        }
//        else if(mode.equals("CARD")){
//            payment.setPaymentMode(PaymentMode.CARD);
//        }
//        else if(mode.equals("UPI")){
//            payment.setPaymentMode(PaymentMode.UPI);
//        }
//        else throw new Exception("Payment mode not detected");
//
//
//        int numOfHours = reservation.getNumberOfHours();
//        int pricePerHour = reservation.getSpot().getPricePerHour();
//        int amountReq = numOfHours * pricePerHour;
//
//        if(amountReq < amountSent){
//            throw new Exception("Insufficient Amount");
//        }
//        spot.setOccupied(false);
//        payment.setPaymentCompleted(true);
//        payment.setReservation(reservation);
//        reservation.setPayment(payment);
//
//        reservationRepository2.save(reservation);
//        return payment;
//
//
////        payment = paymentRepository2.save(payment);       //to get payment id // not required because we used generationtype.auto and it gives the id automaticly.
//    }


    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation=reservationRepository2.findById(reservationId).get();
        Spot spot= reservation.getSpot();
        int bill = reservation.getNumberOfHours() * spot.getPricePerHour();

        Payment payment=new Payment();
        payment.setReservation(reservation);

        String updatedMode = mode.toUpperCase();
        payment.setPaymentCompleted(false);


        if(updatedMode.equals("CASH")){
            payment.setPaymentMode(PaymentMode.CASH);
        }
        else if(updatedMode.equals("CARD")){
            payment.setPaymentMode(PaymentMode.CARD);
        }
        else if(updatedMode.equals("UPI")){
            payment.setPaymentMode(PaymentMode.UPI);
        }
        else {throw new Exception("Payment mode not detected");}


        if( amountSent< bill){
            throw new Exception("Insufficient Amount");
        }
        spot.setOccupied(false);
        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);
        reservation.setPayment(payment);
        reservationRepository2.save(reservation);
        return  payment;

    }

}