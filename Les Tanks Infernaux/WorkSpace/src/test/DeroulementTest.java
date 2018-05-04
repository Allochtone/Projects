package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jeu.Deroulement;
import tanks.Difficultes;
import tanks.IA;
import tanks.Joueur;

public class DeroulementTest {
	Deroulement deroulement1;
	Deroulement deroulement2;
	Deroulement deroulement3;
	@Before
	public void setUp() throws Exception {
		deroulement1 = new Deroulement(Difficultes.FACILE);
		deroulement2 = new Deroulement(Difficultes.MOYEN);
		deroulement3 = new Deroulement(Difficultes.AIMBOT);
	}

	@Test
	public void testGetTank() {
		IA ia1 = (IA) deroulement1.getTank()[1];
		Joueur joueur= (Joueur) deroulement2.getTank()[0];
		IA ia2 = (IA) deroulement3.getTank()[1];
		assertEquals(deroulement1.getTank()[1],ia1);
		assertEquals(deroulement2.getTank()[0],joueur);
		assertNotEquals(ia1, ia2);
	}

	@Test
	public void testPhytagoras() {
		
		assertTrue(true);
	}

	@Test
	public void testSetDirection() {
	}

}
