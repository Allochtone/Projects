package test;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;

import armes.Armes;

public class ArmesTest {
	Armes[] armes;
	Armes armeSelectionner1;
	Armes armeSelectionner2;
	Armes armeSelectionner3;
	
	@Before
	public void setUp() throws Exception {
		armes = Armes.values();
		armeSelectionner1 = armes[0];
		armeSelectionner2 = armes[2];
		armeSelectionner3 = armes[4];
	}

	@Test
	public void testGetNom() {
		assertTrue(armeSelectionner1.getNom()=="sinus");
		assertFalse(armeSelectionner2.getNom()== "cosec");
		assertTrue(armeSelectionner3.getNom() == "rationnelle");
		
	}

	@Test
	public void testGetArmeselectionner() {
		assertTrue(armeSelectionner2.getPath()=="armes/tan(x).png");
		assertFalse(armeSelectionner1.getPath()== "armes/rationelle.png");
		assertTrue(armeSelectionner3.getPath() == "armes/rationelle.png");
		
	}

}
