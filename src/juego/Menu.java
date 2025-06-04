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
	private Image Zap;
	private boton BotonZap;
	private Image AcidSplash;
	private boton BotonAcidSplash;
	private Image FireBall;
	private boton BotonFireBall;
	
	
	public Menu() {
		this.x = 740;
		this.y = 300;
		this.ancho = 100;
		this.alto = 100;
		this.imagenFondo = Herramientas.cargarImagen("elementos/Menu/FondoMenu.png");
		this.imagenMenuHechizos = Herramientas.cargarImagen("elementos/Menu/New Piskel.png");
		this.Zap = Herramientas.cargarImagen("elementos/Menu/zap boton.png");
		this.BotonZap = new boton(x+7,310,120,40,2,Zap);
		this.AcidSplash = Herramientas.cargarImagen("elementos/Menu/botonAcidSplash.png");
		this.BotonAcidSplash = new boton(x+7,390,120,40,0.28,AcidSplash);
		this.FireBall = Herramientas.cargarImagen("elementos/Menu/fireball boton.png");
		this.BotonFireBall = new boton(x+7,480,120,40,2,FireBall);
		
	}
	
	public void dibujarMenu(Entorno e) {
		e.dibujarImagen(imagenFondo, x-ancho/2, y, 0, 1.08);
        e.dibujarImagen(imagenMenuHechizos, x-ancho/2, 385, 0, 0.7);
		dibujarBotones(e);
	}
	
	private void dibujarBotones(Entorno e) {
		BotonZap.dibujar(e);
		BotonAcidSplash.dibujar(e);
		BotonFireBall.dibujar(e);
		if (BotonFireBall.getEstado()) {
			e.dibujarCirculo(BotonFireBall.getX() - BotonFireBall.getAncho()/2, BotonFireBall.getY() - BotonFireBall.getAlto(), 7, new Color(0, 255, 0, 255));
			e.dibujarCirculo(BotonFireBall.getX() - BotonFireBall.getAncho()/2, BotonFireBall.getY() + BotonFireBall.getAlto()/4, 7, new Color(0, 255, 0, 255));
		}
		if (BotonZap.getEstado()) {
			e.dibujarCirculo(BotonZap.getX() - BotonZap.getAncho()/2, BotonZap.getY() - BotonZap.getAlto(), 7, new Color(0, 255, 0, 255));
			e.dibujarCirculo(BotonZap.getX() - BotonZap.getAncho()/2, BotonZap.getY() + BotonZap.getAlto()/4, 7, new Color(0, 255, 0, 255));
		}
		if (BotonAcidSplash.getEstado()) {
			e.dibujarCirculo(BotonAcidSplash.getX() - BotonAcidSplash.getAncho()/2, BotonAcidSplash.getY() - BotonAcidSplash.getAlto(), 7, new Color(0, 255, 0, 255));
			e.dibujarCirculo(BotonAcidSplash.getX() - BotonAcidSplash.getAncho()/2, BotonAcidSplash.getY() + BotonAcidSplash.getAlto()/4, 7, new Color(0, 255, 0, 255));
		}
	}
	
	public boton getBotonZap(){ return this.BotonZap; }
	public boton getBotonAcidSplash(){ return this.BotonAcidSplash; }
	public boton getBotonFireBall(){ return this.BotonFireBall; }
}
