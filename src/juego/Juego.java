package juego;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JOptionPane;

public class Juego extends InterfaceJuego{

	private Entorno entorno;
	private Fondo fondo;
	private Gondolf gondolf;
	private boton botonZap;
	private Zap Zap;
	private boton botonAcidSplash;
	private AcidSplash AcidSplash;
	private boton botonFireBall;
	private FireBall FireBall;
    private ArrayList<Murcielago> murcielagos;
    private ArrayList<Obstaculo> obstaculos = new ArrayList<>();
    private Random random;
    private BarraEstado barraEstado;
	
	private Image[] spritesArriba;
	private Image[] spritesAbajo;
	private Image[] spritesIzquierda;
	private Image[] spritesDerecha;
	private Image[] barriles;
	private int framesMago;
	
	private int entornoAncho = 800;
	private int entornoAlto = 600;
	
	private int mouseX;
	private int mouseY;
	
	private int mapaAncho = 600;
	private int mapaAlto = 600;
	
	private Menu menu;
	
    private static final int MAX_ENEMIGOS = 20;
    private static final int OBSTACULO_SIZE = 32;
    
// Temporizadores y contadores
    private int tiempoSpawn = 0;
    private int enemigosEliminados = 0;

// Estado del juego
    private boolean gano = false;
    private boolean perdio = false;
    private static Juego instancia;
	
	
	// Variables y métodos propios de cada grupo
	// ...
	
	public Juego(){
		
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", entornoAncho, entornoAlto);
		
		// Inicializa el Fondo
		this.fondo = new Fondo(300, 300);
		
		// Inicializa a Gondolf
		this.gondolf = new Gondolf(400, 300);

		// Inicializa lso murcielagos
        this.murcielagos = new ArrayList<>();

		// Inicializa la variable random
        this.random = new Random();
        
        this.menu = new Menu();
        
        // Inicializa la barra de Estado
        this.barraEstado = new BarraEstado();
        
        // Inicializa los Hechizos
        asignarBotonesHechizos();
        
        inicializarObstaculos();
        
        this.gano = false;
		
		// Inicia el juego!
		this.entorno.iniciar();
		
	}

	/*
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		
		actualizarEstadoJuego();

		dibujarElementos();
		
		if (!gano && !perdio) {
        	this.procesarEntrada();
			this.contadoresDeFrames();
			verSiGanoOPerdio();
			
		}
		if (gano) {
			entorno.dibujarImagen(Herramientas.cargarImagen("elementos/Mapa/You Win.jpg"), 400, 300, 0, 2);
			return;
		}
		if (perdio) {
			entorno.dibujarImagen(Herramientas.cargarImagen("elementos/Mapa/Game Over.png"), 400, 300, 0, 1.8);
			return;
		}
	}
	
	
	
	private void actualizarEstadoJuego() {
        gondolf.actualizar();
        manejarSpawnEnemigos();
        actualizarHechizos();
        estaApretandoUnBoton();
        actualizarEnemigos();
	}
	
	private void verSiGanoOPerdio() {
		if (gondolf.getVidaActual() > 0 && enemigosEliminados >= 40) {
			this.gano = true;
		}
		if (gondolf.getVidaActual() <= 0) {
			this.perdio = true;
		}
	}
	
	private void dibujarElementos() {
    	
    	fondo.dibujarse(entorno);
    	
		
    	dibujar(entorno, gondolf.getX(), gondolf.getY(), framesMago);
    	
    	// Dibujar obstáculos (opcional, para debug)
    	for (Obstaculo obs : obstaculos) {
    		obs.dibujar(entorno);
    	}
        
        // Dibujar enemigos
        for (Murcielago m : murcielagos) {
            m.dibujar(entorno);
        }
        
        // Dibujar proyectiles
        for (Proyectil p : gondolf.getProyectiles()) {
            p.dibujar(entorno, framesMago);
        }

        // Dibujar AcidSplah
        if (AcidSplash != null && AcidSplash.getEstado()) {
        	AcidSplash.dibujarse(entorno);
        }
        
        // Dibujar FireBall
        if (FireBall != null && FireBall.getEstado()) {
        	FireBall.dibujarse(entorno);
        }
        
        // Dibujar HUD
        menu.dibujarMenu(entorno);
        dibujarHUD();
    }

	private void contadoresDeFrames() {
		int sec = entorno.tiempo()/100;
		framesMago = sec%2;
	}
	
	
//****************************************************************************
//
//		                       - Funciones Hechizos -
//
//****************************************************************************
	
	private void actualizarHechizos() {
	    if (FireBall != null && FireBall.getEstado()) {
	        FireBall.actualizar();
	        if (!FireBall.getEstado()) {
	            botonFireBall.apretarBoton();
	        }
	    }
	    if (AcidSplash != null && AcidSplash.getEstado()) {
	    	AcidSplash.actualizar();
	        if (!AcidSplash.getEstado()) {
	        	botonAcidSplash.apretarBoton();
	        }
	    }
	}

	private void casteoFireBall() {
	    if (botonFireBall.getEstado() && gondolf.getManaActual() >= 20 && (entorno.mousePresente() && entorno.mouseX() < mapaAncho) && (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO))) {
	        this.FireBall = new FireBall(entorno.mouseX(), entorno.mouseY());
	        this.FireBall.castear(entorno.mouseX(), entorno.mouseY());
	        gondolf.restarMana(this.FireBall.getCoste());
	    }
	}
	
	private void casteoAcidSplash() {
		if (botonAcidSplash.getEstado() && gondolf.getManaActual() >= 5 && (entorno.mousePresente() && entorno.mouseX() < mapaAncho) && (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO))) {
	        this.AcidSplash = new AcidSplash(entorno.mouseX(), entorno.mouseY());
	        this.AcidSplash.castear(entorno.mouseX(), entorno.mouseY());
	        gondolf.restarMana(this.AcidSplash.getCoste());
	    }
	}
	
	
	
//****************************************************************************
//
//			                       - Funciones Botones -
//
//****************************************************************************
	
	private void asignarBotonesHechizos() {
		this.botonZap = this.menu.getBotonZap();
		this.botonAcidSplash = this.menu.getBotonAcidSplash();
		this.botonFireBall = this.menu.getBotonFireBall();
	}
	
	private void estaApretandoUnBoton() {
	    mouseX = entorno.mouseX();
	    mouseY = entorno.mouseY();

	    if (botonZap.estaAdentro(mouseX, mouseY) && entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
	        if (!botonZap.getEstado()) {
	            botonZap.apretarBoton();
	            if (botonFireBall.getEstado()) {
	                botonFireBall.apretarBoton();
	            }
	            if (botonAcidSplash.getEstado()) {
	            	botonAcidSplash.apretarBoton();
	            }
	        } else {
	            botonZap.apretarBoton();
	        }
	    }
	    if (botonFireBall.estaAdentro(mouseX, mouseY) && entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
	        if (!botonFireBall.getEstado()) {
	            botonFireBall.apretarBoton();
	            if (botonZap.getEstado()) { 
	                botonZap.apretarBoton();
	            }
	            if (botonAcidSplash.getEstado()) {
	            	botonAcidSplash.apretarBoton();
	            }
	        } else {
	            botonFireBall.apretarBoton();
	        }
	    }
	    if (botonAcidSplash.estaAdentro(mouseX, mouseY) && entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
	        if (!botonAcidSplash.getEstado()) {
	        	botonAcidSplash.apretarBoton();
	            if (botonZap.getEstado()) { 
	                botonZap.apretarBoton();
	            }
	            if (botonFireBall.getEstado()) {
	            	botonFireBall.apretarBoton();
	            }
	        } else {
	        	botonAcidSplash.apretarBoton();
	        }
	    }
	    casteoFireBall();
	    casteoAcidSplash();
	}
	
//****************************************************************************
//
//				                       - Funciones Murcielagos -
//
//****************************************************************************
	
	private void manejarSpawnEnemigos() {
        tiempoSpawn++;
        if (tiempoSpawn >= 10 && murcielagos.size() < MAX_ENEMIGOS) {
            spawnearMurcielago();
            tiempoSpawn = 0;
        }
    }

    private void spawnearMurcielago() {
        try {
            int lado = random.nextInt(4);
            int x = 0, y = 0;
            
            switch(lado) {
                case 0: x = random.nextInt(mapaAncho); y = -20; break;
                case 1: x = mapaAncho + 20; y = random.nextInt(mapaAlto); break;
                case 2: x = random.nextInt(mapaAncho); y = mapaAlto + 20; break;
                case 3: x = -20; y = random.nextInt(mapaAlto); break;
            }
            
            murcielagos.add(new Murcielago(x, y));
        } catch (Exception e) {
            System.err.println("Error al generar murciélago: " + e.getMessage());
        }
    }

    private void actualizarEnemigos() {
        Iterator<Murcielago> iter = murcielagos.iterator();
        while (iter.hasNext()) {
            Murcielago m = iter.next();
            
            m.mover(gondolf);
            
            // Colisión con jugador
            if (gondolf.getRectangulo().intersects(m.getRectangulo())) {
                gondolf.recibirDaño(10);
                m.recibirDaño(1);
            }
            
            // Colisión con proyectiles
            for (Proyectil p : gondolf.getProyectiles()) {
                if (p.getRectangulo().intersects(m.getRectangulo())) {
                    m.recibirDaño(p.getDaño());
                    p.desactivar();
                }
            }
            
         // Colisión con Fireball
            if (FireBall != null && FireBall.getEstado() && FireBall.getHitbox().intersects(m.getRectangulo())) {
                m.recibirDaño(FireBall.getDaño());
            }
            // Colisión con AcidSplash
            if (AcidSplash != null && AcidSplash.getEstado() && AcidSplash.getHitbox().intersects(m.getRectangulo())) {
            	m.recibirDaño(AcidSplash.getDaño());
            }
            
                        
            if (!m.isActivo()) {
                iter.remove();
                enemigosEliminados++;
            }
        }
    }
    
    
//****************************************************************************
//
//    			                       - Funciones HUD -
//
//****************************************************************************

    private void dibujarHUD() {
        barraEstado.dibujar(entorno, gondolf);
        
        entorno.cambiarFont("Arial", 15, Color.WHITE);
        entorno.escribirTexto("Enemigos: " + murcielagos.size() + "/" + MAX_ENEMIGOS, 600, 100);
        entorno.escribirTexto("Eliminados: " + enemigosEliminados, 600, 120);
        
        if (gondolf.estaInvulnerable()) {
            entorno.dibujarRectangulo(gondolf.getX(), gondolf.getY(), 
                                    gondolf.getMitadTamaño()*2, gondolf.getMitadTamaño()*2, 
                                    0, new Color(255, 0, 0, 100));
        }
    }
	
	
//****************************************************************************
//
//                       - Funciones Gondolf -
//
//****************************************************************************
	
	
// Interpreta el input de las teclas y mueve a Gondolf
	
	private void procesarEntrada() {
		int velocidad = 5;
		int dx = 0;
		int dy = 0;
		
		Rectangle hitbox = this.gondolf.getRectangulo();
		if (this.entorno.estaPresionada('w') && hitbox.y - velocidad > 0) {
			this.gondolf.setDireccion("w");
			dy = -velocidad;
		}
		
		if (this.entorno.estaPresionada('s') && hitbox.y + hitbox.height + velocidad < 600) {
			this.gondolf.setDireccion("s");
			dy = velocidad;
		}
		
		if (this.entorno.estaPresionada('a') && hitbox.x - velocidad > 0) {
			this.gondolf.setDireccion("a");
			dx = -velocidad;
		}
		
		if (this.entorno.estaPresionada('d') && (hitbox.x + hitbox.width + velocidad) < 600) {
			this.gondolf.setDireccion("d");
			dx = velocidad;
		}
		
		// Verificar colisiones antes de mover
        Rectangle hitboxProvisional = new Rectangle(
            gondolf.getX() + dx,
            gondolf.getY() + dy,
            gondolf.getMitadTamaño()*2,
            gondolf.getMitadTamaño()*2
        );
        
        boolean puedeMoverse = true;
        for (Obstaculo obs : obstaculos) {
            if (hitboxProvisional.intersects(obs.getHitbox())) {
                puedeMoverse = false;
                break;
            }
        }
        
        // Verificar límites del mapa
        if (hitboxProvisional.x < 0 || hitboxProvisional.x + hitboxProvisional.width > mapaAncho ||
            hitboxProvisional.y < 0 || hitboxProvisional.y + hitboxProvisional.height > mapaAlto) {
            puedeMoverse = false;
        }
        
        if (puedeMoverse) {
            gondolf.mover(dx, dy, obstaculos);
        }

        
        if (botonZap.getEstado() && entorno.estaPresionada('i')) {  // Arriba
            gondolf.disparar(0);
        }
        if (botonZap.getEstado() && entorno.estaPresionada('l')) {  // Derecha
            gondolf.disparar(1);
        }
        if (botonZap.getEstado() && entorno.estaPresionada('k')) {  // Abajo
            gondolf.disparar(2);
        }
        if (botonZap.getEstado() && entorno.estaPresionada('j')) {  // Izquierda
            gondolf.disparar(3);
        }
	}
	
	
// Dibuja a Gondolf
	
	private void dibujar(Entorno entorno, int X, int Y, int frame) {
		
		try {
            // Cargar sprites para cada dirección (2 frames por dirección)
            this.spritesArriba = new Image[]{
                Herramientas.cargarImagen("elementos/Mago/detras1.png"),
                Herramientas.cargarImagen("elementos/Mago/detras2.png")
            };
            this.spritesAbajo = new Image[]{
                Herramientas.cargarImagen("elementos/Mago/sprite_1.png"),
                Herramientas.cargarImagen("elementos/Mago/sprite_2.png")
            };
            this.spritesIzquierda = new Image[]{
                Herramientas.cargarImagen("elementos/Mago/izquierda1.png"),
                Herramientas.cargarImagen("elementos/Mago/izquierda2.png")
            };
            this.spritesDerecha = new Image[]{
                Herramientas.cargarImagen("elementos/Mago/derecha1.png"),
                Herramientas.cargarImagen("elementos/Mago/derecha2.png")
            };
        } catch (Exception e) {
            System.err.println("Error al cargar los sprites de Gondolf: \n" + e.getMessage());
        }
		// Itera entre los sprites para generar la animación
        Image spriteActual = obtenerSpriteActual(frame);
        entorno.dibujarImagen(spriteActual, X, Y, 0);
    }
	
	
// Animación de caminar de Gondolf
	
	private Image obtenerSpriteActual(int frame) {
    	if (gondolf.direccion == "s" && entorno.estaPresionada('s')) {
    		return spritesAbajo[frame];
    	}
    	if (gondolf.direccion == "a" && entorno.estaPresionada('a')) {
    		return spritesIzquierda[frame];
    	}
    	if (gondolf.direccion == "d" && entorno.estaPresionada('d')) {
    		return spritesDerecha[frame];
    	}
    	if (gondolf.direccion == "w" && entorno.estaPresionada('w')) {
    		return spritesArriba[frame];
    	}
    	if (gondolf.direccion == "s") {
    		return spritesAbajo[0];
    	}
    	if (gondolf.direccion == "a") {
    		return spritesIzquierda[0];
    	}
    	if (gondolf.direccion == "d") {
    		return spritesDerecha[0];
    	}
    	if (gondolf.direccion == "w") {
    		return spritesArriba[0];
    	}
    	return spritesAbajo[0];
    }
	
	
//****************************************************************************
//
//	                       - Funciones Obstaculo -
//
//****************************************************************************
	
	private void inicializarObstaculos() {
        obstaculos.add(new Obstaculo(100, 100, "elementos/Mapa/barril.png"));
        obstaculos.add(new Obstaculo(300, 200, "elementos/Mapa/barril.png"));
        obstaculos.add(new Obstaculo(500, 400, "elementos/Mapa/barril.png"));
        obstaculos.add(new Obstaculo(500, 100, "elementos/Mapa/barril.png"));
        obstaculos.add(new Obstaculo(100, 400, "elementos/Mapa/barril.png"));
    }
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
