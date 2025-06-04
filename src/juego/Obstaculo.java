package juego;

import java.awt.Image;
import java.awt.Rectangle;
import entorno.Entorno;
import entorno.Herramientas;

public class Obstaculo {
    private int x, y;
    private int ancho = 32;
    private int alto = 32;
    private Rectangle hitbox;
    private Image imagen;

    public Obstaculo(int x, int y, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.imagen = Herramientas.cargarImagen(rutaImagen);

        if (imagen != null) {
            this.hitbox = new Rectangle(x, y, ancho, alto);
        } else {
            System.err.println("⚠️ No se pudo cargar la imagen del obstáculo: " + rutaImagen);
        }
    }

    public void dibujar(Entorno entorno) {
        if (imagen != null) {
            entorno.dibujarImagen(imagen, x, y, 0);
        } else {
            entorno.dibujarRectangulo(x, y, ancho, alto, 0, java.awt.Color.RED);
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}

