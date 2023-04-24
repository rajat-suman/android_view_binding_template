package com.viewBindingTemplate.customclasses.otpTimer

import android.os.CountDownTimer
import java.util.concurrent.TimeUnit

/**
 * @author Mukesh Rajput
 * OTP Timer Handler
 *
 * 1. Automatic Time Handler
 * 2. Set Maximum Time
 * 3. Set Minimum Time
 * */
object OtpTimer {

    @JvmStatic
    lateinit var countDownTimer: CountDownTimer

    @JvmStatic
    var sendOtpTimerData: SendOtpTimerData? = null

    private var maxTime: Long = 30000
    private var timeInterval: Long = 1000


    /**
     * Start Time
     * */
    fun startTimer() = try {
        stopTimer()
        countDownTimer = object : CountDownTimer(maxTime, timeInterval) {
            override fun onTick(long: Long) {
                if ((long / timeInterval) < 10) "00:0${(long / timeInterval)}".sendTimerData()
                else "00:${(long / timeInterval)}".sendTimerData()
            }

            override fun onFinish() {
                "".sendTimerData()
            }
        }.start()
    } catch (e: Exception) {
        e.printStackTrace()
    }


    /**
     * Send Otp Timer
     * */
    fun String.sendTimerData() {
        sendOtpTimerData?.otpData(this)
    }


    /**
     * Stop Timer
     * */
    fun stopTimer() = run {
        if (OtpTimer::countDownTimer.isInitialized)
            countDownTimer.cancel()
    }


    /**
     * Set Interface Data
     * */
    interface SendOtpTimerData {
        fun otpData(string: String)
    }


    /**
     * Set Max Time
     * */
    fun setMaxTime(time: Int, type: OtpTimeType = OtpTimeType.MILLISECOND) {
        maxTime = when (type) {
            OtpTimeType.MILLISECOND -> time.toLong()
            OtpTimeType.SECOND -> TimeUnit.SECONDS.toMillis(time.toLong())
            OtpTimeType.MINUTE -> TimeUnit.MINUTES.toMillis(time.toLong())
            OtpTimeType.HOURS -> TimeUnit.HOURS.toMillis(time.toLong())
            OtpTimeType.DAYS -> TimeUnit.DAYS.toMillis(time.toLong())
        }
    }


    /**
     * Set Time Interval
     * */
    fun setTimeInterval(time: Int, type: OtpTimeType = OtpTimeType.MILLISECOND) {
        timeInterval = when (type) {
            OtpTimeType.MILLISECOND -> time.toLong()
            OtpTimeType.SECOND -> TimeUnit.SECONDS.toMillis(time.toLong())
            OtpTimeType.MINUTE -> TimeUnit.MINUTES.toMillis(time.toLong())
            OtpTimeType.HOURS -> TimeUnit.HOURS.toMillis(time.toLong())
            OtpTimeType.DAYS -> TimeUnit.DAYS.toMillis(time.toLong())
        }
    }


    /**
     * @OTP_TYPES
     * You can set OTP Timer
     * */
    enum class OtpTimeType {
        /** @MILLISECOND [setMaxTime] 30000 for 30 second timer */
        MILLISECOND,

        /** @SECOND [setMaxTime] 30 for 30 second timer */
        SECOND,

        /** @MINUTE [setMaxTime] 1 for 1 minute timer */
        MINUTE,

        /** @HOURS [setMaxTime] 1 for 1 hour timer */
        HOURS,

        /** @DAYS [setMaxTime] 1 for 1 day timer */
        DAYS
    }

}