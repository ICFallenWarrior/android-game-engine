package com.innoveworkshop.gametest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import java.util.Random


class MainActivity : AppCompatActivity() {
    protected var gameSurface: GameSurface? = null
    protected var circleButton: Button? = null
    protected var scoreTextView: TextView? = null


    protected var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameSurface = findViewById<View>(R.id.gameSurface) as GameSurface
        game = Game()
        gameSurface!!.setRootGameObject(game)
    }

    inner class Game : GameObject() {
        var circle: Circle? = null
        var velocityX = 0f
        var velocityY = 0f
        var randomX = 0
        var score = 0

        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)
            circle = Circle(
                (surface!!.width / 2).toFloat(),
                (surface.height/2).toFloat(),
                100f,
                Color.WHITE
            )
            surface.addGameObject(circle!!)
            setupControls()
        }

        private fun setupControls() {
            circleButton = findViewById<View>(R.id.circlebutton) as Button
            scoreTextView = findViewById<View>(R.id.textView) as TextView

            circleButton!!.setOnClickListener {
                randomX = (100..gameSurface!!.width - 100).random()
                circle!!.position.x = randomX.toFloat()
                circle!!.position.y -= 500
                velocityY = 40f
                score++
                scoreTextView!!.text = "Score: $score"

                Log.d("ARE YOU BEING CLICKED", randomX.toString())
            }
        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()
            circleButton!!.y = circle!!.position.y - 60
            circleButton!!.x = circle!!.position.x -150
            circle!!.setPosition(circle!!.position.x + velocityX, circle!!.position.y + velocityY)
            if(circle!!.isFloored) {
                circle!!.setPosition(gameSurface!!.width /2.toFloat(), gameSurface!!.height/2.toFloat())
                velocityY = 0f
                velocityX = 0f
                score = 0
            }
            if(circle!!.hitCeiling()){
                velocityY = -velocityY

            }
            if(circle!!.hitRightWall()){
                velocityX = -velocityX
            }

            if(circle!!.hitLeftWall()){
                velocityX = -velocityX
            }
            //randomX = (100..gameSurface!!.width - 100).random()
        }
    }
}