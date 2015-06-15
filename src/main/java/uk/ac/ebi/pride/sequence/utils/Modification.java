package uk.ac.ebi.pride.sequence.utils;

import uk.ac.ebi.pride.mol.Mass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: enrique
 * Date: 7/11/13
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */
  public enum Modification implements Mass {

    a("Acetylation"                 , 1    , "Acetyl"       , 42.010565  , 42.0367),
    p("Phosphorylation"             , 21   , "Phospho"      , 79.966331  , 79.9799),
    o("Oxidation or Hydroxylation"  , 35   , "Oxidation"    , 15.994915  , 15.9994);

    //field
    private final String full_name;
    private final Integer record_id;
    private final String ex_code_name;
    private final double mono_mass;
    private final double avge_mass;

    //Map for to match the modification format user entry with one-letter code
    private static final Map<String, Character> MODIFICATION_SYMBOL;
    static
    {
        MODIFICATION_SYMBOL = new HashMap<String, Character> ();
        MODIFICATION_SYMBOL.put("[Unimod:1]", 'a');
        MODIFICATION_SYMBOL.put("[Unimod:21]", 'p');
        MODIFICATION_SYMBOL.put("[Unimod:35]", 'o');
    }


    //constructor
    private Modification(String full_name, Integer record_id,  String ex_code_name,
                         double monoMass, double avgMass) {

        this.full_name= full_name;
        this.record_id = record_id;
        this.ex_code_name = ex_code_name;
        this.mono_mass = monoMass;
        this.avge_mass = avgMass;
    }

    //methods
    public String getFullname() {
        return full_name;
    }

    public Integer getRecordId() {
        return record_id;
    }

    public String getCodeName() {
        return ex_code_name;
    }

    public double getMonoMass() {
        return mono_mass;
    }

    public double getAvgMass() {
        return avge_mass;
    }

    public static Modification getModification(char modificationIdentifier){
        switch( modificationIdentifier ) {
            case 'a':
                return Modification.a;
            case 'p':
                return Modification.p;
            case 'o':
                return Modification.o;
            default:
                return null;
        }
    }

}
