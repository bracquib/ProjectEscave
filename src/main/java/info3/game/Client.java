package info3.game;

import info3.game.assets.AssetServer;

public class Client {
	/**
	 * Paramètres de la ligne de commande :
	 * 
	 * - rien : lance un client et un serveur dans le même processus, avec un seul
	 * joueur (pour tester surtout) - <ip> : se connecte à un serveur sur le port
	 * 1906 - <ip> <port> : se connecte à un serveur sur le port donné
	 */
	public static void main(String args[]) throws Exception {
		AssetServer.init(true);
		try {
			Controller backend;
			if (args.length == 0) {
				backend = new LocalController();
			} else {
				int port = 1906;
				if (args.length == 2) {
					port = Integer.parseInt(args[1]);
				}
				backend = new RemoteController(args[0], port);
			}
			new LocalView(backend);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
		}
	}
}
