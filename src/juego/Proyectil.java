package juego;

import java.awt.Image;
import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;

public class Proyectil {
    private int x;
    private int y;
    private int tamaño = 20;
    private int velocidad = 8;
    private boolean activo = true;
    private int daño = 2;
    
    private Image[] sprites;
    private int frame = 0;
    private int contadorFrames = 0;
    private int velocidadAnimacion = 5;
    
    private double direccionX;
    private double direccionY;
    
    private void actualizarAnimacion() {
        contadorFrames++;
        if (contadorFrames >= velocidadAnimacion) {
            frame = (frame + 1) % 2; // Alterna entre 0 y 1
            contadorFrames = 0;
        }
    }

    public void dibujar(Entorno entorno, int frame) {
        if (!activo) return;
        Image sprite = sprites[frame];
        entorno.dibujarImagen(sprite, x, y, 0, 0.5); // Escala al 50%
        actualizarAnimacion();
    }

    public Proyectil(int xInicial, int yInicial, int destinoX, int destinoY) {
        this.x = xInicial;
        this.y = yInicial;
        
        // Calcular dirección normalizada hacia el destino (mouse)
        double dx = destinoX - xInicial;
        double dy = destinoY - yInicial;
        double distancia = Math.sqrt(dx*dx + dy*dy);
        
        if (distancia > 0) {
            this.direccionX = dx / distancia;
            this.direccionY = dy / distancia;
        } else {
            this.direccionX = 0;
            this.direccionY = 0;
        }
        
        try {
            this.sprites = new Image[]{
                Herramientas.cargarImagen("elementos/Hechizos/Zap/proyectil1.png"),
                Herramientas.cargarImagen("elementos/Hechizos/Zap/proyectil2.png")
            };
        } catch (Exception e) {
            System.err.println("Error al cargar sprites de proyectil: " + e.getMessage());
        }
    }

    public void mover() {
        if (!activo) return;
        
        x += direccionX * velocidad;
        y += direccionY * velocidad;
        
        // Actualizar animación
        contadorFrames++;
        if (contadorFrames >= velocidadAnimacion) {
            contadorFrames = 0;
            frame = (frame + 1) % sprites.length;
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
    
    public int getDaño() {
        return daño;
    }
}