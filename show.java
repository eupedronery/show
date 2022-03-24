package SO;


import java.util.concurrent.Semaphore;

public class show extends Thread {
	private static int quantidade_ingressos = 100;
	

	private int pedido_ingressos;
	
	private Semaphore semaforo_validacao;
	
	public show(int pedingresso, Semaphore _semaforo_validacao) {
		pedido_ingressos = pedingresso;
		semaforo_validacao = _semaforo_validacao;
	}
	
	public void run() {
		if(!loginSistema())
			return;
		if(!processarCompra())
			return;
		
		validarCompra();
	}
	
	private boolean loginSistema() {
		int tempo_login = (int) ((Math.random() * 1951) + 50);
		int tempo_limite = 1000;
		try {
			sleep(tempo_login);
			if(tempo_login > tempo_limite) {
				System.out.println("#"+getId()+" Login timeout: Tempo de espera - " + tempo_login + "ms");
				return false;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	private boolean processarCompra() {
		int tempo_processo = (int) ((Math.random() * 2001) + 1000);
		int tempo_limite = 2500;
		try {
			sleep(tempo_processo);
			if(tempo_processo > tempo_limite) {
				System.out.println("#"+getId()+" Process timeout: Tempo de espera - " + tempo_processo + "ms");
				return false;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	private void validarCompra() {
		try {
			semaforo_validacao.acquire();
			if(pedido_ingressos <= quantidade_ingressos) {
				quantidade_ingressos -= pedido_ingressos;
				System.out.println("#"+getId()+" "+pedido_ingressos + " vendidos. Disponível: " + quantidade_ingressos);
			}else {
				System.out.println("#"+getId()+" Não há ingressos o suficiente. Cancelando a compra");
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			semaforo_validacao.release();
		}
	}
}