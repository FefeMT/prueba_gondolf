package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Pociones {
	private int x;
	private int y;
	private int tamaño = 20;
	private int cura = 20;
	private Image imagen;
	
	
	public Pociones(int spawnX, int spawnY) {
		this.x = spawnX;
		this.y = spawnY;
		this.imagen = Herramientas.cargarImagen("elementos/Mapa/pocion.png");
	}
	
	public void dibujarse(Entorno entorno) {
    		entorno.dibujarImagen(imagen, x, y, 0, 0.06);
	}
	
//	GETTERS
	public int getX() {return x;}
	public int getY() {return y;}
	public int getCura() {return cura;}
	public Rectangle getHitbox() { return new Rectangle(x - tamaño/2, y - tamaño/2, tamaño, tamaño); }
}
