package juego;

import java.awt.Image;
import java.awt.Color;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class FireBall {
	
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
    private Image[] sprites;
    
    public FireBall(int x, int y) {
        this.x = x;
        this.y = y;
        this.tamaño = 200;
        this.daño = 20;
        this.consumoMana = 20;
        this.casteado = false;
        this.duracionActual = 0;
        this.duracionMax = 20;
        this.frame = 0;
        this.contadorFrames = 0;
        
        try {
        	this.sprites = new Image[]{
        		Herramientas.cargarImagen("elementos/Hechizos/FireBall/sprite_0.png"),
        		Herramientas.cargarImagen("elementos/Hechizos/FireBall/sprite_1.png"),
        		Herramientas.cargarImagen("elementos/Hechizos/FireBall/sprite_2.png"),
        		Herramientas.cargarImagen("elementos/Hechizos/FireBall/sprite_3.png")
        	};
    	} catch (Exception e) {
            System.err.println("Error al cargar sprites de FireBall: \n" + e.getMessage());
        }
    }
    
    public void dibujarse(Entorno entorno) {
    	if (casteado && sprites != null && sprites.length > 0) {
    		entorno.dibujarImagen(sprites[frame], x, y, 0, 4);
    	}
    }
    
    public void actualizar() {
        if (casteado) {
            duracionActual--;
            contadorFrames++;
            if (contadorFrames % 5 == 0) {
                frame = (frame + 1) % sprites.length;
            }

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
        this.frame = 0;
        this.contadorFrames = 0;
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
