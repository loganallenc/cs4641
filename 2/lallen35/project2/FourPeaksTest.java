package project2;

import java.util.Arrays;

import dist.DiscreteDependencyTree;
import dist.DiscreteUniformDistribution;
import dist.Distribution;

import opt.DiscreteChangeOneNeighbor;
import opt.EvaluationFunction;
import opt.GenericHillClimbingProblem;
import opt.HillClimbingProblem;
import opt.NeighborFunction;
import opt.RandomizedHillClimbing;
import opt.SimulatedAnnealing;
import opt.example.*;
import opt.ga.CrossoverFunction;
import opt.ga.DiscreteChangeOneMutation;
import opt.ga.SingleCrossOver;
import opt.ga.GenericGeneticAlgorithmProblem;
import opt.ga.GeneticAlgorithmProblem;
import opt.ga.MutationFunction;
import opt.ga.StandardGeneticAlgorithm;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;
import shared.Instance;

/**
 * A test using the 4 Peaks evaluation function
 * @author James Liu
 * @version 1.0
 */
public class FourPeaksTest {

    public static void main(String[] args) {
        if(args.length < 3)
        {
            System.out.println("Provide a input size, the location of T, and the repeat count");
            System.exit(0);
        }
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        if(T > N || N < 0 || T < 0)
        {
            System.out.println("T cannot be greater than N or less than 0. N cannot be negaitve.");
            System.exit(0);
        }
        int iterations = Integer.parseInt(args[2]);
        int[] ranges = new int[N];
        Arrays.fill(ranges, 2);
        EvaluationFunction ef = new FourPeaksEvaluationFunction(T);
        Distribution odd = new DiscreteUniformDistribution(ranges);
        NeighborFunction nf = new DiscreteChangeOneNeighbor(ranges);
        MutationFunction mf = new DiscreteChangeOneMutation(ranges);
        CrossoverFunction cf = new SingleCrossOver();
        Distribution df = new DiscreteDependencyTree(.1, ranges);
        HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
        GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
        ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);

        double avg = 0.0;
        double best = 0.0;

        System.out.println("\nRandomized Hill Climbing\n---------------------------------");
        System.out.println("RHC: avg, best");
        for(int i = 0; i < iterations; i++)
        {
            RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);
            long t = System.nanoTime();
            FixedIterationTrainer fit = new FixedIterationTrainer(rhc, 20000);
            fit.train();
            //System.out.println(ef.value(rhc.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
            avg = avg + ef.value(rhc.getOptimal());
            if (ef.value(rhc.getOptimal()) > best) {
              best = ef.value(rhc.getOptimal());
            }
        }
        avg = avg / iterations;
        System.out.println(avg + "," + best);

        System.out.println("SA: temp, cool, avg, best");
        for (double temp = 1E10; temp < 1E15; temp *= 10) {
          for (int cool = 80; cool < 100; cool+=5) {
            avg = 0.0;
            best = 0.0;
            for(int i = 0; i < iterations; i++)
            {
                SimulatedAnnealing sa = new SimulatedAnnealing(1E12, ((double) (cool)/100.0), hcp);
                long t = System.nanoTime();
                FixedIterationTrainer fit = new FixedIterationTrainer(sa, 20000);
                fit.train();
                avg = avg + ef.value(sa.getOptimal());
                if (ef.value(sa.getOptimal()) > best) {
                  best = ef.value(sa.getOptimal());
                }
                // System.out.println(ef.value(sa.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
            }
            avg = avg / iterations;

            System.out.println(temp + "," + ((double) (cool)/100.0) + "," + avg + "," + best);
          }
        }


        System.out.println("\nGenetic Algorithm\n---------------------------------");
        System.out.println("GA,MA,MU,avg,best");
        for (int ma = 10; ma < 70; ma += 10) {
          for (int mu = 3; mu < 18; mu+=3) {
            avg = 0.0;
            best = 0.0;

            for(int i = 0; i < iterations; i++)
            {
                StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(200, ma, mu, gap);
                long t = System.nanoTime();
                FixedIterationTrainer fit = new FixedIterationTrainer(ga, 1000);
                fit.train();
                avg = avg + ef.value(ga.getOptimal());
                if (ef.value(ga.getOptimal()) > best) {
                  best = ef.value(ga.getOptimal());
                }
                // System.out.println(ef.value(ga.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
            }

            avg = avg / iterations;
            System.out.println(String.valueOf(ma) + "," + String.valueOf(mu) + "," + avg + "," + best);

          }
        }

        System.out.println("\nMIMIC \n---------------------------------");
        System.out.println("MI,samples,keep,avg,best");
        for (int samples = 100; samples < 300; samples +=50) {
          for (int toKeep = 20; toKeep < 100; toKeep +=20) {
            avg = 0.0;
            best = 0.0;

            for(int i = 0; i < iterations; i++)
            {
                MIMIC mimic = new MIMIC(samples, toKeep, pop);
                long t = System.nanoTime();
                FixedIterationTrainer fit = new FixedIterationTrainer(mimic, 1000);
                fit.train();
                avg = avg + ef.value(mimic.getOptimal());
                if (ef.value(mimic.getOptimal()) > best) {
                  best = ef.value(mimic.getOptimal());
                }
                // System.out.println(ef.value(mimic.getOptimal()) + ", " + (((double)(System.nanoTime() - t))/ 1e9d));
            }

            avg = avg / iterations;
            System.out.println(String.valueOf(samples) + "," + String.valueOf(toKeep) + "," + avg + "," + best);

          }
        }
    }
}
