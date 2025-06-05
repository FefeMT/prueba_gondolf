package juego;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Gondolf {
	   private int x;
	   private int y;
	   private int tamaño = 40;
	   private int vidaMaxima = 100;
	   private int vidaActual;
	   private boolean invulnerable = false;
	   private int tiempoInvulnerable = 0;
	   private int manaMaximo = 100;
	   private int manaActual;
	   

	   private ArrayList<Proyectil> proyectil = new ArrayList<>();
	   private int cooldownDisparo = 0;
	   private final int COOLDOWN_DISPARO = 10;
	   private int contadorRegeneracion = 0;
	   private final int INTERVALO_REGENERACION = 3 * 60; // 3 segundos en frames
	   private Rectangle hitbox;
	   
	   
	   
	   public String direccion = "s";

	   public Gondolf(int x, int y) {
	      this.x = x;
	      this.y = y;
	      this.vidaActual = vidaMaxima;
	      this.manaActual = manaMaximo;
	      this.hitbox = new Rectangle(x, y, 32, 32);
	      
	   }

	   public void setDireccion(String direccion) {
	      this.direccion = direccion;
	   }

	   public void mover(int dx, int dy, Obstaculo[] obs) {
	    	Rectangle hitbox = this.hitbox; 
	    	Rectangle nuevaPosicion = new Rectangle(
	    	    hitbox.x + dx,
	    	    hitbox.y + dy,
	    	    hitbox.width,
	    	    hitbox.height
	    	);

	        boolean colision = false;
	        
	        for (int i = 0; i < obs.length; i++) {
	        	if (nuevaPosicion.intersects(obs[i].getHitbox())) {
	            colision = true;
	        	}
	        }
	        
	        if (!colision) {
	            this.x += dx;
	            this.y += dy;
	            this.hitbox = nuevaPosicion;
	        }
	   }
	   
	   public void actualizar() {
	        if (invulnerable) {
	            tiempoInvulnerable--;
	            if (tiempoInvulnerable <= 0) {
	                invulnerable = false;
	            }
	        }
	        
	        if (cooldownDisparo > 0) cooldownDisparo--;
	        
	        // Regenerar mana
	        contadorRegeneracion++;
	        if (contadorRegeneracion >= INTERVALO_REGENERACION && manaActual < manaMaximo) {
	            manaActual = Math.min(manaMaximo, manaActual + 10);
	            contadorRegeneracion = 0;
	        }
	        
	        // Actualizar proyectiles
	        for (int i = proyectil.size() - 1; i >= 0; i--) {
	            Proyectil p = proyectil.get(i);
	            p.mover();
	            if (!p.isActivo()) {
	                proyectil.remove(i);
	            }
	        }
	   }
	   
	   public void recibirDaño(int cantidad) {
	        if (!invulnerable) {
	            vidaActual = Math.max(0, vidaActual - cantidad);
	            invulnerable = true;
	            tiempoInvulnerable = 30;
	        }
	    }
	   
	   public void curarse(Pociones pocion) {
		   vidaActual += pocion.getCura();
		   if (vidaActual > vidaMaxima) {
			   vidaActual = vidaMaxima;
		   }
	   }
	   
	    
	    public void disparar(int direccionDisparo) {
	        if (cooldownDisparo <= 0 && manaActual >= 2) {
	            int destinoX = x, destinoY = y;
	            String dir = "";
	            
	            switch(direccionDisparo) {
	                case 0: // Arriba
	                    destinoY -= 200;
	                    dir = "w";
	                    break;
	                case 1: // Derecha
	                    destinoX += 200;
	                    dir = "d";
	                    break;
	                case 2: // Abajo
	                    destinoY += 200;
	                    dir = "s";
	                    break;
	                case 3: // Izquierda
	                    destinoX -= 200;
	                    dir = "a";
	                    break;
	            }
	            
	            // Solo cambiar dirección si no se está moviendo
	            if (direccion.equals("s") || direccion.equals("w") && (dir.equals("a") || dir.equals("d")) ||
	                direccion.equals("a") || direccion.equals("d") && (dir.equals("w") || dir.equals("s"))) {
	                direccion = dir;
	            }
	            
	            proyectil.add(new Proyectil(x, y, destinoX, destinoY));
	            cooldownDisparo = COOLDOWN_DISPARO;
	        }
	    }
	    
	    public void restarMana(int manaConsumido) {
	    	this.manaActual -= manaConsumido;
	    }

	    
	    
// Getters
	    public int getX() { return x; }
	    public int getY() { return y; }
	    public int getMitadTamaño() { return tamaño / 2; }
	    public Rectangle getRectangulo() { return new Rectangle(x - tamaño / 2, y - tamaño / 2, tamaño, tamaño); }
	    public int getVidaActual() { return vidaActual; }
	    public int getVidaMaxima() { return vidaMaxima; }
	    public boolean estaInvulnerable() { return invulnerable; }
	    public int getManaActual() { return manaActual; }
	    public int getManaMaximo() { return manaMaximo; }
public ArrayList<Proyectil> getProyectiles() { return proyectil; }
		
}

