package uk.ac.ebi.pride.sequence.isoelectricpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.mol.AminoAcid;
import uk.ac.ebi.pride.sequence.isoelectricpoint.iterativepI.IterativepI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: enriquea
 * Date: 6/11/15
 * Time: 9:03 PM
 * To change this template use File | Settings | File Templates.
 */

public class IterativepITest {

    IterativepI calculator = null;


    /*
    * This method compute the isoelectric point for a given sequence.
    * It use the Iterative algorithm with any pK set predefined.
    * */
    @Test
    public void testComputePI(){

        // Computing pI value for a single sequence

         List<AminoAcid> sequence = new ArrayList<AminoAcid>();
         String temp = "EYQLNDSASYYLNDLDR";
         for(Character character: temp.toCharArray()) sequence.add(AminoAcid.getAminoAcid(character));

         List<List<AminoAcid>> sequences = new ArrayList<List<AminoAcid>>();
         sequences.add(sequence);

         Double sequencesPI = calculator.computePI(sequence);

         //System.out.println(sequencesPI);

         assertTrue("Isoelectric Point equal to ", sequencesPI == 3.5011146068573);
    }


   /*
  * This method compute the isoelectric point for peptides sequences contained in a input file.
  * It use the Iterative algorithm with any pK set predefined.
  * */
    @Test
    public void testComputePIGroup() throws Exception {

        DecimalFormat df = new DecimalFormat("#.###");

        //Computing pI values from sequences file

        URL url = IterativepITest.class.getClassLoader().getResource("HellerDataSet.csv");
        if (url == null) {
            throw new IllegalStateException("no file for input found!");
        }
        File heller_file = new File(url.toURI());
        BufferedReader input = new BufferedReader(new FileReader(heller_file));
        String tuple;
        StringTokenizer token;
        while((tuple = input.readLine())!= null){

            token = new StringTokenizer(tuple, ",");
            String s = token.nextToken();
            String pIexperimental = token.nextToken();

            if(pIexperimental!=null && s!=null){

                List<AminoAcid> sequence = new ArrayList<AminoAcid>();
                for(Character character: s.toCharArray()){
                    if(character.equals('X')){
                        continue;
                    }
                    sequence.add(AminoAcid.getAminoAcid(character));
                }

                Double sequencesPI = calculator.computePI(sequence);
                System.out.println(s + "\t" + pIexperimental + "\t" + df.format(sequencesPI));
            }
        }
        input.close();
    }

    @Test
    public void testComputePIGroupAllMethods() throws Exception {

        DecimalFormat df = new DecimalFormat("#.###");

        //Computing pI values from sequences file

        URL url = IterativepITest.class.getClassLoader().getResource("HellerDataSet.csv");
        if (url == null) {
            throw new IllegalStateException("no file for input found!");
        }
        File heller_file = new File(url.toURI());
        BufferedReader input = new BufferedReader(new FileReader(heller_file));
        String tuple;
        StringTokenizer token;
        while((tuple = input.readLine())!= null){

            token = new StringTokenizer(tuple, ",");
            String s = token.nextToken();
            String pIexperimental = token.nextToken();

            if(pIexperimental!=null && s!=null){

                List<AminoAcid> sequence = new ArrayList<AminoAcid>();
                for(Character character: s.toCharArray()){
                    if(character.equals('X')){
                        continue;
                    }
                    sequence.add(AminoAcid.getAminoAcid(character));
                }

                Double pI = calculator.computePI(sequence);

                System.out.println(s + "\t" + pIexperimental + "\t" + df.format(pI));
            }
        }
        input.close();
    }

    /*
    * This method compute the isoelectric point for proteins from PIPDB.
    * It use the Iterative algorithm with any pK set predefined.
    * */
    @Test
    public void testComputePiFromPIPDB () throws Exception {

        DecimalFormat df = new DecimalFormat("#.###");

        //Parsing PIPDB

        URL url = IterativepITest.class.getClassLoader().getResource("PIPDB.txt");
        if (url == null) {
            throw new IllegalStateException("no file for input found!");
        }
        File pidb_file = new File(url.toURI());
        BufferedReader input = new BufferedReader(new FileReader(pidb_file));
        String tuple;
        StringTokenizer token = null;
        List<ProteinPIDB> proteinGroup = new ArrayList<ProteinPIDB>();
        List<String> accessions = new ArrayList<String>();
        int i = 0;
        while((tuple= input.readLine())!= null){
            token = new StringTokenizer(tuple, "\t");
            String pIexperimental = token.nextToken();
            String sequence = token.nextToken();
            String proteinID = token.nextToken();
            if(pIexperimental!=null && sequence!=null && proteinID!=null){
                accessions.add(proteinID);
                proteinGroup.add(new ProteinPIDB(sequence,   proteinID, pIexperimental, "0.0"));
                i++;
            }
        }

        // System.out.println(i);
        // System.out.println(proteinGroup.get(0).getHeaderForEntry());

        //getting duplicate protein in database
        HashSet set = new HashSet();
        List<String> duplicateSet = new ArrayList<String>();
        for (String acc: accessions) {
            boolean val = set.add(acc);
            if (val == false) {
                duplicateSet.add(acc);
            }
        }

        // System.out.println(duplicateSet);
        //computing pI group

        for(ProteinPIDB protein:proteinGroup){

            //avoiding duplicate protein
            if(duplicateSet.contains(protein.getHeaderForEntry())){
                continue;
            }

            List<AminoAcid> sequence = new ArrayList<AminoAcid>();
            String temp = protein.getSequenceProtein();

            for(Character character: temp.toCharArray()){
                if(character.equals('X')){
                    continue;     //Avoiding not valid character in sequence
                }
                sequence.add(AminoAcid.getAminoAcid(character));   //Building Amino Acid sequence
            }

            List<List<AminoAcid>> sequences = new ArrayList<List<AminoAcid>>();
            sequences.add(sequence);

            Double sequencesPI = calculator.computePI(sequence);
            System.out.println(protein.getHeaderForEntry() + "\t" +protein.getpIexperimental()+ "\t" + df.format(sequencesPI));
        }
    }

    @Before
    public void setUp() throws Exception {
        calculator = IterativepI.getInstance(IterativepI.SILLERO_PKMETHOD, 7.0);
    }

    @After
    public void tearDown() throws Exception {
    }
}
