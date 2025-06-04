package juego;

import java.awt.Image;
import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;

public class Murcielago {
    private int x;
    private int y;
    private double angulo;
    
    private int tamaño = 50;
    private int velocidad = 2;
    private boolean activo = true;
    private int vida = 3; // Puntos de vida del murciélago
    
    // Sprites para cada dirección
    private Image[] spritesArriba;
    private Image[] spritesAbajo;
    private Image[] spritesIzquierda;
    private Image[] spritesDerecha;
    
    // Animación
    private String direccion = "s"; // s: abajo, w: arriba, a: izquierda, d: derecha
    private int frame = 0;
    private int contadorFrames = 0;
    private int velocidadAnimacion = 8;

    public Murcielago(int x, int y) {
        this.x = x;
        this.y = y;
        this.angulo = 0;
        
        try {
            // Cargar sprites para cada dirección (2 frames por dirección)
            this.spritesArriba = new Image[]{
                Herramientas.cargarImagen("elementos/Murcielago/m_atras1.png"),
                Herramientas.cargarImagen("elementos/Murcielago/m_atras2.png")
            };
            this.spritesAbajo = new Image[]{
                Herramientas.cargarImagen("elementos/Murcielago/m_delante1.png"),
                Herramientas.cargarImagen("elementos/Murcielago/m_delante2.png")
            };
            this.spritesIzquierda = new Image[]{
                Herramientas.cargarImagen("elementos/Murcielago/m_izq1.png"),
                Herramientas.cargarImagen("elementos/Murcielago/m_izq2.png")
            };
            this.spritesDerecha = new Image[]{
                Herramientas.cargarImagen("elementos/Murcielago/m_der1.png"),
                Herramientas.cargarImagen("elementos/Murcielago/m_der2.png")
            };
        } catch (Exception e) {
            System.err.println("Error al cargar sprites de murciélago: " + e.getMessage());
        }
    }
    
    public void mover(Gondolf gondolf) {
    	if (!activo) return;
        double deltaX = gondolf.getX() - this.x;
        double deltaY = gondolf.getY() - this.y;
        this.angulo = Math.atan2(deltaY, deltaX);
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0) {
                direccion = "d";
            } else {
                direccion = "a";
            }
        } else {
            if (deltaY > 0) {
                direccion = "s";
            } else {
                direccion = "w";
            }
        }
        
		this.x += Math.cos(this.angulo) * velocidad;
		this.y += Math.sin(this.angulo) * velocidad;
		
		actualizarAnimacion();
	}
    
    private void actualizarAnimacion() {
        contadorFrames++;
        if (contadorFrames >= velocidadAnimacion) {
            contadorFrames = 0;
            frame = (frame + 1) % 2; // Alternar entre 0 y 1
        }
    }

    public void dibujar(Entorno entorno) {
        if (!activo) return;
        
        Image spriteActual = obtenerSpriteActual();
        entorno.dibujarImagen(spriteActual, x, y, 0);
    }
    
    private Image obtenerSpriteActual() {
        switch(direccion) {
            case "w": return spritesArriba[frame];
            case "a": return spritesIzquierda[frame];
            case "d": return spritesDerecha[frame];
            default: return spritesAbajo[frame]; // "s" por defecto
        }
    }

    public void recibirDaño(int cantidad) {
        vida -= cantidad;
        if (vida <= 0) {
            desactivar();
        }
    }

    public void desactivar() {
        activo = false;
    }

    public boolean isActivo() {
        return activo;
    }

    public Rectangle getRectangulo() {
        return new Rectangle(x - tamaño/2, y - tamaño/2, tamaño, tamaño);
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public double getAngulo() { return angulo; }
    public int getTamaño() { return tamaño; }
    public int getVida() { return vida; }
}