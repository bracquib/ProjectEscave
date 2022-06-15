package info3.game.cavegenerator;

import java.util.HashMap;

/*
 *
 * 
 * 
 * [1] [2] 	[2]  [2]  [3]
 * [8] [9] 	[10] [11] [4]
 * [8] [16]	[1] [12] [4]
 * [8] [15]	[14] [13] [4]
 * [7] [6] 	[6]  [6]  [5]
 * 
 * 
 * 
 * 
 * 
 * 
 * -1 : "vide"
 * 	*** Blocs normaux
 * 0 : "noir"
 * 1 : "coin_gauche_haut"
 * 2 : "sol"
 * 3 : "coin_droit_haut"
 * 4 : "mur_droit"
 * 5 : "coin_droit_bas"
 * 6 : "plafond"
 * 7 : "coin_gauche_bas"
 * 8 : "mur_gauche"
 * 9 : "trans_coin_gauche_haut"
 * 10 : "trans_sol"
 * 11 : "trans_coin_droit_haut"
 * 12 : "trans_mur_droit"
 * 13 : "trans_coin_droit_bas"
 * 14 : "trans_plafond"
 * 15 : "trans_coin_gauche_bas"
 * 16 : "trans_mur_gauche"
 * 17 : "volant_seul"
 * 18 : "volant_horizontal_gauche"
 * 19 : "volant_horizontal_droit"
 * 20 : "volant_vertical_haut"
 * 21 : "volant_vertical_bas"
 * 
 *  *** Blocs spéciaux
 * 100 : "noir_blanc"
 * 101 : "noir_noir"
 * 102 : "noir_bleu"
 * 103 : "noir_rose"
 * 104 : "noir_violet"
 * 105 : "noir_vert"
 * 150 : "t_rex_1"
 * 151 : "t_rex_2"
 * 152 : "t_rex_3"
 * 153 : "t_rex_4"
 * 154 : "t_rex_5"
 * 155 : "t_rex_6"
 * 156 : "t_rex_7"
 * 160 : "steg_1"
 * 161 : "steg_2"
 * 162 : "steg_3"
 * 163 : "steg_4"
 * 164 : "steg_5"
 * 165 : "steg_6"
 * 166 : "steg_7"
 * 167 : "steg_7"
 * 170 : "tric_1"
 * 171 : "tric_2"
 * 172 : "tric_3"
 * 173 : "tric_4"
 * 174 : "tric_5"
 * 175 : "tric_6"
 * 
 * 
 * 
 * 
 *  *** Blocs spéciaux sol
 *  	*** Cristaux
 * 200 : "cristal_sol_blanc"
 * 201 : "cristal_sol_vert"
 * 202 : "cristal_sol_noir"
 * 203 : "cristal_sol_bleu"
 * 204 : "cristal_sol_rose"
 * 205 : "cristal_sol_violet"
 * 
 *  	*** Champignons
 * 250 : "champi_sol_rose"
 * 251 : "champi_sol_double_bleu"
 * 252 : "champi_sol_vert"
 * 253 : "champi_sol_escargot_bleu"
 * 254 : "champi_sol_escargot_violet"
 * 255 : "champi_sol_bleu_rose"
 * 256 : "champi_sol_double_orange"
 * 257 : "champi_sol_rouge"

 * 
 *  *** Blocs spéciaux plafond
 * 300 : "cristal_plafond_blanc"
 * 301 : "cristal_plafond_bleu"
 * 302 : "cristal_plafond_vert"
 * 303 : "cristal_plafond_violet"
 * 304 : "cristal_plafond_rose"
 * 305 : "cristal_plafond_noir"
 * 
 * 
 * 350: "plafond_liane"
 * 351 : "plafond_lanterne_courte"
 * 352 : "plafond_lanterne_longue"

 *  *** Blocs spéciaux mur_gauche
 * 400 : "cristal_mur_gauche_blanc"
 * 401 : "cristal_mur_gauche_bleu"
 * 402 : "cristal_mur_gauche_vert"
 * 403 : "cristal_mur_gauche_violet"
 * 404 : "cristal_mur_gauche_rose"
 * 405 : "cristal_mur_gauche_noir"
 * 
 * 450 : "arbre_montant_mur_gauche"
 * 450 : "arbre_tombant_mur_gauche"
 * 
 *  *** Blocs spéciaux mur_droit
 * 400 : "cristal_mur_droit_blanc"
 * 501 : "cristal_mur_droit_bleu"
 * 502 : "cristal_mur_droit_vert"
 * 503 : "cristal_mur_droit_violet"
 * 504 : "cristal_mur_droit_rose"
 * 505 : "cristal_mur_droit_noir"
 * 
 * 550 : "arbre_montant_mur_droit"
 * 551 : "arbre_tombant_mur_droit"
 * 
 */

public class BlockIDs {
	public static HashMap<Integer, String> IDs = new HashMap<Integer, String>();

	static {
		IDs.put(-1, "vide");
		IDs.put(0, "noir");
		IDs.put(1, "coin_gauche_haut");
		IDs.put(2, "sol");
		IDs.put(3, "coin_droit_haut");
		IDs.put(4, "mur_droit");
		IDs.put(5, "coin_droit_bas");
		IDs.put(6, "plafond");
		IDs.put(7, "coin_gauche_bas");
		IDs.put(8, "mur_gauche");
		IDs.put(9, "trans_coin_gauche_haut");
		IDs.put(10, "trans_sol");
		IDs.put(11, "trans_coin_droit_haut");
		IDs.put(12, "trans_mur_droit");
		IDs.put(13, "trans_coin_droit_bas");
		IDs.put(14, "trans_plafond");
		IDs.put(15, "trans_coin_gauche_bas");
		IDs.put(16, "trans_mur_gauche");
		IDs.put(17, "volant_seul");
		IDs.put(18, "volant_horizontal_gauche");
		IDs.put(19, "volant_horizontal_droit");
		IDs.put(20, "volant_vertical_haut");
		IDs.put(21, "volant_vertical_bas");
		IDs.put(100, "noir_blanc");
		IDs.put(101, "noir_noir");
		IDs.put(102, "noir_bleu");
		IDs.put(103, "noir_rose");
		IDs.put(104, "noir_violet");
		IDs.put(105, "noir_vert");
		IDs.put(150, "t_rex_1");
		IDs.put(151, "t_rex_2");
		IDs.put(152, "t_rex_3");
		IDs.put(153, "t_rex_4");
		IDs.put(154, "t_rex_5");
		IDs.put(155, "t_rex_6");
		IDs.put(156, "t_rex_7");
		IDs.put(160, "steg_1");
		IDs.put(161, "steg_2");
		IDs.put(162, "steg_3");
		IDs.put(163, "steg_4");
		IDs.put(164, "steg_5");
		IDs.put(165, "steg_6");
		IDs.put(166, "steg_7");
		IDs.put(167, "steg_7");
		IDs.put(170, "tric_1");
		IDs.put(171, "tric_2");
		IDs.put(172, "tric_3");
		IDs.put(173, "tric_4");
		IDs.put(174, "tric_5");
		IDs.put(175, "tric_6");
		IDs.put(200, "cristal_sol_blanc");
		IDs.put(201, "cristal_sol_vert");
		IDs.put(202, "cristal_sol_noir");
		IDs.put(203, "cristal_sol_bleu");
		IDs.put(204, "cristal_sol_rose");
		IDs.put(205, "cristal_sol_violet");
		IDs.put(250, "champi_sol_rose");
		IDs.put(251, "champi_sol_double_bleu");
		IDs.put(252, "champi_sol_vert");
		IDs.put(253, "champi_sol_escargot_bleu");
		IDs.put(254, "champi_sol_escargot_violet");
		IDs.put(255, "champi_sol_bleu_rose");
		IDs.put(256, "champi_sol_double_orange");
		IDs.put(257, "champi_sol_rouge");
		IDs.put(300, "cristal_plafond_blanc");
		IDs.put(301, "cristal_plafond_bleu");
		IDs.put(302, "cristal_plafond_vert");
		IDs.put(303, "cristal_plafond_violet");
		IDs.put(304, "cristal_plafond_rose");
		IDs.put(305, "cristal_plafond_noir");
		IDs.put(350, "plafond_liane");
		IDs.put(351, "plafond_lanterne_courte");
		IDs.put(352, "plafond_lanterne_longue");
		IDs.put(400, "cristal_mur_gauche_blanc");
		IDs.put(401, "cristal_mur_gauche_bleu");
		IDs.put(402, "cristal_mur_gauche_vert");
		IDs.put(403, "cristal_mur_gauche_violet");
		IDs.put(404, "cristal_mur_gauche_rose");
		IDs.put(405, "cristal_mur_gauche_noir");
		IDs.put(450, "arbre_montant_mur_gauche");
		IDs.put(451, "arbre_tombant_mur_gauche");
		IDs.put(400, "cristal_mur_droit_blanc");
		IDs.put(501, "cristal_mur_droit_bleu");
		IDs.put(502, "cristal_mur_droit_vert");
		IDs.put(503, "cristal_mur_droit_violet");
		IDs.put(504, "cristal_mur_droit_rose");
		IDs.put(505, "cristal_mur_droit_noir");
		IDs.put(550, "arbre_montant_mur_droit");
		IDs.put(551, "arbre_tombant_mur_droit");
	}

}
