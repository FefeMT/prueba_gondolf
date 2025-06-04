package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList; 
import entorno.Entorno;
import entorno.Herramientas;

public class Jefe {
    private int x;
    private int y;
    private int tamaño = 150;
    private double angulo = 150;
    private int velocidad = 1; 
    private boolean activo = true;
    private int vida = 200; 
    private int dañoAtaque = 20; 

    // Sprites para cada dirección 
    private Image[] spritesArriba;
    private Image[] spritesAbajo;
    private Image[] spritesIzquierda;
    private Image[] spritesDerecha;

    // Animación
    private String direccion = "s"; 
    private int frame = 0;
    private int contadorFrames = 0;
    private int velocidadAnimacion = 8; 

    public Jefe(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            this.spritesArriba = new Image[]{
                Herramientas.cargarImagen("elementos/Jefe/jefe_detras0.png"), 
                Herramientas.cargarImagen("elementos/Jefe/jefe_detras1.png")
            };
            this.spritesAbajo = new Image[]{
                Herramientas.cargarImagen("elementos/Jefe/jefe_frente0.png"),
                Herramientas.cargarImagen("elementos/Jefe/jefe_frente1.png")
            };
            this.spritesIzquierda = new Image[]{
                Herramientas.cargarImagen("elementos/Jefe/jefe_izq0.png"),
                Herramientas.cargarImagen("elementos/Jefe/jefe_izq1.png")
            };
            this.spritesDerecha = new Image[]{
                Herramientas.cargarImagen("elementos/Jefe/jefe_der0.png"),
                Herramientas.cargarImagen("elementos/Jefe/jefe_der1.png")
            };
        } catch (Exception e) {
            System.err.println("Error al cargar sprites del Jefe: " + e.getMessage());

        }
    }

    public void moverHacia(Gondolf gondolf) {
        if (!activo) return;
        double deltaX = gondolf.getX() - x;
        double deltaY = gondolf.getY() - y;
        
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

    public void actualizarAnimacion() {
        contadorFrames++;
        if (contadorFrames >= velocidadAnimacion) {
            contadorFrames = 0;
            frame = (frame + 1) % 2; // Alternar entre 0 y 1
        }
    }

    public void dibujar(Entorno entorno) {
        if (!activo) return;

        Image spriteActual = obtenerSpriteActual();
        if (spriteActual != null) { // Solo dibujar si el sprite fue cargado
            entorno.dibujarImagen(spriteActual, x, y, 0, 2); // Escala para que se vea su tamaño real
        } else {
            // Dibujar un rectángulo si el sprite no carga, para depuración
            entorno.dibujarRectangulo(x, y, tamaño, tamaño, 0, Color.MAGENTA); // Color para el jefe
        }

        // Dibujar barra de vida del Jefe (Opcional)
        if (vida > 0) {
            double anchoBarra = tamaño;
            double altoBarra = 5;
            double vidaPorcentaje = (double) vida / 200.0; 
            entorno.dibujarRectangulo(x, y - tamaño/2 - 10, anchoBarra, altoBarra, 0, Color.GRAY); // Fondo
            entorno.dibujarRectangulo(x - (anchoBarra - anchoBarra * vidaPorcentaje) / 2, y - tamaño/2 - 10, anchoBarra * vidaPorcentaje, altoBarra, 0, Color.RED); 
        }
    }

    private Image obtenerSpriteActual() {
        switch(direccion) {
            case "w": return spritesArriba != null && spritesArriba.length > 0 ? spritesArriba[frame] : null;
            case "a": return spritesIzquierda != null && spritesIzquierda.length > 0 ? spritesIzquierda[frame] : null;
            case "d": return spritesDerecha != null && spritesDerecha.length > 0 ? spritesDerecha[frame] : null;
            default: return spritesAbajo != null && spritesAbajo.length > 0 ? spritesAbajo[frame] : null; // "s" por defecto
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

    public boolean estaVivo() {
        return activo;
    }

    public Rectangle getRectangulo() {
        return new Rectangle(x - tamaño/2, y - tamaño/2, tamaño, tamaño);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getTamaño() { return tamaño; }
    public int getVida() { return vida; }
    public int getDañoAtaque() { return dañoAtaque; }

	public void mover(Gondolf gondolf) {
		// TODO Auto-generated method stub
		
	}
}