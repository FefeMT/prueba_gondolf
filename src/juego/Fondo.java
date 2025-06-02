package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Fondo {
	private double x;
	private double y;
	private Image imgFondo;

	public Fondo(double x, double y) {
		this.x = x;
		this.y = y;
		imgFondo = Herramientas.cargarImagen("elementos/Mapa/fondo.jpg");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imgFondo, this.x, this.y, 0, 1.2);
	}
}
