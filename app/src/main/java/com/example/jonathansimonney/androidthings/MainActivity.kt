package com.example.jonathansimonney.androidthings

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.contrib.driver.button.Button.OnButtonEventListener
import android.R.attr.button
import android.graphics.Color
import android.util.Log
import android.view.Display
import com.google.android.things.contrib.driver.button.Button
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
    var text = RainbowHat.openDisplay()
    var buzzer = RainbowHat.openPiezo()
    var ledstrip = RainbowHat.openLedStrip()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        ledRubanDisplay2()
        button.setOnButtonEventListener { button, pressed -> finish() }
    }

    override fun onDestroy() {
        Log.e("finishApp", "destroying app")
        button.close()
        buttonA.close()
        buttonB.close()
        servo.setEnabled(false)
        servo.close()
        text.setEnabled(false)
        text.close()
        buzzer.close()
        hideLedDisplay()
        ledstrip.close()
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

    fun afficherDuTexte1(){
        text.display("BOOM")
        text.setEnabled(true)
    }

    suspend fun playSingleSound(soundFrequency: Double){
        buzzer.play(soundFrequency)
    }

    fun playSound2(){
        val arraySound = intArrayOf(261, 294, 329, 349, 392, 440, 493, 523)

        GlobalScope.launch {
            for (frequency in arraySound){
                playSingleSound(frequency.toDouble())

                delay(300)
            }
            buzzer.stop()
        }
    }

    fun hideLedDisplay(){
        ledstrip.brightness = 0
        val rainbow = IntArray(RainbowHat.LEDSTRIP_LENGTH)
        for (i in 0 until rainbow.size) {
            rainbow[i] = Color.HSVToColor(255, floatArrayOf(i * 360f / rainbow.size, 1.0f, 1.0f))
        }
        ledstrip.write(rainbow)
    }

    fun ledRubanDisplay1() {
        ledstrip.brightness = 31
        val rainbow = IntArray(RainbowHat.LEDSTRIP_LENGTH)
        for (i in 0 until rainbow.size) {
            rainbow[i] = Color.HSVToColor(255, floatArrayOf(i * 360f / rainbow.size, 1.0f, 1.0f))
        }
        ledstrip.write(rainbow)
    }

    suspend fun showRainbow(rainbow: IntArray){
        ledstrip.write(rainbow)
    }

    fun ledRubanDisplay2(){
        //val arrayString = "azer,bouh,caca";
        //val separated = arrayString.split(",");
        ledstrip.brightness = 31
        val rainbow = IntArray(RainbowHat.LEDSTRIP_LENGTH)
        var decalage = 0

        GlobalScope.launch {
            while (true){
                decalage++
                for (i in 0 until rainbow.size) {
                    val index = (i + decalage) % rainbow.size
                    rainbow[index] = Color.HSVToColor(255, floatArrayOf(i * 360f / rainbow.size, 1.0f, 1.0f))
                }
                showRainbow(rainbow)
                delay(1000)
                decalage %= rainbow.size
            }
        }
    }


    suspend fun showText(textToDisplay: String){
        text.display(textToDisplay)
        text.setEnabled(true)
    }

    fun displayText3(){
        val arrayString = "azer,bouh,coco"
        val separated = arrayString.split(",")

        GlobalScope.launch {
            separated.forEach{
                showText(it)
                delay(2000)
            }
        }
    }
}
