package uk.ac.ebi.pride.sequence.isoelectricpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.mol.AminoAcid;
import uk.ac.ebi.pride.sequence.isoelectricpoint.bjellpI.BjellpI;
import uk.ac.ebi.pride.sequence.isoelectricpoint.svmpI.SvmpI;
import uk.ac.ebi.pride.sequence.utils.SequenceUtils;
import weka.experiment.PairedStats;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: yperez, enriquea
 * Date: 6/11/15
 * Time: 11:46 PM
 * To change this template use File | Settings | File Templates.
 */

public class SvmpITest {

    SvmpI calculator = null;
    BjellpI calculatorBjell = null;
    private HashMap<List<AminoAcid>, Double> mapSequences;

    /*
    * This method compute the isoelectric point for peptides sequences contained in a input file.
    * It use the SVM algorithm. By default, the training dataset is got
    * from input sequences and pI experimental values file. The algorithm use an iterative approach (15-fold)
    * to find the best classifier based on the least RMSE. This process could be time-consuming.
    * */
    @Test
    public void testComputePIGroup() throws Exception {

        /*List<AminoAcid> sequence = new ArrayList<AminoAcid>();
        String temp = "EYQLNDSASYYLNDLDR";
        for(Character character: temp.toCharArray()) sequence.add(AminoAcid.getAminoAcid(character));

        List<List<AminoAcid>> sequences = new ArrayList<List<AminoAcid>>();
        sequences.add(sequence);*/

        DecimalFormat df = new DecimalFormat("#.###");

        double[] svmpIs   = new double[mapSequences.size()];
        double[] bjellpIs = new double[mapSequences.size()];
        double[] exppI    = new double[mapSequences.size()];
        int i = 0;
        for(List<AminoAcid> sequence: mapSequences.keySet()){
            Double svmPI = calculator.computePI(sequence);
            Double bjellPI = calculatorBjell.computePI(sequence);
            svmpIs[i] = svmPI;
            bjellpIs[i] = bjellPI;
            exppI[i]    = mapSequences.get(sequence);
            //Printing sequence + pIexperimental + pIpredicted
            System.out.println(SequenceUtils.convertAminoAcidListToString(sequence) + "\t" + exppI[i]  + "\t" + df.format(svmPI));
            i++;

        }

        PairedStats bjellStats = new PairedStats(1.0);
        bjellStats.add(bjellpIs,exppI);
        bjellStats.calculateDerived();
        double stDBjell = bjellStats.differencesStats.stdDev;
        double correlationBjel = bjellStats.correlation;

        PairedStats svmStats = new PairedStats(1.0);
        svmStats.add(svmpIs,exppI);
        svmStats.calculateDerived();
        double sTDSVM = svmStats.differencesStats.stdDev;
        double correlationSVM = svmStats.correlation;



        assertTrue("Standard deviation should be less for SVM ", sTDSVM < stDBjell);
        assertTrue("Correlation should be better for SVM ", correlationSVM > correlationBjel);


    }

    @Before
    public void setUp() throws Exception {
        URL url = SvmpITest.class.getClassLoader().getResource("HellerDataSet.csv");
        if (url == null) {
            throw new IllegalStateException("no file for input found!");
        }
        File inputFile = new File(url.toURI());
        Scanner scanner = new Scanner(inputFile);
        mapSequences = new HashMap<List<AminoAcid>, Double>();
        //first use a Scanner to get each line
        while ( scanner.hasNextLine() ){
            String stringLine = scanner.nextLine();
            stringLine.trim();
            String[] attr = stringLine.split(",");
            List<AminoAcid> sequence = new ArrayList<AminoAcid>();
            for(Character character: attr[0].toCharArray()) sequence.add(AminoAcid.getAminoAcid(character));
            mapSequences.put(sequence, Double.parseDouble(attr[1]));
        }
        calculator = SvmpI.getInstance(mapSequences, false);
        calculatorBjell = BjellpI.getInstance(BjellpI.EXPASY_PKMETHOD,7.0);
    }

    @Test
    public void testSetDefaultSVM() throws Exception {
        SvmpI calculatorDefault = SvmpI.getInstance(null,true);

        double[] svmpIs   = new double[mapSequences.size()];
        double[] bjellpIs = new double[mapSequences.size()];
        double[] exppI    = new double[mapSequences.size()];
        int i = 0;
        for(List<AminoAcid> sequence: mapSequences.keySet()){
            Double svmPI = calculatorDefault.computePI(sequence);
            Double bjellPI = calculatorBjell.computePI(sequence);
            svmpIs[i] = svmPI;
            bjellpIs[i] = bjellPI;
            exppI[i]    = mapSequences.get(sequence);
            i++;
        }
        PairedStats bjellStats = new PairedStats(1.0);
        bjellStats.add(bjellpIs,exppI);
        bjellStats.calculateDerived();
        double stDBjell = bjellStats.differencesStats.stdDev;
        double correlationBjel = bjellStats.correlation;

        PairedStats svmStats = new PairedStats(1.0);
        svmStats.add(svmpIs,exppI);
        svmStats.calculateDerived();
        double sTDSVM = svmStats.differencesStats.stdDev;
        double correlationSVM = svmStats.correlation;



        assertTrue("Standard deviation should be less for SVM ", sTDSVM < stDBjell);
        assertTrue("Correlation should be better for SVM ", correlationSVM > correlationBjel);
    }

    @After
    public void tearDown() throws Exception {
    }
}
