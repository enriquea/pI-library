package uk.ac.ebi.pride.sequence.utils;

import uk.ac.ebi.pride.mol.AminoAcid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yperez
 * Date: 8/20/13
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class SequenceUtils {

    public static List<AminoAcid> getSequence(String sequence){
        List<AminoAcid> sequenceList = new ArrayList<AminoAcid>();
        for(Character character: sequence.toCharArray()) sequenceList.add(AminoAcid.getAminoAcid(character));
        return sequenceList;
    }

    public static List<AminoAcid> getCommonAminoAcids(List<AminoAcid> v1, List<AminoAcid> v2){
        List<AminoAcid> resultList = new ArrayList<AminoAcid>();
        for(AminoAcid aminoAcid: v1)
            if(v2.contains(v1)) resultList.add(aminoAcid);
        return resultList;
    }

    /*
     * Calculate the number of a certain type of aa. The set gives the type of these amino acids
     * (for example, the type could be the list "A", "I", "L", "M", "F", "W", "Y", "V", "C" for
     * hydrophobic aa)
     * */
    public static Integer numberTypeAA(List<AminoAcid> sequence, List<AminoAcid> aaTypes) {
        Integer occurrences = 0;
        for(AminoAcid aminoAcid: sequence)
            if(aaTypes.contains(aminoAcid)) occurrences++;
        return occurrences;
    }

    public static Integer numberAAOccurrences(List<AminoAcid> sequence, AminoAcid aminoAcid){
        Integer occurrences = 0;
        for(AminoAcid aminoAcidType: sequence)
            if(aminoAcidType == aminoAcid) occurrences++;
        return occurrences;
    }

    //getting String representation from AminoAcid List structure
    public static String convertAminoAcidListToString(List<AminoAcid> sequence){
        StringBuilder str_sequence = new StringBuilder();
        for (AminoAcid aa:sequence) {
            str_sequence.append(aa.getOneLetterCode());
        }
        return str_sequence.toString();
    }

}
