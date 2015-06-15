[sequence-features](Web site)
======

A Java Library for computing physic-chemical properties from amino acid sequence such as retention time and isoelectric point. For computing the isoelectric point of peptides and proteins, it is include some isoelectric point prediction methods such as Bjellvsquit, Iterative, Cofactor and pI-SVM. Each method have available different settings options (i.e. pK sets or factors correction) where required.


### How to use
The current version have available several test methods that shows the way to compute the isoelectric point from single sequence or multiple sequences (i.e. from sequences file) for each pI prediction method implemented. Different sequences files are included into resource folder for test.


### Example

         //Computing isoelectric point with Iterative method (pKset = "SILLERO")

         List<AminoAcid> sequence = new ArrayList<AminoAcid>();
         
         String temp = "EYQLNDSASYYLNDLDR";
         
         for(Character character: temp.toCharArray()) sequence.add(AminoAcid.getAminoAcid(character));

         IterativepI calculator = IterativepI.getInstance(IterativepI.SILLERO_PKMETHOD, 7.0);
         
         Double sequencesPI = calculator.computePI(sequence);

         assertTrue("Isoelectric Point equal to ", sequencesPI == 3.5011146068573);


### How to cite
* Enrique Audain, Yassel Ramos, Henning Hermjakob, Darren R. Flower and Yasset Perez-Riverol (2015). Comparative Benchmarking Methods Estimating Isoelectric Point (pI) of Peptide and Proteins from Amino Acid Sequence. Summited to Bioinformatics.   
  


   
