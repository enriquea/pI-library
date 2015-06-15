package uk.ac.ebi.pride.sequence.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: enrique
 * Date: 10/11/13
 * Time: 3:29
 * To change this template use File | Settings | File Templates.
 */
public class SequenceConverterTest {

           SequenceConverter converter = new SequenceConverter();

    @Test
    public void testGetSequenceParsed() throws Exception {
                  String userSequence = "[Unimod:1].AL[Unimod:35]MPSTTK[Unimod:35]MLR.";
                  String seqParsed = converter.getSequenceParsed(userSequence);
                  System.out.println(seqParsed);

                 // assertTrue(seqParsed == "p.ALoMPSTTKoMLR.");
    }

    @Test
    public void testGetListSequenceParsed() throws Exception {

                 String userSeq01 = "[Unimod:1].AL[Unimod:35]MPSTTK[Unimod:35]MLR.";
                 String userSeq02 = "[Unimod:1].ATTK[Unimod:35]MLR.";
                 String userSeq03 = ".AL[Unimod:35]MPSTTK[Unimod:21]SMLR.";
                 String userSeq04 = "[Unimod:1].HL[Unimod:21]S[Unimod:21]SMPS[Unimod:35]MLR.";
                 String userSeq05 = ".HL[Unimod:21]S[Unimod:21]SMPSMLR[Unimod:35].";

                 List<String> sequencesUser = new ArrayList<String>();
                 sequencesUser.add(userSeq01);
                 sequencesUser.add(userSeq02);
                 sequencesUser.add(userSeq03);
                 sequencesUser.add(userSeq04);
                 sequencesUser.add(userSeq05);

                 List<String> sequencesProcessed = converter.getListSequenceParsed(sequencesUser);

                System.out.println(sequencesProcessed);

             //out> [a.ALoMPSTTKoMLR., a.ATTKoMLR., .ALoMPSTTKpSMLR., a.HLpSpSMPSoMLR., .HLpSpSMPSMLRo.]
    }
}
