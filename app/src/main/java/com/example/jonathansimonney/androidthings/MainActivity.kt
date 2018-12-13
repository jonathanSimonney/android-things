package com.example.jonathansimonney.androidthings

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.contrib.driver.button.Button.OnButtonEventListener
import android.R.attr.button
import android.util.Log
import com.google.android.things.contrib.driver.button.Button


/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
class MainActivity : Activity() {
    var buttonA = RainbowHat.openButtonA()
    var buttonB = RainbowHat.openButtonB()
    var button = RainbowHat.openButtonC()
    var servo = RainbowHat.openServo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        useServoMoteur6()
        button.setOnButtonEventListener { button, pressed -> finish() }
    }

    override fun onDestroy() {
        Log.e("finishApp", "destroying app")
        button.close()
        buttonA.close()
        buttonB.close()
        servo.setEnabled(false)
        servo.close()
        super.onDestroy()
    }

    fun useServoMoteur2(){
        servo.angle = 90.0
        servo.setPulseDurationRange(1.0, 2.0)
        servo.setEnabled(true)
    }

    fun useServoMoteur3(){
        servo.angle = 90.0
        servo.setPulseDurationRange(servo.minimumPulseDuration, servo.maximumPulseDuration)
        servo.setEnabled(true)
    }

    fun useServoMoteur4(){
//        servo.angle = servo.minimumAngle
        servo.angle = servo.maximumAngle
        servo.setPulseDurationRange(servo.minimumPulseDuration, servo.maximumPulseDuration)
        servo.setEnabled(true)
    }

    fun useServoMoteur5(){
//        servo.angle = servo.minimumAngle
        servo.angle = servo.maximumAngle
        servo.setPulseDurationRange(servo.minimumPulseDuration, servo.maximumPulseDuration)
        servo.setEnabled(true)
        Thread.sleep(200)
        servo.setEnabled(false)
    }

    fun reverseServoMotor5(){
        servo.angle = servo.minimumAngle
        servo.setPulseDurationRange(servo.minimumPulseDuration, servo.maximumPulseDuration)
        servo.setEnabled(true)
        Thread.sleep(200)
        servo.setEnabled(false)
    }

    fun useServoMoteur6(){
//        servo.angle = servo.minimumAngle
        buttonA.setOnButtonEventListener { button, pressed -> useServoMoteur5() }
        buttonB.setOnButtonEventListener { button, pressed -> reverseServoMotor5() }
    }

}
