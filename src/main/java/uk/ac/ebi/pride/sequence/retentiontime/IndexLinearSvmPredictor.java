package uk.ac.ebi.pride.sequence.retentiontime;

import uk.ac.ebi.pride.mol.AminoAcid;
import uk.ac.ebi.pride.sequence.utils.SequenceUtils;
import uk.ac.ebi.pride.sequence.utils.svm.HeaderSvmConstants;
import uk.ac.ebi.pride.sequence.utils.svm.SvmClassifier;
import uk.ac.ebi.pride.sequence.utils.svm.SvmKernel;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: yperez
 * Date: 8/23/13
 * Time: 11:55 AM
 */
public class IndexLinearSvmPredictor {

    private static volatile IndexLinearSvmPredictor instance = null;

    private static double rmseMin          = 14.0;

    private static int countModels         = 15;

    private static double gamma = 0.5;

    private static int cache               = 100000;    //parameter defined

    private static String[] options        = { "-L 1e-03", "-W 1", "-P 1e-12", "-T 1e-03" , "-V"};

    private static SvmClassifier currentClassifier = null;

    private List<AminoAcid> AAList = RetentionTimeFeature.AAList;

    public static IndexLinearSvmPredictor getInstance(Map<List<AminoAcid>, Double> sequences, boolean defaultSvm) throws Exception {
        if (instance == null) {
            synchronized (IndexLinearSvmPredictor .class){
                if (instance == null) {
                    instance = new IndexLinearSvmPredictor(sequences, defaultSvm);
                }
            }
        }
        return instance;
    }

    private IndexLinearSvmPredictor(Map<List<AminoAcid>, Double> sequences, boolean defaultSvm) throws Exception {
        if(!defaultSvm && sequences != null){
            BuilRtLinearDataSet RtLinearDataSet = new BuilRtLinearDataSet(sequences);
            currentClassifier = getBestClassifier(RtLinearDataSet);
        }else{
            URL url = IndexLinearSvmPredictor.class.getClassLoader().getResource("BetaHeLaLinearV2Train.csv");
            if (url == null) {
                throw new IllegalStateException("no file for input found!");
            }
            File inputFile = new File(url.toURI());
            sequences = new HashMap<List<AminoAcid>, Double>();
            Scanner scanner = new Scanner(inputFile);
            //first use a Scanner to get each line
            while (scanner.hasNextLine() ){
                String stringLine = scanner.nextLine();
                getSequenceMapFromString(stringLine,sequences);
            }
            BuilRtLinearDataSet RtLinearDataSet = new BuilRtLinearDataSet(sequences);
            currentClassifier = getBestClassifier(RtLinearDataSet);
        }
    }

    public double [] getCoefficients(){
        double[] coefficients = currentClassifier.getCoefficients();
       return coefficients;
    }

    /*
     * This method compute the isoelectric point for a given sequence. It use the SVM current machine in memory.
     * The method constructs a weka instance of a peptide and then compute the pI.
     *
     * @param sequence The sequence of the peptide to compute the isoelectric point.
     * @return isoelectric point
     * */

    /*
     * This method construct five classifiers using the training dataset and storage
     * the classifiers using Serialization file. Each classifier is evaluated using a test
     * dataset and the classifier with a better standard deviation is selected as the best
     * classifier.
     *
     * @param data The Dataset for classifier training.
     * @return SvmClassifier Best Classifier
     */

    public SvmClassifier getBestClassifier (BuilRtLinearDataSet RtLinearDataSet) throws Exception {

        SvmClassifier svm;

        for(int i = 0; i < countModels; i++){

            RtLinearDataSet.randomizeDataSet();
            /*Randomize the dataset each time that a new Classifier is created and Study*/

            Instances train = RtLinearDataSet.getTrainDataset(5,0);
            Instances test  = RtLinearDataSet.getTestDataset(5,0);
            /* Divide the dataset in two subsets: training and test datasets*/

            test.setClassIndex(test.numAttributes() - 1);
            train.setClassIndex(train.numAttributes() - 1);
            /* Setting class attribute for test and training datasets*/

            PolyKernel linearKernel = SvmKernel.getInstanceLinearKernel(train, cache);
            /* Build a new Radial Kernel*/

            svm = new SvmClassifier(train, options, linearKernel);
            /* Create a new SvmClassifier using the kernel, options and the train dataset*/

            double svmRmsd = svm.evaluateRMSE(test);
            /*Compute the rmsd for the svm and take the */
            System.out.println(svmRmsd);


            if( svmRmsd < rmseMin){
                rmseMin = svmRmsd;
                currentClassifier = svm;
            }
        }
       return currentClassifier;
    }

    private static Map<List<AminoAcid>,Double> getSequenceMapFromString(String line, Map<List<AminoAcid>,Double> mapSequences){
        line.trim();
        String[] attr = line.split(",");
        List<AminoAcid> sequence = new ArrayList<AminoAcid>();
        for(Character character: attr[0].toCharArray()) sequence.add(AminoAcid.getAminoAcid(character));
        mapSequences.put(sequence,Double.parseDouble(attr[1]));
        return mapSequences;
    }


    public class BuilRtLinearDataSet{

        private Instances dataset;

        private FastVector atts;

        /*
         * Constructor
         */
        public BuilRtLinearDataSet(String namedata, int size){
            atts = getAtts();
            this.dataset = new Instances(namedata, atts, size);
        }

        public BuilRtLinearDataSet(Map<List<AminoAcid>, Double> sequences){
            atts = getAtts();
            /*Set the FVector attributes of the BuildPIDataSet*/
            this.dataset = new Instances(HeaderSvmConstants.DEFAULT_DATA_NAME, atts, sequences.size());

            List<List<Double>> sequenceDescriptor = new ArrayList<List<Double>>();
            for(List<AminoAcid> sequence: sequences.keySet()){

            }

            for(List<AminoAcid> sequence: sequences.keySet()){
                add(sequence, sequences.get(sequence));
            }
        }

        public void add(List<AminoAcid> sequence, double exppI){
            dataset.add(getInstance(sequence, exppI));
            /* Adding a new instance to the instances of the dataset*/
        }

        public Instance getInstance(List<AminoAcid> sequence, double exppI){
            double[] valuesDescriptors = getValuesFromSequence(sequence);
            double[] values = new double[atts.size()];
            values[atts.size()-1] = exppI;
            return new Instance(1.0, values);
        }

        private double[] getValuesFromSequence(List<AminoAcid> sequence){
            double[] values = new double[atts.size()-1];
            for(int i = 0; i < AAList.size(); i++){
                double occurrences = SequenceUtils.numberAAOccurrences(sequence, AAList.get(i));
                values[i] = occurrences;
            }
            return values;
        }

        //This method return traninig dataset with croos validation approach
        public Instances getTrainDataset (int folds, int fold){
            return dataset.trainCV(folds, fold);

        }
        //This method return testing dataset with croos validation approach
        public Instances getTestDataset (int folds, int fold){
            return dataset.testCV(folds, fold);

        }

        //This method randomize the full datset using Random instance
        public void randomizeDataSet(){
            Random ran = new Random();  //argument could be System.currentTimeMillis()
            this.dataset.randomize(ran);
        }

        public FastVector getAtts(){
            FastVector localAtts = new FastVector(AAList.size());
            for(AminoAcid aminoAcid: AAList){
                localAtts.addElement(new Attribute(HeaderSvmConstants.AA_NAME_HEADER + aminoAcid.getOneLetterCode()));
            }
            localAtts.addElement(new Attribute(HeaderSvmConstants.CLASS_INSTANCE_HEADER));      //class attribute (pI experimental)
            return localAtts;
        }

        public Instance getEvaluateInstance(List<AminoAcid> sequence, double exppI){
            Instance evalInstance = getInstance(sequence,exppI);
            Instances instances = new Instances(HeaderSvmConstants.DEFAULT_DATA_NAME,atts,atts.size());
            instances.setClassIndex(atts.size()-1);
            evalInstance.setDataset(instances);
            return evalInstance;
        }

    }


}
