package uk.ac.ebi.pride.sequence.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created with IntelliJ IDEA.
 * User: enrique
 * Date: 10/11/13
 * Time: 1:17
 * To change this template use File | Settings | File Templates.
 */
public class SequenceConverter {

        //field
       // private static  String sequenceUser;

       // private static  String sequenceParsed;

        private static final String BRACKETS = "\\[|\\]";

        private static Matcher acetylation;

        private static  Matcher phosphorylation;

        private static  Matcher oxidation;

        //constructor
        public SequenceConverter (){ }

        //methods
         private void initModificationMatcher(String sequenceUser){

             this.acetylation =  PatternModification.ACETYLATION.matcher(sequenceUser);
             this.phosphorylation  = PatternModification.PHOSPHORYLATION.matcher(sequenceUser);
             this.oxidation  = PatternModification.OXIDATION.matcher(sequenceUser);

         }


         public String getSequenceParsed(String sequenceUser){

             this.initModificationMatcher(sequenceUser);

             if(acetylation.find())
                 sequenceUser = sequenceUser.replaceAll("Unimod:1", "a");


             if (phosphorylation.find())
                 sequenceUser = sequenceUser.replaceAll("Unimod:21", "p");


             if (oxidation.find())
                 sequenceUser = sequenceUser.replaceAll("Unimod:35", "o");

            return sequenceUser.replaceAll(BRACKETS, "");
         }


         public List<String> getListSequenceParsed(List<String> sequences){
            List<String> sequences_processed = new ArrayList <String> ();
             for (String s : sequences){
               sequences_processed.add(this.getSequenceParsed(s));
             }
           return sequences_processed;
         }
}
