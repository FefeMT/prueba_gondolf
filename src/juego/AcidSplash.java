package juego;

import java.awt.Image;
import java.awt.Color;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class AcidSplash {
	
	private int x;
    private int y;
    private int tamaño;
    private boolean casteado;
    private int daño;
    private int consumoMana;
    private int duracionActual;
    private int duracionMax;
    private int frame;
    private int contadorFrames;
    private Image sprite;
    
    public AcidSplash(int x, int y) {
        this.x = x;
        this.y = y;
        this.tamaño = 50;
        this.daño = 10;
        this.consumoMana = 10;
        this.casteado = false;
        this.duracionActual = 0;
        this.duracionMax = 20;
        this.frame = 0;
        this.contadorFrames = 0;
       	this.sprite = Herramientas.cargarImagen("elementos/Hechizos/AcidSplash/Acid Splash.png");
    }
    
    public void dibujarse(Entorno entorno) {
    	if (casteado) {
    		entorno.dibujarImagen(sprite, x, y, 0, 2);
    	}
    }
    
    public void actualizar() {
        if (casteado) {
            duracionActual--;
            if (duracionActual <= 0) {
                descastear();
            }
        }
    }
    
    public void castear(int x, int y) {
    	this.x = x;
    	this.y = y;
    	this.casteado = true;
		this.duracionActual = duracionMax;
    }
    
    public void descastear() {
    	this.casteado = false;
    	this.duracionActual = 0;
    }
    
    
 // GETTERS
 	public int getDaño() { return daño; }
 	public int getCoste() { return consumoMana; }
 	public boolean getEstado() { return casteado; }
    public Rectangle getHitbox() { return new Rectangle(x - tamaño/2, y - tamaño/2, tamaño, tamaño); }
}
