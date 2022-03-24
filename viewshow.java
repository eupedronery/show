package view;

import java.util.concurrent.Semaphore;

import SO.show;

public class viewshow {
	
	public static void main(String[] args) {
		int compradores = 300;
		Semaphore semaforo_validacao = new Semaphore(1);
	
		for(int i=0; i < compradores; i++) {
			int numero_ingressos = (int) ((Math.random() * 4) + 1);
			new show(numero_ingressos, semaforo_validacao).start();
		}
	}
}