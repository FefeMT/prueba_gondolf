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

    public void moverHacia(int objetivoX, int objetivoY, ArrayList<Obstaculo> obstaculos) {
        if (!activo) return;

        int nuevaX = x;
        int nuevaY = y;

        // Mover en X
        if (objetivoX > x) {
            nuevaX += velocidad;
            direccion = "d"; // Derecha
        } else if (objetivoX < x) {
            nuevaX -= velocidad;
            direccion = "a"; // Izquierda
        }

        // Mover en Y
        if (objetivoY > y) {
            nuevaY += velocidad;
            if (direccion.equals("s")) direccion = "s"; // Ya es 's', no cambiar
            else direccion = "s"; // Abajo
        } else if (objetivoY < y) {
            nuevaY -= velocidad;
            if (direccion.equals("w")) direccion = "w"; // Ya es 'w', no cambiar
            else direccion = "w"; // Arriba
        }

        // Verificar colisión con obstáculos antes de moverse
        Rectangle futuraHitbox = new Rectangle(nuevaX - tamaño / 2, nuevaY - tamaño / 2, tamaño, tamaño);
        boolean colisiona = false;
        for (Obstaculo o : obstaculos) {
            if (futuraHitbox.intersects(o.getHitbox())) {
                colisiona = true;
                break;
            }
        }

        if (!colisiona) {
            x = nuevaX;
            y = nuevaY;
        } else {
            // Si hay colisión al moverse en ambos ejes, intentar mover solo en X o solo en Y
            // Intentar solo en X
            futuraHitbox = new Rectangle(nuevaX - tamaño / 2, y - tamaño / 2, tamaño, tamaño);
            colisiona = false;
            for (Obstaculo o : obstaculos) {
                if (futuraHitbox.intersects(o.getHitbox())) {
                    colisiona = true;
                    break;
                }
            }
            if (!colisiona) {
                x = nuevaX;
            } else {
                // Intentar solo en Y
                futuraHitbox = new Rectangle(x - tamaño / 2, nuevaY - tamaño / 2, tamaño, tamaño);
                colisiona = false;
                for (Obstaculo o : obstaculos) {
                    if (futuraHitbox.intersects(o.getHitbox())) {
                        colisiona = true;
                        break;
                    }
                }
                if (!colisiona) {
                    y = nuevaY;
                }
            }
        }
    }

    public void actualizarAnimacion() {
        contadorFrames++;
        if (contadorFrames >= velocidadAnimacion) {
            contadorFrames = 0;
            frame = (frame + 1) % 2; // Alternar entre 0 y 1
        }
    }

    public void dibujar(Entorno entorno, int camaraX, int camaraY) {
        if (!activo) return;

        Image spriteActual = obtenerSpriteActual();
        if (spriteActual != null) { // Solo dibujar si el sprite fue cargado
            entorno.dibujarImagen(spriteActual, x - camaraX, y - camaraY, 0, 0.2); // Escala para que se vea su tamaño real
        } else {
            // Dibujar un rectángulo si el sprite no carga, para depuración
            entorno.dibujarRectangulo(x - camaraX, y - camaraY, tamaño, tamaño, 0, Color.MAGENTA); // Color para el jefe
        }

        // Dibujar barra de vida del Jefe (Opcional)
        if (vida > 0) {
            double anchoBarra = tamaño;
            double altoBarra = 5;
            double vidaPorcentaje = (double) vida / 200.0; 
            entorno.dibujarRectangulo(x - camaraX, y - camaraY - tamaño/2 - 10, anchoBarra, altoBarra, 0, Color.GRAY); // Fondo
            entorno.dibujarRectangulo(x - camaraX - (anchoBarra - anchoBarra * vidaPorcentaje) / 2, y - camaraY - tamaño/2 - 10, anchoBarra * vidaPorcentaje, altoBarra, 0, Color.RED); 
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

	public void mover(int x2, int y2, ArrayList<Obstaculo> obstaculos) {
		// TODO Auto-generated method stub
		
	}
}