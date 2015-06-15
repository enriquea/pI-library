package uk.ac.ebi.pride.sequence.isoelectricpoint.cofactorAdjacentpI;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.mol.AminoAcid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enriquea
 * Date: 9/12/13
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
public class CofactorSixWindowAATest {

    @Test
    public void testGetInstance() throws Exception {
        CofactorSixWindowAA cofactor = CofactorSixWindowAA.getInstance();

    }


    @Test
    public void testGetIonizableGroupDistances () throws Exception {


        List<AminoAcid> sequence = new ArrayList<AminoAcid>();
        String temp = "KAHHFADDDAFAFA";
        for(Character character: temp.toCharArray()){
            sequence.add(AminoAcid.getAminoAcid(character));
        }

        HashMap<Integer, Character> distances = new HashMap<Integer, Character> ();
        distances = CofactorSixWindowAA.getIonizableGroupDistances(sequence, 'H', 3);

        System.out.println (distances);

        //output> {3=D, 4=D, 5=D, -3=K, -1=H}
    }


    @Test
    public void testGetDpkConstant() throws Exception {
        CofactorSixWindowAA cofactor = CofactorSixWindowAA.getInstance();
                Character currentAA = new Character('H');
                Character neighborAA = new Character('H');
                Integer d = new Integer(3);
        System.out.println(cofactor.getDpkConstant(currentAA,neighborAA,d));
    }

    @Test
    public void testComputePI() throws Exception {

        List<AminoAcid> sequence = new ArrayList<AminoAcid>();
        String temp = "AAAAAAAAAAAAAAAAAAAAAP";
        for(Character character: temp.toCharArray()){
            sequence.add(AminoAcid.getAminoAcid(character));
        }

        List<List<AminoAcid>> sequences = new ArrayList<List<AminoAcid>>();
        sequences.add(sequence);

        CofactorSixWindowAA cofactor = CofactorSixWindowAA.getInstance();
        Double sequencePI = cofactor.computePI(sequence);

        System.out.println(sequencePI);

        // assertTrue("Isoelectric Point equal to ", sequencesPI == 3.7196044921875);

   }

    @Before
    public void setUp() throws Exception {
        CofactorSixWindowAA  cofactor = CofactorSixWindowAA.getInstance();
    }
}


