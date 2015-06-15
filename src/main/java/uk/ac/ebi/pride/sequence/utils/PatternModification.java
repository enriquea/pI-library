package uk.ac.ebi.pride.sequence.utils;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: enrique
 * Date: 9/11/13
 * Time: 23:41
 * To change this template use File | Settings | File Templates.
 */

     //Class with fields for building patterns representing user modification symbol (UNIMOD code).
public class PatternModification {

      public static final Pattern ACETYLATION = Pattern.compile("Unimod:1");

      public static final Pattern PHOSPHORYLATION = Pattern.compile("Unimod:21");

      public static final Pattern OXIDATION = Pattern.compile("Unimod:35");
}
