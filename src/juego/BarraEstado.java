package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class BarraEstado {
    private Image spriteCorazonLleno;
    private Image spriteCorazonVacio;
    private Image spriteManaLleno;
    private Image spriteManaVacio;
    private int x, y;
    private int tamanoIcono = 15; // Tamaño reducido de iconos (16px)
    private int espaciado = 15;    // Espacio reducido entre iconos

    public BarraEstado() {
        try {
            // Cargar sprites para corazones
            this.spriteCorazonLleno = Herramientas.cargarImagen("elementos/Menu/corazon_lleno.png");
            this.spriteCorazonVacio = Herramientas.cargarImagen("elementos/Menu/corazon_vacio.png");
            
            // Cargar sprites para mana 
            this.spriteManaLleno = Herramientas.cargarImagen("elementos/Menu/mana_lleno.png");
            this.spriteManaVacio = Herramientas.cargarImagen("elementos/Menu/mana_vacio.png");
            
            this.x = 600;
            this.y = 30;
        } catch (Exception e) {
            System.err.println("Error al cargar sprites de barras: " + e.getMessage());
        }
    }

    public void dibujar(Entorno entorno, Gondolf jugador) {
        // Dibujar barra de vida (corazones)
        dibujarCorazones(entorno, jugador.getVidaActual(), jugador.getVidaMaxima());
        
        // Dibujar barra de mana (iconos)
        dibujarMana(entorno, jugador.getManaActual(), jugador.getManaMaximo());
    }

    private void dibujarCorazones(Entorno entorno, int vidaActual, int vidaMaxima) {
        int corazonesTotales = 10;
        int corazonesLlenos = vidaActual / (vidaMaxima / corazonesTotales);
        
        for (int i = 0; i < corazonesTotales; i++) {
            Image sprite = (i < corazonesLlenos) ? spriteCorazonLleno : spriteCorazonVacio;
            entorno.dibujarImagen(sprite, x + i * (tamanoIcono + espaciado), y, 0, 0.3); // Escala al 50%
        }
    }
    
    private void dibujarMana(Entorno entorno, int manaActual, int manaMaximo) {
        int iconosTotales = 10;
        int iconosLlenos = manaActual / (manaMaximo / iconosTotales); // Cambiado para mostrar unidades exactas
        
        for (int i = 0; i < iconosTotales; i++) {
            Image sprite = (i < iconosLlenos) ? spriteManaLleno : spriteManaVacio;
            entorno.dibujarImagen(sprite, x + i * (tamanoIcono + espaciado-10), y + tamanoIcono + 10, 0, 0.3);
        }
    }
}