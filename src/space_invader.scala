import Global.display
import hevs.graphics.FunGraphics


import java.awt._
import java.awt.event._
import javax.swing.Timer
import scala.Console._
import scala.collection.mutable.ArrayBuffer

object Global {
  var display: FunGraphics = new FunGraphics(1920, 1080)
}

class Spaceship(var x: Int, var y: Int, var size: Int, var c: Color){

  private var up = false
  private var down = false
  private var left = false
  private var right = false
  var shot = false
  var speed = 3
  private var lastDx = 1
  private var lastDy = 0



  def computeDirection(): (Int, Int) = {
    val dx =
      (if (right) 1 else 0) +
        (if (left) -1 else 0)

    val dy =
      (if (down) 1 else 0) +
        (if (up) -1 else 0)

    if (dx != 0 || dy != 0) {
      lastDx = dx
      lastDy = dy
    }

    (dx, dy)
  }

  def draw(): Unit = {
    display.setColor(c)
    display.drawFillRect(x, y, size, size)
  }


  display.setKeyManager(new KeyAdapter() {
    override def keyPressed(e: KeyEvent): Unit = {
      e.getKeyCode match {
        case KeyEvent.VK_LEFT | KeyEvent.VK_A => left = true
        case KeyEvent.VK_RIGHT | KeyEvent.VK_D => right = true
        case KeyEvent.VK_UP | KeyEvent.VK_W => up = true
        case KeyEvent.VK_DOWN | KeyEvent.VK_S => down = true
        case KeyEvent.VK_E => shot = true
        case KeyEvent.VK_ESCAPE => sys.exit()
        case _ =>
      }
    }

    override def keyReleased(e: KeyEvent): Unit = {
      e.getKeyCode match {
        case KeyEvent.VK_LEFT | KeyEvent.VK_A => left = false
        case KeyEvent.VK_RIGHT | KeyEvent.VK_D => right = false
        case KeyEvent.VK_UP | KeyEvent.VK_W => up = false
        case KeyEvent.VK_DOWN | KeyEvent.VK_S => down = false
        case KeyEvent.VK_E => shot = false
        case _ =>
      }
    }
  })
}

class Invader(var x: Int, var y:Int, var size: Int, var c: Color){

//  private val d= 100
////  private val d1 = 200
//  private var tempDist = 200
//  private var left = true
//  private var right = false
//  private var lDx = 1
//  //private var lDy = 0
//  var speed = 2


  /*def movement():Int={
    for(_ <- 0 to d){
      if(tempDist >= 0 && left)
        tempDist -= 1

      if(tempDist == 0){
        left = false
        right = true
      }
      else if(tempDist <= 200 && right)
        tempDist += 1
      if(tempDist == 200){
        left = true
        right = false
      }
    }
    val dx = (if (right) 1 else 0) +
      (if (left) -1 else 0)

    if(dx != 0) {
      lDx = dx
    }

    dx
  }*/


  def draw(): Unit ={
    display.setColor(c)
    display.drawFillRect(x, y, size, size)
  }
}


class Projectile(var x: Int, var y: Int, var size: Int, var speed: Int, var dx: Int, var dy: Int) {
  def update(): Unit = {
    x += dx * speed
    y += dy * speed
  }

  def draw(): Unit = {
    display.setColor(Color.GREEN)
    display.drawFillRect(x, y, size, size)
  }

  def isOutOfBounds(width: Int, height: Int): Boolean = {
    x < 0 || x > width || y < 0 || y > height
  }
}


class Game1{


  private val grid : Array[Array[Int]] = Array.fill(1980, 1080)(0)

  private var numLives = 3

  private val score = 0

  //var high-score = 0

  private var start = false
  private var end = false
  private var checkBtn = true

  private var invaderDir = 1
  private val invaderSpeed = 3





  private val s: Spaceship = new Spaceship(
    960,
    540,
    30,
    Color.CYAN
  )

  private val i: ArrayBuffer[Invader] = ArrayBuffer(
    new Invader(200, 200, 40, Color.RED),
    new Invader(500, 200, 40, Color.RED),
    new Invader(800, 200, 40, Color.RED),

  )

//  private var prevX = s.x
//  private var prevY = s.y

  private var ship_proj: ArrayBuffer[Projectile] = ArrayBuffer()

  private val inv_proj: ArrayBuffer[Projectile] = ArrayBuffer()



  private var shotCooldown = 0
  private val SHOT_CD_FRAMES = 15
  private var invaderShot = 0
  private val INVADER_SHOT_CD = 60

  private var firstDraw = true


  def startbtn():Unit ={
    display.setColor(Color.white)
    val customFont = new java.awt.Font("Lithograph", java.awt.Font.BOLD, 36)
    display.drawString(800, 800, s"Start", customFont, new Color(255,255, 255))

  }

  def gameStart():Unit ={

  }

  def gameEnd():Unit ={

  }



  private val timer = new Timer(16, (_: ActionEvent)=>{
    if(firstDraw){
      for(row <- grid.indices; _ <- grid(row).indices){
        Global.display.setColor(Color.BLACK)
      }
      firstDraw = false
    }

    val (dx, dy) = s.computeDirection()
    s.x = Math.max(0, Math.min(1920 - s.size, s.x + dx * s.speed))
    s.y = Math.max(0, Math.min(1080 - s.size, s.y + dy * s.speed))



    if(i.nonEmpty){

      val leftEdge = i.map(_.x).min
      val rightEdge = i.map(iv => iv.x + iv.size).max

      if(rightEdge >= 1920) invaderDir = -1
      if(leftEdge <= 0) invaderDir = 1

    }

    i.foreach(iv => {
      iv.x += invaderDir * invaderSpeed
    })


    // projectile management of the ship
    if(shotCooldown > 0){
      shotCooldown -= 1
    }

    if(s.shot && shotCooldown <= 0){
      val projX = s.x + s.size / 2
      val projY = s.y + s.size / 2

      val dx = 0
      val dy = -1

      ship_proj += new Projectile(projX, projY, 8, 8, dx, dy)
      shotCooldown = SHOT_CD_FRAMES
    }

    if(!s.shot){
      shotCooldown = 0
    }

    //projectile management of the invaders

    if(invaderShot > 0){
      invaderShot -= 1
    }


    if(invaderShot <= 0 && i.nonEmpty){
      val shooter = i(scala.util.Random.nextInt(i.size))

      val pjX = shooter.x + shooter.size /2
      val pjY = shooter.y + shooter.size

      inv_proj += new Projectile(pjX, pjY, 9, 6, 0, 1)

      invaderShot = INVADER_SHOT_CD
    }

    // collision detection spaceship -> invader


    val invadersToRemove = ArrayBuffer[Int]()
    val projectilesToRemove = ArrayBuffer[Int]()

    for ((proj, pIdx) <- ship_proj.zipWithIndex) {
      for ((invader, iIdx) <- i.zipWithIndex) {
        if (
          proj.x < invader.x + invader.size &&
            proj.x + proj.size > invader.x &&
            proj.y < invader.y + invader.size &&
            proj.y + proj.size > invader.y
        ) {
          invadersToRemove += iIdx
          projectilesToRemove += pIdx
        }
      }
    }

    invadersToRemove.distinct.sorted.reverse.foreach(i.remove)
    projectilesToRemove.distinct.sorted.reverse.foreach(ship_proj.remove)

    inv_proj.foreach(proj =>{
      val px = s.x
      val py = s.y
      if(proj.x < px + s.size &&
      proj.x +proj.size> px &&
      proj.y < py + s.size &&
      proj.y + proj.size > py){
        numLives -=1
      }

    })




    ship_proj.foreach(_.update())
    ship_proj.filterInPlace(!_.isOutOfBounds(1920, 1080))

    inv_proj.foreach(_.update())
    inv_proj.filterInPlace(!_.isOutOfBounds(1920, 1080))

    display.setColor(Color.BLACK)
    display.drawFillRect(0, 0, 1920, 1080)

    s.draw()
    ship_proj.foreach(_.draw())

    i.foreach(_.draw())
    inv_proj.foreach(_.draw())

    display.setColor(Color.WHITE)
    display.drawString(20, 30, s"Score : $score", WHITE)

  })
  timer.start()

}

object Spacegame extends App{
  new Game1()
}