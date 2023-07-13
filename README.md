# BINF8500

All projects in this repository were used for the algorithms course at UGA
This repository contains implementations of various algorithms and methods used in bioinformatics. Each directory contains file with a different functionality for analyzing biological data. Below is a brief description of each file:

## Sort_algorithm/Quick_sort.java

The Quick_sort.java file implements the quicksort algorithm to sort a Fasta file in alphabetical order using an ArrayList. In addition to sorting the strings, this implementation includes an Index_String class, where each object contains an index and a string. By sorting the Index_String objects, we can determine the position of each string after sorting.

This implementation uses Lomuto's partition method for the quicksort algorithm. It can be particularly useful for organizing sequence data or any other information in a Fasta file

## Kmeans/Kmeans_algorithm.java

The Kmeans_algorithm.java file implements the K-means clustering algorithm for unsupervised clustering of bacterial and archeal genomes. The clustering is performed based on the normalized amino acid compositions of the genomes. This can be used to identify similar groups of genomes based on their amino acid composition.

## Alignment/N_W_Alignment.java (Needleman-Wunch)

The N-W_Alignment.java file provides an implementation of the Needleman-Wunch algorithm, which performs global sequence alignment. It can be used to align sequences of DNA, RNA, or protein. This algorithm is useful for identifying similarities and differences between sequences and finding conserved regions.

## Motif_finding/find_motif.java (Position Specific Scoring Matrix)

The find_motif.java file implements the position specific scoring matrix method (PSSM), which is a supervised motif finding method for TF binding sites. It can be used to identify potential transcription factor binding sites in DNA sequences. The PSSM approach considers the position-specific frequencies of each nucleotide or amino acid in known binding sites.

## GibbsSample/gibbs_sampler.java.java (Gibbs Sampler for Motif Finding)

The gibbs_sampler.java file implements the Gibbs Sampler algorithm for motif finding. This method is an unsupervised approach that samples DNA sequences to identify an optimal recurrent motif generated in a ChIP-Seq experiment. It returns information such as the motif window size, motif sequence, and motif position in each of the ChIP sequences.

Feel free to explore the code and use these implementations for your bioinformatics projects. If you have any questions or suggestions, please don't hesitate to contact me. Happy coding!
