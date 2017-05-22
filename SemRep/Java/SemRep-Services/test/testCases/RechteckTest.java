package testCases;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.oanaureche.swifc.Rechteck;
 
public class RechteckTest {
    
   private static Rechteck myRechteck;
    
   @BeforeClass
   public static void create() {
      // Test-Objekt erschaffen mit den Testwerten (Länge: 10 und Breite: 20)
      myRechteck = new Rechteck(10, 20);
      System.out.println("Start!");
   }
    
   @Before
   public void vor() {
      // Diese Methode wird vor jedem Testfall ausgeführt
      System.out.println("vor Test");
   }
    
   @Test
   public void derTest1() {
      // Testfall 1: Prüfung ob Umfangsberechnung stimmt
      System.out.println("Test1");
      Assert.assertTrue(60 == myRechteck.berechneUmfang());      
   }
    
   @Test
   public void derTest2() {
      // Testfall 2: Prüfung ob Flächeninhaltsberechnung stimmt
      System.out.println("Test2");
      Assert.assertNotNull(myRechteck.berechneInhalt());   
   }
    
   @After
   public void nach() {
      // Diese Methode wird nach jedem Testfall ausgeführt z.B. um einen bestimmten Zustand zu erreichen
      System.out.println("nach Test");
   }
    
   @AfterClass
   public static void delete() {
      // Diese Methode wird am Ende der Test-Klasse ausgeführt z.B. zum aufräumen oder löschen von Rückständen
      System.out.println("Test Ende!");
   }
}