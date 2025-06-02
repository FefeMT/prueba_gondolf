package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Zap {

	private int x;
    private int y;
    private int tamaño;
    private boolean casteado;
    private int daño;
    private int velocidad;
    private int duracion;
    
    private double direccionX;
    private double direccionY;
    
    private Image boton;
    private Image[] sprites;
    
    
    public Zap(int x, int y, int destinoX, int destinoY) {
        this.x = x;
        this.y = y;
        this.tamaño = 20;
        this.daño = 2;
        this.casteado = false;
        this.duracion = 20;
        
        double dx = destinoX - x;
        double dy = destinoY - y;
        double distancia = Math.sqrt(dx*dx + dy*dy);
        
        if (distancia > 0) {
            this.direccionX = dx / distancia;
            this.direccionY = dy / distancia;
        } else {
            this.direccionX = 0;
            this.direccionY = 0;
        }
        
        this.boton = Herramientas.cargarImagen("elementos/Menu/zap boton.png");
        
        try {
        	this.sprites = new Image[]{
        		Herramientas.cargarImagen("elementos/Hechizos/Zap/proyectil1.png"),
        		Herramientas.cargarImagen("elementos/Hechizos/Zap/proyectil2.png")
        	};
    	} catch (Exception e) {
            System.err.println("Error al cargar sprites de Zap: \n" + e.getMessage());
        }
    }
    
    public void dibujarse(Entorno e, int frame) {
    	if (!casteado) return;
		e.dibujarImagen(sprites[frame], x, y, 0, 5);
		duracion--;
		if (duracion <= 0) {this.descastear();}
	}
    
    public void mover() {
        if (!casteado) return;
        x += direccionX * velocidad;
        y += direccionY * velocidad;
    }
    
    public void castear() {
    	this.casteado = true;
		duracion = 40;
    }
    
    public void descastear() {
    	this.casteado = false;
    	duracion = 0;
    }
    
    
 // GETTERS
 	public int getTamaño() {	return tamaño; }
 	public int getDaño() { return daño; }
 	public boolean getEstado() { return casteado; }
 	public Image getImagenBoton() { return boton; }
    public Rectangle getHitbox() { return new Rectangle(x - tamaño/2, y - tamaño/2, tamaño, tamaño); }
    public boolean animacionActiva() { return duracion > 0; }
    public boolean termino() { return duracion <= 0; }
	
}
