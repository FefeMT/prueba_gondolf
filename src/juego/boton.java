package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;


public class boton {
	
	private int x;
	private int y;
	private int alto;
	private int ancho;
	private double escala;
	private Image imagen;
	private boolean estadoBoton;
	
	public boton(int x, int y, int ancho, int alto, double escala, Image imagen) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.escala = escala;
		this.estadoBoton = false;
		this.imagen = imagen;
	}
	
	
	public boolean estaAdentro(int mouseX, int mouseY) {
		if (mouseX >= x - ancho && mouseX <= x && mouseY >= y - alto && mouseY <= y) {
			return true;
		}
		return false;
	}
	
	public void apretarBoton() {
		this.estadoBoton = !this.estadoBoton; 
	}
	
	public void dibujar(Entorno entorno) {
        if (imagen != null) {
            entorno.dibujarImagen(imagen, x - ancho/2, y - alto/2, 0, escala);
        }
    }
	
	// GETTERS
	public int getX() { return x; }
	public int getY() {	return y; }
	public int getAncho() {	return ancho; }
	public int getAlto() { return alto; }
	public boolean getEstado() { return estadoBoton; }
	

}
