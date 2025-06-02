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
    private int duracion;
    
    private Image boton;
    private Image[] sprites;
    
    public FireBall(int x, int y) {
        this.x = x;
        this.y = y;
        this.tamaño = 200;
        this.daño = 20;
        this.casteado = false;
        this.duracion = 400;
        this.boton = Herramientas.cargarImagen("elementos/Menu/fireball boton.png");
        
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
    
    public void dibujarse(Entorno entorno, int x, int y, int frame) {
    	if (duracion > 0) {
    		entorno.dibujarImagen(sprites[frame], x, y, 0, 5);
    		duracion--;
    	}
		if (duracion <= 0) {this.descastear();}
	}
    
    public void castear() {
    	this.casteado = true;
		duracion = 400;
    }
    
    public void descastear() {
    	this.casteado = false;
    	duracion = 0;
    }
    
    
 // GETTERS
    public int getX() {	return x; }
    public int getY() {	return y; }
 	public int getTamaño() {	return tamaño; }
 	public int getDaño() { return daño; }
 	public boolean getEstado() { return casteado; }
 	public Image getImagenBoton() { return boton; }
    public Rectangle getHitbox() { return new Rectangle(x - tamaño/2, y - tamaño/2, tamaño, tamaño); }
    public boolean animacionActiva() { return duracion > 0; }
    public boolean termino() { return duracion <= 0; }
}
