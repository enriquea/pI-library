package uk.ac.ebi.pride.sequence.retentiontime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.mol.AminoAcid;
import uk.ac.ebi.pride.sequence.utils.SequenceUtils;

import java.text.DecimalFormat;
import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: yperez
 * Date: 8/20/13
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetentionTimeFeatureTest {

    RetentionTimeFeature calculator;
    @Before
    public void setUp() throws Exception {
        calculator = RetentionTimeFeature.getInstance();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetTotalNumberFeatures() throws Exception {

    }

    @Test
    public void testIndexSum() throws Exception {
        // the index does not include the modified peptide
        String sequence = "ASAY";
        double hydrophobicity_sum = calculator.indexSum(SequenceUtils.getSequence(sequence),RetentionTimeFeature.kKyteDoolittle);
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println( hydrophobicity_sum);
        assertTrue("The correct IndexSum for ASAY sequence should be 1.5", Double.parseDouble(df.format(hydrophobicity_sum)) == 1.5);
    }

    @Test
    public void testIndexAvg() throws Exception {
        String sequence = "ASAAY";
        double hydrophobicity_avg = calculator.indexAvg(SequenceUtils.getSequence(sequence),RetentionTimeFeature.kKyteDoolittle);
        assertTrue(3.3 / 5.0 == hydrophobicity_avg);
    }

    @Test
    public void testAvgHydrophobicityIndex() throws Exception {
        // no modified aa
        Map<AminoAcid, Double> idx = new HashMap<AminoAcid, Double>();
        idx.put(AminoAcid.A,1.0);
        idx.put(AminoAcid.D,5.0);
        idx.put(AminoAcid.H,2.0);
        idx.put(AminoAcid.R,3.0);
        double avg_hydrophobicity = calculator.avgHydrophobicityIndex(idx);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(2.75 == Double.parseDouble(df.format(avg_hydrophobicity)));
    }

    @Test
    public void testIndexNTerm() throws Exception {
        // no modified aa
        String sequence = "DAYY";
        double hydrophobicity_N = calculator.indexNTerm(SequenceUtils.getSequence(sequence),RetentionTimeFeature.kKyteDoolittle);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(-3.5 == Double.parseDouble(df.format(hydrophobicity_N)));
    }

    @Test
    public void testIndexCTerm() throws Exception {
        // no modified aa
        String sequence = "DAYY";
        double hydrophobicity_C = calculator.indexCTerm(SequenceUtils.getSequence(sequence), RetentionTimeFeature.kKyteDoolittle);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(-1.3 == Double.parseDouble(df.format(hydrophobicity_C)));
    }

    @Test
    public void testIndexSumSquaredDiff() throws Exception {
        Map<AminoAcid, Double> idx = new HashMap<AminoAcid, Double>();
        idx.put(AminoAcid.B,1.0);
        idx.put(AminoAcid.A,5.0);
        idx.put(AminoAcid.R,-2.0);
        idx.put(AminoAcid.D,3.0);

        String sequence = "ABAR";
        double sum_squared_diff = calculator.indexSumSquaredDiff(SequenceUtils.getSequence(sequence), idx);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(Double.parseDouble(df.format(sum_squared_diff)) == 81.0);
    }

    @Test
    public void testNumberConsecTypeAA() throws Exception {
        List<AminoAcid> typeAA  = new ArrayList<AminoAcid>(){{
            add(AminoAcid.A);
            add(AminoAcid.R);
            add(AminoAcid.I);
        }};

        String sequence = "EEKHPADDRAAVVIA";
        double occurences = calculator.numberConsecTypeAA(SequenceUtils.getSequence(sequence), typeAA);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(Double.parseDouble(df.format(occurences)) == 3.0);
    }

    @Test
    public void testIndexNearestNeigbour() throws Exception {
        // get the polar aa
        List<AminoAcid> polar_aa = (calculator.getExtremeRetentionAA(RetentionTimeFeature.kKyteDoolittle)).firstElement();
        // no modified aa
        String sequence = "KAYYKYCNCYYYRA";
        double sum_neighbour_pos = calculator.indexNearestNeigbour(SequenceUtils.getSequence(sequence), RetentionTimeFeature.kKyteDoolittle, polar_aa);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(Double.parseDouble(df.format(sum_neighbour_pos)) == 8.6);
    }

    @Test
    public void testIndexMaxPartialSum() throws Exception {
        // no modified aa
        String sequence = "DDDFFFDDDIIDDD";
        double max_partial_sum = calculator.indexMaxPartialSum(SequenceUtils.getSequence(sequence), RetentionTimeFeature.kKyteDoolittle, 2);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(Double.parseDouble(df.format(max_partial_sum)) == 9.0);
    }

    @Test
    public void testIndexMinPartialSum() throws Exception {
        // no modified aa
        String sequence = "DDDFFFDDDIIDDD";
        double min_partial_sum = calculator.indexMinPartialSum(SequenceUtils.getSequence(sequence),RetentionTimeFeature.kKyteDoolittle, 2);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(Double.parseDouble(df.format(min_partial_sum)) == -7.0);
    }

    @Test
    public void testIndexMaxHydrophobicSideHelix() throws Exception {
        Map<AminoAcid, Double> idx = new HashMap<AminoAcid, Double>();
        idx.put(AminoAcid.B,1.0);
        idx.put(AminoAcid.A,5.0);
        idx.put(AminoAcid.R,-2.0);
        idx.put(AminoAcid.D,3.0);
        idx.put(AminoAcid.Y,4.1);

        String sequence = "BDDAARBDDY";
        Double max_hydrophobic_side = calculator.indexMaxHydrophobicSideHelix(SequenceUtils.getSequence(sequence), idx);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(Double.parseDouble(df.format(max_hydrophobic_side)) == 11.06);
    }

    @Test
    public void testIndexMinHydrophobicSideHelix() throws Exception {
        Map<AminoAcid, Double> idx = new HashMap<AminoAcid, Double>();
        idx.put(AminoAcid.B,1.0);
        idx.put(AminoAcid.A,5.0);
        idx.put(AminoAcid.R,-2.0);
        idx.put(AminoAcid.D,3.0);
        idx.put(AminoAcid.Y,4.1);

        String sequence = "BDDAARBDDY";
        Double min_hydrophobic_side = calculator.indexMinHydrophobicSideHelix(SequenceUtils.getSequence(sequence), idx);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(Double.parseDouble(df.format(min_hydrophobic_side)) == 6.44);

    }

    @Test
    public void testIndexMaxHydrophobicMoment() throws Exception {
        Map<AminoAcid, Double> idx = new HashMap<AminoAcid, Double>();
        idx.put(AminoAcid.B,1.0);
        idx.put(AminoAcid.A,5.0);
        idx.put(AminoAcid.R,-2.0);
        idx.put(AminoAcid.D,3.0);
        idx.put(AminoAcid.Y,4.1);

        // peptide shorter than the window (assume w = 4), helix
        String sequence = "ABA";
        double max_hmoment = calculator.indexMaxHydrophobicMoment(SequenceUtils.getSequence(sequence),idx, 100.0, 4);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(Double.parseDouble(df.format(max_hmoment)) == 0.99);

        // peptide longer than the window (assume w = 2), beta sheet
        sequence = "ABD";
        max_hmoment = calculator.indexMaxHydrophobicMoment(SequenceUtils.getSequence(sequence), idx, 180.0, 2);
        assertTrue(Double.parseDouble(df.format(max_hmoment)) == 4.0);
    }

    @Test
    public void testIndexMinHydrophobicMoment() throws Exception {
        Map<AminoAcid, Double> idx = new HashMap<AminoAcid, Double>();
        idx.put(AminoAcid.B,1.0);
        idx.put(AminoAcid.A,5.0);
        idx.put(AminoAcid.R,-2.0);
        idx.put(AminoAcid.D,3.0);
        idx.put(AminoAcid.Y,4.1);


        // peptide shorter than the window (assume w = 4), helix
        String sequence = "AAB";
        double min_hmoment = calculator.indexMinHydrophobicMoment(SequenceUtils.getSequence(sequence), idx, 100.0, 4);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(Double.parseDouble(df.format(min_hmoment)) == 0.99);

        // peptide longer than the window (assume w = 2), beta sheet
        sequence = "BAD";
        min_hmoment = calculator.indexMinHydrophobicMoment(SequenceUtils.getSequence(sequence), idx, 180.0, 2);
        assertTrue(Double.parseDouble(df.format(min_hmoment)) == 2.0);
    }

    @Test
    public void testnumberTypeAA() throws Exception {
        List<AminoAcid> typeAA  = new ArrayList<AminoAcid>(){{
            add(AminoAcid.A);
            add(AminoAcid.R);
            add(AminoAcid.I);
        }};
        String sequence = "EEKHPADDRVVI";
        double occurences = SequenceUtils.numberTypeAA(SequenceUtils.getSequence(sequence), typeAA);
        DecimalFormat df = new DecimalFormat("#.##");
        assertTrue(Double.parseDouble(df.format(occurences)) == 3.0);

    }

    @Test
    public void testgetExtremeRetentionAA() throws  Exception{
        List<AminoAcid> lowest, highest;
        List<AminoAcid> exp_lowest = new ArrayList<AminoAcid>(){{
            add(AminoAcid.D);
            add(AminoAcid.R);
            add(AminoAcid.Q);
            add(AminoAcid.N);
            add(AminoAcid.K);
            add(AminoAcid.E);
        }};
        List<AminoAcid> exp_highest = new ArrayList<AminoAcid>(){{
            add(AminoAcid.C);
            add(AminoAcid.F);
            add(AminoAcid.I);
            add(AminoAcid.L);
            add(AminoAcid.V);
        }};

        Vector<List<AminoAcid>> set_pair = calculator.getExtremeRetentionAA(RetentionTimeFeature.kKyteDoolittle);
        lowest = set_pair.firstElement();
        assertTrue(lowest.size() == 6);
        Collections.sort(lowest);
        Collections.sort(exp_lowest);
        for(AminoAcid aminoAcid: exp_lowest){
            if(!lowest.contains(aminoAcid))
                assertTrue(false);
        }
        highest = set_pair.lastElement();
        assertTrue(highest.size() == 5);
        for(AminoAcid aminoAcid: exp_highest){
            if(!highest.contains(aminoAcid))
                assertTrue(false);
        }
    }
}
