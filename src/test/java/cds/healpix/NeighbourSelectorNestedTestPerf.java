// Copyright 2017-2018 - Universite de Strasbourg/CNRS
// The CDS HEALPix library is developped by the Centre de Donnees
// astronomiques de Strasbourgs (CDS) from the following external papers:
//  - [Gorsky2005]     - "HEALPix: A Framework for High-Resolution Discretization and
//                       Fast Analysis of Data Distributed on the Sphere"
//                       http://adsabs.harvard.edu/abs/2005ApJ...622..759G
//  - [Calabretta2004] - "Mapping on the HEALPix grid"
//                       http://adsabs.harvard.edu/abs/2004astro.ph.12607C
//  - [Calabretta2007] - "Mapping on the HEALPix grid"
//                       http://adsabs.harvard.edu/abs/2007MNRAS.381..865C
//  - [Reinecke2015]   - "Efficient data structures for masks on 2D grids"
//                       http://adsabs.harvard.edu/abs/2015A&A...580A.132R
// It is distributed under the terms of the BSD License 2.0
//
// This file is part of the CDS HEALPix library.
//

package cds.healpix;

import java.util.Arrays;

import org.junit.Test;

import cds.healpix.FlatHashList;
import cds.healpix.Healpix;
import cds.healpix.HealpixNested;
import cds.healpix.HealpixNestedFast;
import cds.healpix.ListOfHash;
import cds.healpix.NeighbourList;
import cds.healpix.NeighbourSelector;
import cds.healpix.fillingcurve.FillingCurve2DType;

public class NeighbourSelectorNestedTestPerf {

  @Test
  public void neighboursTestPerf() throws Exception {
    final int depth = 12;
    final HealpixNested hn = new HealpixNested(depth, FillingCurve2DType.Z_ORDER_LUPT);
    final FlatHashList fhl = new FlatHashList(depth, 8);
    NeighbourSelector ns = hn.newNeighbourSelector();
    long sum = 0;
    long l1 = System.currentTimeMillis();
    for (long i = 0; i < Healpix.nHash(depth); i++) {
      ns.neighbours(i, fhl);
      sum += fhl.get(5);
    }
    long l2 = System.currentTimeMillis();
    System.out.println("Sum: " + sum + "; in " + (l2 - l1) + " ms. Compute " + Healpix.nHash(depth) + " x 8 neighbours (reuse same list)");
    l1 = System.currentTimeMillis();
    for (long i = 0; i < Healpix.nHash(depth); i++) {
      ns.neighbours(i, fhl);
      sum += fhl.get(5);
    }
    l2 = System.currentTimeMillis();
    System.out.println("Sum: " + sum + "; in " + (l2 - l1) + " ms" + Healpix.nHash(depth) + " x 8 neighbours (reuse same list)");
    l1 = System.currentTimeMillis();
    for (long i = 0; i < Healpix.nHash(depth); i++) {
      ns.neighbours(i, fhl);
      sum += fhl.get(5);
    }
    l2 = System.currentTimeMillis();
    System.out.println("Sum: " + sum + "; in " + (l2 - l1) + " ms" + Healpix.nHash(depth) + " x 8 neighbours (reuse same list)");
    // 3500 ms
  }
  
  @Test
  public void neighboursTestPerfBis() throws Exception {
    final int depth = 12;
    final HealpixNested hn = new HealpixNested(depth, FillingCurve2DType.Z_ORDER_LUPT);
    NeighbourSelector ns = hn.newNeighbourSelector();
    long sum = 0;
    long l1 = System.currentTimeMillis();
    for (long i = 0; i < Healpix.nHash(depth); i++) {
      final ListOfHash fhl = ns.neighbours(i);
      sum += fhl.get(5);
    }
    long l2 = System.currentTimeMillis();
    System.out.println("Sum: " + sum + "; in " + (l2 - l1) + " ms. Compute " + Healpix.nHash(depth) + " x 8 neighbours (create new list)");
    l1 = System.currentTimeMillis();
    for (long i = 0; i < Healpix.nHash(depth); i++) {
      final ListOfHash fhl = ns.neighbours(i);
      sum += fhl.get(5);
    }
    l2 = System.currentTimeMillis();
    System.out.println("Sum: " + sum + "; in " + (l2 - l1) + " ms. Compute " + Healpix.nHash(depth) + " x 8 neighbours (create new list)");
    l1 = System.currentTimeMillis();
    for (long i = 0; i < Healpix.nHash(depth); i++) {
      final ListOfHash fhl = ns.neighbours(i);
      sum += fhl.get(5);
    }
    l2 = System.currentTimeMillis();
    System.out.println("Sum: " + sum + "; in " + (l2 - l1) + " ms. Compute " + Healpix.nHash(depth) + " x 8 neighbours (create new list)");
    // 5700 ms
  }
  
}
