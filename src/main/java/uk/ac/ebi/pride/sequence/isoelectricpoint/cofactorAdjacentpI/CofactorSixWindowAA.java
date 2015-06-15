package uk.ac.ebi.pride.sequence.isoelectricpoint.cofactorAdjacentpI;

import uk.ac.ebi.pride.mol.AminoAcid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: enrique
 * Date: 5/12/13
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */


public class CofactorSixWindowAA {

      private static final Map<String,String> pKconstans = new HashMap<String, String>();

      private static volatile CofactorSixWindowAA instance = null;

      private static char Nterm = 'n';

      private static char Cterm = 'c';

      private Double factor_correction;

      private static Map<AminoAcid,Double>  Cterm_Pk     = new HashMap<AminoAcid, Double>(); // pk at CTerm position

      private static Map<AminoAcid,Double>  Nterm_Pk     = new HashMap<AminoAcid, Double>(); // pk at the NTerm position

      private static Map<AminoAcid, Double> sideGroup_Pk = new HashMap<AminoAcid, Double>(); // in oder position

      private static final double PH_Adjust = 7.00;


      public static CofactorSixWindowAA getInstance() throws Exception {
        if (instance == null) {
            synchronized (CofactorAdjacentpI .class){
                if (instance == null) {
                    instance = new CofactorSixWindowAA();
                }
            }
         }
        return instance;
     }

     /*
     * Constructor of CofactorSixWindowAA needs to load into pKconstans Map the pk values from resources
     * in the sequence.
     */
     private CofactorSixWindowAA() throws Exception {
         URL url = CofactorSixWindowAA.class.getClassLoader().getResource("predpi_pKconstants-plain_20120430.txt");
         if (url == null) {
             throw new IllegalStateException("The pK constants file was not found!!");
         }

         File inputFile = new File(url.toURI());
         loadPkConstantsFromFile(inputFile);
         initMap();
     }


     private void loadPkConstantsFromFile (File inputFile) throws Exception {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));

         String line = null;
         String [] value_key;
        while ((line = bufferedReader.readLine()) != null) {
            value_key = line.split("=");
            pKconstans.put(value_key[0], value_key[1]);
        }
        //System.out.println(pKconstans.size());
     }


    //This methods compute the factor correction for pK0 of any ionizable group
    private static double computeFactorCorrection (List<AminoAcid> sequence, Character currentIonizableGroup, Integer position, double pH){
        double  factorCorrection = 0.0;
        HashMap<Integer, Character> ionizableGroupTable = getIonizableGroupDistances (sequence, currentIonizableGroup, position);
        Set<Integer> distances = ionizableGroupTable.keySet();
        for(Integer d : distances){
            factorCorrection += computeFractionCharge(ionizableGroupTable.get(d), pH)*getDpkConstant(currentIonizableGroup, ionizableGroupTable.get(d), d);
        }

       return factorCorrection;
    }


     //The pK value are refers to pK0.
    private static double computeFractionCharge (Character AA, double pH) {

         // pH = 7.0;

         if (AA == 'D') return  1.0 / (1.0 + Math.pow(10, (3.96 - pH)));


         if (AA == 'E') return  1.0 / (1.0 + Math.pow(10, (4.45 - pH)));


         if (AA == 'H') return  -1.0 / (1.0 + Math.pow(10, (pH - 5.9)));


         if (AA == 'K') return  -1.0 / (1.0 + Math.pow(10, (pH - 9.8)));


         if (AA == 'R') return  -1.0 / (1.0 + Math.pow(10, (pH - 10.8)));

         else return 0.0;

    }


    private static String buildKeyMap (Character AA, Character neighborAA, Integer d){
      return "dpK(".concat(AA.toString()).concat(",").concat(neighborAA.toString()).concat(",").concat(d.toString()).concat(")");
    }


    //getting dpK constant from Map
    public static double getDpkConstant (Character currentAA, Character neighborAA, Integer distance){

        String dpK = pKconstans.get(buildKeyMap(currentAA, neighborAA, distance));

         if (!dpK.contains("d")){
            return Double.parseDouble(dpK);
         }

         if (dpK.contains("d") && dpK.startsWith("-")){
             return - Double.parseDouble(pKconstans.get(dpK.replaceFirst("-","")));
         }

         if (dpK.contains("d") && !dpK.startsWith("-")){
            return  Double.parseDouble(pKconstans.get(dpK));
         }

      return 0.0;
   }



   public static HashMap<Integer, Character> getIonizableGroupDistances (List<AminoAcid> sequence, Character currentAA, Integer position){

        HashMap<Integer, Character> ionizableGroupTable = new HashMap<Integer, Character>();

        for (int i = 0; i < sequence.size(); i++){

            int distance = i - position;

            if (distance >= -6 && distance <= 6 && distance != 0) {

                if (sequence.get(i).getOneLetterCode() == 'D') ionizableGroupTable.put(distance, 'D');

                if (sequence.get(i).getOneLetterCode() == 'E') ionizableGroupTable.put(distance, 'E');

                if (sequence.get(i).getOneLetterCode() == 'H') ionizableGroupTable.put(distance, 'H');

                if (sequence.get(i).getOneLetterCode() == 'K') ionizableGroupTable.put(distance, 'K');

                if (sequence.get(i).getOneLetterCode() == 'R') ionizableGroupTable.put(distance, 'R');

            }

       }

       return ionizableGroupTable;
   }


    private static double getNtermPk0 (Character AA){
        return 0.0;
    }


    private static double getCtermPk0 (Character AA){
        return 0.0;
    }


    private static double computePkOptimized (List<AminoAcid> sequence, Character currentIonizableGroup,  Integer position, double pH){

        double pK0;

        if (position == 0) pK0 = Nterm_Pk.get(AminoAcid.getAminoAcid(currentIonizableGroup));

        else if (position == sequence.size() - 1) pK0 = Cterm_Pk.get(AminoAcid.getAminoAcid(currentIonizableGroup));

        else pK0 = sideGroup_Pk.get(AminoAcid.getAminoAcid(currentIonizableGroup));

     return  pK0 + computeFactorCorrection(sequence, currentIonizableGroup, position, pH);
   }


    public static double computePI (List<AminoAcid> sequence){

        double PI;
        double NET_CHARGE = 0.0;
        double pH = 6.5;             //starting point pI = 6.5 - theoretically it should be 7, but
                                     //average protein pI is 6.5 so we increase the probability
        double pHprev = 0.0;         //of finding the solution
        double pHnext = 14.0;        //0-14 is possible pH range
        double E = 0.001;             //epsilon means precision [pI = pH ± E]
        double temp = 0.0;
        double QN;
        double QP;


        while ((pH-pHprev > E) && (pHnext-pH > E)){

            QP =+ 1.0 / (1.0 + Math.pow(10, (pH - Nterm_Pk.get(sequence.get(0)))));

            QN =+ -1.0 / (1.0 + Math.pow(10, (Cterm_Pk.get(sequence.get(sequence.size()-1)) - pH)));

        for (int i = 1; i < sequence.size() - 1; i++){

            if (sequence.get(i).getOneLetterCode() == 'D') QN =+ -1.0 / (1.0 + Math.pow(10, (computePkOptimized(sequence, 'D', i, pH)-  pH)));


            if (sequence.get(i).getOneLetterCode() == 'E') QN =+ -1.0 / (1.0 + Math.pow(10, (computePkOptimized(sequence, 'E', i, pH) - pH)));


            if (sequence.get(i).getOneLetterCode() == 'H') QP =+  1.0 / (1.0 + Math.pow(10, (pH - computePkOptimized(sequence, 'H', i, pH))));  //computePkOptimized(sequence, 'H', i, pH)


            if (sequence.get(i).getOneLetterCode() == 'K') QP =+  1.0 / (1.0 + Math.pow(10, (pH - computePkOptimized(sequence, 'K', i, pH))));


            if (sequence.get(i).getOneLetterCode() == 'R') QP =+  1.0 / (1.0 + Math.pow(10, (pH - computePkOptimized(sequence, 'R', i, pH))));

        }

        NET_CHARGE = QN + QP;


            if(NET_CHARGE < 0)              //we are out of range, thus the new pH value must be smaller
            {
                temp = pH;
                pH = pH-((pH-pHprev)/2);
                pHnext = temp;
                System.out.println("pH: " +  pH + " pHnext: " + pHnext);

            }
            else                  //we used to small pH value, so we have to increase it
            {
                temp = pH;
                pH = pH + ((pHnext-pH)/2);
                pHprev = temp;
                System.out.println("pH: " + pH  + " pHprev: " + pHprev);

            }

            QN = 0.0;
            QP = 0.0;
        }

       PI = pH;
    return PI;
    }

    private static void initMap(){

            Cterm_Pk.clear();
            Nterm_Pk.clear();
            sideGroup_Pk.clear();

            Cterm_Pk.put(AminoAcid.A, Double.valueOf(3.75D));
            Cterm_Pk.put(AminoAcid.R, Double.valueOf(2.84D));
            Cterm_Pk.put(AminoAcid.N, Double.valueOf(3.64D));
            Cterm_Pk.put(AminoAcid.D, Double.valueOf(3.04D));
            Cterm_Pk.put(AminoAcid.C, Double.valueOf(3.10D));
            Cterm_Pk.put(AminoAcid.E, Double.valueOf(3.45D));
            Cterm_Pk.put(AminoAcid.Q, Double.valueOf(3.57D));
            Cterm_Pk.put(AminoAcid.G, Double.valueOf(3.70D));
            Cterm_Pk.put(AminoAcid.H, Double.valueOf(3.47D));
            Cterm_Pk.put(AminoAcid.I, Double.valueOf(3.72D));
            Cterm_Pk.put(AminoAcid.L, Double.valueOf(3.75D));
            Cterm_Pk.put(AminoAcid.K, Double.valueOf(2.892D));
            Cterm_Pk.put(AminoAcid.M, Double.valueOf(3.68D));
            Cterm_Pk.put(AminoAcid.F, Double.valueOf(3.98D));
            Cterm_Pk.put(AminoAcid.P, Double.valueOf(3.40D));
            Cterm_Pk.put(AminoAcid.S, Double.valueOf(3.61D));
            Cterm_Pk.put(AminoAcid.T, Double.valueOf(3.57D));
            Cterm_Pk.put(AminoAcid.W, Double.valueOf(3.78D));
            Cterm_Pk.put(AminoAcid.Y, Double.valueOf(3.6D));
            Cterm_Pk.put(AminoAcid.V, Double.valueOf(3.69D));

            Nterm_Pk.put(AminoAcid.A, Double.valueOf(7.5D));
            Nterm_Pk.put(AminoAcid.R, Double.valueOf(7.5D));
            Nterm_Pk.put(AminoAcid.N, Double.valueOf(7.22D));
            Nterm_Pk.put(AminoAcid.D, Double.valueOf(8.3D));
            Nterm_Pk.put(AminoAcid.C, Double.valueOf(8.12D));
            Nterm_Pk.put(AminoAcid.E, Double.valueOf(7.9D));
            Nterm_Pk.put(AminoAcid.Q, Double.valueOf(6.73D));
            Nterm_Pk.put(AminoAcid.G, Double.valueOf(7.5D));
            Nterm_Pk.put(AminoAcid.H, Double.valueOf(9.5D));
            Nterm_Pk.put(AminoAcid.I, Double.valueOf(7.46D));
            Nterm_Pk.put(AminoAcid.L, Double.valueOf(7.46D));
            Nterm_Pk.put(AminoAcid.K, Double.valueOf(7.5D));
            Nterm_Pk.put(AminoAcid.M, Double.valueOf(6.98D));
            Nterm_Pk.put(AminoAcid.F, Double.valueOf(6.96D));
            Nterm_Pk.put(AminoAcid.P, Double.valueOf(8.36D));
            Nterm_Pk.put(AminoAcid.S, Double.valueOf(6.86D));
            Nterm_Pk.put(AminoAcid.T, Double.valueOf(7.02D));
            Nterm_Pk.put(AminoAcid.W, Double.valueOf(7.11D));
            Nterm_Pk.put(AminoAcid.Y, Double.valueOf(6.83D));
            Nterm_Pk.put(AminoAcid.V, Double.valueOf(7.44D));

            sideGroup_Pk.put(AminoAcid.R, Double.valueOf(10.8D));
            sideGroup_Pk.put(AminoAcid.D, Double.valueOf(3.96D));
     //     sideGroup_Pk.put(AminoAcid.C, Double.valueOf(8.28D));
            sideGroup_Pk.put(AminoAcid.E, Double.valueOf(4.45D));
            sideGroup_Pk.put(AminoAcid.H, Double.valueOf(5.90D));
            sideGroup_Pk.put(AminoAcid.K, Double.valueOf(9.80D));
     //     sideGroup_Pk.put(AminoAcid.Y, Double.valueOf(9.84D));

    }
}
