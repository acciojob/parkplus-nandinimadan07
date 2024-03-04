package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.model.PaymentMode;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation=reservationRepository2.findById(reservationId).get();


        if(amountSent < reservation.getSpot().getPricePerHour()*reservation.getNumberOfHours())
        {
            throw new Exception("Insufficient Amount");
        }

        Payment payment=new Payment();

        String modeType=mode.toUpperCase();
        if(modeType.equals("CASH")){
            payment.setPaymentMode(PaymentMode.CASH);
        }
        else if(modeType.equals("CARD")) {
            payment.setPaymentMode(PaymentMode.CARD);
        }
        else if(modeType.equals("UPI")){
            payment.setPaymentMode(PaymentMode.UPI);
        }
        else{
            throw new Exception("Payment mode not detected");
        }
        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);
        reservation.getSpot().setOccupied(false);
        reservation.setPayment(payment);
        reservationRepository2.save(reservation);
        return payment;
    }
}
