package com.swaraajsports.technician.util;

public interface OtpReceivedInterface {
    void onOtpReceived(String otp);

    void onOtpTimeout();
}
