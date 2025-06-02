package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Menu {
	
	private int x;
	private int y;
	private int alto;
	private int ancho;
	private Image imagenFondo;
	
	private Image imagenMenuHechizos;
	private FireBall FireBall;
	private boton BotonFireBall;
	private Zap Zap;
	private boton BotonZap;
	
	
	public Menu() {
		this.x = 740;
		this.y = 300;
		this.ancho = 100;
		this.alto = 100;
		this.imagenFondo = Herramientas.cargarImagen("elementos/Menu/FondoMenu.png");
		this.imagenMenuHechizos = Herramientas.cargarImagen("elementos/Menu/New Piskel.png");
		this.FireBall = new FireBall(x,y);
		this.BotonFireBall = new boton(x,450,120,40,FireBall.getImagenBoton());
		this.Zap = new Zap(x,y,0,0);
		this.BotonZap = new boton(x,350,120,40,Zap.getImagenBoton());
		
	}
	
	public void dibujarMenu(Entorno e) {
		e.dibujarImagen(imagenFondo, x-ancho/2, y, 0, 1.08);
        e.dibujarImagen(imagenMenuHechizos, x-ancho/2, 385, 0, 0.7);
		dibujarBotones(e);
	}
	
	private void dibujarBotones(Entorno e) {
		BotonFireBall.dibujar(e);
		BotonZap.dibujar(e);
		if (BotonFireBall.getEstado()) {
			e.dibujarCirculo(BotonFireBall.getX() - BotonFireBall.getAncho()/2, BotonFireBall.getY() - BotonFireBall.getAlto(), 7, new Color(0, 255, 0, 255));
			e.dibujarCirculo(BotonFireBall.getX() - BotonFireBall.getAncho()/2, BotonFireBall.getY() + BotonFireBall.getAlto()/4, 7, new Color(0, 255, 0, 255));
		}
		if (BotonZap.getEstado()) {
			e.dibujarCirculo(BotonZap.getX() - BotonZap.getAncho()/2, BotonZap.getY() - BotonZap.getAlto(), 7, new Color(0, 255, 0, 255));
			e.dibujarCirculo(BotonZap.getX() - BotonZap.getAncho()/2, BotonZap.getY() + BotonZap.getAlto()/4, 7, new Color(0, 255, 0, 255));

		}
	}
	
	public boton getBotonZap(){ return this.BotonZap; }
	public boton getBotonFireBall(){ return this.BotonFireBall; }
}
