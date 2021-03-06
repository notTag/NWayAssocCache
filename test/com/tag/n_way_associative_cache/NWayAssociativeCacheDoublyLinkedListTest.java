package com.tag.n_way_associative_cache;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.tag.exceptions.CacheSizeConstructionException;
import com.tag.replacement_algorithm.LRU;
import com.tag.replacement_algorithm.MRU;
import com.tag.replacement_algorithm.ReplacementAlgorithm;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author quantumDrop
 */
public class NWayAssociativeCacheDoublyLinkedListTest {
       private NWayAssociativeCache<Integer, Integer> myCache;
    private NWayAssociativeCache<Integer, Integer> defaultSizeCache;
    private NWayAssociativeCache<Integer, Integer> mySmallCache;
    private LRU lru;
    private MRU mru;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws CacheSizeConstructionException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        myCache = new NWayAssociativeCache<>(DoublyLinkedListSet.class, 4); //instantiating a 4-way associative cache
        defaultSizeCache = new NWayAssociativeCache<>(DoublyLinkedListSet.class, 4, 32); //instantiating a 4-way associative cache
        mySmallCache = new NWayAssociativeCache<>(DoublyLinkedListSet.class, 1,1);
        lru = new LRU();
        mru = new MRU();
    }
    
    @After
    public void tearDown() {
    }
    
        
    @Test(expected=CacheSizeConstructionException.class)
    public void NWayAssociativeCacheNWayGreaterThanDefaultCacheSize() throws CacheSizeConstructionException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
        NWayAssociativeCache<Integer,Integer> newCache = new NWayAssociativeCache<>(null, 35);
    }
    
    @Test(expected=CacheSizeConstructionException.class)
    public void NWayAssociativeCacheNWayGreaterThanCacheSize() throws CacheSizeConstructionException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
        NWayAssociativeCache<Integer,Integer> newCache = new NWayAssociativeCache<>(null, 500, 100);
    }
    
    @Test(expected=CacheSizeConstructionException.class)
    public void NWayAssociativeCacheNegativeInstation() throws CacheSizeConstructionException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
        NWayAssociativeCache<Integer,Integer> newCache = new NWayAssociativeCache<>(null, -1);
    }
    
    @Test
    public void NWayAssociativeCacheTestInitCache() throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
        myCache.initCache(null, this.myCache.getNumWays(), this.myCache.getRepAlg());
        for(CacheSet<Integer, Integer> set : myCache.getCache()){
            assertTrue(set != null);
        }
    }
    
    /*
     * Init with LRU replacement algorithm.
    */
    @Test
    public void NWayAssociativeCacheTestInitReplacementAlgorithmLRU(){
        myCache.setRepAlg(lru);
        assertTrue(myCache.getRepAlg() instanceof LRU);        
    }
    /*
     * Init with MRU replacement algorithm.
    */
    @Test
    public void NWayAssociativeCacheTestInitReplacementAlgorithmMRU(){
        myCache.setRepAlg(mru);
        assertTrue(myCache.getRepAlg() instanceof MRU);
    }
    /*
     * Init with URA (User defined) replacement algorithm.
     * Tests if what was added is a replacementAlgorithm.
    */
    @Test
    public void NWayAssociativeCacheTestInitReplacementAlgorithmURA(){
        myCache.setRepAlg(lru);
        assertTrue(myCache.getRepAlg() instanceof ReplacementAlgorithm);        
    }
    


    /**
     * Tests the NWayAssociativeCache "add" function
     */    
    @Test
    public void NWayAssociativeCacheTestAdd(){
        Integer key = 1, value = 10;
        myCache.add(key, value);
        
        assertTrue(Objects.equals(myCache.get(key), value));
    }
    
    /**
     * Tests the NWayAssociativeCache "get" function
     */
    @Test
    public void NWayAssociativeCacheTestGet(){
        //add to our cache utilizing LRU (by default). Get does not depend on the replacement algorithm used.
        myCache.add(1, 10);
        Integer key = 1, value = 10;
        //When key exists in cache return associated value
        assertEquals(value, myCache.get(key));
        //When key DNE in cache return expected to be null
        key = 2;
        assertEquals(null, myCache.get(key));
    }  
    
    @Test
    public void overwriteValueTest(){
        myCache.add(1, 1);
        assertTrue(Objects.equals(myCache.get(1), 1));
        myCache.add(1, 2);
        assertFalse(Objects.equals(myCache.get(1), 1));
        assertTrue(Objects.equals(myCache.get(1), 2));
    }
    /**
     * Fills a size 32 cache with 32 values
     */
    @Test
    public void fillCacheTest(){
        for(int i=0; i<32; i++){
            Integer value = i * 10;
            defaultSizeCache.add(i, value);
            
            assertTrue(Objects.equals(defaultSizeCache.get(i), value));
        }
    }
    /**
     * Fills a 32 filled cache (of size 32) with values (by replacement) for another 100 values.  
     */
    @Test
    public void fillAlreadyFullCacheTest(){
        for(int i=32; i<128; i++){
            Integer value = i * 10;
            defaultSizeCache.add(i, value);
            assertTrue(Objects.equals(defaultSizeCache.get(i), value));
        }
    }
    
}
