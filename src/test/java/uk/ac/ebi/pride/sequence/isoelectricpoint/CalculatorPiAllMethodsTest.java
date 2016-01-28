package uk.ac.ebi.pride.sequence.isoelectricpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.sequence.isoelectricpoint.bjellpI.BjellpI;
import uk.ac.ebi.pride.sequence.isoelectricpoint.cofactorAdjacentpI.CofactorAdjacentpI;
import uk.ac.ebi.pride.sequence.isoelectricpoint.iterativepI.IterativepI;
import uk.ac.ebi.pride.sequence.isoelectricpoint.svmpI.SvmpI;
import uk.ac.ebi.pride.sequence.utils.AminoAcid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Enrique
 * Date: 15/06/15
 * Time: 0:25
 * To change this template use File | Settings | File Templates.
 */

public class CalculatorPiAllMethodsTest {

    //All prediction isoelectric point methods
    IterativepI calculator_iterative = null;
    BjellpI calculator_bjell = null;
    CofactorAdjacentpI calculator_cofactor = null;
    SvmpI calculator_svm = null;
    private HashMap<List<AminoAcid>, Double> mapSequences;



   //
   //This method compute the isoelectric point for proteins from peptides file (Heller dataset).
   //It use all methods implemented in this library.
   //

    @Test
    public void testComputePIGroupAllMethods() throws Exception {

        DecimalFormat df = new DecimalFormat("#.###");

        //Computing pI values from sequences file

        URL url = IterativepITest.class.getClassLoader().getResource("gauci-acetylated-peptide-data.txt");
        if (url == null) {
            throw new IllegalStateException("no file for input found!");
        }
        File heller_file = new File(url.toURI());
        BufferedReader input = new BufferedReader(new FileReader(heller_file));
        String tuple;
        StringTokenizer token;
        while((tuple = input.readLine())!= null){

            token = new StringTokenizer(tuple, "\t");
            String seq = token.nextToken();
            String pIexperimental = token.nextToken();

            if(pIexperimental!=null && seq!=null){

                List<AminoAcid> sequence = new ArrayList<AminoAcid>();
                for(Character character: seq.toCharArray()){
                    if(character.equals('X')){
                        continue;
                    }
                    sequence.add(AminoAcid.getMolecule(character));
                }

                //setting pI methods and computing pI

                calculator_iterative.setPKIterative(IterativepI.SILLERO_PKMETHOD);
                Double pI_sill = calculator_iterative.computePI(sequence);

                calculator_iterative.setPKIterative(IterativepI.PATRICKIOS_PKMETHOD);
                Double pI_pat = calculator_iterative.computePI(sequence);

                calculator_iterative.setPKIterative(IterativepI.LEHNINGER_PKMETHOD);
                Double pI_leh = calculator_iterative.computePI(sequence);

                calculator_iterative.setPKIterative(IterativepI.EMBOSS_PKMETHOD);
                Double pI_emb = calculator_iterative.computePI(sequence);

                calculator_iterative.setPKIterative(IterativepI.GRIMSLEY_PKMETHOD);
                Double pI_gri = calculator_iterative.computePI(sequence);

                calculator_iterative.setPKIterative(IterativepI.DTASelect_PKMETHOD);
                Double pI_dta = calculator_iterative.computePI(sequence);

                calculator_iterative.setPKIterative(IterativepI.RODWELL_PKMETHOD);
                Double pI_rodw = calculator_iterative.computePI(sequence);

                calculator_iterative.setPKIterative(IterativepI.SOLOMON_PKMETHOD);
                Double pI_sol = calculator_iterative.computePI(sequence);

                calculator_iterative.setPKIterative(IterativepI.THURLKILL_PKMETHOD);
                Double pI_thu = calculator_iterative.computePI(sequence);

                calculator_iterative.setPKIterative(IterativepI.TOSELAND_PKMETHOD);
                Double pI_tos = calculator_iterative.computePI(sequence);

                calculator_bjell.setPkMethod(BjellpI.BJELL_PKMETHOD);
                Double pI_bjell_default = calculator_bjell.computePI(sequence);

                calculator_bjell.setPkMethod(BjellpI.SKOOG_PKMETHOD);
                Double pI_bjell_skood = calculator_bjell.computePI(sequence);

                calculator_bjell.setPkMethod(BjellpI.EXPASY_PKMETHOD);
                Double pI_bjell_expasy = calculator_bjell.computePI(sequence);

                calculator_bjell.setPkMethod(BjellpI.CALIBRATED_PKMETHOD);
                Double pI_bjell_calib = calculator_bjell.computePI(sequence);

                Double pI_svm = calculator_svm.computePI(sequence);

                //Double pI_cofactor = calculator_cofactor.computePI(sequence);

                //printing all pI computed
                System.out.println(seq + "\t" + pIexperimental + "\t" + df.format(pI_sill) + "\t" + df.format(pI_pat) +
                                  "\t" + df.format(pI_leh) + "\t" + df.format(pI_emb) + "\t" + df.format(pI_gri) +
                                  "\t" + df.format(pI_dta) + "\t" + df.format(pI_rodw) + "\t" + df.format(pI_sol) +
                                  "\t" + df.format(pI_thu) + "\t" + df.format(pI_tos) + "\t" + df.format(pI_bjell_default) +
                                  "\t" + df.format(pI_bjell_skood) + "\t" + df.format(pI_bjell_expasy) + "\t" + df.format(pI_bjell_calib)+
                                  "\t" + df.format(pI_svm)); // + "\t" + df.format(pI_cofactor));
            }
        }
        input.close();
    }

    //
    //This method compute the isoelectric point for proteins from PIPDB.
    //It use all methods implemented in this library.
    //
    @Test
    public void testComputePiAllMethodsFromPIPDB() throws Exception {

        DecimalFormat df = new DecimalFormat("#.###");

        //Parsing PIPDB

        URL url = CalculatorPiAllMethodsTest.class.getClassLoader().getResource("PIPDB.txt");
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
                sequence.add(AminoAcid.getMolecule(character));   //Building Amino Acid sequence
            }

            List<List<AminoAcid>> sequences = new ArrayList<List<AminoAcid>>();
            sequences.add(sequence);

           // Double sequencesPI = calculator.computePI(sequence);
           // System.out.println(protein.getHeaderForEntry() + "\t" +protein.getpIexperimental()+ "\t" + df.format(sequencesPI));

            //setting pI methods and computing pI

            calculator_iterative.setPKIterative(IterativepI.SILLERO_PKMETHOD);
            Double pI_sill = calculator_iterative.computePI(sequence);

            calculator_iterative.setPKIterative(IterativepI.PATRICKIOS_PKMETHOD);
            Double pI_pat = calculator_iterative.computePI(sequence);

            calculator_iterative.setPKIterative(IterativepI.LEHNINGER_PKMETHOD);
            Double pI_leh = calculator_iterative.computePI(sequence);

            calculator_iterative.setPKIterative(IterativepI.EMBOSS_PKMETHOD);
            Double pI_emb = calculator_iterative.computePI(sequence);

            calculator_iterative.setPKIterative(IterativepI.GRIMSLEY_PKMETHOD);
            Double pI_gri = calculator_iterative.computePI(sequence);

            calculator_iterative.setPKIterative(IterativepI.DTASelect_PKMETHOD);
            Double pI_rich = calculator_iterative.computePI(sequence);

            calculator_iterative.setPKIterative(IterativepI.RODWELL_PKMETHOD);
            Double pI_rodw = calculator_iterative.computePI(sequence);

            calculator_iterative.setPKIterative(IterativepI.SOLOMON_PKMETHOD);
            Double pI_sol = calculator_iterative.computePI(sequence);

            calculator_iterative.setPKIterative(IterativepI.THURLKILL_PKMETHOD);
            Double pI_thu = calculator_iterative.computePI(sequence);

            calculator_iterative.setPKIterative(IterativepI.TOSELAND_PKMETHOD);
            Double pI_tos = calculator_iterative.computePI(sequence);

            calculator_bjell.setPkMethod(BjellpI.BJELL_PKMETHOD);
            Double pI_bjell_default = calculator_bjell.computePI(sequence);

            calculator_bjell.setPkMethod(BjellpI.SKOOG_PKMETHOD);
            Double pI_bjell_skood = calculator_bjell.computePI(sequence);

            calculator_bjell.setPkMethod(BjellpI.EXPASY_PKMETHOD);
            Double pI_bjell_expasy = calculator_bjell.computePI(sequence);

            calculator_bjell.setPkMethod(BjellpI.CALIBRATED_PKMETHOD);
            Double pI_bjell_calib = calculator_bjell.computePI(sequence);

            Double pI_svm = calculator_svm.computePI(sequence);

            Double pI_cofactor = calculator_cofactor.computePI(sequence);


            //printing all pI computed
            System.out.println(protein.getProteinID() + "\t" +protein.getpIexperimental() + "\t" + df.format(pI_sill) + "\t" + df.format(pI_pat) +
                    "\t" + df.format(pI_leh) + "\t" + df.format(pI_emb) + "\t" + df.format(pI_gri) +
                    "\t" + df.format(pI_rich) + "\t" + df.format(pI_rodw) + "\t" + df.format(pI_sol) +
                    "\t" + df.format(pI_thu) + "\t" + df.format(pI_tos) + "\t" + df.format(pI_bjell_default) +
                    "\t" + df.format(pI_bjell_skood) + "\t" + df.format(pI_bjell_expasy) + "\t" + df.format(pI_bjell_calib)+
                    "\t" + df.format(pI_svm) + "\t" + df.format(pI_cofactor));
        }
    }

    @Before
    public void setUp() throws Exception {

        //Initializing Iterative method
        calculator_iterative = IterativepI.getInstance(IterativepI.SILLERO_PKMETHOD, 7.0);

        //Initializing Bjell method
        calculator_bjell = BjellpI.getInstance(BjellpI.CALIBRATED_PKMETHOD, 4.8);

        //Initializing Cofactor method
        calculator_cofactor = CofactorAdjacentpI.getInstance();

        //Building and training pI-SVM model for PIPDB data...
       /* URL url = SvmpITest.class.getClassLoader().getResource("PIPDB.txt");
        if (url == null) {
            throw new IllegalStateException("no file for input found!");
        }

        File inputFile = new File(url.toURI());
        Scanner scanner = new Scanner(inputFile);
        mapSequences = new HashMap<List<AminoAcid>, Double>();

        List<ProteinPIDB> proteinGroup = new ArrayList<ProteinPIDB>();
        List<String> accessions = new ArrayList<String>();

        while(scanner.hasNextLine()){
            String stringLine = scanner.nextLine();
            StringTokenizer token = new StringTokenizer(stringLine, "\t");
            String pIexperimental = token.nextToken();
            String sequence = token.nextToken();
            String proteinID = token.nextToken();
            if(pIexperimental!=null && sequence!=null && proteinID!=null){
                accessions.add(proteinID);
                proteinGroup.add(new ProteinPIDB(sequence,   proteinID, pIexperimental, "0.0"));
            }
        }

        //getting duplicate protein in database
        HashSet set = new HashSet();
        List<String> duplicateSet = new ArrayList<String>();
        for (String acc: accessions) {
            boolean val = set.add(acc);
            if (val == false) {
                duplicateSet.add(acc);
            }
        }


        for(ProteinPIDB protein:proteinGroup){

            //avoiding duplicate protein in training dataset
            if(duplicateSet.contains(protein.getHeaderForEntry())){
                continue;
            }

            List<AminoAcid> sequence = new ArrayList<AminoAcid>();
            String temp = protein.getSequenceProtein();

            for(Character character: temp.toCharArray()){
                if(character.equals('X')){
                    continue;     //Avoiding not valid character in sequence
                }
                sequence.add(AminoAcid.getMolecule(character));   //Building Amino Acid sequence
            }

            List<List<AminoAcid>> sequences = new ArrayList<List<AminoAcid>>();
            sequences.add(sequence);
            mapSequences.put(sequence, Double.parseDouble(protein.getpIexperimental()));
        }

        calculator_svm = SvmpI.getInstance(mapSequences, false);*/


        URL url = SvmpITest.class.getClassLoader().getResource("GauciDataNon-modified.csv");
        if (url == null) {
            throw new IllegalStateException("no file for input found!");
        }
        File inputFile = new File(url.toURI());
        Scanner scanner = new Scanner(inputFile);
        mapSequences = new HashMap<List<AminoAcid>, Double>();

        //first use a Scanner to get each line
        while ( scanner.hasNextLine() ){
            String stringLine = scanner.nextLine();
            if (stringLine != null) {
                stringLine.trim();
                String[] attr = stringLine.split(",");
                List<AminoAcid> sequence = new ArrayList<AminoAcid>();
                for(Character character: attr[0].toCharArray()) sequence.add(AminoAcid.getMolecule(character));
                mapSequences.put(sequence, Double.parseDouble(attr[1]));
            }

        }
        calculator_svm = SvmpI.getInstance(mapSequences, false);
    }

    @After
    public void tearDown() throws Exception {
    }
}
